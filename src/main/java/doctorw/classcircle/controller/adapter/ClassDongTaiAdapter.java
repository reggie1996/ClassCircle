package doctorw.classcircle.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.BaseActivity;
import doctorw.classcircle.controller.activity.DongTaiDetailActivity;
import doctorw.classcircle.controller.activity.ImagePagerActivity;
import doctorw.classcircle.controller.activity.SelfDongTaiActivity;
import doctorw.classcircle.model.bean.CommentItem;
import doctorw.classcircle.model.bean.DongTai;
import doctorw.classcircle.model.bean.FavortItem;
import doctorw.classcircle.model.bean.RecordPlayer;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.MeasureUtils;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.SystemUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.utils.cache.ImageLoaderCache;
import doctorw.classcircle.utils.goodview.GoodView;
import doctorw.classcircle.view.CommentListView;
import doctorw.classcircle.view.NoScrollGridView;
import doctorw.classcircle.view.PraiseListView;
import doctorw.classcircle.view.YEditText;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/28.
 */

public class ClassDongTaiAdapter extends BaseAdapter {

    private PopupWindow mPopWindow, mPopWindowDelete;
    private Context mContext;
    private List<DongTai> mDongTais;
    private View.OnClickListener lis;
    private GoodView mGoodView;
    private Boolean isZan;


    private String id;
    private BaseActivity mActivity = null;
    private FragmentActivity fActivity = null;
    private RecordPlayer recordPlayer ;
    private int type = 0;

    public ClassDongTaiAdapter(Context mContext, List<DongTai> dongTais, BaseActivity mActivity) {
        this.mContext = mContext;
        this.mDongTais = dongTais;
        this.mActivity = mActivity;
        mGoodView = new GoodView(mContext);
    }

    public ClassDongTaiAdapter(Context mContext, List<DongTai> dongTais, FragmentActivity fActivity) {
        this.mContext = mContext;
        this.mDongTais = dongTais;
        this.fActivity = fActivity;
        mGoodView = new GoodView(mContext);
    }


    @Override
    public int getCount() {
        return mDongTais.size();
    }

    @Override
    public Object getItem(int position) {
        return mDongTais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dongtai_item, null, true);
            viewHolder = new ViewHolder();

            //头像 名字
            viewHolder.dt_civ_headphoto = (CircleImageView) convertView.findViewById(R.id.dt_civ_headphoto);
            viewHolder.dt_tv_name = (TextView) convertView.findViewById(R.id.dt_tv_name);

            //下弹选项
            viewHolder.dt_iv_popchoose = (ImageView) convertView.findViewById(R.id.dt_iv_popchoose);

            //说说正文
            viewHolder.dt_tv_content = (TextView) convertView.findViewById(R.id.dt_tv_content);
            //照片，录音，视频显示
            viewHolder.dt_gridView = (NoScrollGridView) convertView.findViewById(R.id.dt_gridView);


            viewHolder.jc_movie = (JCVideoPlayerStandard) convertView.findViewById(R.id.jc_movie);
            viewHolder.fl_movie = (FrameLayout) convertView.findViewById(R.id.fl_movie);
//            viewHolder.danmakuView = (DanmakuView) convertView.findViewById(R.id.danmakuView);

            viewHolder.ll_voice = (LinearLayout) convertView.findViewById(R.id.ll_voice);
            viewHolder.bt_voice = (Button) convertView.findViewById(R.id.bt_voice);
            viewHolder.tv_voicetime = (TextView) convertView.findViewById(R.id.tv_voicetime);

            //日期，时间
            viewHolder.dt_tv_date = (TextView) convertView.findViewById(R.id.dt_tv_date);
            viewHolder.dt_tv_time = (TextView) convertView.findViewById(R.id.dt_tv_time);

            //点赞 评论 转发（先不写）
            viewHolder.dt_iv_dianzan = (ImageView) convertView.findViewById(R.id.dt_iv_dianzan);
            viewHolder.dt_iv_comment = (ImageView) convertView.findViewById(R.id.dt_iv_comment);
            viewHolder.dt_iv_share = (ImageView) convertView.findViewById(R.id.dt_iv_share);

            //评论部分，点赞表，分割线，评论表
            viewHolder.dt_CommentBody = (LinearLayout) convertView.findViewById(R.id.dt_CommentBody);
            viewHolder.praiseListView = (PraiseListView) convertView.findViewById(R.id.praiseListView);
            viewHolder.dt_lin = convertView.findViewById(R.id.dt_lin);
            viewHolder.commentList = (CommentListView) convertView.findViewById(R.id.commentList);

            viewHolder.btn_send = (Button) convertView.findViewById(R.id.btn_send);
            viewHolder.et_comment = (YEditText) convertView.findViewById(R.id.et_comment);
            viewHolder.ll_blank = (LinearLayout) convertView.findViewById(R.id.ll_blank);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DongTai dongTaiBean = mDongTais.get(position);

        if (dongTaiBean != null) {
            //设置头像
            ImageLoaderCache.getInstance().DisplayImage(dongTaiBean.getHeadUrl(), viewHolder.dt_civ_headphoto);
//            ImageLoader.getInstance().displayImage(dongTaiBean.getHeadUrl(), viewHolder.dt_civ_headphoto);
            viewHolder.dt_civ_headphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //传递用户ID，根据ID
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", dongTaiBean.getUserId());
                    Intent intent = new Intent(mContext, SelfDongTaiActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            viewHolder.ll_blank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("dongtai", dongTaiBean);
                    Intent intent = new Intent(mContext, DongTaiDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
//                    mContext.startActivity((DongTaiDetailActivity.class).putExtra("dt",bundle);
                }
            });
            //设置昵称
            viewHolder.dt_tv_name.setText(dongTaiBean.getUserName());
            //设置说说
            viewHolder.dt_tv_content.setText(dongTaiBean.getContent());
            viewHolder.dt_tv_content.setVisibility(TextUtils.isEmpty(dongTaiBean.getContent()) ? View.GONE : View.VISIBLE);
            //设置时间，日期
            viewHolder.dt_tv_date.setText(dongTaiBean.getCreateTime());

            //下拉框的处理
            viewHolder.dt_iv_popchoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindows(viewHolder, dongTaiBean, position);
                }
            });

            //视频的处理
            String url = dongTaiBean.getMovieUrl();
            viewHolder.fl_movie.setVisibility(TextUtils.isEmpty(url) ? View.GONE : View.VISIBLE);
            if (TextUtils.isEmpty(url)) {
                viewHolder.jc_movie.setVisibility(View.GONE);
            } else {
                Log.e("test_video", url);
                viewHolder.jc_movie.setVisibility(View.VISIBLE);
                viewHolder.jc_movie.setUp(url, JCVideoPlayer.CURRENT_STATE_NORMAL, "");
//                viewHolder.jc_movie.thumbImageView.setImageBitmap(CommonUtil.createVideoThumbnail(url));
                viewHolder.jc_movie.thumbImageView.setImageBitmap(CommonUtil.getVideoBitmap(url));
//                detailDanmu(viewHolder,dongTaiBean);
            }


            //音频的处理
            viewHolder.ll_voice.setVisibility(TextUtils.isEmpty(dongTaiBean.getVideoUrl()) ? View.GONE : View.VISIBLE);
            Log.e("test_",dongTaiBean.getVideoUrl());
            if (!TextUtils.isEmpty(dongTaiBean.getVideoUrl())) {
                MediaPlayer md = new MediaPlayer();

                try {
                    md.setDataSource(dongTaiBean.getVideoUrl());
                    md.prepare();
                    viewHolder.tv_voicetime.setText("" + (md.getDuration() / 1000));

                    recordPlayer = new RecordPlayer(mContext,dongTaiBean.getVideoUrl(),viewHolder.bt_voice);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                RecordPlayer recordPlayer = new RecordPlayer(mContext, dongTaiBean.getVideoUrl(), viewHolder.bt_voice);
            }
            viewHolder.bt_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (recordPlayer != null){
                        recordPlayer.playRecordFile();
                    }else{
                        recordPlayer.stopPalyer();
                    }

                }
            });
//            viewHolder.jc_movie.setVisibility(TextUtils.isEmpty(dongTaiBean.getMovieUrl()) ? View.GONE : View.VISIBLE);


            //设置点赞 评论 转发的点击事件
            isZan = checkZan(dongTaiBean.getFavorters());
            if (isZan) {
                viewHolder.dt_iv_dianzan.setImageResource(R.drawable.good_checked);
            }


            viewHolder.dt_iv_dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("test", isZan.toString());
                    if (isZan) {//已经点赞，取消赞
                        viewHolder.dt_iv_dianzan.setImageResource(R.drawable.icon_dianzan);
                        int loc = -1;
                        for (FavortItem item : dongTaiBean.getFavorters()) {
                            if (item.getUserId().equals(SpUtils.getInstance().getString("userid", ""))) {
                                loc++;
                                break;
                            } else {
                                loc++;
                            }
                        }
                        isZan = false;
//                        dongTaiBean.getFavorters().remove(loc);
                        notifyDataSetChanged();
                    } else {
                        (viewHolder.dt_iv_dianzan).setImageResource(R.drawable.good_checked);
                        mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.good_checked));
                        mGoodView.show(viewHolder.dt_iv_dianzan);
                        isZan = true;

                        OkGo.post(Urls.URL_DIANZAN)
                                .params("userNo", SpUtils.getInstance().getString("userid", ""))
                                .params("infoId", dongTaiBean.getDongtaiId())
                                .tag(this)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
//                                        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                                        dongTaiBean.getFavorters().add(new FavortItem("userid", SpUtils.getInstance().getString("userid", "")));
                                        notifyDataSetChanged();
                                    }
                                });
//
                    }
                }
            });


            //发送评论
            viewHolder.dt_iv_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.et_comment.setFocusable(true);
                    viewHolder.et_comment.setEnabled(true);
                    viewHolder.et_comment.setFocusableInTouchMode(true);
                    viewHolder.et_comment.requestFocus();
                    MeasureUtils.showKeyboard(mContext, viewHolder.et_comment);
//                    mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    Toast.makeText(mContext, "comment", Toast.LENGTH_SHORT).show();
                    type = 0;
                }
            });

            viewHolder.btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String comment = viewHolder.et_comment.getText().toString().trim();
                    if (comment.isEmpty()) {
                        Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
                        SystemUtils.hideKeyboard(mContext, viewHolder.et_comment.getWindowToken());
                        viewHolder.et_comment.setText("");
                        SystemUtils.hideKeyboard(mContext, viewHolder.et_comment.getWindowToken());
                        return;
                    } else {//不为空 发送
                        viewHolder.et_comment.setText("");
                        SystemUtils.hideKeyboard(mContext, viewHolder.et_comment.getWindowToken());


                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("infoId", dongTaiBean.getDongtaiId());
                        params.put("userNo", SpUtils.getInstance().getString("userid", ""));
                        if (type == 0) {
                            params.put("toUserNo", null);
                            final List<CommentItem> commentsDatas = dongTaiBean.getComments();
                            CommentItem com = new CommentItem("001", comment, null, null, SpUtils.getInstance().getString("myname", "小野老师"), SpUtils.getInstance().getString("userid", ""));
                            commentsDatas.add(com);
                        } else {
                            params.put("toUserNo", id);
//                            CommentItem com = new CommentItem("001",comment,null,null,SpUtils.getInstance().getString("myname","小野老师"),SpUtils.getInstance().getString("userid", ""));

                        }


                        notifyDataSetChanged();
                        params.put("text", comment);
                        OkGo.post(Urls.URL_COMMENT)
                                .tag(this)
                                .params(params)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
//                                        Toast.makeText(mContext, "chegngong", Toast.LENGTH_SHORT).show();
                                        type = 0;
                                    }
                                });
                    }
                }
            });
            viewHolder.dt_iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
                }
            });

            //显示评论
            showComment(viewHolder, dongTaiBean);
            //照片显示
            showPhoto(dongTaiBean, viewHolder);
        }
        return convertView;
    }

//    //处理弹幕
//    private void detailDanmu(ViewHolder viewHolder, DongTai dongTaiBean) {
//        List<IDanmakuItem> list = DatasUtil.initItems(viewHolder.danmakuView,mContext);
//        //变成随机数据
//        Collections.shuffle(list);
//        //添加到弹幕控件里面
//        viewHolder.danmakuView.addItem(list, true);
//    }


    private Boolean checkZan(List<FavortItem> favortDatas) {
        Boolean b = false;
        String UserId = SpUtils.getInstance().getString("userid", "");
        if (favortDatas.size() == 0 || favortDatas == null) {
            b = false;
        } else {
            for (FavortItem item : favortDatas) {
                if (item.getUserId().equals(UserId)) {
                    b = true;
                    break;
                }
            }
        }
        Log.e("testb", b.toString());
        return b;
    }

    //长按删除评论
    private void showPopupWindowsDelete(ViewHolder viewHolder, final List<CommentItem> commentsDatas, final int position) {

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.delete, null);
        mPopWindowDelete = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopWindowDelete.setBackgroundDrawable(cd);

        // 产生背景变暗效果
        WindowManager.LayoutParams lp;
        if (mActivity == null) {
            lp = fActivity.getWindow()
                    .getAttributes();
            lp.alpha = 0.4f;
            fActivity.getWindow().setAttributes(lp);
        } else {
            lp = mActivity.getWindow()
                    .getAttributes();
            lp.alpha = 0.4f;
            mActivity.getWindow().setAttributes(lp);
        }


        // 点击popup之外的

        mPopWindowDelete.setOutsideTouchable(true);
        mPopWindowDelete.setFocusable(true);
        mPopWindowDelete.showAtLocation(viewHolder.dt_iv_comment, Gravity.CENTER, 0, 0);
//        mPopWindowDelete.showAtLocation(viewHolder.dt_CommentBody.getParent(), Gravity.CENTER
//                | Gravity.CENTER_HORIZONTAL, 0, 0);
        contentView.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtils.getInstance().getString("type", "").equals(Constants.TYPE_TEACHER) || SpUtils.getInstance().getString("userid", "").equals(commentsDatas.get(position).getUserNo())) {
                    //可以删除
                    OkGo.post(Urls.URL_DELCOMMENT)
                            .tag(this)
                            .params("id", commentsDatas.get(position).getCommentId())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();

                                    commentsDatas.remove(position);
                                    notifyDataSetChanged();

                                }
                            });
                } else {//不可以删除者条评论
                    Toast.makeText(mContext, "您不可以删除", Toast.LENGTH_SHORT).show();
                }
                mPopWindowDelete.dismiss();
            }
        });

        mPopWindowDelete.update();
        mPopWindowDelete.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp;
                if (mActivity == null) {
                    lp = fActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 1f;
                    fActivity.getWindow().setAttributes(lp);
                } else {
                    lp = mActivity.getWindow()
                            .getAttributes();
                    lp.alpha = 1f;
                    mActivity.getWindow().setAttributes(lp);
                }

            }
        });
    }

    //
    private void showPopupWindows(final ViewHolder viewHolder, final DongTai dongTaiBean, final int position) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_xiala, null);
        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopWindow.setOutsideTouchable(true);
        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_delete);
//        final TextView tv2 = (TextView) contentView.findViewById(R.id.showdanmu);
//        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_manage);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtils.getInstance().getString("type", "").equals(Constants.TYPE_TEACHER) || SpUtils.getInstance().getString("userid", "").equals(dongTaiBean.getUserId())) {
                    //本人或者老师可以删除
                    mDongTais.remove(position);
                    notifyDataSetChanged();
                    deleteDongtai(dongTaiBean.getDongtaiId(), position);
                } else {
                    Toast.makeText(mContext, "您不能删除这条动态", Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();
                }

            }
        });
//        tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //播放隐藏弹幕
//                if (viewHolder.danmakuView.isPaused()) {
//                    tv2.setText("隐藏弹幕");
//                    viewHolder.danmakuView.show();
//                } else {
//                    tv2.setText("弹幕");
//                    viewHolder.danmakuView.hide();
//                }
//            }
//        });
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
        mPopWindow.showAsDropDown(viewHolder.dt_iv_popchoose,0,0);
    }

    private void deleteDongtai(String dongtaiId, int position) {
        OkGo.post(Urls.URL_DELDT)
                .tag(this)
                .params("infoId", dongtaiId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                        mPopWindow.dismiss();
                    }
                });
        mPopWindow.dismiss();
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.pop_delete:
////                mPopWindow.dismiss();
//                break;
//            case R.id.pop_financial:
//                Toast.makeText(mContext, "clicked financial", Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();
//
//                break;
//            case R.id.pop_manage:
//                Toast.makeText(mContext, "clicked manage", Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();

//                break;
//            case R.id.dt_iv_comment:
//                Toast.makeText(mContext,"comment",Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();
//
//            break;
//            case R.id.dt_iv_share:
//                Toast.makeText(mContext,"share",Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();
//
//            break;
//            case R.id.dt_iv_dianzan:
//                Toast.makeText(mContext,"dianzan",Toast.LENGTH_SHORT).show();
//                mPopWindow.dismiss();
//            break;
//        }
//    }


    private void showComment(final ViewHolder viewHolder, DongTai dongTaiBean) {
        final List<FavortItem> favortDatas = dongTaiBean.getFavorters();
        final List<CommentItem> commentsDatas = dongTaiBean.getComments();
        boolean hasFavort = dongTaiBean.hasFavort();
        boolean hasComment = dongTaiBean.hasComment();
        if (hasComment || hasFavort) {
            if (hasFavort) {//处理点赞列表
                viewHolder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        //暂时处理，之后点击头像进入个人空间
                        String userName = favortDatas.get(position).getUserName();
                        String userId = favortDatas.get(position).getUserId();
                        Toast.makeText(mContext, userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.praiseListView.setDatas(favortDatas);
                viewHolder.praiseListView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.praiseListView.setVisibility(View.GONE);
            }
            if (hasComment) {
                viewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        CommentItem commentitem = commentsDatas.get(position);
                        viewHolder.et_comment.setFocusable(true);
                        viewHolder.et_comment.setEnabled(true);
                        viewHolder.et_comment.setFocusableInTouchMode(true);
                        viewHolder.et_comment.requestFocus();
                        MeasureUtils.showKeyboard(mContext, viewHolder.et_comment);
                        type = 1;
                        id = commentitem.getUserNo();
                    }
                });

                //长按删除
                viewHolder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int position) {
                        CommentItem commentitem = commentsDatas.get(position);
                        showPopupWindowsDelete(viewHolder, commentsDatas, position);
//                        Toast.makeText(mContext, "shanchu", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.commentList.setDatas(commentsDatas);
                viewHolder.commentList.setVisibility(View.VISIBLE);
            } else {
                viewHolder.commentList.setVisibility(View.GONE);
            }
            viewHolder.dt_CommentBody.setVisibility(View.VISIBLE);
        } else {
            viewHolder.dt_CommentBody.setVisibility(View.GONE);
        }
        viewHolder.dt_lin.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);
    }


    private void showPhoto(DongTai dongTaiBean, ViewHolder viewHolder) {
        //照片需要显示
        if (dongTaiBean.getPhotos() != null && dongTaiBean.getPhotos().size() > 0) {
            viewHolder.dt_gridView.setVisibility(View.VISIBLE);
            //图片地址集合转化为数组
            final String[] photoUrls = dongTaiBean.getPhotos().toArray(new String[dongTaiBean.getPhotos().size()]);
            viewHolder.dt_gridView.setAdapter(new CircleGridAdapter(photoUrls));
            viewHolder.dt_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    enterPhotoDetailed(photoUrls, position);
                }
            });
        } else {
            //没有照片，影藏
            viewHolder.dt_gridView.setVisibility(View.GONE);
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


    private static class ViewHolder {
        CircleImageView dt_civ_headphoto;
        TextView dt_tv_name;
        ImageView dt_iv_popchoose;
        TextView dt_tv_content;
        NoScrollGridView dt_gridView;
        TextView dt_tv_date;
        TextView dt_tv_time;
        ImageView dt_iv_dianzan;
        ImageView dt_iv_comment;
        ImageView dt_iv_share;
        LinearLayout dt_CommentBody;
        PraiseListView praiseListView;
        View dt_lin;
        CommentListView commentList;
        LinearLayout ll_blank;
        YEditText et_comment;
        Button btn_send;
        JCVideoPlayerStandard jc_movie;
        LinearLayout ll_voice;
        Button bt_voice;
        TextView tv_voicetime;
        FrameLayout fl_movie;
//        DanmakuView danmakuView;
    }
}
