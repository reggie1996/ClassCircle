package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.CircleDetail;

/**
 * Created by asus on 2017/5/10.
 */

public class CircleItemAdapter2 extends CommonAdapter<CircleDetail> {

    public CircleItemAdapter2(Context context, List<CircleDetail> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CircleDetail circleDetail) {
        holder.setText(R.id.tv_name,circleDetail.getCirName());
        holder.setText(R.id.tv_num,circleDetail.getPerNum());
        holder.setImageURI(R.id.iv_head,circleDetail.getHeadUrl());
    }
}
