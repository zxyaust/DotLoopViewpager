package z.dotloopviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import z.dotloopviewpager.other.ADBean;
import z.dotloopviewpager.other.ToastUtils;
import z.dotloopviewpagerlibrary.DotLoopViewpager;

public class MainActivity extends AppCompatActivity {

    private DotLoopViewpager mDlvpDotLoopViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDlvpDotLoopViewpager = (DotLoopViewpager) findViewById(R.id.dlvp);
        mDlvpDotLoopViewpager.setEventListener(new DotLoopViewpager.onBindImageAndClickListener<ADBean>() {
            @Override
            public void onClick(ADBean bean) {
                ToastUtils.show(getApplicationContext(), bean.getDetail_url());
            }


            @Override
            public void onBindTitle(ADBean bean, TextView title) {
                title.setText(bean.getDetail_url());
            }

            @Override
            public void onBind(ADBean bean, ImageView imageView) {
                Glide.with(getApplicationContext()).load(bean.getImg_url()).centerCrop().into(imageView);
            }
        });
        getWebImageViews();
    }

    private void getWebImageViews() {
        List<ADBean> list = new ArrayList<>();
        ADBean e = new ADBean();
        e.setDetail_url("xixiixixiixi");
        e.setImg_url("http://www.mm4493.com/d/file/p/2016-01-04/9d6bfeac26903776f7260b1443fc3f28.jpg");
        list.add(e);
        list.add(e);
        list.add(e);
        list.add(e);
        list.add(e);
        list.add(e);
        list.add(e);
        list.add(e);
        mDlvpDotLoopViewpager.setData(list);

    }


}
