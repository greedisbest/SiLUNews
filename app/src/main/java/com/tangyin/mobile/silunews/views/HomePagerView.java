package com.tangyin.mobile.silunews.views;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tangyin.mobile.silunews.R;
import com.tangyin.mobile.silunews.adapter.HomeRecyclerViewAdapter;
import com.tangyin.mobile.silunews.listener.RecyclerItemClickListener;
import com.tangyin.mobile.silunews.model.ContentInfo;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * <p>
 * <p>
 * ViewPager 适配器 填充的 Pager【返回View给HomeTitlePagerAdapter适配器】
 * <p>
 * <p>
 */
public class HomePagerView implements XRecyclerView.LoadingListener {

    private final int PULL_TO_REFRESH = 0;
    private final int LOAD_MORE = 1;

    private Context mContext;
    private XRecyclerView mRecyclerView;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private TextView mTextViewError;
    private ProgressBar mProgressBar;

    private boolean hasInitData = false;
    private List<ContentInfo> homeNewsDataItems;
    private Subscription getMoreSubscribe;
    private CompositeSubscription mSubscriptions;
    private View headerView;
    private String catId;
    private Kanner kanner;

    public HomePagerView(Context context) {
        this.mContext = context;
    }

    public View inflateView() {
        View mRootView = View.inflate(mContext, R.layout.pager_home, null);
        mRecyclerView = (XRecyclerView) mRootView.findViewById(R.id.recycle_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
        mTextViewError = (TextView) mRootView.findViewById(R.id.tv_error);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.pb_pager_loading);
        initPagerView();
        return mRootView;
    }

    /**
     * 初始化PagerView的视图
     */
    private void initPagerView() {
        homeNewsDataItems = new ArrayList<>();
        mSubscriptions = new CompositeSubscription();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(mContext, homeNewsDataItems);
        mRecyclerView.setAdapter(homeRecyclerViewAdapter);
        mRecyclerView.setLoadingListener(this);
        homeRecyclerViewAdapter.setOnItemClickListener(new RecyclerItemClickListener(mContext));

        initHeaderView();
    }

    /**
     * 初始化轮播图视图
     */
    private void initHeaderView() {
        headerView=  View.inflate(mContext, R.layout.header_circle_images, null);
        kanner= (Kanner) headerView.findViewById(R.id.kanner);
        String[] images = initHeaderImages();
        String[] headerTitles = initHeaderTitles();
        kanner.setResourseUrl(images,headerTitles);
        mRecyclerView.addHeaderView(headerView);
    }

    /**
     * 模拟轮播图图片
     * @return
     */
    private String [] initHeaderImages(){
        return new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489669857955&di=7593627ce626d5620e2b3728e285b369&imgtype=0&src=http%3A%2F%2Fwww.cnr.cn%2Fnewscenter%2Ftyxw%2Ffootball%2F201207%2FW020120726525425666366.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490264529&di=2e596ba8509e30eeee770a422b9c99e7&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.jiaodong.net%2Fpic%2F0%2F11%2F17%2F89%2F11178993_999453.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489669840992&di=030392528c183242315e786fd0c9d165&imgtype=0&src=http%3A%2F%2Fwww.sznews.com%2Fhoroscope%2Fimages%2Fattachement%2Fjpg%2Fsite3%2F20111104%2F0014224750fe101d815f4c.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489669858738&di=2d4c0b379d3e5ae83f8212e139110f92&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20163_17_7%2Fa0g4323021709618362.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489669940370&di=68c990608bd1bc7ceb9513f45f5e1604&imgtype=0&src=http%3A%2F%2Fwww.fjsen.com%2Fimages%2Fattachement%2Fjpg%2Fsite2%2F20130408%2Fd067e519cb6612cc46615c.jpg"};
    }

    private String [] initHeaderTitles(){
        return new String[]{"我爱苍老师1","我爱苍老师1","我爱苍老师1","我爱苍老师1","我爱苍老师1"};
    }
    /**
     *  刷新数据
     * @param catId 每个选项卡对应的ID
     */
    public void refreshData(String catId) {
        this.catId = catId;
        if (!hasInitData) {
            final List<ContentInfo> contentInfos = analogNetData();//模拟从网络获取的数据列表
            handler.postDelayed(new Runnable() {//模拟网络延迟加载
                @Override
                public void run() {
                    handleResponseData(contentInfos, PULL_TO_REFRESH);
                }
            }, 2000);

        }
    }

    /**
     * 加载更多
     */
    private void getDataMore() {
        final List<ContentInfo> contentInfos = analogNetData();//模拟从网络获取的数据列表
        handler.postDelayed(new Runnable() {//模拟网络延迟加载
            @Override
            public void run() {
                handleResponseData(contentInfos, LOAD_MORE);
            }
        }, 2000);

    }

    private void handleResponseData( List<ContentInfo> homeNewsData, int type) {
        if (type == PULL_TO_REFRESH) { // 下拉刷新
            mProgressBar.setVisibility(View.GONE);
            if (homeNewsData != null) {
                mRecyclerView.setLoadingMoreEnabled(true);
                mTextViewError.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                homeNewsDataItems.clear();
                homeNewsDataItems.addAll(homeNewsData);
                homeRecyclerViewAdapter.notifyDataSetChanged();
            } else if (homeNewsDataItems.isEmpty()) {
                mTextViewError.setVisibility(View.VISIBLE);
            }
            mRecyclerView.refreshComplete();
        } else { // 加载更多
            if (homeNewsData != null) {
                mTextViewError.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                homeNewsDataItems.addAll(homeNewsData);
                homeRecyclerViewAdapter.notifyDataSetChanged();
            }
            mRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onRefresh() {
        hasInitData = false;
        refreshData(catId);
    }

    @Override
    public void onLoadMore() {
        getDataMore();
    }


    Handler handler=new Handler();

    /**
     *  模拟从网络加载数据
     */
    private List<ContentInfo> analogNetData(){
        List<ContentInfo> items=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ContentInfo contentInfo=new ContentInfo();
            contentInfo.setWapImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489663625355&di=ad5b560eb2a7359bdb4311ad7fdf5ec5&imgtype=0&src=http%3A%2F%2Fwww.hinews.cn%2Fpic%2F0%2F14%2F51%2F10%2F14511034_251576.jpg");
            items.add(contentInfo);
        }
        return items;

    }
}
