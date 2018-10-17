package com.example.luat.base;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luat.CopyDBEvent;
import com.example.luat.R;

import org.greenrobot.eventbus.EventBus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.luat.base.DBHelper.DATABASE_NAME;
import static com.example.luat.base.DBHelper.DB_PATH;

public class CopyAsyncTask  extends AsyncTask<Void, Integer, Void> {
    Context context;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private AppCompatTextView tvProgress;
    private LinearLayout llProgress;
    private TabLayout tab;

    public CopyAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = ((Activity) context).findViewById(R.id.progressBar);
        viewPager = ((Activity) context).findViewById(R.id.viewPager);
        tvProgress = ((Activity) context).findViewById(R.id.tvProgress);
        llProgress = ((Activity) context).findViewById(R.id.llProgress);
        tab = ((Activity) context).findViewById(R.id.tab);
        tab.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        llProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        InputStream myInput = null;
        double total = 0;
        double size = 0;
        try
        {
            myInput =context.getAssets().open(DATABASE_NAME);
            size = myInput.available();
            Log.d("size", size + "");
            myOutput =new FileOutputStream(DB_PATH + DATABASE_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                total += length;
                publishProgress((int)((total*100)/size));
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int number = values[0];
        progressBar.setProgress(number);
//        progressBar.setVisibility(View.VISIBLE);
        tvProgress.setText(String.valueOf(number) + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        EventBus.getDefault().post(new CopyDBEvent(true));
        tab.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        llProgress.setVisibility(View.GONE);
        super.onPostExecute(aVoid);
    }
}