package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.UploadImgAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddEvaluationJsonData;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.QiLiuUtils;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.TakePhotoUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.UploadImgGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.iv_add_img)
    private ImageView iv_add_img;
    @ViewInject(R.id.gv_add_img)
    UploadImgGridView gv_add_img;
    @ViewInject(R.id.et_addevaluation)
    private EditText et_addevaluation;
    @ViewInject(R.id.btn_add_evaluation)
    private Button btn_add_evaluation;
    private HashMap requestMap;
    private List<String> base64List= new LinkedList<>();
    private List<Bitmap> imgList = new LinkedList<>();
    private UploadImgAdapter uploadImgAdapter;
    private AlertDialog.Builder builder;
    private ShapeLoadingDialog dialog;
    private ProgressDialog progDialog;
    private Bitmap currentbitmap;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ViewUtils.inject(this);
        initView();
        initListener();
        AppUtil.Setbar(this);
    }

    private void initView() {
        nav_title.setText("意见反馈");
        dialog= ProgressHUD.show(this,"上传图片中");
        builder = new AlertDialog.Builder(this).setTitle("提交").setMessage("确定提交反馈?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                httpRequest();
            }
        }).setNegativeButton("取消", null);

    }

    private void initListener() {
        nav_back.setVisibility(View.VISIBLE);
        nav_back.setOnClickListener(this);
        iv_add_img.setOnClickListener(this);
        btn_add_evaluation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.iv_add_img:
                TakePhotoUtil.showDialog(this);
                break;
            case R.id.btn_add_evaluation:
                if (TextUtils.isEmpty(et_addevaluation.getText().toString())) {
                   shortToast("内容不能为空");
                } else {
                    content = et_addevaluation.getText().toString();
                }
                builder.create().show();
                break;
        }
    }
    private void httpRequest() {
        requestMap=new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("idea", content);
        String url="";
        for (int i = 0; i < base64List.size(); i++) {
            if (i!=base64List.size()-1){
                url=url+base64List.get(i)+",";
            }else {
                url=url+base64List.get(i);
            }
        }
        requestMap.put("imgs",url);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        showProgressDialog();
        OkHttpManager.postAsync(G.Host.FEEDBACK, requestMap, new MyCallBack(1, this, new AddEvaluationJsonData(), handler));

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    AddEvaluationJsonData addEvaluationJsonData = (AddEvaluationJsonData) msg.obj;
                    dissmissProgressDialog();
                    if (addEvaluationJsonData != null) {
                        if ("SUCCESS".equals(addEvaluationJsonData.getRespCode())) {
                           shortToast("反馈提交成功");
                            finish();
                        } else {
                           shortToast(addEvaluationJsonData.getRespMsg());
                        }
                    }
                    break;
                case 2:
                    dialog.dismiss();
                    String imgURL= (String) msg.obj;
                    imgList.add(currentbitmap);
                    base64List.add(imgURL);
                    Log.e("=========",imgURL);
                    uploadImgAdapter = new UploadImgAdapter(imgList, FeedbackActivity.this, base64List, iv_add_img);
                    gv_add_img.setAdapter(uploadImgAdapter);
                    uploadImgAdapter.notifyDataSetChanged();

                    if (imgList.size() == 5 && base64List.size() == 5) {
                        iv_add_img.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在提交评论信息");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        currentbitmap = TakePhotoUtil.dealActivityResult(this, requestCode, resultCode, data, true);

        if (currentbitmap == null) {
            return;
        }
        String picUri=TakePhotoUtil.picPath;
        Log.e("-----",picUri);
        QiLiuUtils.updateQL(picUri,this,handler,2);
        dialog.show();
    }
}
