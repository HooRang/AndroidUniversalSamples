package com.hr.samples;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.Set;


/**
 * author: HooRang
 * dateTime: 2018/2/9/009-15:24
 * description:
 */

public class OpenByJavaScriptActivity extends Activity {

    private static final String TAG = "OpenByJavaScript";
    TextView tvLog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pay_javascript);

        tvLog = (TextView)findViewById(R.id.activity_log_message);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri != null){
            Set<String> queries = uri.getQueryParameterNames();
            StringBuilder message = new StringBuilder();
            for (String name : queries) {
                message.append(name + "#" + uri.getQueryParameter(name) +",");
            }
            Bundle extras = intent.getExtras();
            Set<String> bundles = extras.keySet();
            for (String key : bundles) {
                message.append(key + "#" + extras.get(key) +",");
            }

            Log.i(TAG ,"DataString：" +  intent.getDataString());
            if(intent.getDataString() !=null){
               try {
                   Intent dataStringIntent = Intent.parseUri(intent.getDataString() , 0);
                   if(dataStringIntent.hasExtra("param")){
                       message.append("param#" + dataStringIntent.getStringExtra("param"));

                   }
               }catch (URISyntaxException e ){
                   e.printStackTrace();
               }
            }

            tvLog.setText("参数串：" +  message.toString());

        }
    }
}
