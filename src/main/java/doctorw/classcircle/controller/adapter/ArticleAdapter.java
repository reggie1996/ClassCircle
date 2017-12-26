package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.Article;

/**
 * Created by asus on 2017/4/25.
 */

public class ArticleAdapter extends CommonAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Article article) {
        holder.setText(R.id.tv_art_title, article.getTitle());
        holder.setText(R.id.tv_art_read, article.getReadNum());
        holder.setText(R.id.tv_art_favor, article.getFavorNum());
        holder.setText(R.id.tv_art_comment, article.getCommentNum());
        holder.setImageURI(R.id.iv_art_pic, article.getPicArt());
    }
}
