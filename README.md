# DotLoopViewpager
小圆点指示器的viewpager,使用非常方便,自动轮播图片,dot circle indicator viewpager auto looping
![示意图][1]![此处输入图片的描述][2]
## 一.特点

 - 小圆点指示器,
 - 支持自动轮播,触摸是停止轮播,抬起手再次开始轮播
 - 非常强的自定义属性,可以设置任何图片或者shape文件作为指示器
 [git地址,上面有demo][3]
## 二.初衷
最近在做轮播图,心想这么简单的东西,应网上第三方的挺多的吧,找一个用用,结果找了好多,没有几个满意的,各种bug,各种麻烦,所以就自己写一个吧,要写就要写好,最首要的就是稳定,其次就是容易上手,最后要扩展性强.已经经过调试,目前没发现什么bug,如果有发现的欢迎提出,使用也是超级方便,简单的几个方法,至于扩展性,个人认为也是很强的.
## 三.使用方法
eclipse首先要依赖此项目的library文件,(后期会传到jcenter)
 as用户现在可以添加依赖: compile 'com.z:DotLoopViewpager:1.0.0'

### 简单使用
1.在布局文件中引入:

    \<z.dotloopviewpagerlibrary.DotLoopViewpager
    android:id="@+id/dlvp"
    android:layout_width="300dp"
    android:layout_height="200dp"
    android:layout_gravity="center"\>\</z.dotloopviewpagerlibrary.DotLoopViewpager\>

2.在activity中的代码:

     private DotLoopViewpager<ADBean> mDlvpDotLoopViewpager;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDlvpDotLoopViewpager = (DotLoopViewpager) findViewById(R.id.dlvp);
        //设置监听
          mDlvpDotLoopViewpager.setonBindImageAndClickListener(new             DotLoopViewpager.onBindImageAndClickListener<ADBean>() {
            @Override
            public void onClick(ADBean bean) {
                ToastUtils.show(getApplicationContext(), bean.getDetail_url());
            }

            @Override
            public void onBind(ADBean bean, ImageView imageView) {
            //你可以使用你自己喜欢的方式绑定图片,我这里用的是glide
                Glide.with(getApplicationContext()).load(bean.getImg_url()).centerCrop().into(imageView);
            }
        });
        List<ADBean> tLists = new Arraylist()<>;
        tLists.add(...);
        ....
        //设置数据
          mDlvpDotLoopViewpager.setData(tLists);
          }
***注意:private DotLoopViewpager<ADBean> mDlvpDotLoopViewpager;这里一定要带泛型,List<ADBean> tLists = new Arraylist()<>;这里要传入的数据集合也要带泛型,DotLoopViewpager.onBindImageAndClickListener<ADBean>()设置监听的时候也要带泛型,这样可以直接绑定图片,设置点击事件也能直接获取到数据,非常方便.***
### 自定义各种样式

            //设置小圆点指示器容器的各种属性,可以设置gravity,margin,等所有linelayout有的属性
            FrameLayout.LayoutParams params = mDlvpDotLoopViewpager.getIndicatorCotainerLayoutParams();
            params.gravity = Gravity.LEFT | Gravity.BOTTOM;
            mDlvpDotLoopViewpager.setIndicatorCotainerLayoutParams(params);
            //设置小圆点之间的距离,dp值
            mDlvpDotLoopViewpager.setSpaceDip(20);
            //设置指示器的selector,默认是红色小圆点和灰色小圆点,你可以自己建立一个selector文件,然后设置上去,定制性非常高,你想设置什么样的图片都可以
            mDlvpDotLoopViewpager.setSelectorResource(R.drawable.selector_point1);
            //设置指示器的直径,默认是WRAP_CONTENT,自己根据需要可以调整
            mDlvpDotLoopViewpager.setIndicatordiameter(150);
            //如果设置的selector中的两个图片资源大小不同,必须设置这个值,否则乱,selector特性决定的
            //其他的属性后面会介绍到
            ...
***注意:  mDlvpDotLoopViewpager.setIndicatordiameter(150);
            //如果设置的selector中的两个图片资源大小不同,必须设置这个值,否则乱,selector特性决定的***
## 四.所有可定制属性介绍(后面的值是默认值,所有下面属性可以用set方法设置)

    //viewpager的图片缩放方式
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    //自定义的selector,selector中需要设置两种状态,一种是enable=ture 一种是enable=false,谨记
    private int selectorResource = R.drawable.selector_point;
    //小圆点之间的距离
    private int spaceDip = 15;
    //是否自动轮播(轮播时触摸停止轮播,抬起手轮播继续的)
    private boolean autoLoop = true;
    //轮播间隔时间
    private long loopTime = 3000;
    //指示条的layoutparams,可以先get方法获取到,然后再set上去
    private LayoutParams indicatorCotainerLayoutParams;
    //小圆点的直径,默认包裹内容
    private int indicatordiameter = LayoutParams.WRAP_CONTENT;


  [1]: https://github.com/zxyaust/DotLoopViewpager/blob/master/SCR_20160714_122356.gif?raw=true
  [2]: https://github.com/zxyaust/DotLoopViewpager/blob/master/Screenshot_2016-07-15-10-03-58.jpg?raw=true
  [3]: https://github.com/zxyaust/DotLoopViewpager