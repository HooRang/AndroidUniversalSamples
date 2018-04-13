package com.hr.samples.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

/**
 *
 * Created by Admin on 2018/3/28/028.
 */

public class PermissionUtils {

    private Activity activity;
    private  boolean granted ;
    private int targetSdkVersion ;

    private static PermissionUtils pu ;
    public  static PermissionUtils getInstance(){
        if(pu ==  null){
            pu = new PermissionUtils();
        }
        return pu;
    }

    public void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
    }

    public boolean checkPermission(Activity activity) {
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("FuckPermission" , "PermissionUtils");
        if (requestCode == 1 && grantResults.length > 0) {
            granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }


    public boolean selfPermissionGranted(Activity activity ,String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                final PackageInfo info = activity.getPackageManager().getPackageInfo(
                        activity.getPackageName(), 0);
                targetSdkVersion = info.applicationInfo.targetSdkVersion;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = activity.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(activity, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }



}
