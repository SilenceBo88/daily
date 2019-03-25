package com.zb.daily.UI.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/2 21:36
 * @Description: 实现自己的触摸事件
 */
public class MyItemTouchCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapterCallback itemTouchCallback;

    public MyItemTouchCallback(ItemTouchHelperAdapterCallback itemtouchcallback) {
        this.itemTouchCallback = itemtouchcallback;
    }

    //是否长按拖动
    @Override
    public boolean isLongPressDragEnabled() {//可以进行长按拖动
        return true;
    }

    //最先调用，判断ITEM触摸拖动方向，如上下左右拖动；滑动方向SWIPE 左右滑动
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //callback监听那些动作?ItemTouchHelperItemTouchHelper--判断方向的
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//上下拖动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//左右拖动
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    //拖动时item时，调用adapter里的onItemMove函数实现ITEM交换位置与交换数据
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
        if(srcHolder.getItemViewType()!=targetHolder.getItemViewType()){
            return false;
        }
        boolean result = itemTouchCallback.onItemMove(srcHolder.getAdapterPosition(), targetHolder.getAdapterPosition());
        return result;
    }

    //swipe侧滑时调用adapter里的onItemRemove函数实现item删除
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    //拖动时ITEM背景色
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.color_shallow2_gray));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //交换数据后停止拖动，背景色恢复为白色
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        super.clearView(recyclerView, viewHolder);
    }

    //重新绘制ITEM的大小等。例如在这里滑动删除时，让ITEM逐渐缩小，并逐渐变成透明而消失
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {//侧滑状态
            //透明度动画
            float alpha = 1-Math.abs(dX)/viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);//1~0
            viewHolder.itemView.setScaleX(alpha);//1~0
            viewHolder.itemView.setScaleY(alpha);//1~0
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}