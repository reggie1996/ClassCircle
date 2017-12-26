package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.test;

public class CommonFrameFragmentAdapter extends CommonAdapter<test> {
    public CommonFrameFragmentAdapter(Context context, List<test> datas, int layoutId) {
        super(context, datas, layoutId);
    }
    @Override
    public void convert(ViewHolder holder, test test) {
        holder.setText(R.id.testname,test.getName());
    }
}
