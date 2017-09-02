package com.obito.recitewords.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    public int[] textArr = new int[]{R.string.text1,
            R.string.text2,
            R.string.text3,
            R.string.text4,
            R.string.text5,
            R.string.text6,
            R.string.text7,
            R.string.text8,
            R.string.text9,
            R.string.text10,
            R.string.text11,
            R.string.text12,
    };
    public int[] img = new int[]{
            R.mipmap.wbg1,
            R.mipmap.wbg2,
            R.mipmap.wbg3,
            R.mipmap.wbg4,
            R.mipmap.wbg5
    };
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
        ll.setBackgroundResource(img[ran.nextInt(5)]);
        tv.setText(textArr[ran.nextInt(12)]);
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
