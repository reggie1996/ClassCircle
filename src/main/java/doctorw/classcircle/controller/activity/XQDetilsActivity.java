package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.CircleGridAdapter;
import doctorw.classcircle.controller.adapter.DtCommentAdapter2;
import doctorw.classcircle.model.bean.CircleDongTai;
import doctorw.classcircle.model.bean.CommentItem;
import doctorw.classcircle.model.bean.RecordPlayer;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.utils.goodview.GoodView;
import doctorw.classcircle.view.MyPopupWindow;
import doctorw.classcircle.view.NoScrollGridView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;

public class XQDetilsActivity extends BaseActivity {

    @Bind(R.id.dt_civ_headphoto)
    CircleImageView dtCivHeadphoto;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.dt_gridView)
    NoScrollGridView dtGridView;
    @Bind(R.id.jc_movie)
    JCVideoPlayerStandard jcMovie;
    @Bind(R.id.bt_voice)
    Button btVoice;
    @Bind(R.id.tv_voicetime)
    TextView tvVoicetime;
    @Bind(R.id.ll_voice)
    LinearLayout llVoice;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.bt_send)
    Button btSend;
    @Bind(R.id.iv_comment)
    ImageView ivComment;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.iv_favour)
    ImageView ivFavour;
    private Context mContext;

    private CircleDongTai circleDongTai;
    private RecordPlayer recordPlayer;
    private List<CommentItem> commentItems = new ArrayList<>();
    private DtCommentAdapter2 mAdapter;
    private GoodView mGoodView;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_xq_dong_tai_detail);
        setContentView(R.layout.activity_xqdetils);


    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        circleDongTai = bundle.getParcelable("circleDongTai");
        Glide.with(mContext).load(R.drawable.baby).into(dtCivHeadphoto);
        tvName.setText(circleDongTai.getUserName());
        tvDate.setText(circleDongTai.getCreateTime());

        tvTitle.setText(circleDongTai.getTitle());
        tvContent.setText(circleDongTai.getContent());

        //照片的处理
        showPhoto(circleDongTai, dtGridView);

        //视频的处理
        String url = circleDongTai.getMovieUrl();
        if (TextUtils.isEmpty(url)) {
            jcMovie.setVisibility(View.GONE);
        } else {
            Log.e("test_video", url);
            jcMovie.setVisibility(View.VISIBLE);
            jcMovie.setUp(url, JCVideoPlayer.CURRENT_STATE_NORMAL, "");
//                viewHolder.jc_movie.thumbImageView.setImageBitmap(CommonUtil.createVideoThumbnail(url));
            jcMovie.thumbImageView.setImageBitmap(CommonUtil.getVideoBitmap(url));
//                detailDanmu(viewHolder,dongTaiBean);
        }


        //音频的处理
        llVoice.setVisibility(TextUtils.isEmpty(circleDongTai.getVideoUrl()) ? View.GONE : View.VISIBLE);

        if (!TextUtils.isEmpty(circleDongTai.getVideoUrl())) {
            MediaPlayer md = new MediaPlayer();

            try {
                md.setDataSource(circleDongTai.getVideoUrl());
                md.prepare();
                tvVoicetime.setText("" + (md.getDuration() / 1000));

                recordPlayer = new RecordPlayer(mContext, circleDongTai.getVideoUrl(), btVoice);
            } catch (IOException e) {
                e.printStackTrace();
            }
//                RecordPlayer recordPlayer = new RecordPlayer(mContext, dongTaiBean.getVideoUrl(), viewHolder.bt_voice);
        }
        btVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordPlayer != null) {
                    recordPlayer.playRecordFile();
                } else {
                    recordPlayer.stopPalyer();
                }
            }
        });

        commentItems = circleDongTai.getComments();
        mAdapter = new DtCommentAdapter2(mContext, commentItems, R.layout.item_article_comment);
        lvComment.setAdapter(mAdapter);
        tvNum.setText("" + commentItems.size());

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_more, R.id.bt_send, R.id.iv_comment, R.id.iv_favour})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.bt_send:
                sendComment();
                break;
            case R.id.iv_favour:
                mGoodView = new GoodView(mContext);
                ivFavour.setImageResource(R.drawable.icon_lovepressed);
                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.icon_lovepressed));
                mGoodView.show(ivFavour);
                OkGo.post(Urls.URL_INTERESTLOVE)
                        .params("infoId",circleDongTai.getDongtaiId())
                        .params("userNo", SpUtils.getInstance().getString("userid",""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {

                            }
                        });
                break;
        }
    }

    private void sendComment() {
        final String comment = etComment.getText().toString();
        HashMap<String,String> params = new HashMap<>();
        params.put("infoId",circleDongTai.getDongtaiId());
        params.put("userNo",SpUtils.getInstance().getString("userid",""));
        params.put("text",comment);
        OkGo.post(Urls.URL_INTERESTCOMMENT)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                commentItems.add(new CommentItem(comment,"小野"));
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }
    private void showPopupWindow() {
        MyPopupWindow popupWindow = new MyPopupWindow(this);
        String[] str = {"举报", "删除"};
        popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
            @Override
            public void callback(String text, final int position) {
                switch (position) {
                    case 0: //对于不好的动态向系统举报
                        showToast("举报成功，等待处理");
                        break;
                    case 1: //对于不好的动态向系统举报
                        OkGo.post(Urls.URL_DELETECIRCLEDT)
                                .params("infoId", circleDongTai.getDongtaiId())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        showToast("删除成功");
                                        finish();
                                        removeActivity();
                                    }
                                });
                        break;
                }
            }
        });
    }
}
