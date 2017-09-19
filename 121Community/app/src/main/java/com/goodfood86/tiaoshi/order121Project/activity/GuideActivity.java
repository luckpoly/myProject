package com.goodfood86.tiaoshi.order121Project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.utils.BitmapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements OnPageChangeListener {

    // ViewPage组件
    @ViewInject(R.id.vp_guide)
    private ViewPager vp_guide;
    // 当前页卡
    @ViewInject(R.id.ll_dots)
    private LinearLayout ll_dots;

    // 存放view的数组
    private List<View> mPageViews = new ArrayList<View>();

    // 圆点
    private ImageView[] mDots;
    private int mDotsCount;

    // 判断是否跳转
    private Boolean isRedirectToMain = false;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Order121Application.getInstance().finishAllActivity();
            Order121Application.getInstance().addActivity((Activity) msg.obj);

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide_activity);
        ViewUtils.inject(this);

        Message msg = Message.obtain();
        msg.obj = this;
        handler.sendMessageDelayed(msg, 1000);

        // 初始化pageviews
        initPageViews();

        // 初始化圆点
        initDots();

        vp_guide.setAdapter(new ViewPageAdapter());
        vp_guide.setOnPageChangeListener(this);


    }
    public void onResume() {
        super.onResume();
        //友盟统计
//       //已删
    }
    public void onPause() {
        super.onPause();
        //友盟统计
//        //已删
    }


    /**
     * 初始化圆点
     */
    private void initDots() {
        mDotsCount = mPageViews.size();
        mDots = new ImageView[mDotsCount];
        for (int i = 0; i < mDotsCount; i++) {
            mDots[i] = getDot(i);
            ll_dots.addView(mDots[i]);
        }
    }

    private ImageView getDot(int i) {
        ImageView imageView = new ImageView(this);
        if (i == 0) {
            imageView.setBackgroundResource(R.drawable.guide_dot_focused);
        } else {
            imageView.setBackgroundResource(R.drawable.guide_dot_normal);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                20, 20);
        layoutParams.setMargins(10, 0, 10, 0);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    /**
     * 初始化ViewPage的页面
     */
    private void initPageViews() {
        mPageViews.add(getPageView(R.mipmap.guide_1));
        mPageViews.add(getPageView(R.mipmap.guide_2));
        mPageViews.add(getLastPageView(R.mipmap.guide_3));
    }

    // 获取布局并给imgview设置图片
    private View getPageView(int imgResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.guide_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.guide_img);
        imageView.setImageBitmap(BitmapUtil.getBitmapHD(this, imgResId));
        return view;
    }
    private View getLastPageView(int imgResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.guide_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.guide_img);
        Button button= (Button) view.findViewById(R.id.tiyan);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                Order121Application.getInstance().addActivity(GuideActivity.this);
                finish();
            }
        });
        imageView.setImageBitmap(BitmapUtil.getBitmapHD(this, imgResId));
        return view;
    }

    private class ViewPageAdapter extends PagerAdapter {

        // 获取要滑动的控件的数量
        @Override
        public int getCount() {
            return mPageViews.size();
        }

        // 判断显示的是否是同一张图片
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // PagerAdapter只缓存三个元素，如果超出了缓存的范围，就会调用这个方法，将元素销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPageViews.get(position));
        }

        // 当要显示的元素可以进行缓存的时候，会调用这个方法进行元素的初始化，我们将要显示的view加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 添加item到缓存，不能省略
            container.addView(mPageViews.get(position));
            return mPageViews.get(position);
        }

    }

    /**
     * state有三种状态（0，1，2）。
     * <p/>
     * state==1:开始滑动，每次都会执行
     * <p/>
     * state==2:滑动完毕，不能滑动时不会执行
     * <p/>
     * state==0:操作结束，必定执行
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:// 0
                // 操作结束后判断当前页是否为最后一页,是最后一页的话，进行跳转到MainActivity操作
                if (vp_guide.getCurrentItem() == mPageViews.size() - 1
                        && isRedirectToMain) {
                    startActivity(new Intent(this, MainActivity.class));
                    Order121Application.getInstance().addActivity(this);
                    isRedirectToMain = false;
                    finish();
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING: // 1
                isRedirectToMain = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING: // 2
                isRedirectToMain = false;
                break;
            default:
                break;
        }
    }

    /**
     * 当页面在滑动的时候会调用此方法
     * <p/>
     * arg0 :当前页面，及你点击滑动的页面
     * <p/>
     * arg1:当前页面偏移的百分比
     * <p/>
     * arg2:当前页面偏移的像素位置
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    /**
     * 页面被选中时执行
     * <p/>
     * arg0:当前被选中的页面的下标
     */
    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < mDotsCount; i++) {
            if (i == arg0) {
                mDots[arg0].setBackgroundResource(R.drawable.guide_dot_focused);
            } else {
                mDots[i].setBackgroundResource(R.drawable.guide_dot_normal);
            }
        }
    }
}
