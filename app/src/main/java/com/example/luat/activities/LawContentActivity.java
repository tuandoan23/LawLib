package com.example.luat.activities;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luat.PresenterIplm.LawContentPresenterIplm;
import com.example.luat.R;
import com.example.luat.base.BaseActivity;
import com.example.luat.contract.LawContract;
import com.example.luat.model.LawContent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import butterknife.BindView;

public class LawContentActivity extends BaseActivity implements LawContract.LawContentView{
    private String key;
    private LawContract.LawContentPresenter presenter;
//
//    @BindView(R.id.tvLawContent)
//    AppCompatTextView tvLawContent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webView;

    @Override
    public int initLayout() {
        return R.layout.activity_lawcontent;
    }

    @Override
    public void getExtraData() {
        key = getIntent().getStringExtra("key");
    }

    @Override
    public void createPresenter() {
        presenter = new LawContentPresenterIplm(this);
    }

    @Override
    public void createAdapter() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void loadData() {
        presenter.getLaw(this.getApplicationContext(), key);
    }

    @Override
    public void getLawSuccess(LawContent lawContent) {
        String data = lawContent.getData();
//        data = data.replace("\r", "<br>").replace("\n","<br>").replace("</>","</t>").replace("<t/>","<t>");
//        String newString = data.replace("</>", "</t>");
//        newString = newString.replace("<t/>", "<t>");
//        tvLawContent.setText(lawContent.getData());
//        tvLawContent.setText(Html.fromHtml(lawContent.getData()));
//        String data = "<!DOCTYPE html PUBLIC “-//W3C//DTD XHTML 1.0 Transitional//EN”\n" +
//                "“http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd”><html xmlns=”http://www.w3.org/1999/xhtml”><head>\n" +
//                "<title>Title of document</title>\n" +
//                "</head>\n" +
//                "<body>\n" + lawContent.getData()+ "\n" +  "</body>\n" +
//                "\n" +
//                "</html>";
        webView.loadData(data, "text/xhtml","UTF-8");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                injectCSS();
                Log.d("inject", "CSS");
                super.onPageFinished(view, url);
            }
        });
        Log.d("content", data);
//        webView.loadUrl("file:///android_asset/out.html");
//        webView.loadDataWithBaseURL();
    }

    private void injectCSS(){
        try {
            InputStream inputStream = getAssets().open("style.css");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("out.html", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void getLawError() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
