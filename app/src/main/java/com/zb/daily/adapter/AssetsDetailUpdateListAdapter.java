package com.zb.daily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zb.daily.R;
import com.zb.daily.model.AssetsTransfer;
import com.zb.daily.model.AssetsUpdate;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 23:11
 * @Description: 资产详情页面的转账页面list适配器
 */
public class AssetsDetailUpdateListAdapter extends RecyclerView.Adapter<AssetsDetailUpdateListAdapter.ViewHolder> {

    private Context mContext;
    private List<AssetsUpdate> mAssetsUpdateList;

    public AssetsDetailUpdateListAdapter(List<AssetsUpdate> assetsUpdateList) {
        mAssetsUpdateList = assetsUpdateList;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromAssetsName;
        TextView toAssetsName;
        TextView assetsUpdateDate;

        public ViewHolder(View view) {
            super(view);
            fromAssetsName = view.findViewById(R.id.item_assets_detail_update_list_from);
            toAssetsName = view.findViewById(R.id.item_assets_detail_update_list_to);
            assetsUpdateDate = view.findViewById(R.id.item_assets_detail_update_list_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_detail_update_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AssetsUpdate assetsUpdate = mAssetsUpdateList.get(position);
        holder.fromAssetsName.setText(assetsUpdate.getFromMoney().toString());
        holder.toAssetsName.setText(assetsUpdate.getToMoney().toString());
        holder.assetsUpdateDate.setText(assetsUpdate.getDate());
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mAssetsUpdateList.size();
    }

    //获取资产列表
    public List<AssetsUpdate> getAssetsList(){
        return mAssetsUpdateList;
    }
}
