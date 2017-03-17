package com.tangyin.mobile.silunews.activity;

import android.content.Intent;
import android.os.Handler;

import com.tangyin.mobile.silunews.base.BaseActivity;

/**
 * 欢迎页（加载页）
 */
public class WelcomeActivity extends BaseActivity {
    Handler mHandler=new Handler();
    private final long DELAY_SKIP=2000;

    @Override
    protected int inflateLayoutId() {
        return 0;
    }
    @Override
    protected void initViews() {

        mHandler.postDelayed(mRunnable,DELAY_SKIP);
    }

    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

}
