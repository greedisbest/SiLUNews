package com.tangyin.mobile.silunews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.tangyin.mobile.silunews.R;
import com.tangyin.mobile.silunews.model.ContentInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <p>
 * 首页RecycleView 的适配器
 * <p>
 * Email:zhujun2730@gmail.com
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<ContentInfo> items;
    private OnItemClickListener clickListener;

    public HomeRecyclerViewAdapter(Context context, List<ContentInfo> items) {
        this.items = items;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v, List<ContentInfo> items);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_pager_home, parent,false);
        return  new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderOne holder= (ViewHolderOne) viewHolder;
        ContentInfo contentInfo = items.get(position);
        if(!TextUtils.isEmpty(contentInfo.getWapImg())){
            Picasso.with(context).load(contentInfo.getWapImg()).placeholder(R.mipmap.default_pic).error(R.mipmap.default_pic).into(holder.listImg);
            if("1".equals(items.get(position).getUniqueIdentify())) {
                holder.markImg.setVisibility(View.VISIBLE);
            } else {
                holder.markImg.setVisibility(View.GONE);
            }
        }else {
            holder.listImg.setVisibility(View.GONE);
            holder.markImg.setVisibility(View.GONE);
        }
    }

    /**
     * 右边一张小图片的布局
     */
    class ViewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.list_img)
        ImageView listImg;
        @Bind(R.id.list_title)
        TextView listTitle;
        @Bind(R.id.tv_bottom)
        TextView tvBottom;
        @Bind(R.id.comment_count)
        TextView commentCount;
        @Bind(R.id.icon_video)
        ImageView markImg;

        ViewHolderOne(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v, items);
        }
    }
}
