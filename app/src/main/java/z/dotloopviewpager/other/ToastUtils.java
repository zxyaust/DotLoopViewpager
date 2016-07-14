package z.dotloopviewpager.other;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by jeff on 2016/6/1.
 */
public class ToastUtils {


    private static Toast toast;

    public static void show(Context con, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(con, "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }

        toast.setText(text);
        toast.show();
    }


}
