package z.dotloopviewpagerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Miller Zhang  on 2016/11/23.
 * desc:
 * github:https://github.com/zxyaust  CSDN:http://blog.csdn.net/qq_31340657
 * Whatever happens tomorrow,we've had today.
 */

public class LoopPage extends FrameLayout {
    private Context mContext;

    private int indicatorResourceId;
    private ImageView indicator;

    public LoopPage(Context context) {
        this(context, null);
    }

    public LoopPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.LoopPage);
        indicatorResourceId = typedArray.getResourceId(R.styleable.LoopPage_indicator_drawable, R.drawable.shape_point_red);
        typedArray.recycle();
        initView();

    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.loop_page, null);
        indicator = (ImageView) view.findViewById(R.id.indicator);
        indicator.setImageResource(indicatorResourceId);
        addView(view);
    }

}
