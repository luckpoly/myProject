package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.brandsh.tiaoshi.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * Created by sisi on 16/3/16.
 */
public class SelfLoadingLayout extends LoadingLayout {
    // 下拉完成后的帧动画
    private AnimationDrawable animationDrawableLoading;

    // 随着下拉动作变化的帧动画
    private AnimationDrawable animationDrawable;

    public SelfLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        initFrame(context);

        mHeaderImage.setImageDrawable(animationDrawable);
    }

    private void initFrame(Context context) {
        Resources res = context.getResources();

        animationDrawableLoading = new AnimationDrawable();
        animationDrawableLoading.addFrame(
                res.getDrawable(R.mipmap.g_2), 100);
        animationDrawableLoading.addFrame(
                res.getDrawable(R.mipmap.g_3), 100);
        animationDrawableLoading.addFrame(
                res.getDrawable(R.mipmap.g_4), 100);
        animationDrawableLoading.addFrame(
                res.getDrawable(R.mipmap.g_5), 100);
        animationDrawableLoading.setOneShot(false);

        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g_2), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g1), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g1), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g1), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g2), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g3), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g4), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g5), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g6), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g7), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g8), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g9), 130);
        animationDrawable.addFrame(
                res.getDrawable(R.mipmap.g10), 130);

    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.mipmap.g10;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        // 一共10多帧动画,计算每一帧对应的scale数
        float scaleOfFrame = 2f / animationDrawable.getNumberOfFrames();
        // 当前下拉scale值除以每一帧scale计算出当前应该播放哪一帧动画
        int idx = (int) (scaleOfLayout / scaleOfFrame);
        // 超过最大数量时(对应scaleOfLayout > 1f),停留在最后一帧
        if (idx > animationDrawable.getNumberOfFrames() - 1) {
            idx = animationDrawable.getNumberOfFrames() - 1;
        }
        // 设置当前帧角标
        animationDrawable.selectDrawable(idx);
    }

    @Override
    protected void pullToRefreshImpl() {

    }

    @Override
    protected void refreshingImpl() {
        // 松手释放后,开始加载另一个loading动画
        mHeaderImage.clearAnimation();
        mHeaderImage.setImageDrawable(animationDrawableLoading);
        animationDrawableLoading.start();
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {
        // 开始下拉前重置,加载设置动画
        mHeaderImage.clearAnimation();
        mHeaderImage.setImageDrawable(animationDrawable);
    }
}
