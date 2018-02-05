package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;

public class WebAct extends Activity {
    private ImageView iv_back;
    private TextView tv_title;
    private WebView webView;
    private String title;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ease_act_comweb);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        steepStatusBar();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        tv_title = (TextView) findViewById(R.id.tv_title);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
//支持缩放，默认为true。
        webSettings.setSupportZoom(false);
//调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
// 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
//设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.supportMultipleWindows();
//获取触摸焦点
        webView.requestFocusFromTouch();
//允许访问文件
        webSettings.setAllowFileAccess(true);
//开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient());
       tv_title.setText(title);
       webView.loadUrl(url);
    }
    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
