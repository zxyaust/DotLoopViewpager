package z.dotloopviewpagerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 2016/7/13.
 */
public class DotLoopViewpager extends FrameLayout {

    private final Context context;


    private EventListener listener;
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    private boolean isAutoLoop = true;
    private long loopTime;

    private LinearLayout indicatorContainer;
    private RelativeLayout rlContainer;
    private TextView titleView;
    private ViewPager viewPager;
    private List mData = new ArrayList<>();
    private MyAdapter adapter;
    private List<ImageView> imageViews;

    private int indicatorDrawable;
    private int indicatorUnselected;
    private boolean isInfiniteLoop;
    private int indicator_hight;
    private int indicator_width;
    private float indiactor_space;
    private Handler mHandler;
    private ImageView indicator;
    private LayoutParams titleParams;
    private RelativeLayout.LayoutParams indicatorBarParams;

    //-----------------------------------构造方法
    public DotLoopViewpager(Context context) {
        this(context, null);
    }

    public DotLoopViewpager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotLoopViewpager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotLoopViewpager);
        indiactor_space = typedArray.getDimension(R.styleable.DotLoopViewpager_indicator_space, 10);
        Log.d("聊天:间隔距离", indiactor_space + "");
        indicatorDrawable = typedArray.getResourceId(R.styleable.DotLoopViewpager_indicator_drawable_selected, R.drawable.shape_point_red);
        indicatorUnselected = typedArray.getResourceId(R.styleable.DotLoopViewpager_indicator_drawable_unselected, R.drawable.shape_point_gray);
        isAutoLoop = typedArray.getBoolean(R.styleable.DotLoopViewpager_auto_loop, true);
        isInfiniteLoop = typedArray.getBoolean(R.styleable.DotLoopViewpager_infinite_loop, true);
        indicator_hight = (int) typedArray.getDimension(R.styleable.DotLoopViewpager_indicator_height, 20);
        indicator_width = (int) typedArray.getDimension(R.styleable.DotLoopViewpager_indicator_width, 20);
        loopTime = typedArray.getInt(R.styleable.DotLoopViewpager_loop_time, 3000);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (!isAutoLoop)
                    return;
                int currentItem = viewPager.getCurrentItem();//当前页面位置
                //跳到下一页
                currentItem++;
                if (imageViews.size() > 0) {
                    if (isInfiniteLoop)
                        currentItem = currentItem % imageViews.size();
                    viewPager.setCurrentItem(currentItem);
                    //继续发送延时3秒的消息,形成内循环, 类似递归
                    mHandler.sendEmptyMessageDelayed(0, loopTime);
                }
            }
        };
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.dot_loop_viewpager, null);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        indicatorContainer = (LinearLayout) view.findViewById(R.id.ll_indicator);
        rlContainer = (RelativeLayout) view.findViewById(R.id.rl_container);
        indicatorBarParams = (RelativeLayout.LayoutParams) rlContainer.getLayoutParams();
        indicator = (ImageView) view.findViewById(R.id.indicator);
        indicator.setImageResource(indicatorDrawable);
        indicator.getLayoutParams().width = indicator_width;
        indicator.getLayoutParams().height = indicator_hight;
        //titleview初始化
        titleView = (TextView) view.findViewById(R.id.title);
        titleParams = (LayoutParams) titleView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicatorContainer.getLayoutParams();
        FrameLayout.LayoutParams layoutParams1 = (LayoutParams) rlContainer.getLayoutParams();
        indicatorContainer.setLayoutParams(layoutParams);
        addView(view);

    }

    /**
     * 设置adapter的数据
     *
     * @param mData
     */
    public void setData(List mData) {
        this.mData = mData;
        if (mData.size() > 0)
            initImages();
    }

    public void addData(List mData) {

    }

    /**
     * 初始化小圆点,并设置adapter
     */
    private void initImages() {
        imageViews = new ArrayList<>();
        if (mData.size() <= 0)
            return;
        for (final Object bean : mData) {
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
            imageViews.add(imageView);
        }
        initDot(imageViews);
        setListener(imageViews);
        adapter = new MyAdapter(imageViews);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(10000 * imageViews.size());
    }

    /**
     * 添加监听事件
     *
     * @param list
     */
    private void setListener(final List<ImageView> list) {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面被选中
            @Override
            public void onPageSelected(int position) {
                listener.onBindTitle(mData.get(position % list.size()), titleView);
            }

            //页面滑动
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicator.getLayoutParams();
                int i = (int) ((position % list.size()) * (indiactor_space + indicator.getWidth())
                        + positionOffset * (indiactor_space + indicator.getWidth()));
                layoutParams.leftMargin = i;
                if ((position % list.size() == 0 && positionOffset < 0)
                        || (position % list.size() == list.size() - 1) && positionOffset > 0) {
                    indicator.setVisibility(GONE);
                } else {
                    indicator.setVisibility(VISIBLE);
                }
                indicator.setLayoutParams(layoutParams);
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

    /**
     * 初始化小圆点,设置轮播事件
     *
     * @param list
     */
    private void initDot(List<ImageView> list) {
        for (int i = 0; i < list.size(); i++) {
            ImageView point = new ImageView(getContext());

            point.setImageResource(indicatorUnselected);
            //通过代码设置外边距(父控件是谁,就使用谁定义的布局参数)
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams((int) indicator_width, (int) indicator_hight);//宽高包裹内容
            if (i > 0)
                params.leftMargin = (int) indiactor_space;
            //设置布局参数
            point.setLayoutParams(params);
            indicatorContainer.addView(point);
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(0, loopTime);
        }
    }

    /**
     * 适配器
     */
    class MyAdapter extends PagerAdapter {
        List<ImageView> views = new ArrayList<>();

        public MyAdapter(List<ImageView> data) {
            views = data;
        }

        @Override
        public int getCount() {
            return isInfiniteLoop ? Integer.MAX_VALUE : views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView child = views.get(isInfiniteLoop ? position % views.size() : position);
            if (child.getParent() == null) {
                ViewGroup parent = viewPager;
                parent.removeView(child);
            }
            container.addView(child);
            return views.get(isInfiniteLoop ? position % views.size() : position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(isInfiniteLoop ? position % views.size() : position));
        }
    }

    /**
     * 设置监听事件
     *
     * @param o 绑定监听事件
     */
    public void setEventListener(EventListener o) {
        listener = o;
    }
}

