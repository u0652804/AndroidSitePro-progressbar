package per.noah.progressdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import per.noah.progressdownload.downloadUtil.DownloadHelper;
import per.noah.progressdownload.permission.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    private final static boolean show = true;
    private static final String TAG = MainActivity.class.getCanonicalName();

    TextView tvDownloadPercent;
    ProgressBar proBarDownload;
    TextView tvProgressStatus;
    Button btnDownload;

    private static final String FILE_NAME = "test.png";
    private static final String DOWNLOAD_URL = "https://gitforwindows.org/img/git_logo.png";

    private static final String TEXT_DOWNLOAD = "Download";
    private static final String TEXT_CANCEL = "Cancel";

    DownloadHelper.DownloadAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionHelper.verifyStoragePermissions(this);
        initUI();
        setListener();
    }

    private void setListener() {
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = btnDownload.getText();
                if (TEXT_DOWNLOAD.equals(text)) {
                    showLog("enter download");
                    downloadFile();
                } else if (TEXT_CANCEL.equals(text)) {
                    showLog("enter cancel");
                    downloadFileCancel();
                }
            }
        });
    }

    private void downloadFileCancel() {
        if (null == task) return;
        task.cancel(true);
        task = null;
        btnDownload.setText(TEXT_DOWNLOAD);
    }

    private void downloadFile() {
        if (null != task) return;

        String localPath = Environment.getExternalStorageDirectory() + File.separator + FILE_NAME;
        task = new DownloadHelper.DownloadAsyncTask(DOWNLOAD_URL, localPath, new DownloadHelper.OnDownloadListener() {
            @Override
            public void onStart() {
//                        btnDownload.setEnabled(false);
                proBarDownload.setProgress(0);
                btnDownload.setText(TEXT_CANCEL);
                tvDownloadPercent.setText(getPercentStr(0));
                tvProgressStatus.setText("start download");
            }
            @Override
            public void onSuccess(File file) {
                btnDownload.setText(TEXT_DOWNLOAD);
                tvProgressStatus.setText(String.format("download success：%s", file.getPath()));
                task = null;
            }
            @Override
            public void onFail(File file, String failInfo) {
//                        btnDownload.setEnabled(true);
                btnDownload.setText(TEXT_DOWNLOAD);
                tvProgressStatus.setText(String.format("download fail info =：%s", failInfo));
                task = null;
            }
            @Override
            public void onProgress(int progress) {
                showLog(getPercentStr(progress));
                proBarDownload.setProgress(progress);
                tvDownloadPercent.setText(getPercentStr(progress));
            }
        });
        task.execute();
    }

    private void initUI() {
        tvDownloadPercent = findViewById(R.id.textView);
        tvProgressStatus = findViewById(R.id.textView1);
        proBarDownload = findViewById(R.id.progressBar);
        btnDownload = findViewById(R.id.button);
        btnDownload.setText(TEXT_DOWNLOAD);
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

    private void showLog(String msg){
        if (!show) return;
        Log.d(TAG, msg);
    }
}
