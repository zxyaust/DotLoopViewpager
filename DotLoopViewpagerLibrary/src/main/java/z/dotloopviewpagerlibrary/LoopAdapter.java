package z.dotloopviewpagerlibrary;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器
 */
class LoopAdapter extends PagerAdapter {
    List<ImageView> views = new ArrayList<>();
    private boolean isInfinite;

    public LoopAdapter(List<ImageView> data, boolean isInfinite) {
        views = data;
        this.isInfinite = isInfinite;
    }

    @Override
    public int getCount() {
        return isInfinite ? Integer.MAX_VALUE : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(isInfinite ? position % views.size() : position));
        return views.get(isInfinite ? position % views.size() : position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(isInfinite ? position % views.size() : position));
    }
}