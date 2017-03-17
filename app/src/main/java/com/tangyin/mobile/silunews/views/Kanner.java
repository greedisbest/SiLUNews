package com.tangyin.mobile.silunews.views;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tangyin.mobile.silunews.R;

import java.util.ArrayList;
import java.util.List;

public class Kanner extends FrameLayout {
	private int count;
	private List<ImageView> imageViews;
	private Context context;
	private ViewPager vp;
	private boolean isAutoPlay;
	private int currentItem;
	private int delayTime;
	private RelativeLayout rl_title;
	private String[] titles;
	private TextView tv_title,tv_index,tv_count;
	private Handler handler = new Handler();

	public Kanner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initData();
	}

	public Kanner(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Kanner(Context context) {
		this(context, null);
	}

	private void initData() {
		imageViews = new ArrayList<ImageView>();
		delayTime = 4000;
	}

	public void setResourseUrl(String[] imagesUrl, String[] titles) {
		this.titles=titles;
		initLayout();
		initIndexFromNet(imagesUrl);
		showTime();
	}

	private void initLayout() {
		imageViews.clear();
		View view = LayoutInflater.from(context).inflate(
				R.layout.kanner_layout, this, true);
		vp = (ViewPager) view.findViewById(R.id.vp);
		rl_title = (RelativeLayout) view.findViewById(R.id.ll_dot);
		tv_title=(TextView) view.findViewById(R.id.tv_title);
		tv_index=(TextView) view.findViewById(R.id.index);
		tv_count=(TextView) view.findViewById(R.id.count);
	}

	private void initIndexFromNet(String[] imagesUrl) {
		count = imagesUrl.length;
		for (int i = 0; i <= count + 1; i++) {
			ImageView iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setImageResource(R.mipmap.loading);
			if (i == 0) {
				Picasso.with(context).load(imagesUrl[count - 1]).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(iv);
			} else if (i == count + 1) {
				Picasso.with(context).load(imagesUrl[0]).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(iv);
			} else {
				Picasso.with(context).load(imagesUrl[i - 1]).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(iv);
			}
			imageViews.add(iv);
		}
		tv_index.setText("1");
		tv_count.setText("/"+count);
		if(titles!=null){
			tv_title.setText(titles[0]);
		}
	}

	private void showTime() {
		vp.setAdapter(new KannerPagerAdapter());
		vp.setFocusable(true);
		vp.setCurrentItem(1);
		currentItem = 1;
		vp.setOnPageChangeListener(new MyOnPageChangeListener());
		startPlay();
	}

	private void startPlay() {
		isAutoPlay = true;
		handler.postDelayed(task, 4000);
	}

	private final Runnable task = new Runnable() {

		@Override
		public void run() {
			if (isAutoPlay) {
				currentItem = currentItem % (count + 1) + 1;
				if(currentItem==count+1){
					tv_title.setText(titles[0]);
					tv_index.setText(1+"");
				}else{
					tv_title.setText(titles[currentItem-1]);
					tv_index.setText(currentItem+"");
				}
				
				if (currentItem == 1) {
					vp.setCurrentItem(currentItem, false);
					handler.post(task);
				} else {
					vp.setCurrentItem(currentItem);
					handler.postDelayed(task, 3000);
				}

				
			} else {
				handler.postDelayed(task, 5000);
			}
		}
	};

	class KannerPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}

	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:
				isAutoPlay = false;
				break;
			case 2:
				isAutoPlay = true;
				break;
			case 0:
				if (vp.getCurrentItem() == 0) {
					vp.setCurrentItem(count, false);
				} else if (vp.getCurrentItem() == count + 1) {
					vp.setCurrentItem(1, false);
				}
				currentItem = vp.getCurrentItem();
				isAutoPlay = true;
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {

			if(arg0==count+1){
				tv_title.setText(titles[0]);
				tv_index.setText(1+"");
			}else{
				tv_title.setText(titles[arg0-1]);
				tv_index.setText(arg0+"");
			}
		}

	}

}
