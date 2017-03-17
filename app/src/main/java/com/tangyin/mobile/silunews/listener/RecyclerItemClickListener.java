package com.tangyin.mobile.silunews.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tangyin.mobile.silunews.adapter.HomeRecyclerViewAdapter;
import com.tangyin.mobile.silunews.model.ContentInfo;

import java.util.List;


/**
 * 新闻列表点击监听
 */
public class RecyclerItemClickListener implements HomeRecyclerViewAdapter.OnItemClickListener {

    private Context mContext;

    public RecyclerItemClickListener(Context context) {
        mContext = context;
    }


    @Override
    public void onItemClick(int position, View v, List<ContentInfo> items) {
//        Intent intent = new Intent(mContext, HomeNewsDetailActivity.class);
//        intent.putExtra("news_data", items.get(itemListPosition - 1));
//        mContext.startActivity(intent);
    }
}
