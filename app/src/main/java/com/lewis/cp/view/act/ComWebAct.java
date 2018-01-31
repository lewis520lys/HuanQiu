package com.lewis.cp.view.act;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/1/30.
 */

public class ComWebAct extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;
    String url;
    private String title;

    @Override
    protected void initData() {
        webView.loadUrl(url);
        tvTitle.setText(title);
    }

    @Override
    protected void initView() {
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


    }

    @Override
    public void initParms(Bundle parms) {
        url = parms.getString("url");
        title = parms.getString("title");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_comweb;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
