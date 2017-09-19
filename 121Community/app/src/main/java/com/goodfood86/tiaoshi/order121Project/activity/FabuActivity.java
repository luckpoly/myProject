package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.adapter.PhotoRecyclerViewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CreateActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GroupUserQueryModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.utils.DateTimePickDialogUtil;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.QiLiuUtils;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StatusBarUtil;
import com.goodfood86.tiaoshi.order121Project.utils.StringUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.utils.XCallbackListener;
import com.goodfood86.tiaoshi.order121Project.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FabuActivity extends FragmentActivity implements View.OnClickListener{
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
    private String initEndDateTime = "2016年8月8日 08:08"; // 初始化结束时间
    private List<String> imgList=new ArrayList<>();
    private ProgressHUD dialog;


    @ViewInject(R.id.nav_back)
    private ImageView nav_back;
    @ViewInject(R.id.nav_title)
    private TextView nav_title;
    @ViewInject(R.id.tv_phone_type)
    private TextView tv_phone_type;
    @ViewInject(R.id.inputDate)
    private TextView activityTime;
    @ViewInject(R.id.inputDate1)
    private TextView startDateTime;
    @ViewInject(R.id.inputDate2)
    private TextView endDateTime;
    @ViewInject(R.id.ll_select_phonetype)
    private LinearLayout ll_select_phonetype;
    @ViewInject(R.id.et_act_name)
    private EditText et_act_name;
    @ViewInject(R.id.et_act_money)
    private EditText et_act_money;
    @ViewInject(R.id.et_act_pNo)
    private EditText et_act_pNo;
    @ViewInject(R.id.et_act_content)
    private EditText et_act_content;
    @ViewInject(R.id.et_link_phone)
    private EditText et_link_phone;
 @ViewInject(R.id.tv_fabu)
    private TextView tv_fabu;
    private String capita;
    private String maxPeople;
    private String content;
    private String startTime;
    private String applyTime;
    private String endTime;
    private String name;
    CreateActivityModel createActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu);
        initView();
    }
    private void initView() {
        ViewUtils.inject(this);
        initEndDateTime= formatDate(System.currentTimeMillis()+"") ;
        Log.e("1111111111","_"+initEndDateTime+"-");
        nav_back.setOnClickListener(this);
        ll_select_phonetype.setOnClickListener(this);
        startDateTime.setOnClickListener(this);
        endDateTime.setOnClickListener(this);
        activityTime.setOnClickListener(this);
        tv_fabu.setOnClickListener(this);
        nav_title.setText("活动发布");
        mRecyclerAddPhoto = (RecyclerView) findViewById(R.id.my_recycler_addphoto);

        mPhotoList = new ArrayList<>();
        mPhotoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(FabuActivity.this,getSupportFragmentManager(), mPhotoList, new XCallbackListener() {
            @Override
            protected void callback(Object... obj) {
                int pos = Integer.parseInt(obj[0].toString());
            }
        });


        //设置水平布局
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerAddPhoto.setLayoutManager(mLayoutManager);
        mRecyclerAddPhoto.setAdapter(mPhotoRecyclerViewAdapter);
    }
    private String formatDate(String str){
        long seconds = Long.parseLong(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        return simpleDateFormat.format(gc.getTime());
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
            case R.id.ll_select_phonetype:
                showPopup(v);
                break;
            case R.id.inputDate1:
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        FabuActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);
                break;
            case R.id.inputDate2:
                DateTimePickDialogUtil dateTimePicKDialog1 = new DateTimePickDialogUtil(
                        FabuActivity.this, initEndDateTime);
                dateTimePicKDialog1.dateTimePicKDialog(endDateTime);
                break;
            case R.id.inputDate:
                DateTimePickDialogUtil dateTimePicKDialog2 = new DateTimePickDialogUtil(
                        FabuActivity.this, initEndDateTime);
                dateTimePicKDialog2.dateTimePicKDialog(activityTime);
                break;
            case R.id.tv_fabu:
                imgList.clear();
                isDataOk();
                dialog = ProgressHUD.show(this,"发布活动中，请耐心等候",false,null);
                dialog.show();

                break;
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    createActivityModel= (CreateActivityModel) msg.obj;
                    dialog.dismiss();
                    if (null!=createActivityModel&&"SUCCESS".equals(createActivityModel.getRespCode())){
                        creayGroup();
                    }else {
                        ToastUtil.showShort(FabuActivity.this,createActivityModel.getRespMsg());
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
                        QiLiuUtils.updateQL(picUri,FabuActivity.this,handler,2);
                        imgsId=imgsId+1;
                    }
                    break;
                case 4:
                    GroupUserQueryModel model= (GroupUserQueryModel) msg.obj;
                    if (model.getCode()==200){
                        initUserGroup();
                    }
                    break;
                case 3:
                    GroupUserQueryModel model1= (GroupUserQueryModel) msg.obj;
                    if (model1.getCode()==200){
                        ToastUtil.showShort(FabuActivity.this,"发布成功！");
                       finish();
                    }
                    break;
            }
        }
    };
    private void creayGroup(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<NameValuePair> nameValuePair = new ArrayList<>();
                nameValuePair.add(new BasicNameValuePair("groupId",createActivityModel.getData().getCustomActivityId()+""));
                nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
                nameValuePair.add(new BasicNameValuePair("groupName",createActivityModel.getData().getCustomActivityName()));
                RongHttp.rPostHttp("group/create.json",nameValuePair,new GroupUserQueryModel(),handler,4,FabuActivity.this);
            }
        }.start();
    }
    private void initUserGroup(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<NameValuePair> nameValuePair = new ArrayList<>();
                nameValuePair.add(new BasicNameValuePair("groupId",createActivityModel.getData().getCustomActivityId()+""));
                nameValuePair.add(new BasicNameValuePair("userId",Order121Application.globalLoginModel.getData().getPhone()));
                nameValuePair.add(new BasicNameValuePair("groupName",createActivityModel.getData().getCustomActivityName()));
                RongHttp.rPostHttp("group/join.json",nameValuePair,new GroupUserQueryModel(),handler,3,FabuActivity.this);
            }
        }.start();
    }
    private void isDataOk(){
        name=et_act_name.getText().toString();
        capita=et_act_money.getText().toString();
        maxPeople=et_act_pNo.getText().toString();
        content=et_act_content.getText().toString();
        startTime=activityTime.getText().toString().replaceAll("年","-").replaceAll("月","-").replaceAll("日","")+":00";
        applyTime=startDateTime.getText().toString().replaceAll("年","-").replaceAll("月","-").replaceAll("日","")+":00";
        endTime=endDateTime.getText().toString().replaceAll("年","-").replaceAll("月","-").replaceAll("日","")+":00";
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort(FabuActivity.this,"活动名称不能为空");
            return;
        }else if (TextUtils.isEmpty(capita)){
            ToastUtil.showShort(FabuActivity.this,"活动费用不能为空");
            return;
        }else if(TextUtils.isEmpty(maxPeople)){
            ToastUtil.showShort(FabuActivity.this,"活动人数不能为空");
            return;
        }else if(TextUtils.isEmpty(content)){
            ToastUtil.showShort(FabuActivity.this,"活动内容不能为空");
            return;
        }else if (TextUtils.isEmpty(startTime)){
            ToastUtil.showShort(FabuActivity.this,"开始时间不能为空");
            return;
        }
        else if (TextUtils.isEmpty(applyTime)){
            ToastUtil.showShort(FabuActivity.this,"报名时间不能为空");
            return;
        }
        else if (TextUtils.isEmpty(endTime)){
            ToastUtil.showShort(FabuActivity.this,"报名截止时间不能为空");
            return;
        }
        if (!StringUtil. isOK(startTime,applyTime,endTime,this)){
            return;
        }
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
    private  void  sendData(){

        String omPhone="";
        String omQq="";
        String omWeixin="";
        switch (tv_phone_type.getText().toString()){
            case "手机":
                omPhone=et_link_phone.getText().toString();
                break;
            case "QQ":
                omQq=et_link_phone.getText().toString();
                break;
            case "微信":
                omWeixin=et_link_phone.getText().toString();
                break;
        }
        if ((omPhone+omQq+omWeixin).length()<1){
            ToastUtil.showShort(FabuActivity.this,"联系方式不能为空");
            return;
        }
        HashMap map=new HashMap();
        map.put("token", Order121Application.globalLoginModel.getData().getToken());
        map.put("name",name);
        map.put("capita",capita+"00");
        map.put("maxPeople",maxPeople);
        map.put("content",content);
        map.put("startTime",startTime);
        map.put("applyTime",applyTime);
        map.put("endTime",endTime);
        map.put("omPhone",omPhone);
        map.put("omQq",omQq);
        map.put("omWeixin",omWeixin);
        map.put("imgs",imgs);
        map.put("actReq",SignUtil.getRandom());
        map.put("actTime",System.currentTimeMillis()/1000+"");
        String sign= SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.CREATE_ACTIVITY,map,new MyCallBack(1, this, new CreateActivityModel(), handler));
    }
    private PopupWindow mPopupWindow;
    private void showPopup(View view){
        View popupView = getLayoutInflater().inflate(R.layout.layout_phone_popupwindow, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(view);
        popupView.findViewById(R.id.tv_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone_type.setText("手机");
                mPopupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone_type.setText("QQ");
                mPopupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone_type.setText("微信");
                mPopupWindow.dismiss();
            }
        });

    }
}
