package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by 习惯 on 15/9/1.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * toast事件
     *
     * @param msg
     */
    private Toast mToast = null;
    public void  shortToast(String msg){
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
    public void  longToast(String msg){
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }


}
