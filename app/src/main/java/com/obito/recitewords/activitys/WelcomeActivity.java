package com.obito.recitewords.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.obito.recitewords.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by obito on 17-9-2.
 */

public class WelcomeActivity extends Activity {
    public LinearLayout ll;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        ll = (LinearLayout) findViewById(R.id.wll1);
        tv = (TextView) findViewById(R.id.wtv1);
        Random ran = new Random();
        ll.setBackgroundResource(R.mipmap.wbg5);
        tv.setText("愿你被世界温柔以待。");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 2500);

    }

}
