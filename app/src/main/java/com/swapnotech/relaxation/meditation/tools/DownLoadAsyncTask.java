package com.swapnotech.relaxation.meditation.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Dev on 12/7/2017.
 */

public class DownLoadAsyncTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private String path;
    private ProgressDialog progressDialog;

    public DownLoadAsyncTask(Context context, String path) {
        this.context = context;
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, null, "downloading.... 0%");
        Log.e("DOWNLOAD", "start:" + path);
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.e("DOWNLOAD_RUN", "url:" + strings[0]);
        int count;
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            InputStream inputStream = new BufferedInputStream(url.openStream());
            OutputStream outputStream = new FileOutputStream(path);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                publishProgress((int) total * 100 / lengthOfFile);
                outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "internet problem", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e("DOWNLOAD_PROG", "%:" + values[0]);
        progressDialog.setMessage("downloded"+ values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.e("DOWNLOAD_POST", "completed");
        progressDialog.dismiss();
    }
}
