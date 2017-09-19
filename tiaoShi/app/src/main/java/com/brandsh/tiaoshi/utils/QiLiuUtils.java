package com.brandsh.tiaoshi.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.brandsh.tiaoshi.constant.G;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.AsyncRun;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/19.
 */
public class QiLiuUtils {
    private Context mContext;
    private String mPicUri;
    private Handler handler;
    private static QiLiuUtils qiLiuUtils;
    private int what;

    public static QiLiuUtils getInstance() {
        if (qiLiuUtils == null) {
            qiLiuUtils = new QiLiuUtils();
        }
        return qiLiuUtils;
    }
    public static void updateQL(String picUri, Context context,Handler handler,int what){
        qiLiuUtils=QiLiuUtils.getInstance();
        qiLiuUtils.uploadFile(picUri,context,handler,what);
    }

    public  void uploadFile(String picUri, Context context,Handler handler,int what) {

        if (picUri == null) {
            return;
        }
        this.mContext=context;
        this.mPicUri=picUri;
        this.handler=handler;
        this.what=what;
        //从业务服务器获取上传凭证
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClient httpClient = new OkHttpClient();
                HashMap<String,String> hashMap=new HashMap<>();
                FormBody.Builder builder = new FormBody.Builder();
                String random=SignUtil.getRandom();
                String currentTime=System.currentTimeMillis() / 1000 + "";
                builder.add("actReq", random);
                hashMap.put("actReq", random);
                builder.add("actTime",currentTime);
                hashMap.put("actTime",currentTime);
                String sign = SignUtil.getSign(hashMap);
                builder.add("sign", Md5.toMd5(sign));
                Request req = new Request.Builder().url(G.Host.GET_TOKEN).post(builder.build()).build();
                Response resp = null;
                try {

                    resp = httpClient.newCall(req).execute();
                    String result=resp.body().string();
                    Log.e("-------------",result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    String uploadToken = jsonObject1.getString("token");
                    String domain=jsonObject1.getString("domain");
                    Log.e("uploadToken",uploadToken);
                    uploadPic(uploadToken,mPicUri,domain);
                } catch (IOException e) {
                    AsyncRun.run(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    mContext,
                                    "申请上传凭证失败!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (JSONException e) {
                } finally {
                    if (resp != null) {
                        resp.body().close();
                    }
                }
            }
        }).start();
    }
    private void uploadPic(String uploadToken, String picUri, final String domain){
        String picPath=picUri;
        String key=System.currentTimeMillis()+".jpg";
        UploadManager uploadManager=new UploadManager();
        uploadManager.put(new File(picPath), key, uploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String s, com.qiniu.android.http.ResponseInfo responseInfo, JSONObject jsonObject) {
                Log.e("--",s+"  "+responseInfo);
                String url=domain+s;
                Message message=new Message();
                message.obj=url;
                message.what=what;
                handler.sendMessage(message);
            }
        },null);
    }
}
