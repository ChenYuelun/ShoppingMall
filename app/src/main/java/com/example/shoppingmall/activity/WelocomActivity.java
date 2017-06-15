package com.example.shoppingmall.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.shoppingmall.R;

public class WelocomActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //两秒后进入主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivit();
            }
        }, 2000);
    }

    private void startMainActivit() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击直接进入主线程
        handler.removeCallbacksAndMessages(null);
        startMainActivit();
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        //移除消息
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
