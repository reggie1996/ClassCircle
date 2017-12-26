package doctorw.classcircle.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import doctorw.classcircle.R;

/**
 * Created by asus on 2017/5/8.
 */

public class XingquFragment extends BaseFragment {

    private View mView;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        mView = inflater.inflate(R.layout.fg_xingqu, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mView);
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

    }
}
