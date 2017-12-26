package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.ArticleComment;

/**
 * Created by asus on 2017/5/9.
 */

public class ArticleCommentAdapter extends CommonAdapter<ArticleComment>{


    public ArticleCommentAdapter(Context context, List<ArticleComment> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ArticleComment articleComment) {
        holder.setCircleImageURI(R.id.dt_civ_headphoto,articleComment.getHeadPhoto());
        holder.setText(R.id.tv_name,articleComment.getName());
        holder.setText(R.id.tv_comment,articleComment.getComment());
    }
}
