package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.XQDetils2Activity;
import doctorw.classcircle.controller.adapter.XQCircleDtAdapter;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.model.bean.CircleDongTai;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.MyPopupWindow;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/25.
 */

public class XQLookFragment extends BaseFragment {


    private LinearLayout llSearch;
    private View mView;
    private ListView lv_recommend;
    private XQCircleDtAdapter xqCircleDtAdapter;
    private List<CircleDongTai> circleDongTais = new ArrayList<>();
    private XQCircleDtAdapter.OnCircleListener onCircleListener = new XQCircleDtAdapter.OnCircleListener() {
        //显示删除，举报
        @Override
        public void OnClickMore(int position) {
            showPopupWindow(position);
        }

        //分享
        @Override
        public void OnClickShare(int position) {
            showToast("" + position);
        }

        //进入评论界面
        @Override
        public void OnClickComment(int position) {
            //携带数据
            CircleDongTai circleDongTai = circleDongTais.get(position);
            final Bundle bundle = new Bundle();
            bundle.putParcelable("circleDongTai", circleDongTai);
            OkGo.get(Urls.URL_GETCIRCLEBYID)
                    .params("id", circleDongTai.getCircleId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            CircleDetail circleDetail = JSON.parseObject(s, CircleDetail.class);
                            bundle.putParcelable("circleDetail", circleDetail);
                            startActivity(XQDetils2Activity.class, bundle);
                        }
                    });
        }

        //点赞
        @Override
        public void OnClickLove(int position) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_xqlook, container, false);
        return mView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        lv_recommend = (ListView) mView.findViewById(R.id.lv_recommend);
        loadDatas();
    }

    private void loadDatas() {
        //获得数据
        OkGo.get(Urls.URL_GETHOTDT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        circleDongTais = JSON.parseArray(s, CircleDongTai.class);
                        xqCircleDtAdapter = new XQCircleDtAdapter(mContext, circleDongTais, onCircleListener);
                        lv_recommend.setAdapter(xqCircleDtAdapter);
                    }
                });
    }


    private void showPopupWindow(final int position1) {
        MyPopupWindow popupWindow = new MyPopupWindow(mContext);
        String[] str = {"举报", "删除"};
        popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
            @Override
            public void callback(String text, int position) {
                switch (position) {
                    case 0: //对于不好的动态向系统举报
                        showToast("举报成功，等待处理");
                        break;
                    case 1: //对于不好的动态向系统举报
                        String infoId = circleDongTais.get(position1).getDongtaiId();

                        OkGo.post(Urls.URL_DELETECIRCLEDT)
                                .params("infoId", infoId)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        showToast("删除成功");
                                        circleDongTais.remove(position1);
                                        xqCircleDtAdapter.refresh(circleDongTais);
                                    }
                                });
                        break;
                }
            }
        });
    }
}
