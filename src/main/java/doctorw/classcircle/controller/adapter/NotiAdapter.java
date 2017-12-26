package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.Noti;

/**
 * Created by asus on 2017/5/1.
 */

public class NotiAdapter extends CommonAdapter<Noti>{

    public NotiAdapter(Context context, List<Noti> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Noti noti) {
        holder.setText(R.id.tv_notititle,noti.getTitle());
        holder.setText(R.id.tv_notitime,noti.getTime());
        holder.setText(R.id.tv_noticontext,noti.getContext());

    }
}
