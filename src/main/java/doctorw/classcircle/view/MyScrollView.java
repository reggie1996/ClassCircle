package doctorw.classcircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by asus on 2017/5/8.
 */

public class MyScrollView extends ScrollView {
    View.OnTouchListener mGestureListener;
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if(xDistance > yDistance){
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}