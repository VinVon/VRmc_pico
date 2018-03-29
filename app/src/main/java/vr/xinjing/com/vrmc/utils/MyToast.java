package vr.xinjing.com.vrmc.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import vr.xinjing.com.vrmc.R;

/**
 * Created by raytine on 2017/4/17.
 */

public class MyToast {
    private Toast mToast;
    private MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
        TextView tv  = (TextView) v.findViewById(R.id.textView1);
        TextView tv2  = (TextView) v.findViewById(R.id.textView2);
        tv.setText(text);
        tv2.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
