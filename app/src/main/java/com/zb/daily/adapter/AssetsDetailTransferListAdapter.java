package com.zb.daily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.AssetsTransfer;
import com.zb.daily.model.Record;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 23:11
 * @Description: 资产详情页面的转账页面list适配器
 */
public class AssetsDetailTransferListAdapter extends RecyclerView.Adapter<AssetsDetailTransferListAdapter.ViewHolder> {

    private Context mContext;
    private List<AssetsTransfer> mAssetsTransferList;

    public AssetsDetailTransferListAdapter(List<AssetsTransfer> assetsTransferList) {
        mAssetsTransferList = assetsTransferList;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromAssetsName;
        TextView toAssetsName;
        TextView assetsTransferBalance;
        TextView assetsTransferRemark;
        TextView assetsTransferDate;

        public ViewHolder(View view) {
            super(view);
            fromAssetsName = view.findViewById(R.id.item_assets_detail_transfer_list_from);
            toAssetsName = view.findViewById(R.id.item_assets_detail_transfer_list_to);
            assetsTransferBalance = view.findViewById(R.id.item_assets_detail_transfer_list_money);
            assetsTransferRemark = view.findViewById(R.id.item_assets_detail_transfer_list_remark);
            assetsTransferDate = view.findViewById(R.id.item_assets_detail_transfer_list_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_detail_transfer_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AssetsTransfer assetsTransfer = mAssetsTransferList.get(position);
        holder.fromAssetsName.setText(assetsTransfer.getFromAssets().getName());
        holder.toAssetsName.setText(assetsTransfer.getToAssets().getName());
        holder.assetsTransferBalance.setText(assetsTransfer.getMoney().toString());
        holder.assetsTransferRemark.setText(assetsTransfer.getRemark());
        holder.assetsTransferDate.setText(assetsTransfer.getDate());
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mAssetsTransferList.size();
    }

    //获取资产列表
    public List<AssetsTransfer> getAssetsList(){
        return mAssetsTransferList;
    }
}
