package vr.xinjing.com.vrmc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import vr.xinjing.com.vrmc.PlayerActivity;
import vr.xinjing.com.vrmc.R;
import vr.xinjing.com.vrmc.adapter.abs.AbstractAdapter;
import vr.xinjing.com.vrmc.adapter.abs.ViewHolder;
import vr.xinjing.com.vrmc.bean.Prescription;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;

/**
 * 删除
 * Created by 123 on 2017/2/4.
 */

public class MyAdapterC extends AbstractAdapter<PrescriptionContent.DataBean> {
    Callplat call;
    public MyAdapterC(Context context, List<PrescriptionContent.DataBean> listData, int resId,Callplat call) {
        super(context, listData, resId);
        this.call = call;
    }

    @Override
    public void convert(ViewHolder holder, final PrescriptionContent.DataBean bean) {
        TextView tv_name = holder.getView(R.id.mlist_name);
        TextView tv_style= holder.getView(R.id.mlist_style);
        TextView tv_num = holder.getView(R.id.mlist_num);
        TextView tv_numcode = holder.getView(R.id.mlist_numcode);
//        TextView tv_date = holder.getView(R.id.mlist_date);
        TextView tv_paly = holder.getView(R.id.mlist_paly);
        tv_name.setText(bean.getContent_name());
        if (bean.getContent_type() == 1){
            tv_style.setText("视频");
        }else if (bean.getContent_type() == 2){
            tv_style.setText("音频");
        }else{
            tv_style.setText("图片集");
        }

        tv_num.setText(bean.getTimes()+"");
        tv_numcode.setText(bean.getUseTimes()+"");
//        tv_date.setText(bean.getUpdatedAt());
        tv_paly.setText("操作");
        tv_paly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.onsuccess(bean);

            }
        });
    }
    public interface  Callplat{
        void onsuccess(PrescriptionContent.DataBean id);
    };

}
