package com.hr.samples;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Set;

/**
 * author: HooRang
 * dateTime: 2018/1/23/023-10:05
 * description:
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    WebView webView;

    Button btnPay ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.main_webview);
        btnPay  = (Button)findViewById(R.id.btn_invoke);
        btnPay.setOnClickListener(this);

        WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        webView.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
        webView.loadUrl("file:///android_asset/pay_method.html");
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Uri uri = Uri.parse(webView.getUrl());
//                if ("xiyou".equals(uri.getScheme())){
//                    if("webview".equals(uri.getAuthority())){
//                        Set<String> collection = uri.getQueryParameterNames();
//                        for(String str: collection){
//                            Log.d(TAG , "key#                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         " + str + ",value#" + uri.getQueryParameter(str));
//                        }
//                        webView.loadUrl("javascript:returnResult('Return from android')");
//                        return true;
//                    }
//
//                }
//                    return super.shouldOverrideUrlLoading(view, request);
//            }
//        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG , "onJsPrompt");
                Uri uri = Uri.parse(message);
                if ("xiyou".equals(uri.getScheme())){
                    if("prompt".equals(uri.getAuthority())){
                        Set<String> collection = uri.getQueryParameterNames();
                        for(String str: collection){
                            Log.d(TAG , "key#" + str + ",value#" + uri.getQueryParameter(str));
                        }
                        result.confirm("js调用了Android的方法成功啦");
                        return true;
                    }

                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int targetId = v.getId();
        switch (targetId){
            case R.id.btn_invoke:
                if (hasJellyBeanMR1()){
                    webView.evaluateJavascript("javascript:callByAndroid()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d(TAG  , "onReceiveValue#" + value);
                        }
                    });
                }else{
                    webView.post(new Runnable() {
                        @Override
                        public void run() {
                            // 注意调用的JS方法名要对应上
                            // 调用javascript的callJS()方法
                            webView.loadUrl("javascript:callByAndroid()");
                        }
                    });
                }



                break;

            default:
                break;
        }
    }


    public class AndroidtoJs extends  Object{

        @JavascriptInterface
        public void hello(String message){
            Log.d(TAG  , "AndroidtoJs#" + message);
        }

    }

    private boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }
}














