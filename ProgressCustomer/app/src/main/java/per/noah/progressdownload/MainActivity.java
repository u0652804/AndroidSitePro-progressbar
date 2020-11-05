package per.noah.progressdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static boolean show = true;
    private static final String TAG = MainActivity.class.getCanonicalName();

    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        testProgressBar();
    }

    private void initUI() {
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void testProgressBar() {
        loopShow(0);
    }

    private void loopShow(final int percent){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLog("Set progress as " + percent);
                textView.setText(getPercentStr(percent));
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                progressBar.setProgress(percent);

                int nextPercent = getNextDemoPercent(percent);
                loopShow(nextPercent);
            }
        },3000);
    }

    /**
     * return like __0 or _10 or 100
     * @param percent  : value is 0 to 100
     * */

    private String getPercentStr(int percent){
        String s = "" + percent;
        if (s.length() == 1) return "  " + s + "%";
        else if (s.length() == 2) return " " + s + "%";
        else return s + "%";
    }

    private int getNextDemoPercent(int percent){
        if (percent == 0) return 35;
        else if (percent == 35) return 100;
        else return 0;
    }

    private void showLog(String msg){
        if (!show) return;
        Log.d(TAG, msg);
    }
}
