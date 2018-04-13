package com.hr.samples.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.hr.samples.R;

import java.io.IOException;

/**
 *
 * Created by Admin on 2018/3/28/028.
 */

public class PermissionActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        findViewById(R.id.btn_permission_audio).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission_audio){
//            if (PermissionUtils.getInstance().selfPermissionGranted(this , Manifest.permission.RECORD_AUDIO)){
//               record();
//            }else{
//                Log.d("FuckPermission" , " " + PermissionUtils.getInstance().checkPermission(this));
//                PermissionUtils.getInstance().requestPermission(this);
//            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't blockthis thread waiting for the user's response!
                    // After the user sees the explanation, try again to request the permission.

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, 1);
                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is anapp-defined int constant.
                    // The callback method gets the result of the request.
                }
            }
        }
    }


    private void record() {
        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(getCacheDir().getPath() + "1.mp3");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        recorder.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i = 0 ; i<permissions.length ; i++){
            Log.d("FuckPermission" , "授权回调#" + permissions[i] + ":" + grantResults[i]);
            if(Manifest.permission.READ_PHONE_STATE.equals(permissions[i]) && grantResults[i] == PackageManager.PERMISSION_GRANTED ){
                Log.d("FuckPermission" , "授权回调成功" );
                break;
            }
        }
        Log.d("FuckPermission" , "PermissionActivity");
    }
}

