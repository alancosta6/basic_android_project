package Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by alancosta on 5/21/16.
 */
public class ViewUtil {


    public static void setStatusBarColor(Activity activity, int color) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Window window = activity.getWindow();


            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            window.setStatusBarColor(color);
            ColorDrawable colorDrawable = new ColorDrawable(color);
            window.setBackgroundDrawable(colorDrawable);
            //window.setNavigationBarColor(color);
        }
    }


    @SuppressLint("NewApi")
    public static void enableTranslucentStatusBar(Activity activity) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
