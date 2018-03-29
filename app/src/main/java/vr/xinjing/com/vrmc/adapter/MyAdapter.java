package vr.xinjing.com.vrmc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vr.xinjing.com.vrmc.R;
import vr.xinjing.com.vrmc.adapter.abs.AbstractAdapter;
import vr.xinjing.com.vrmc.adapter.abs.ViewHolder;
import vr.xinjing.com.vrmc.bean.Prescription;

/**
 * Created by 123 on 2017/2/4.
 */

public class MyAdapter extends AbstractAdapter<Prescription.DataBean> {

    public MyAdapter(Context context, List<Prescription.DataBean> listData, int resId) {
        super(context, listData, resId);
    }

    @Override
    public void convert(ViewHolder holder, Prescription.DataBean bean) {
        TextView tv_date = holder.getView(R.id.list_date);
        TextView tv_doctor = holder.getView(R.id.list_doctor);
        TextView tv_style = holder.getView(R.id.list_style);
        TextView tv_content = holder.getView(R.id.list_content);
        tv_date.setText(bean.getCreatedAt().substring(0,11));
        tv_doctor.setText(bean.getDoctorName());
        if (bean.getSource() == 1){
            tv_style.setText("线上");
        }else if (bean.getSource() == 2){
            tv_style.setText("线下");
        }
        tv_content.setText(bean.getDisease());
    }
}
