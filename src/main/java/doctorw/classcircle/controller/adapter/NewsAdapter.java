package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.Article;

public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflr;
    private int itemtype = 1;
    private OnRemoveListener mRemoveListener;

    private List<Article> articles = new ArrayList<>();


    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public NewsAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.layoutInflr = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.articles = articles;
    }

    public void setRemoveListener(OnRemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
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
            convertView = layoutInflr.inflate(R.layout.item_article_slide,
                    null);
            viewHolder.tvDelete = (TextView) convertView
                    .findViewById(R.id.tvDelete);
            viewHolder.iv_art_pic = (ImageView) convertView.findViewById(R.id.iv_art_pic);
            viewHolder.tv_art_title = (TextView) convertView.findViewById(R.id.tv_art_title);
            viewHolder.tv_art_read = (TextView) convertView.findViewById(R.id.tv_art_read);
            viewHolder.tv_art_comment = (TextView) convertView.findViewById(R.id.tv_art_comment);
            viewHolder.tv_art_favor = (TextView) convertView.findViewById(R.id.tv_art_favor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Article article = articles.get(position);

        viewHolder.tv_art_title.setText(article.getTitle());
        viewHolder.tv_art_comment.setText(article.getCommentNum());
        viewHolder.tv_art_favor.setText(article.getFavorNum());
        viewHolder.tv_art_read.setText(article.getReadNum());
        Glide.with(mContext).load(article.getWeburl()).into(viewHolder.iv_art_pic);

        //对删除事件进行监听
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

        private TextView tv_art_title;
        private TextView tv_art_read;
        private TextView tv_art_comment;
        private TextView tv_art_favor;
        private ImageView iv_art_pic;
        private TextView tvDelete;
    }

    // 刷新数据
    public void refresh(List<Article> articles_) {
        if (articles_ != null && articles_.size() >= 0) {
            articles.clear();
            articles.addAll(0, articles_);
        }
        notifyDataSetChanged();
    }
}
