package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import doctorw.classcircle.R;

public class MembersAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflr;
    private int itemtype = 1;
    private OnRemoveListener mRemoveListener;
    private List<String> members;

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public MembersAdapter(Context context, List<String> members) {
        this.mContext = context;
        this.layoutInflr = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.members = members;
    }

    public void setRemoveListener(OnRemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
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
            convertView = layoutInflr.inflate(R.layout.item_members_listview,
                    null);
            viewHolder.tvDelete = (TextView) convertView
                    .findViewById(R.id.tvDelete);
//            viewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.dt_civ_headphoto);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_userName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//		ImageLoader.getInstance().displayImage(R.drawable.default_touxiang,convertView.circleImageView);
//		viewHolder.circleImageView.setImageResource(R.drawable.default_touxiang);
        viewHolder.tvUserName.setText(members.get(position).toString());

        //对删除时间进行监听
        viewHolder.tvDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemoveListener != null)
                    mRemoveListener.onRemoveItem(position);
            }
        });
        return convertView;
    }

    public interface OnRemoveListener {
        void onRemoveItem(int position);
    }

    private static class ViewHolder {
        //		private CircleImageView circleImageView;
        private TextView tvUserName;
        private TextView tvDelete;
    }

    // 刷新数据
    public void refresh(List<String> members_) {
        if (members_ != null && members_.size() >= 0) {
            members.clear();
            members.addAll(0, members_);
        }
        notifyDataSetChanged();
    }
}
