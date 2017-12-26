package doctorw.classcircle.controller.activity;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ClassDongTaiAdapter;
import doctorw.classcircle.model.bean.DongTai;
import doctorw.classcircle.model.http.Datas;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.SwipeRefreshView;
import okhttp3.Call;
import okhttp3.Response;

public class SelfDongTaiActivity extends BaseActivity {

    @Bind(R.id.lv_dongtai)
    ListView lvDongtai;
    @Bind(R.id.sr_dongtai)
    SwipeRefreshView srDongtai;
    private ClassDongTaiAdapter dongTaiAdapter;

    private List<DongTai> mDongTais;
    private Context mContent;

    //当前下拉菜单是否为显示状态
    private boolean showItems = false;
    //添加数据按钮
    private ImageView addDataIv;
    //下拉功能菜单
    private LinearLayout dropDownView;
    private View bgView;

    private  BaseActivity mActivity;
    private LinearLayout ll_top;
    private CircleImageView ci_userphoto;
    private TextView tv_username;
    private View header;
    private String userId;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_self_dong_tai2);
        mContent = this;
//        根据穿过来的ID获取数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userid");
        mActivity = this;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        header = LayoutInflater.from(mContent).inflate(R.layout.headerdongtai, null);
        ll_top = (LinearLayout) header.findViewById(R.id.ll_top);
        ci_userphoto = (CircleImageView) header.findViewById(R.id.ci_userphoto);
        tv_username = (TextView) header.findViewById(R.id.tv_username);
        lvDongtai.addHeaderView(header);

        addDataIv = (ImageView) findViewById(R.id.main_newData);
        dropDownView = (LinearLayout) findViewById(R.id.dropDownView);
        bgView = findViewById(R.id.main_bg);

        srDongtai.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        srDongtai.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);



        initDongTai();
//        //设置属性
//        mDongTais = DatasUtil.initDongtai();
//        dongTaiAdapter = new ClassDongTaiAdapter(mContent, mDongTais,this);
//        lvDongtai.setAdapter(dongTaiAdapter);
    }

    private void initDongTai() {

        HashMap <String,String> params = new HashMap<>();
        params.put("userNo",userId);

        showToast(userId);
        Datas.loadNotis(Urls.URL_LOADSELFDT, mContent, params, new Datas.HandleResponse() {
            @Override
            public void handleResponse(String s, Call call, Response response) {
                mDongTais = JSON.parseArray(s,DongTai.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dongTaiAdapter = new ClassDongTaiAdapter(mContent,mDongTais,mActivity);
                        lvDongtai.setAdapter(dongTaiAdapter);
                    }
                });
            }
        });

    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back_dt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ci_userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("touxiang");
            }
        });

        addDataIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showItems) {
                    showView();
                } else {
                    dismissView();
                }
            }
        });

        //点击空白隐藏弹出框
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showItems) {
                    dismissView();
                }
            }
        });

        //下拉功能菜单上按钮的点击事件
        View.OnClickListener l = new OnClickImpl();
        findViewById(R.id.dropDown_item1).setOnClickListener(l);
        findViewById(R.id.dropDown_item2).setOnClickListener(l);
        findViewById(R.id.dropDown_item3).setOnClickListener(l);
//        findViewById(R.id.dropDown_item4).setOnClickListener(l);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        srDongtai.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //需要刷新的数据到服务器获取
                        //刷新list数据
//                        String[] urls = getResources().getStringArray(R.array.url);
//                        List list = Arrays.asList(urls);
//                        List arrayList = new ArrayList(list);
//                        headerBanner.update(arrayList);
//
//                        //刷新文章里面的数据
//                        articlesList.add(0, new Article());
//                        mAdapter.notifyDataSetChanged();
//                        showToast("刷新了一条数据");
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        srDongtai.setRefreshing(false);
                    }
                }, 2000);
                // System.out.println(Thread.currentThread().getName());
                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        // 设置下拉加载更多
        srDongtai.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        articlesList.add(new Article());
//                        // 这里要放在里面刷新，放在外面会导致刷新的进度条卡住
//                        mAdapter.notifyDataSetChanged();

                        // 加载完数据设置为不加载状态，将加载进度收起来
                        srDongtai.setLoading(false);
                    }
                }, 2000);
            }
        });
    }

    //显示下拉菜单
    private void showView() {
        addDataIv.startAnimation(AnimationUtils.loadAnimation(mContent, R.anim.rotate_open));
//        ObjectAnimator anim2 = ObjectAnimator .ofFloat(addDataIv, "rotationX", 0f, 45f);
//        anim2.setDuration(300);
//        anim2.start();

        dropDownView.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(dropDownView, "translationY", -50f, 0f);
        anim.setDuration(300);
        anim.start();

        bgView.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(bgView, "alpha", 0f, 0.5f);
        alpha.setDuration(300);//设置动画时间
        alpha.start();
        showItems = true;
    }

    //隐藏下拉菜单
    private void dismissView() {
//        ObjectAnimator anim = ObjectAnimator .ofFloat(addDataIv, "rotationX", 45f, 0f);
//        anim.setDuration(300);
//        anim.start();
        addDataIv.startAnimation(AnimationUtils.loadAnimation(mContent, R.anim.rotate_close));
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(dropDownView, "translationY", 0f, -250f);
        anim2.setDuration(300);
        anim2.start();
        dropDownView.setVisibility(View.GONE);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(bgView, "alpha", 0.5f, 0f);
        alpha.setDuration(300);//设置动画时间
        alpha.start();
        bgView.setVisibility(View.GONE);
        showItems = false;
    }


    private class OnClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.dropDown_item1:
                    startActivity(PublishCircleActivity.class);
                    dismissView();
                    break;

                case R.id.dropDown_item2:
                    showToast("item2 onClick");
                    dismissView();
                    break;

                case R.id.dropDown_item3:
                    showToast("item3 onClick");
                    dismissView();
                    break;

//                case R.id.dropDown_item4:
//                    Toast.makeText(ClassDongtaiActivity.this, "item4 onClick", Toast.LENGTH_SHORT).show();
//                    dismissView();
//                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //点击返回键时，如果下拉菜单是显示状态，则隐藏它
    @Override
    public void onBackPressed() {
        if (showItems) {
            dismissView();
            return;
        }
        super.onBackPressed();
    }
}
