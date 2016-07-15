package z.dotloopviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import z.dotloopviewpager.other.ADBean;
import z.dotloopviewpager.other.LogUtil;
import z.dotloopviewpager.other.MyListBeansCallBack;
import z.dotloopviewpager.other.ToastUtils;
import z.dotloopviewpager.other.URLConstant;
import z.dotloopviewpagerlibrary.DotLoopViewpager;

public class MainActivity extends AppCompatActivity {

    private DotLoopViewpager<ADBean> mDlvpDotLoopViewpager;
    private DotLoopViewpager<ADBean> mDlvpDotLoopViewpager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDlvpDotLoopViewpager = (DotLoopViewpager) findViewById(R.id.dlvp);
        mDlvpDotLoopViewpager1 = (DotLoopViewpager) findViewById(R.id.dlvp1);
        FrameLayout.LayoutParams params = mDlvpDotLoopViewpager.getIndicatorCotainerLayoutParams();
        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
        mDlvpDotLoopViewpager.setIndicatorCotainerLayoutParams(params);
        mDlvpDotLoopViewpager.setSpaceDip(20);
        mDlvpDotLoopViewpager.setSelectorResource(R.drawable.selector_rectangle);
        mDlvpDotLoopViewpager._setIndicatordiameter(150);//如果设置的连个图片资源大小不同,必须设置这个值,否则乱,selector特性决定的,不建议使用此属性,如果要调整指示器的大小,请自定义selector来调整
        mDlvpDotLoopViewpager.setonBindImageAndClickListener(new DotLoopViewpager.onBindImageAndClickListener<ADBean>() {
            @Override
            public void onClick(ADBean bean) {
                ToastUtils.show(getApplicationContext(), bean.getDetail_url());
            }

            @Override
            public void onBind(ADBean bean, ImageView imageView) {
                Glide.with(getApplicationContext()).load(bean.getImg_url()).centerCrop().into(imageView);
            }
        });
        mDlvpDotLoopViewpager1.setonBindImageAndClickListener(new DotLoopViewpager.onBindImageAndClickListener<ADBean>() {
            @Override
            public void onClick(ADBean bean) {
                ToastUtils.show(getApplicationContext(), bean.getDetail_url());
            }

            @Override
            public void onBind(ADBean bean, ImageView imageView) {
                Glide.with(getApplicationContext()).load(bean.getImg_url()).centerCrop().into(imageView);
            }
        });
        getWebImageViews();
    }

    private void getWebImageViews() {
        OkHttpUtils.get().url(URLConstant.PICTURE_LUNBO).build().execute(new MyListBeansCallBack() {
            @Override
            public void onResultInfo(int result_code, String result_message) {

            }

            @Override
            public Object getT() {
                return new ADBean();
            }

            @Override
            public void onResultData(List tLists, String response) {
                mDlvpDotLoopViewpager.setData(tLists);
                mDlvpDotLoopViewpager1.setData(tLists);
                LogUtil.d(response + "条数" + tLists.size());
            }
        });


    }


}
