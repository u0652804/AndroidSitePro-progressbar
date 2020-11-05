package per.noah.progressdownload.downloadUtil;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadHelper {
    public static class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
        private String mFailInfo;
        private String mUrl;
        private String mFilePath;
        private OnDownloadListener mListener;
        public DownloadAsyncTask(String mUrl, String mFilePath, OnDownloadListener mListener) {
            this.mUrl = mUrl;
            this.mFilePath = mFilePath;
            this.mListener = mListener;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            String pdfUrl = mUrl;
            try {
                // use input stream and url connecter to get file data
                URL url = new URL(pdfUrl);
                URLConnection urlConnection = url.openConnection();
                InputStream in = urlConnection.getInputStream();
                int contentLength = urlConnection.getContentLength();
                File pdfFile = new File(mFilePath);

                // check file, if exist => delete it
                if (pdfFile.exists()) {
                    boolean result = pdfFile.delete();
                    if (!result) {
                        mFailInfo = "儲存路徑下的同名檔案刪除失敗！";
                        return false;
                    }
                }

                int downloadSize = 0;
                byte[] bytes = new byte[1024];
                int length;

                // get out stream for write data to local
                OutputStream out = new FileOutputStream(mFilePath);

                // write data to file
                while ((length = in.read(bytes)) != -1) {
                    out.write(bytes, 0, length);
                    downloadSize += length;
                    publishProgress(downloadSize *100 / contentLength );
                }

                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                mFailInfo = e.getMessage();
                return false;
            }
            return true;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mListener != null) {
                mListener.onStart();
            }
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mListener != null) {
                if (aBoolean) {
                    mListener.onSuccess(new File(mFilePath));
                } else {
                    mListener.onFail(new File(mFilePath), mFailInfo);
                }
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                if (mListener != null) {
                    mListener.onProgress(values[0]);
                }
            }
        }
    }
    public interface OnDownloadListener{
        void onStart();
        void onSuccess(File file);
        void onFail(File file, String failInfo);
        void onProgress(int progress);
    }
}