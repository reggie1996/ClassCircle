package doctorw.classcircle.view;

import android.content.Context;
import android.widget.EditText;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by asus on 2017/4/12.
 */
public class YEditText extends EditText {

    private String hint;

    public YEditText(Context context) {
        super(context);
        init();
    }

    public YEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        hint = getHint().toString();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            setHint("");
        } else {
            setHint(hint);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

}
