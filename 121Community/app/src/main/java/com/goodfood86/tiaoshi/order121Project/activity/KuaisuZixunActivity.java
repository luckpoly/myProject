package com.goodfood86.tiaoshi.order121Project.activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.PhotoRecyclerViewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.QiLiuUtils;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StringUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.utils.XCallbackListener;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/12/15.
 */

public class KuaisuZixunActivity extends FragmentActivity implements View.OnClickListener{
    @ViewInject(R.id.nav_back)
    ImageView nav_back;
    @ViewInject(R.id.nav_title)
    TextView nav_title;
    @ViewInject(R.id.et_name)
    TextView et_name;
    @ViewInject(R.id.et_phone)
    TextView et_phone;
    @ViewInject(R.id.et_content)
    TextView et_content;
    @ViewInject(R.id.tv_fabu)
    TextView tv_fabu;
    RecyclerView mRecyclerAddPhoto;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    FunctionConfig functionConfig;
    private List<PhotoInfo> mPhotoList;
    PhotoRecyclerViewAdapter mPhotoRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    private  String imgs="";
    private  int i=0;
    private int imgsId=0;
    private List<String> imgList=new ArrayList<>();
    private ProgressHUD dialog;
    String name;
    String phone;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaisu_zixun);
        ViewUtils.inject(this);
        initView();
        initListener();
    }
    private void initView() {
        nav_title.setText("快速咨询");
        mRecyclerAddPhoto = (RecyclerView) findViewById(R.id.my_recycler_addphoto);

        mPhotoList = new ArrayList<>();
        mPhotoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(KuaisuZixunActivity.this,getSupportFragmentManager(), mPhotoList, new XCallbackListener() {
            @Override
            protected void callback(Object... obj) {
                int pos = Integer.parseInt(obj[0].toString());
            }
        },"zixun");
        //设置水平布局
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerAddPhoto.setLayoutManager(mLayoutManager);
        mRecyclerAddPhoto.setAdapter(mPhotoRecyclerViewAdapter);
    }
    private void initListener() {
        nav_back.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
    }
    private void initData() {
        if (!Order121Application.isLogin()){
            startActivity(new Intent(this,LoginActivity.class));
            return;
        }


         name=et_name.getText().toString();
         phone= et_phone.getText().toString();
        content=et_content.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort(this,"姓名不能为空");
            return;
        }else if (TextUtils.isEmpty(phone)){
            ToastUtil.showShort(this,"电话不能为空");
            return;
        }else if (phone.length()!=11){
            ToastUtil.showShort(this,"请输入正确的手机号码");
            return;
        }else if (TextUtils.isEmpty(content)){
            ToastUtil.showShort(this,"内容不能为空");
            return;
        }
        dialog = ProgressHUD.show(this,"正在提交，请耐心等候",false,null);
        dialog.show();
        if (mPhotoList.size()==0){
            sendData();
            return;
        }
        if (mPhotoList.size()!=0){
            String  picUri=  mPhotoList.get(0).getPhotoPath();
            Log.e("-------", mPhotoList.get(0).getPhotoPath());
            QiLiuUtils.updateQL(picUri,this,handler,2);
            imgsId=1;
        }

    }
    private void sendData(){
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        map.put("name",name);
        map.put("phone",phone);
        map.put("intro",content);
        map.put("imgs",imgs);
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        LogUtils.e(sign);
        OkHttpManager.postAsync(G.Host.CREATE_ZIXUN,map,new MyCallBack(1,this, new CreateActivityModel(),handler));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.tv_fabu:
                imgList.clear();
               initData();

                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                   CreateActivityModel createActivityModel= (CreateActivityModel) msg.obj;
                    dialog.dismiss();
                    if (null!=createActivityModel&&"SUCCESS".equals(createActivityModel.getRespCode())){
                        ToastUtil.showShort(KuaisuZixunActivity.this,"发布成功");
                        et_name.setText("");
                        et_content.setText("");
                        et_phone.setText("");
                        mPhotoList.clear();
                        mPhotoRecyclerViewAdapter.notifyDataSetChanged();
                    }else {
                        ToastUtil.showShort(KuaisuZixunActivity.this,createActivityModel.getRespMsg());
                    }
                    break;
                case 2:
                    String imgURL= (String) msg.obj;
                    imgList.add(imgURL);
                    if (imgList.size()==mPhotoList.size()){
                        StringBuffer buffer =new StringBuffer();
                        for (int i=0;i<imgList.size();i++){
                            buffer.append(imgList.get(i)+",");
                        }
                        imgs=  buffer.substring(0,buffer.length()-1);
                        Log.e("00000000",imgs);
                        sendData();
                    } else {
                        String  picUri=  mPhotoList.get(imgsId).getPhotoPath();
                        Log.e("-------", mPhotoList.get(imgsId).getPhotoPath());
                        QiLiuUtils.updateQL(picUri,KuaisuZixunActivity.this,handler,2);
                        imgsId=imgsId+1;
                    }
                    break;
            }
        }
    };
    /**
     * 打开相机和相册功能
     */
    public void openChoiceDialog(Context context, FragmentManager fragmentManager) {
        final Dialog photoDialog = new Dialog(context,
                R.style.dialog_style);
        photoDialog.setContentView(R.layout.takephoto_dialog);
        Window win = photoDialog.getWindow();
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        win.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        photoDialog.setCanceledOnTouchOutside(true);
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setSelected(mPhotoList)//添加过滤集合
                .setMutiSelectMaxSize(9)
                .build();
        photoDialog.findViewById(R.id.fromphoto).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //配置功能

                        i=1;
                        // 打开相册
                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY,functionConfig, mOnHanlderResultCallback);

                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.takephoto).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i=2;
                        //打开拍照
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                        photoDialog.dismiss();
                    }
                });

        photoDialog.findViewById(R.id.btn_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消
                        photoDialog.dismiss();
                    }
                });

        photoDialog.show();

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
            if (resultList != null) {
//                ImageLoader.getInstance().displayImage("file://"+resultList.get(0).getPhotoPath(),img);
//                Drawable defaultDrawable = getResources().getDrawable(cn.finalteam.galleryfinal.R.drawable.ic_gf_default_photo);
//                GalleryFinal.getCoreConfig().getImageLoader().displayImage(MainActivity.this, resultList.get(0).getPhotoPath(), (GFImageView) img, defaultDrawable, 100, 100);
                if (i==1){
                    mPhotoList.clear();
                }
                mPhotoList.addAll(resultList);
                mPhotoRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };
}
