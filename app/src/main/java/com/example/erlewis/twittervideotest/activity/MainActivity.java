package com.example.erlewis.twittervideotest.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.erlewis.twittervideotest.R;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

import utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String TWEET_URL = "https://twitter.com/EddieSolisJr1/status/792027346169532416";
    private static final String TWEET_HTML = "<blockquote class=\"twitter-tweet\"><p lang=\"en\" dir=\"ltr\">" +
            "My reaction to seeing Drake and Taylor Swift are dating <a href=\"https://t.co/Fxq2CHH4iT\">pic.twitter." +
            "com/Fxq2CHH4iT</a></p>&mdash; Eddie Solis Jr. (@EddieSolisJr1) <a href=\"https://twitter.com/" +
            "EddieSolisJr1/status/792027346169532416\">October 28, 2016</a></blockquote>\n";


    private WebView tweetView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTweetView();
        setTweetHtml();
    }

    private void initTweetView() {
        this.tweetView = (WebView) findViewById(R.id.tweet_view);

        tweetView.getSettings().setJavaScriptEnabled(true);
        tweetView.getSettings().setSupportMultipleWindows(true);
        tweetView.getSettings().setDomStorageEnabled(true);
        tweetView.setWebChromeClient(new WebChromeClientCustom());
        tweetView.setWebViewClient(new WebViewClientCustom());
    }

    private void setTweetHtml() {
        String templateHtml = Utils.getAndroidAssetFile(this, "twitter_oembed.html");
        String oEmbedHtml = templateHtml.replace("<!-- {{oembed}} -->", TWEET_HTML);
        tweetView.loadDataWithBaseURL("file:///android_asset/", oEmbedHtml, "text/html", "utf-8", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tweetView != null) {
            tweetView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tweetView != null) {
            tweetView.onResume();
        }
    }

    private static class WebChromeClientCustom extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {

            // http://therockncoder.blogspot.com/2014/04/understanding-androids-webchromeclient.html
            // http://stackoverflow.com/questions/18661372/android-webview-containing-hyperlinks-inside-iframe-not-working

            WebView newWebView = new WebView(view.getContext());
            newWebView.setWebViewClient(new TempWebClient());
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;
        }

        public class TempWebClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
        }
    }

    private static class WebViewClientCustom extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
