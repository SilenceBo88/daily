package com.zb.daily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.UI.helper.ItemTouchHelperAdapterCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.model.Assets;

import java.util.Collections;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 资产滚动控件的适配器
 */
public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.ViewHolder> implements ItemTouchHelperAdapterCallback {

    private Context mContext;
    private List<Assets> mAssetsList;
    private StartDragListener startDragListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView assetsImage;
        TextView assetsName;
        TextView assetsBalance;
        TextView assetsRemark;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            assetsImage = view.findViewById( R.id.assets_image);
            assetsName = view.findViewById(R.id.assets_name);
            assetsBalance = view.findViewById(R.id.assets_balance);
            assetsRemark = view.findViewById(R.id.assets_remark);
        }
    }

    public AssetsAdapter(List<Assets> assetsList, StartDragListener startDragListener) {
        mAssetsList = assetsList;
        this.startDragListener = startDragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_assets, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //滑动控件的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int position = holder.getAdapterPosition();
            Assets assets = mAssetsList.get(position);
            Toast.makeText(v.getContext(), assets.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //触摸事件
        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //这里面当点击图片的时候 开始执行拖动。因为itemtouchhelper的startDrag方法需要一个viewholder。所以通过回调的方法吧holder传出去
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    startDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
        return holder;
    }

    //绑定数据到列表项
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Assets assets = mAssetsList.get(position);
        holder.assetsName.setText(assets.getName());
        holder.assetsBalance.setText(assets.getBalance().toString());
        if (!(assets.getRemark() == "") && !(assets.getRemark() == null)){
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

    //两个接口回调的方法.来执行 移动和 删除之后的操作
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //数据发生改变  两个数据交换位置
        Collections.swap(mAssetsList, fromPosition, toPosition);
        //刷新数据
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }
}
