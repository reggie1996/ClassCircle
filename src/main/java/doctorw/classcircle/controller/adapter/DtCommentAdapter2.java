package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.CommentItem;

/**
 * Created by asus on 2017/5/9.
 */

public class DtCommentAdapter2 extends CommonAdapter<CommentItem> {


    public DtCommentAdapter2(Context context, List<CommentItem> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CommentItem commentItem) {
        holder.setText(R.id.tv_name,commentItem.getToUserName());
        holder.setText(R.id.tv_comment,commentItem.getText());
    }
}
