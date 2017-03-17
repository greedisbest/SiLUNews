package com.tangyin.mobile.silunews.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
	protected abstract int inflateLayoutId();
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if(inflateLayoutId()!=0){
			setContentView(inflateLayoutId());
		}
		ButterKnife.bind(this);
		initViews();
	}
	protected abstract void initViews();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

}
