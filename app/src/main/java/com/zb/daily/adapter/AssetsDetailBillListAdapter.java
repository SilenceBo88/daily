package com.zb.daily.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.UI.AssetsDetailActivity;
import com.zb.daily.UI.helper.ItemTouchHelperAdapterCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Record;

import java.util.Collections;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 23:11
 * @Description: 资产详情页面的账单页面list适配器
 */
public class AssetsDetailBillListAdapter extends RecyclerView.Adapter<AssetsDetailBillListAdapter.ViewHolder> {

    private Context mContext;
    private List<Record> mRecordList;

    public AssetsDetailBillListAdapter(List<Record> recordList) {
        mRecordList = recordList;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recordImage;
        TextView recordName;
        TextView recordBalance;
        TextView recordRemark;
        TextView recordDate;

        public ViewHolder(View view) {
            super(view);
            recordImage = view.findViewById( R.id.item_assets_detail_bill_list_image);
            recordName = view.findViewById(R.id.item_assets_detail_bill_list_name);
            recordBalance = view.findViewById(R.id.item_assets_detail_bill_list_balance);
            recordRemark = view.findViewById(R.id.item_assets_detail_bill_list_remark);
            recordDate = view.findViewById(R.id.item_assets_detail_bill_list_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_detail_bill_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Record record = mRecordList.get(position);
        holder.recordName.setText(record.getCategory().getName());
        holder.recordDate.setText(record.getDate());
        if (record.getType() == 1){
            holder.recordBalance.setText("- " + record.getMoney().toString());
            holder.recordBalance.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else {
            holder.recordBalance.setText("+ " + record.getMoney().toString());
            holder.recordBalance.setTextColor(mContext.getResources().getColor(R.color.color_green));
        }
        if (!record.getRemark().isEmpty()){
            holder.recordRemark.setVisibility(View.VISIBLE);
            holder.recordRemark.setText(record.getRemark());
        }else {
            holder.recordRemark.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(record.getCategory().getImageId()).into(holder.recordImage);
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    //获取资产列表
    public List<Record> getAssetsList(){
        return mRecordList;
    }
}
