package doctorw.classcircle.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import doctorw.classcircle.R;


/**
 * Dialog工具类
 * @author zhouyou
 */
public class MyAlertDialog implements OnClickListener {
	
	private List<View> mViews = new ArrayList<>();
	private static AlertDialog mDialog;
	private static Context mContext;
	private TextView mTime3View;
	private TextView mTime5View;
	private TextView mTime10View;
	
	public MyAlertDialog(Context context) {
		MyAlertDialog.mContext = context;
	}
	
	/**
	 * 显示IOS风格Dialog
	 * @param title 标题
	 * @param hint 提示
	 * @param callback 按钮回调
	 */
	public static void showDialogForIOS(Context context, String title, String hint,final OnCallbackListener callback){
		mContext = context;
		mDialog = new Builder(mContext).create();
		View inflate = View.inflate(mContext, R.layout.dialog_ios, null);
		inflate.findViewById(R.id.btn_dialog_confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onConfrimClick(mDialog);
			}
		});
		inflate.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onCancelClick(mDialog);
			}
		});
		
		((TextView)inflate.findViewById(R.id.tv_dialog_title)).setText(title);
		((TextView)inflate.findViewById(R.id.tv_dialog_hint)).setText(hint);
		mDialog.setView(inflate);
		mDialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_dialog_cancel:
			if(mDialog != null && mDialog.isShowing()){
				mDialog.dismiss();
			}
			break;
		case R.id.btn_dialog_confirm:
			if(mDialog != null && mDialog.isShowing()){
				mDialog.dismiss();
			}
			break;
		}
	}
	
	/** 回调监听 */
	public interface OnCallbackListener{
		/** 确认点击回调　*/
		public void onConfrimClick(AlertDialog dialog);
		/** 取消点击回调　*/
		public void onCancelClick(AlertDialog dialog);
	}
}