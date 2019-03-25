package com.zb.daily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.UI.AssetsDetailActivity;
import com.zb.daily.UI.helper.ItemTouchHelperAdapterCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.model.Assets;

import java.util.Collections;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 资产管理页面的list适配器
 */
public class AssetsMainListAdapter extends RecyclerView.Adapter<AssetsMainListAdapter.ViewHolder> implements ItemTouchHelperAdapterCallback {

    private Context mContext;
    private List<Assets> mAssetsList;
    //用来实现长按交换item顺序
    private StartDragListener startDragListener;

    public AssetsMainListAdapter(List<Assets> assetsList, StartDragListener startDragListener) {
        mAssetsList = assetsList;
        this.startDragListener = startDragListener;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView assetsImage;
        TextView assetsName;
        TextView assetsBalance;
        TextView assetsRemark;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            assetsImage = view.findViewById( R.id.item_assets_mainList_image);
            assetsName = view.findViewById(R.id.item_assets_mainList_name);
            assetsBalance = view.findViewById(R.id.item_assets_mainList_balance);
            assetsRemark = view.findViewById(R.id.item_assets_mainList_remark);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets_main_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //list中的每个item的点击事件，打开资产详情页面
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Assets assets = mAssetsList.get(position);
                AssetsDetailActivity.actionStart(mContext, assets);
            }
        });

        //触摸事件，用来实现长按交换item顺序
        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //长按事件
                    holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            startDragListener.onStartDrag(holder);
                            return true;
                        }
                    });
                }
                return false;
            }
        });
        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Assets assets = mAssetsList.get(position);
        holder.assetsName.setText(assets.getName());
        holder.assetsBalance.setText(assets.getBalance().toString());
        if (!assets.getRemark().isEmpty()){
            holder.assetsRemark.setText(assets.getRemark());
        }else {
            holder.assetsRemark.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(assets.getImageId()).into(holder.assetsImage);
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mAssetsList.size();
    }

    //接口回调的方法,来执行移动之后的操作（实现长按交换item顺序）
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //数据发生改变  两个数据交换位置
        Collections.swap(mAssetsList, fromPosition, toPosition);
        //刷新数据
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    //获取资产列表
    public List<Assets> getAssetsList(){
        return mAssetsList;
    }
}
