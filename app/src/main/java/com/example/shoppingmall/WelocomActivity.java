package com.example.shoppingmall;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import static android.R.attr.start;

public class WelocomActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivit();
            }
        }, 3000);
    }

    private void startMainActivit() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handler.removeCallbacksAndMessages(null);
        startMainActivit();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
