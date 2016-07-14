package z.dotloopviewpagerlibrary;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 2016/7/13.
 */
public class DotLoopViewpager<T> extends FrameLayout {
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    private int selectorResource = R.drawable.selector_point;
    private int spaceDip = 15;
    private boolean autoLoop = true;
    private long loopTime = 3000;
    private LayoutParams indicatorCotainerLayoutParams;
    private int indicatordiameter = LayoutParams.WRAP_CONTENT;
    private LinearLayout indicatorCotainer;
    private ViewPager viewPager;
    private List<T> mData = new ArrayList<>();
    private MyAdapter adapter;
    private List<ImageView> list;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!autoLoop)
                return;
            int currentItem = viewPager.getCurrentItem();//当前页面位置
            //跳到下一页
            currentItem++;
            if (list.size() > 0) {
                currentItem = currentItem % list.size();
                viewPager.setCurrentItem(currentItem);
                //继续发送延时3秒的消息,形成内循环, 类似递归
                mHandler.sendEmptyMessageDelayed(0, loopTime);
            }
        }
    };

    public DotLoopViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DotLoopViewpager(Context context) {
        this(context, null);
    }

    public DotLoopViewpager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public void setSelectorResource(int selectorResource) {
        this.selectorResource = selectorResource;
    }

    public void setSpaceDip(int spaceDip) {
        this.spaceDip = spaceDip;
    }

    public void setAutoLoop(boolean autoLoop) {
        this.autoLoop = autoLoop;
    }

    public void setLoopTime(long loopTime) {
        this.loopTime = loopTime;
    }

    public void setIndicatorCotainerLayoutParams(LayoutParams indicatorCotainerLayoutParams) {
        this.indicatorCotainerLayoutParams = indicatorCotainerLayoutParams;
        setIndicatorCotainerLayoutParams();
    }

    public void setIndicatordiameter(int indicatordiameter) {
        this.indicatordiameter = indicatordiameter;
        indicatordiameter = indicatordiameter >= 0 ? dip2px(getContext(), indicatordiameter) : indicatordiameter;
    }

    public LayoutParams getIndicatorCotainerLayoutParams() {
        return indicatorCotainerLayoutParams;
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.dot_loop_viewpager, null);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        indicatorCotainer = (LinearLayout) view.findViewById(R.id.ll_indicator);
        indicatorCotainerLayoutParams = (LayoutParams) indicatorCotainer.getLayoutParams();
        initImages();
        addView(view);
    }

    private void setIndicatorCotainerLayoutParams() {
        indicatorCotainer.setLayoutParams(indicatorCotainerLayoutParams);
    }

    private void initImages() {
        list = new ArrayList<>();
        for (final T bean : mData) {
            ImageView imageView = new ImageView(getContext());
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(scaleType);
            listener.onBind(bean, imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(bean);
                }
            });
            list.add(imageView);
        }
        initDot(list);
        setListener(list);
        adapter = new MyAdapter(list);
        viewPager.setAdapter(adapter);
    }


    private void setListener(final List<ImageView> list) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //页面被选中
            @Override
            public void onPageSelected(int position) {
                //更新标题
                position = position % list.size();
//                tvTitle.setText(mImageDes[position]);

                //更新圆点
                int childCount = indicatorCotainer.getChildCount();//获取子控件个数
                for (int i = 0; i < childCount; i++) {
                    View child = indicatorCotainer.getChildAt(i);//获取某个位置的子控件
                    if (i == position) {
                        //红色
                        child.setEnabled(true);
                    } else {
                        //灰色
                        child.setEnabled(false);
                    }
                }

            }

            //页面滑动
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            //滑动状态发生变化
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //viewpager触摸时不允许轮播
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mHandler.removeCallbacksAndMessages(null);//删除所有的消息
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        //抬起
                        mHandler.sendEmptyMessageDelayed(0, loopTime);//延时3秒之后发送消息
                        break;
                    default:
                        break;
                }
                return false;//事件不能消费掉,应该继续传递,使ViewPager原生的触摸效果起作用
            }
        });
    }

    private void initDot(List<ImageView> list) {
        for (int i = 0; i < list.size(); i++) {
            ImageView point = new ImageView(getContext());
            point.setImageResource(selectorResource);
            //通过代码设置外边距(父控件是谁,就使用谁定义的布局参数)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicatordiameter, indicatordiameter);//宽高包裹内容
            if (i > 0) {//从第二个点开始设置左边距
                params.leftMargin = dip2px(getContext(), spaceDip);
                point.setEnabled(false);//设置为不可用,图片就会显示为灰色
            }

            //设置布局参数
            point.setLayoutParams(params);

            indicatorCotainer.addView(point);
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(0, loopTime);
        }
    }


    class MyAdapter extends PagerAdapter {
        List<ImageView> views = new ArrayList<>();

        public MyAdapter(List<ImageView> data) {
            views = data;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        initImages();
    }

    public static int dip2px(Context ctx, float dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        //dp = px/density
        int px = (int) (dp * density + 0.5f);
        return px;
    }

    public interface onBindImageAndClickListener<T> {
        public void onClick(T bean);

        public void onBind(T bean, ImageView imageView);
    }

    onBindImageAndClickListener listener;

    public void setonBindImageAndClickListener(onBindImageAndClickListener<T> o) {
        listener = o;
    }
}

