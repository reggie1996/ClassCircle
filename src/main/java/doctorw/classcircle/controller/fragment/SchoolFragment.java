//package doctorw.classcircle.controller.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import doctorw.classcircle.R;
//import doctorw.classcircle.controller.activity.ClassCircleActivity;
//import doctorw.classcircle.controller.activity.ClassDongtaiActivity;
//import doctorw.classcircle.controller.activity.ClassInterestActivity;
//
///**
// * Created by asus on 2017/4/9.
// */
//
//public class SchoolFragment extends BaseFragment {
//    @Bind(R.id.title_change)
//    TextView titleChange;
//    @Bind(R.id.query)
//    EditText query;
//    @Bind(R.id.search_clear)
//    ImageButton searchClear;
//
//    private View mView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        mView = inflater.inflate(R.layout.fg_school, container, false);
//        ButterKnife.bind(this, mView);
//        return mView;
//    }
//
//    @Override
//    protected void initView() {
//        titleChange.setText("班级圈");
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initListener() {
//        query.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                contactListLayout.filter(s);
//                if (s.length() > 0) {
//                    searchClear.setVisibility(View.VISIBLE);
//                } else {
//                    searchClear.setVisibility(View.INVISIBLE);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @OnClick({R.id.search_clear, R.id.iv_ClassCircle, R.id.iv_DongTai, R.id.iv_Interest})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.search_clear:
//                query.getText().clear();
//                hideSoftKeyboard(query);
//                break;
////            case R.id.iv_ClassCircle:
////                showToast("班级圈")
//                startActivity(ClassCircleActivity.class);
//                break;
//            case R.id.iv_DongTai:
////                showToast("班级动态");
//                startActivity(ClassDongtaiActivity.class);
//                break;
//            case R.id.iv_Interest:
////                showToast("兴趣圈");
//                startActivity(ClassInterestActivity.class);
//                break;
//        }
//    }
//
//
////    query.addTextChangedListener()
////
////
////
////    query.addTextChangedListener(new TextWatcher() {
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                contactListLayout.filter(s);
////                if (s.length() > 0) {
////                    clearSearch.setVisibility(View.VISIBLE);
////                } else {
////                    clearSearch.setVisibility(View.INVISIBLE);
////
////                }
////            }
// /*
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        clearSearch.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query.getText().clear();
//                hideSoftKeyboard();
//            }
//        });
//     */
//}
