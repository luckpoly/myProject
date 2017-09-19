package com.brandsh.tiaoshi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * toast事件
     *
     * @param msg
     */
    private Toast mToast = null;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * 当前Fragment的可见状态
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }
    // 视图完全显示出来时候调用
    protected void onVisible() {
    }

    // 视图完全消失时候调用
    protected void onInvisible() {
    }
}
