package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.ImagePagerActivity;
import doctorw.classcircle.model.bean.CircleDongTai;
import doctorw.classcircle.model.bean.RecordPlayer;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.goodview.GoodView;
import doctorw.classcircle.view.NoScrollGridView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by asus on 2017/5/10.
 */

public class XQCircleDtAdapter extends BaseAdapter {

    private Context mContext;
    private List<CircleDongTai> mCircleDongTais = new ArrayList<>();
    private OnCircleListener mOnCircleListener;
    private RecordPlayer recordPlayer;
    private GoodView mGoodView;

    public XQCircleDtAdapter(Context mContext, List<CircleDongTai> mCircleDongTais, OnCircleListener mOnCircleListener) {
        this.mContext = mContext;
        this.mCircleDongTais = mCircleDongTais;
        this.mOnCircleListener = mOnCircleListener;
        mGoodView = new GoodView(mContext);
    }

    @Override
    public int getCount() {
        return mCircleDongTais.size();
    }

    @Override
    public Object getItem(int position) {
        return mCircleDongTais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_xqdongtai, null);

            holder.dt_civ_headphoto = (CircleImageView) convertView.findViewById(R.id.dt_civ_headphoto);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.dt_gridView = (NoScrollGridView) convertView.findViewById(R.id.dt_gridView);
            holder.jc_movie = (JCVideoPlayerStandard) convertView.findViewById(R.id.jc_movie);
            holder.ll_voice = (LinearLayout) convertView.findViewById(R.id.ll_voice);
            holder.bt_voice = (Button) convertView.findViewById(R.id.bt_voice);
            holder.tv_voicetime = (TextView) convertView.findViewById(R.id.tv_voicetime);
            holder.tv_cirname = (TextView) convertView.findViewById(R.id.tv_cirname);
            holder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            holder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            holder.tv_commentnum = (TextView) convertView.findViewById(R.id.tv_commentnum);
            holder.ll_love = (LinearLayout) convertView.findViewById(R.id.ll_love);
            holder.iv_love = (ImageView) convertView.findViewById(R.id.iv_love);
            holder.tv_lovenum = (TextView) convertView.findViewById(R.id.tv_lovenum);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CircleDongTai mCircleDongTai = mCircleDongTais.get(position);
        //头像
        Glide.with(mContext).load(R.drawable.baby).into(holder.dt_civ_headphoto);
        //用户的名字 时间
        holder.tv_name.setText(mCircleDongTai.getUserName());
        holder.tv_date.setText(mCircleDongTai.getCreateTime());
        //对下拉框处理
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickMore(position);
            }
        });

        //动态的标题 内容
        holder.tv_title.setText(mCircleDongTai.getTitle());
//        holder.tv_title.setText("biaoti");
        holder.tv_content.setText(mCircleDongTai.getContent());

        //照片的处理
        showPhoto(mCircleDongTai, holder.dt_gridView);

        //视频的处理
        String url = mCircleDongTai.getMovieUrl();
        if (TextUtils.isEmpty(url)) {
            holder.jc_movie.setVisibility(View.GONE);
        } else {
            Log.e("test_video", url);
            holder.jc_movie.setVisibility(View.VISIBLE);
            holder.jc_movie.setUp(url, JCVideoPlayer.CURRENT_STATE_NORMAL, "");
//                viewHolder.jc_movie.thumbImageView.setImageBitmap(CommonUtil.createVideoThumbnail(url));
            holder.jc_movie.thumbImageView.setImageBitmap(CommonUtil.getVideoBitmap(url));
//                detailDanmu(viewHolder,dongTaiBean);
        }

        //音频的处理
        holder.ll_voice.setVisibility(TextUtils.isEmpty(mCircleDongTai.getVideoUrl()) ? View.GONE : View.VISIBLE);
        Log.e("test_", mCircleDongTai.getVideoUrl());
        if (!TextUtils.isEmpty(mCircleDongTai.getVideoUrl())) {
            MediaPlayer md = new MediaPlayer();

            try {
                md.setDataSource(mCircleDongTai.getVideoUrl());
                md.prepare();
                holder.tv_voicetime.setText("" + (md.getDuration() / 1000));

                recordPlayer = new RecordPlayer(mContext, mCircleDongTai.getVideoUrl(), holder.bt_voice);
            } catch (IOException e) {
                e.printStackTrace();
            }
//                RecordPlayer recordPlayer = new RecordPlayer(mContext, dongTaiBean.getVideoUrl(), viewHolder.bt_voice);
        }
        holder.bt_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordPlayer != null) {
                    recordPlayer.playRecordFile();
                } else {
                    recordPlayer.stopPalyer();
                }
            }
        });
//            viewHolder.jc_movie.setVisibility(TextUtils.isEmpty(dongTaiBean.getMovieUrl()) ? View.GONE : View.VISIBLE);

        //兴趣圈的名字
        holder.tv_cirname.setText(mCircleDongTai.getName());

        //分享
        holder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickShare(position);
            }
        });


        //评论
        holder.tv_commentnum.setText("" + mCircleDongTai.getComments().size());
        holder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickComment(position);
            }
        });


        //喜欢
        holder.iv_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (holder.iv_love).setImageResource(R.drawable.icon_lovepressed);
                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.icon_lovepressed));
                mGoodView.show(holder.iv_love);
            }
        });
        holder.tv_lovenum.setText("" + mCircleDongTai.getFavorters().size());
        holder.ll_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickLove(position);
            }
        });

        //还有一些点击事件 点击查看详情
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickComment(position);
            }
        });

        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCircleListener.OnClickComment(position);
            }
        });


        return convertView;
    }

    private void showPhoto(CircleDongTai mCircleDongTai, NoScrollGridView dt_gridView) {
        //照片需要显示
        if (mCircleDongTai.getPhotos() != null && mCircleDongTai.getPhotos().size() > 0) {
            dt_gridView.setVisibility(View.VISIBLE);
            //图片地址集合转化为数组
            final String[] photoUrls = mCircleDongTai.getPhotos().toArray(new String[mCircleDongTai.getPhotos().size()]);
            dt_gridView.setAdapter(new CircleGridAdapter(photoUrls));
            dt_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    enterPhotoDetailed(photoUrls, position);
                }
            });
        } else {
            //没有照片，影藏
            dt_gridView.setVisibility(View.GONE);
        }
    }

    /**
     * 进入图片详情页
     * //     * @param array 图片数组
     *
     * @param position 角标
     */
    protected void enterPhotoDetailed(String[] urls, int position) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    public interface OnCircleListener {
        void OnClickMore(int position);

        void OnClickShare(int position);

        void OnClickComment(int position);

        void OnClickLove(int position);
    }


    private static class ViewHolder {
        private CircleImageView dt_civ_headphoto;
        private TextView tv_name;
        private TextView tv_date;
        private ImageView iv_more;
        private TextView tv_title;
        private TextView tv_content;
        private NoScrollGridView dt_gridView;
        private JCVideoPlayerStandard jc_movie;
        private LinearLayout ll_voice;
        private Button bt_voice;
        private TextView tv_voicetime;
        private TextView tv_cirname;
        private LinearLayout ll_share;
        private LinearLayout ll_comment;
        private TextView tv_commentnum;
        private LinearLayout ll_love;
        private ImageView iv_love;
        private TextView tv_lovenum;
    }

    // 刷新数据
    public void refresh(List<CircleDongTai> mCircleDongTais_) {
        if (mCircleDongTais_ != null && mCircleDongTais_.size() >= 0) {
            mCircleDongTais.clear();
            mCircleDongTais.addAll(0, mCircleDongTais_);
        }
        notifyDataSetChanged();
    }
}
