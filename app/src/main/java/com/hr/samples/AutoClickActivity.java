package com.hr.samples;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.io.IOException;

import static android.R.attr.category;
import static android.R.attr.x;
import static android.R.attr.y;


/**
 * author: HooRang
 * dateTime: 2018/2/7/007-18:54
 * description:
 */

public class AutoClickActivity extends Activity {
    private static final String TAG = "AutoClickActivity";
    private int count = 0 ;
    private boolean bContinue  = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_click);
        final int width = getWindowManager().getDefaultDisplay().getWidth();
        final int height = getWindowManager().getDefaultDisplay().getHeight();

        //每10s产生一次点击事件，点击的点坐标为(0.2W - 0.8W,0.2H - 0.8 H),W/H为手机分辨率的宽高.
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (bContinue) {
//                    //生成点击坐标
//                    int x = (int) (Math.random() * width * 0.6 + width * 0.2);
//                    int y = (int) (Math.random() * height * 0.6 + height * 0.2);
//                    //利用ProcessBuilder执行shell命令
//                    String[] order = {
//                            "input",
//                            "tap",
//                            "" + x,
//                            "" + y
//                    };
//
//                    try {
//                        Log.d(TAG , "process builder");
//                        if (count >=10){
//                            bContinue = false ;
//                        }
//                        new ProcessBuilder(order).start();
//                        count++;
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch(IOException e){
//
//                    }
//                }
//            }
//        }).start();
        this.getWindow().getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate(){
            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if(action == AccessibilityNodeInfo.ACTION_CLICK || action == AccessibilityNodeInfo.ACTION_LONG_CLICK){
                    Log.d(TAG , "performAccessibilityAction#过滤了事件" );
                    return true ;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        });
    }


    /**
     * 打印点击的点的坐标
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        count++;
        Log.d(TAG , "onTouchEvent#" + "X at " + x + ";Y at " + y +",count:" + count);
        return true;
    }




}
