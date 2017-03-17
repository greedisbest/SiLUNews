package com.tangyin.mobile.silunews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.tangyin.mobile.silunews.views.HomePagerView;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * Tablayout 与 ViewPager 的 Pager数据源
 */
public class HomeTabPagerAdapter extends PagerAdapter {

    private List<HomePagerView> pagerList;//盛放pager（轮播图+新闻列表的view）
    private List<String> titleTextList;//标题

    private LinkedHashMap<Integer, View> views = new LinkedHashMap<>();//每个被加载出来的view都放入此列表

    public HomeTabPagerAdapter(List<HomePagerView> pagerList, List<String> titleTextList) {
        this.pagerList = pagerList;
        this.titleTextList = titleTextList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleTextList.get(position);
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HomePagerView homePager = pagerList.get(position);
        View view = views.get(position);
        if (view == null) {//如果还没有被加载过则加载view
            view = homePager.inflateView();
            views.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
