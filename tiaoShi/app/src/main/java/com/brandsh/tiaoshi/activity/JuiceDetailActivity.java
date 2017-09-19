package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.brandsh.tiaoshi.R;
/**
 * Created by tiashiwang on 2016/5/23.
 */
public class JuiceDetailActivity extends Activity {
  private  PopupWindow pop;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_juice_detail);
    }
    public void showPop() {
        // 加载自定义布局
      View view = LayoutInflater.from(this).inflate(R.layout.layout_share_popup, null);
        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.findViewById(R.id.tv_llq_popuptext).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pop.dismiss();
            }
        });
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
//        startGetWebData();
         pop.showAtLocation(view, 0, 0, 0);
    }
}
