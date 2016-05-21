package Util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;

/**
 * Created by alancosta on 5/21/16.
 */
public class ViewUtil {


    public static void setStatusBarColor(Activity activity, int color) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setStatusBarColor(color);
            ColorDrawable colorDrawable = new ColorDrawable(color);
            activity.getWindow().setBackgroundDrawable(colorDrawable);
            // activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static int gravityLeft() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return Gravity.START;
        } else {
            return Gravity.LEFT;
        }

    }
}
