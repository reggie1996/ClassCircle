package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.CircleDetail;

/**
 * Created by asus on 2017/5/10.
 */

public class CircleItemAdapter extends BaseAdapter {

    private Context mContext;

    private List<CircleDetail> circleDetails;
    private OnAddCircleListener mOnAddCircleListener;

    public CircleItemAdapter(Context mContext, List<CircleDetail> circleDetails,OnAddCircleListener mOnAddCircleListener) {
        this.mContext = mContext;
        this.circleDetails = circleDetails;
        this.mOnAddCircleListener = mOnAddCircleListener;
    }

    @Override
    public int getCount() {
        return circleDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return circleDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_circle, null);
            viewHolder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num1);
            viewHolder.bt_add = (Button) convertView.findViewById(R.id.bt_add);
            viewHolder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CircleDetail circleDetail = circleDetails.get(position);

        viewHolder.tv_name.setText(circleDetail.getCirName());
        viewHolder.tv_num.setText(circleDetail.getPerNum());
        Glide.with(mContext).load(circleDetail.getHeadUrl()).into(viewHolder.iv_head);

        //加入监听器
        viewHolder.bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddCircleListener.OnAddCircleItem(position);
            }
        });
        //加入监听器
        viewHolder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddCircleListener.OnEnterItem(position);
            }
        });
        //加入监听器
        viewHolder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddCircleListener.OnEnterItem(position);
            }
        });



        return convertView;
    }


    // 刷新数据
    public void refresh(List<CircleDetail> circleDetails_) {
        if (circleDetails_ != null && circleDetails_.size() >= 0) {
            circleDetails.clear();
            circleDetails.addAll(0, circleDetails_);
        }
        notifyDataSetChanged();
    }


    private static class ViewHolder {
        private ImageView iv_head;
        private TextView tv_name;
        private TextView tv_num;
        private Button bt_add;
        private LinearLayout ll_main;
    }

    public interface OnAddCircleListener {
        void OnAddCircleItem(int position);
        void OnEnterItem(int position);
    }

}
