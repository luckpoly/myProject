package com.brandsh.tiaoshi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.utils.AppUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * FCActivity = FragmentContainerActivity
 */
public class FCActivity extends FragmentActivity {

    public static final String CLASS_NAME = "FRAGMENT_CLASS_NAME";
    public static final String ARGUMENTS = "FRAGMENT_ARGUMENTS";


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        Intent intent = getIntent();
        setContentView(R.layout.fc_container);
    //沉浸状态栏
        AppUtil.Setbar(this);
        String fragmentClassName = intent.getStringExtra(CLASS_NAME);
        if (TextUtils.isEmpty(fragmentClassName)) {
            return;
        }
        Bundle arguments = (Bundle) intent.getParcelableExtra(ARGUMENTS);

        try {
            Class<Fragment> fragmentClazz = (Class<Fragment>) this.getClass().getClassLoader().loadClass(fragmentClassName);
            Fragment fragment = fragmentClazz.newInstance();
            if (arguments != null) {
                fragment.setArguments(arguments);
            }
            addFragment(fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_fragment_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
//        boolean backResult = getSupportFragmentManager().popBackStackImmediate();
//        int fragmentSize = getSupportFragmentManager().getBackStackEntryCount();
//        LogUtils.d(fragmentSize + "");
//        if (fragmentSize == 0) {
//            super.onBackPressed();
//        }
        finish();
    }


    public static Intent getFCActivityIntent(Activity activity, Class<? extends Fragment> fragmentClazz) {
        Intent intent = new Intent(activity, FCActivity.class);
        intent.putExtra(CLASS_NAME, fragmentClazz.getName());
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
