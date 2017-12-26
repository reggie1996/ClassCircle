package doctorw.classcircle.controller.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import doctorw.classcircle.R;

public class TestActivity2 extends AppCompatActivity {
    private int gridHeight, gridWidth;
    private RelativeLayout layout;
    private RelativeLayout tmpLayout;
    private static boolean isFirst = true;

    private String[][] str = {{"语文", "数学", "语文", "舞蹈", "政治", "政治", "自习"},
            {"思想", "体育", "书法", "英语", "体育", "音乐", "体育"},
            {"体育", "数学", "语文", "英语", "自习", "思想", "历史"},
            {"数学", "书法", "语文", "音乐", "自习", "体育", "舞蹈"},
            {"体育", "数学", "语文", "英语", "自习", "思想", "历史"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sche_activity_main);
        tmpLayout = (RelativeLayout) findViewById(R.id.Monday);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            gridWidth = tmpLayout.getWidth();
            gridHeight = tmpLayout.getHeight() / 7;
        }
        showCourse();

    }

    private void showCourse() {
        String text = "算法设计基础@W3502";
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 7; j++) {
                addView(i, j, j, str[i - 1][j - 1]);
            }
        }

    }

    private TextView createTv(int start, int end, String text) {
        TextView tv = new TextView(this);
        /*
         指定高度和宽度
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight * (end - start + 1));
        /*
        指定位置
         */
        tv.setY(gridHeight * (start - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(text);
        return tv;
    }

    private void addView(int i, int start, int end, String text) {
        TextView tv;
        switch (i) {
            case 1:
                layout = (RelativeLayout) findViewById(R.id.Monday);
                break;
            case 2:
                layout = (RelativeLayout) findViewById(R.id.Tuesday);
                break;
            case 3:
                layout = (RelativeLayout) findViewById(R.id.Wednesday);
                break;
            case 4:
                layout = (RelativeLayout) findViewById(R.id.Thursday);
                break;
            case 5:
                layout = (RelativeLayout) findViewById(R.id.Friday);
                break;
//            case 6:
//                layout = (RelativeLayout) findViewById(R.id.Saturday);
//                break;
//            case 7:
//                layout = (RelativeLayout) findViewById(R.id.Sunday);
//                break;
        }
        tv = createTv(start, end, text);
        tv.setBackgroundColor(Color.argb(100, start * 5, (start + end) * 20, 0));
        layout.addView(tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            String text = "算法设计基础@W3502";
            addView(1, 1, 2, text);
            addView(5, 3, 2, text);
            addView(4, 2, 3, text);
            addView(3, 5, 5, text);
            addView(4, 1, 1, text);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
