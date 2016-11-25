package z.dotloopviewpagerlibrary;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 监听器
 *
 * @param <T>
 */
public interface EventListener<T> {
    void onClick(T bean);

    void onBind(T bean, ImageView imageView);

    void onBindTitle(T bean, TextView title);
}