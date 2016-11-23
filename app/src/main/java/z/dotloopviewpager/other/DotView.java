package z.dotloopviewpager.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import z.dotloopviewpager.R;

/**
 * Created by Miller Zhang  on 2016/11/20.
 * desc:
 * github:https://github.com/zxyaust  CSDN:http://blog.csdn.net/qq_31340657
 * Whatever happens tomorrow,we've had today.
 */
public class DotView extends View {

    private Paint paint;
    private RectF rectF;
    private Canvas canvas;
    private float space;
    private float diameter;

    public DotView(Context context) {
        this(context, null);
    }

    public DotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.RED);
        TypedArray array = context.obtainStyledAttributes(R.styleable.DotView);
        diameter = array.getDimension(R.styleable.DotView_diameter, dip2px(10));
        space = array.getDimension(R.styleable.DotView_space, dip2px(10));

        LogUtil.d("construc");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.d("layout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("measure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        LogUtil.d("draw");
    }


    public void drawOvals(int i) {
        rectF = new RectF(0, 0, diameter, diameter);
        for (int j = 0; j < i; j++) {
            rectF.offset(diameter + space, 0);
            canvas.drawOval(rectF, paint);
        }
    }

    public int dip2px(float dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        //dp = px/density
        int px = (int) (dp * density + 0.5f);
        return px;
    }
}
