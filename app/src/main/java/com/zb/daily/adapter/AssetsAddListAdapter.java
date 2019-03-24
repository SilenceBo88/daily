package com.zb.daily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.UI.AssetsNewActivity;
import com.zb.daily.model.Assets;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 添加资产页面的list的适配器
 */
public class AssetsAddListAdapter extends RecyclerView.Adapter<AssetsAddListAdapter.ViewHolder>{

    private Context mContext;
    private List<Assets> mAssetsList;

    public AssetsAddListAdapter(List<Assets> assetsList) {
        mAssetsList = assetsList;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView assetsImage;
        TextView assetsName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            assetsImage = view.findViewById( R.id.item_assets_addList_image);
            assetsName = view.findViewById(R.id.item_assets_addList_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_add_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //list中的每个item的点击事件，打开新建资产页面
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Assets assets = mAssetsList.get(position);

                Intent intent = new Intent();
                intent.setClass(mContext, AssetsNewActivity.class);
                intent.putExtra("assets", JSONObject.toJSONString(assets));
                ((AppCompatActivity)mContext).startActivityForResult(intent,1);
            }
        });
        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Assets assets = mAssetsList.get(position);
        holder.assetsName.setText(assets.getName());
        Glide.with(mContext).load(assets.getImageId()).into(holder.assetsImage);
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mAssetsList.size();
    }

}
