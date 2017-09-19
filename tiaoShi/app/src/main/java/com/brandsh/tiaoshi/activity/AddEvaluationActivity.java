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
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.adapter.OrderListItemAdapter;
import com.brandsh.tiaoshi.adapter.UploadImgAdapter;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.AddEvaluationJsonData;
import com.brandsh.tiaoshi.model.OrderListJsondata1;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.QiLiuUtils;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.TakePhotoUtil;
import com.brandsh.tiaoshi.widget.ProductDetailImgListView;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.brandsh.tiaoshi.widget.UploadImgGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sisi~ on 2016/3/12.
 */
public class AddEvaluationActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.product_detail_rlBack)
    RelativeLayout product_detail_rlBack;
    @ViewInject(R.id.tv_order_code)
    private TextView tv_order_code;
    @ViewInject(R.id.tv_store_name)
    private TextView tv_store_name;
    @ViewInject(R.id.order_product_PDILV)
    private ProductDetailImgListView order_product_PDILV;
    @ViewInject(R.id.tv_order_totalprice)
    private TextView tv_order_totalprice;
    @ViewInject(R.id.et_addevaluation)
    private EditText et_addevaluation;
    @ViewInject(R.id.btn_add_evaluation)
    private Button btn_add_evaluation;
    @ViewInject(R.id.gv_add_img)
    UploadImgGridView gv_add_img;
    @ViewInject(R.id.iv_add_evaluation_star1)
    private ImageView iv_add_evaluation_star1;
    @ViewInject(R.id.iv_add_evaluation_star2)
    private ImageView iv_add_evaluation_star2;
    @ViewInject(R.id.iv_add_evaluation_star3)
    private ImageView iv_add_evaluation_star3;
    @ViewInject(R.id.iv_add_evaluation_star4)
    private ImageView iv_add_evaluation_star4;
    @ViewInject(R.id.iv_add_evaluation_star5)
    private ImageView iv_add_evaluation_star5;
    @ViewInject(R.id.iv_add_img)
    private ImageView iv_add_img;


    private String content;
    private String path;

    private RequestParams requestParams;
    private HashMap requestMap;
    private OrderListJsondata1.DataBean.ListBean listEntity;
    private Bitmap currentbitmap;
    private List<Bitmap> imgList;
    private List<String> base64List;
    private UploadImgAdapter uploadImgAdapter;
    private AlertDialog.Builder builder;
    private int score;
    private OrderListItemAdapter orderListItemAdapter;
    private List<OrderListJsondata1.DataBean.ListBean.OrderGoodsBean> resList;
    private ProgressDialog progDialog;
    private ShapeLoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evaluation);
        //沉浸状态栏
        AppUtil.Setbar(this);
        ViewUtils.inject(this);
        initData();

    }

    private void initData() {
        dialog=ProgressHUD.show(this,"上传图片中");
        listEntity = (OrderListJsondata1.DataBean.ListBean) getIntent().getExtras().getSerializable("order_detail");
        product_detail_rlBack.setOnClickListener(this);
        btn_add_evaluation.setOnClickListener(this);
        iv_add_evaluation_star1.setOnClickListener(this);
        iv_add_evaluation_star2.setOnClickListener(this);
        iv_add_evaluation_star3.setOnClickListener(this);
        iv_add_evaluation_star4.setOnClickListener(this);
        iv_add_evaluation_star5.setOnClickListener(this);
        iv_add_img.setOnClickListener(this);
        builder = new AlertDialog.Builder(this).setTitle("提交评价").setMessage("确定提交评价?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                httpRequest();
            }
        }).setNegativeButton("取消", null);
        score = 5;

        imgList = new LinkedList<>();
        base64List = new LinkedList<>();
        requestParams = new RequestParams();
        tv_order_code.setText(listEntity.getOrderCode());
        tv_store_name.setText("商铺：" + listEntity.getShopName());
        int count = 0;
        for (int i = 0; i < listEntity.getOrderGoods().size(); i++) {
            count = count + Integer.parseInt(listEntity.getOrderGoods().get(i).getGoodsCount()+"");
        }
        tv_order_totalprice.setText("共 " + count + " 件,合计 ￥" + listEntity.getTotal() + "元");
        resList = listEntity.getOrderGoods();
        orderListItemAdapter = new OrderListItemAdapter(resList, this);
        order_product_PDILV.setAdapter(orderListItemAdapter);


    }

    private void httpRequest() {
        requestMap=new HashMap();
        requestMap.put("token", TiaoshiApplication.globalToken);
        requestMap.put("order_id", listEntity.getOrderId()+"");
        requestMap.put("shop_id", listEntity.getShopId()+"");
        requestMap.put("score", score + "");
        requestMap.put("description", content);
        String url="";
        for (int i = 0; i < base64List.size(); i++) {
            if (i!=base64List.size()-1){
                url=url+base64List.get(i)+",";
            }else {
                url=url+base64List.get(i);
            }
        }
        requestMap.put("img",url);
        requestMap.put("actReq", SignUtil.getRandom());
        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(requestMap);
        requestMap.put("sign", Md5.toMd5(sign));
        showProgressDialog();
        OkHttpManager.postAsync(G.Host.ADD_EVALUATION, requestMap, new MyCallBack(1, this, new AddEvaluationJsonData(), handler));

    }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_detail_rlBack:
                finish();
                break;
            case R.id.btn_add_evaluation:
                if (TextUtils.isEmpty(et_addevaluation.getText().toString())) {
                    content = "默认好评";
                } else {
                    content = et_addevaluation.getText().toString();
                }
                builder.create().show();
                break;
            case R.id.iv_add_evaluation_star1:
                iv_add_evaluation_star1.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star2.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star3.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star4.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star5.setImageResource(R.mipmap.rating_bar_normal);
                score = 1;
                break;
            case R.id.iv_add_evaluation_star2:
                iv_add_evaluation_star1.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star2.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star3.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star4.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star5.setImageResource(R.mipmap.rating_bar_normal);
                score = 2;
                break;
            case R.id.iv_add_evaluation_star3:
                iv_add_evaluation_star1.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star2.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star3.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star4.setImageResource(R.mipmap.rating_bar_normal);
                iv_add_evaluation_star5.setImageResource(R.mipmap.rating_bar_normal);
                score = 3;
                break;
            case R.id.iv_add_evaluation_star4:
                iv_add_evaluation_star1.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star2.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star3.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star4.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star5.setImageResource(R.mipmap.rating_bar_normal);
                score = 4;
                break;

            case R.id.iv_add_evaluation_star5:
                iv_add_evaluation_star1.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star2.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star3.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star4.setImageResource(R.mipmap.rating_bar_facus);
                iv_add_evaluation_star5.setImageResource(R.mipmap.rating_bar_facus);
                score = 5;
                break;
            case R.id.iv_add_img:
                TakePhotoUtil.showDialog(this);
                break;

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    AddEvaluationJsonData addEvaluationJsonData = (AddEvaluationJsonData) msg.obj;
                    dissmissProgressDialog();
                    if (addEvaluationJsonData != null) {
                        if ("SUCCESS".equals(addEvaluationJsonData.getRespCode())) {
                            Toast.makeText(AddEvaluationActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(AddEvaluationActivity.this, addEvaluationJsonData.getRespMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    dialog.dismiss();
                    String imgURL= (String) msg.obj;
                    imgList.add(currentbitmap);
                    base64List.add(imgURL);
                    Log.e("=========",imgURL);
                    uploadImgAdapter = new UploadImgAdapter(imgList, AddEvaluationActivity.this, base64List, iv_add_img);
                    gv_add_img.setAdapter(uploadImgAdapter);
                    uploadImgAdapter.notifyDataSetChanged();

                    if (imgList.size() == 5 && base64List.size() == 5) {
                        iv_add_img.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
