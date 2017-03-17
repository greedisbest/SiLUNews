package com.tangyin.mobile.silunews.activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tangyin.mobile.silunews.R;
import com.tangyin.mobile.silunews.adapter.HomeTabPagerAdapter;
import com.tangyin.mobile.silunews.model.Channel;
import com.tangyin.mobile.silunews.views.HomePagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.vp_home)
    ViewPager mViewPager;
    private ArrayList<HomePagerView> pagerList;//PagerView列表
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTabData();
    }

    /**
     * 加载Tablayout网络数据
     */
    private void initTabData() {
        ArrayList<Channel> homeTitleItems = analogNativeData();//模拟本地数据
        handleResponseData(homeTitleItems);
    }

    private void handleResponseData(final ArrayList<Channel> homeTitleItems) {
        List<String> titleTextList = new ArrayList<>();
        pagerList = new ArrayList<>();
        for (Channel homeTitle : homeTitleItems) {
            pagerList.add(new HomePagerView(this));
            titleTextList.add(homeTitle.getName());
        }

        HomeTabPagerAdapter mAdapter = new HomeTabPagerAdapter(pagerList, titleTextList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int position;

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                // 当滑动停止的时候，才去加载数据
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pagerList.get(position).refreshData("0");
                }
            }
        });

        //默认加载第一页
        mViewPager.setCurrentItem(0);
        pagerList.get(0).refreshData(homeTitleItems.get(0).getId());
    }

    /**
     * 模拟本地数据
     */
    private ArrayList<Channel> analogNativeData(){
        ArrayList<Channel> titleList=new ArrayList<>();
        titleList.add(new Channel("A","新闻"));
        titleList.add(new Channel("B","吉尔吉斯斯坦"));
        titleList.add(new Channel("C","哈萨克斯坦"));
        titleList.add(new Channel("D","乌兹别克斯坦"));
        titleList.add(new Channel("E","土库曼斯坦"));
        titleList.add(new Channel("F","俄罗斯"));
        titleList.add(new Channel("G","外高索三国"));
        titleList.add(new Channel("H","我的"));
        return titleList;
    }
}
