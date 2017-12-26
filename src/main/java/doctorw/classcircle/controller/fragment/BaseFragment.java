package doctorw.classcircle.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import doctorw.classcircle.ClassCircleApplication;


public abstract class BaseFragment extends Fragment {
    private ClassCircleApplication mApplication;
    /**
     * 上下文
     */
    protected  Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mContainer = container;
//        mInflater = inflater;
//        return initView();
//    }

    public void startActivity(Class<?> clz) {
        mContext.startActivity(new Intent(getActivity(),clz));
    }

    /**
     * 强制子类重写，实现子类特有的ui
     * @return
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mApplication == null) {
            // 得到Application对象
            mApplication = (ClassCircleApplication) getActivity().getApplication();
        }
        addActivity();// 调用添加方法
        initView();
        initData();
        initListener();
    }

    /**
     * 当孩子需要初始化数据，或者联网请求绑定数据，展示数据的 等等可以重写该方法
     */
    protected abstract void initData();

    protected abstract  void initListener();

    protected  abstract void initView();


    protected void hideSoftKeyboard(EditText et) {
        InputMethodManager imm =(InputMethodManager)mContext.getSystemService(
                mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public void showToast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // 添加Activity方法
    public void addActivity() {
        mApplication.addActivity_(getActivity());// 调用myApplication的添加Activity方法
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


}
