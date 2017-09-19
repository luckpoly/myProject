package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.application.TiaoshiApplication;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.FenxiangModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.QRCodeUtil;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FenxiangActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.tv_bianji)
    private TextView tv_bianji;
    @ViewInject(R.id.my_fenxiang_rlBack)
    private RelativeLayout my_fenxiang_rlBack;
    @ViewInject(R.id.tv_no)
    private TextView tv_no;
    @ViewInject(R.id.tv_wanfa)
    private TextView tv_wanfa;
    @ViewInject(R.id.tv_fenxiang)
    private TextView tv_fenxiang;
    @ViewInject(R.id.iv_two_img)
    private ImageView iv_two_img;
    @ViewInject(R.id.iv_tishi)
    private ImageView iv_tishi;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxiang);
        AppUtil.Setbar(this);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        if (getSharedPreferences(G.SP.APP_NAME, MODE_PRIVATE).getString("isfarst_fx", "YES").equals("YES")) {
            iv_tishi.setVisibility(View.VISIBLE);
        }
        my_fenxiang_rlBack.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
        tv_wanfa.setOnClickListener(this);
        tv_fenxiang.setOnClickListener(this);
        iv_tishi.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
    }
    private void initData() {
        HashMap map = new HashMap();
        map.put("token", TiaoshiApplication.globalToken);
        map.put("orderId", getIntent().getStringExtra("id"));
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        Log.e("-----", sign);
        OkHttpManager.postAsync(G.Host.GET_GOODS_CODE, map, new MyCallBack(1, this, new FenxiangModel(), handler));
    }
    private void creayTwoCode(final String data, final ImageView view) {
        filePath = getFileRoot(FenxiangActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        Log.e("-----", filePath);
        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(data, 800, 800,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.logo),
                        filePath);

                if (success) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    FenxiangModel fenxiangModel = (FenxiangModel) msg.obj;
                    if ("SUCCESS".equals(fenxiangModel.getRespCode()) && null != fenxiangModel.getData()) {
                        tv_no.setText(fenxiangModel.getData().getGetCode());
                        creayTwoCode(fenxiangModel.getData().getGetUrl(), iv_two_img);
                    }
                    break;
            }
        }
    };
    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }
    public void SharePhoto(String photoUri, final Activity activity) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(photoUri);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String code = data.getStringExtra("code");
            String url = data.getStringExtra("url");
            tv_no.setText(code);
            creayTwoCode(url, iv_two_img);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_fenxiang_rlBack:
                finish();
                break;
            case R.id.tv_wanfa:
                startActivity(new Intent(FenxiangActivity.this, AboutUsSMActivity.class).putExtra("type", "玩法").putExtra("url", "APPSharePlay"));
                break;
            case R.id.tv_fenxiang:
                SharePhoto(filePath, this);
                break;
            case R.id.iv_tishi:
                iv_tishi.setVisibility(View.GONE);
                SharedPreferences.Editor editor = getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE).edit();
                editor.putString("isfarst_fx", "NO");
                editor.commit();
                break;
            case R.id.tv_bianji:
                Intent intent = new Intent(FenxiangActivity.this, BianjiActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("etcode", tv_no.getText().toString());
                startActivityForResult(intent, 1);
                break;
        }
    }
}
