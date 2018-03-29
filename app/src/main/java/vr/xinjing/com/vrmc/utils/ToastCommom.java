package vr.xinjing.com.vrmc.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 *
 * @author Administrator
 *
 */
public class ToastCommom {
    private static ToastCommom toastCommom;

    private Toast toast;

    private ToastCommom(){
    }

    public static ToastCommom createInstance(){
        if (toastCommom==null) {
            toastCommom = new ToastCommom();
        }
        return toastCommom;
    }

    /**
     * 显示Toast
     * @param context
     *
     * @param tvString
     */

    public void ToastShow(Context context,String tvString){
        toast =Toast.makeText(context,tvString,Toast.LENGTH_SHORT);
        toast.show();
    }
}
