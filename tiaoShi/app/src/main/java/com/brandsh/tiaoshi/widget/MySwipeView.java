package com.brandsh.tiaoshi.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/2/23.
 */
public class MySwipeView extends FrameLayout{
    private View contentView, deleteView;//内容区域,删除区域,不需要手动通过 addview 方式添加,因为已经在布局文件中加入了
    private int contentViewHeight, contentViewWidth, deleteViewWeidth;//内容区域的款高度和删除区域的宽度,高度与内容区域高度一致
    private ViewDragHelper viewDragHelper = ViewDragHelper.create(this, new MyCallback());
    private SwipeState swipeState = SwipeState.Closed;//用于标记当前的开关状态,默认是关闭
    private ListView listView;//它所依赖的 listview
    private PullToRefreshListView pullToRefreshListView;

    public MySwipeView(Context context) {
        super(context);
    }

    public MySwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setPtrListView(PullToRefreshListView pullToRefreshListView){
        this.pullToRefreshListView = pullToRefreshListView;
    }

    /**
     * 布局文件加载完成后的回调,但是只是加载完成布局,但是自己具体有多高多宽不知道,只知道自己内部有什么控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);//获取到内容区域
        deleteView = getChildAt(1);//获取到删除区域
    }

    /**
     * 这里的时候就已经知道控件有多宽多高了
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentViewHeight = contentView.getMeasuredHeight();//获取内容区域和删除区域的测量高度
        contentViewWidth = contentView.getMeasuredWidth();//获取内容区域的宽度
        deleteViewWeidth = deleteView.getMeasuredWidth();//获取删除区域的宽度
    }

    /**
     * 摆放内部子控件的位置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        contentView.layout(0, 0, contentViewWidth, contentViewHeight);//重新摆放内容区域
//        deleteView.layout(contentViewWidth, 0, contentViewWidth + deleteViewWeidth, contentViewHeight);//绘制删除区域
        quickClose();//初始位置

    }


    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);//交给 helper 来处理拦截事件
    }

    private int lastX, lastY,clickX,clickY;//记录滑动时候的 x y, 和记录点击时候的 x,y

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX =clickX= (int) event.getX();
                lastY =clickY= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - lastX) > Math.abs(y - lastY)) {//左右滑动的距离差大于上下,那么当前事件交给我自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                //抬起起来
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - clickX) <=20 || Math.abs(upY - clickY) <=20) {//如果按下去的 x y 和抬起的时候的x y 像素相处10个像素之内 则代表是点击事件
                    //在这里要执行点击事件,执行谁的点击事件,执行 listview 的 item 点击事件
                    if (swipeState == SwipeState.Closed) {//如果是关闭状态就执行点击事件
                        if (listView != null){
                            AdapterView.OnItemClickListener onItemClickListener = listView.getOnItemClickListener();//获取设置的 onitem 点击事件
                            if (onItemClickListener != null) {
                                int position = (int) getTag(getId());
                                onItemClickListener.onItemClick(listView,this,position,position);
                            }
                        }
                        if (pullToRefreshListView !=null){
                            AdapterView.OnItemClickListener onItemClickListener = pullToRefreshListView.getRefreshableView().getOnItemClickListener();//获取设置的 onitem 点击事件
                            if (onItemClickListener != null) {
                                int position = (int) getTag(getId());
                                onItemClickListener.onItemClick(pullToRefreshListView.getRefreshableView(),this,position,position);
                            }
                        }
                    } else{//否则就关闭自己
                        close();
                    }


                }

                break;

        }

        viewDragHelper.processTouchEvent(event);//由 helper 处理事件
        return true;
    }

    private class MyCallback extends ViewDragHelper.Callback {
        /**
         * 用于判断触摸的子控件是否是可以拖动
         *
         * @param child     当前触摸的子控件
         * @param pointerId
         * @return 如果返回 true 代表当前触摸的子控件可以被拖动,否则不可拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == contentView || child == deleteView;
//            return true;
        }

        /**
         * 当 view 的位置发生变化的时候的回调
         *
         * @param changedView 变化的 view
         * @param left        当前的getleft
         * @param top         当前的gettop
         * @param dx          拖动的x 的值
         * @param dy          拖动的Y 的值
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == contentView) {//当拖动内容区域的时候,我们手动变化删除区域
                deleteView.layout(deleteView.getLeft() + dx, deleteView.getTop(), deleteView.getRight() + dx, deleteView.getBottom());
            } else if (changedView == deleteView) {//如果拖动的是删除区域,则应该 手动滑动内容区域
                contentView.layout(contentView.getLeft() + dx, contentView.getTop(), contentView.getRight() + dx, contentView.getBottom());
            }
            if (contentView.getLeft() == 0&&swipeState!=SwipeState.Closed) {//关闭状态
                swipeState = SwipeState.Closed;
                unClosedSwipeViews.remove(MySwipeView.this);
            } else if (contentView.getLeft() == -deleteViewWeidth&&swipeState!=SwipeState.Open) {//打开状态
                swipeState = SwipeState.Open;
                //在此 应该先把已经打开的关掉
                for (int i = 0; i < unClosedSwipeViews.size(); i++) {//打开后遍历集合,将不是自己的控件全部关闭
                    if (unClosedSwipeViews.get(i) != MySwipeView.this) {
                        unClosedSwipeViews.get(i).close();
                    }
                }
                if (!unClosedSwipeViews.contains(MySwipeView.this)) {//当前集合里面已经打开的控件不包含自己
                    unClosedSwipeViews.add(MySwipeView.this);//把自己加进去
                }
            } else {
                swipeState = SwipeState.Swiping;
//                unClosedSwipeViews.add(MySwipeView.this);
                for (int i = 0; i < unClosedSwipeViews.size(); i++) {
                    if (unClosedSwipeViews.get(i) != MySwipeView.this&&dx<0) {//遍历所有的已经打开,并且判断滑动方向是往左滑,然后才执行, 如果不加方向的判断,自动关闭回去的那个控件,又会把我们打开的控件关闭掉
                        unClosedSwipeViews.get(i).close();
                    }
                }
                if (!unClosedSwipeViews.contains(MySwipeView.this)) {//当前集合里面已经打开的控件不包含自己
                    unClosedSwipeViews.add(MySwipeView.this);//把自己加进去
                }
            }
        }


        /**
         * 抬起手指或者是事件被拦截释放后的回调
         *
         * @param releasedChild 触摸的 view
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (contentView.getLeft() < -deleteViewWeidth / 2) {//内容区域滑出屏幕超出了一半,完全显示
                open();
            } else {
                close();
            }
        }


        /**
         * 用于返回被触摸的 view 到底滑动多少的回调
         *
         * @param child 被触摸的控件
         * @param left  当前控制理论上的 getleft
         * @param dx    滑动的距离
         * @return 默认返回值0, 如果返回 left,就一切按照系统默认的拖动速度 你拖多少,我动多少
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                if (left > 0) left = 0;//如果大于0 则等于0
                if (left < -deleteViewWeidth)
                    left = -deleteViewWeidth;//如果左滑动超过 deleteview 的宽度,就等于它的宽度
            } else {
                if (left > contentViewWidth)
                    left = contentViewWidth;//删除区域滑动的最大值 如果超出内容区域的宽度,则为内容区域宽度
                if (left < contentViewWidth - deleteViewWeidth)//如果小于内容区域的宽度减去删除区域的宽度,则代表已经完全滑出了,不应该再变了
                    left = contentViewWidth - deleteViewWeidth;
            }

            return left;
        }
        /**
         * 获取 view 的水平拖拽方位
         *
         * @param child
         * @return 默认返回值是0, 0的话代表内部子控件不能滑动,
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteViewWeidth;
        }
    }
    /**
     * scrllor滑动的时候调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(MySwipeView.this);//刷新界面
        }
    }
    /**
     * 打开
     */
    public void open() {
        //第一个参数 把谁滚过去,
        viewDragHelper.smoothSlideViewTo(contentView, -deleteViewWeidth, 0);
        ViewCompat.postInvalidateOnAnimation(MySwipeView.this);//刷新界面
    }
    /**
     * 关闭控件
     */
    public void close() {
        viewDragHelper.smoothSlideViewTo(contentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(MySwipeView.this);//刷新界面
    }
    /**
     * 快速关闭
     */
    public void quickClose() {
        contentView.layout(0, 0, contentViewWidth, contentViewHeight);//重新摆放内容区域
        deleteView.layout(contentViewWidth, 0, contentViewWidth + deleteViewWeidth, contentViewHeight);//绘制删除区域
        unClosedSwipeViews.remove(this);//手动移除,因为手动 layout 不会导致 viewdraghelper 里面的回调执行
    }
    /**
     * 记录滑动状态的枚举
     */
    enum SwipeState {
        Open, Closed, Swiping//打开,关闭,滑动
    }
    //用于存放打开非关闭状态的的控件
    public static List<MySwipeView> unClosedSwipeViews = new ArrayList<>();
}
