package com.zb.daily.UI.helper;

/**
 * @auther: zb
 * @Date: 2019/3/2 21:36
 * @Description: 触摸事件接口
 */
public interface ItemTouchHelperAdapterCallback {
    /**
     * 拖拽滑动
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */
    boolean onItemMove(int fromPosition, int toPosition);
}