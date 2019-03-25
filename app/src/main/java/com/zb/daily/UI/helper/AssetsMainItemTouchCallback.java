package com.zb.daily.UI.helper;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 21:14
 * @Description: 资产主页面的触摸帮助类
 */
public class AssetsMainItemTouchCallback extends MyItemTouchCallback{

    public AssetsMainItemTouchCallback(ItemTouchHelperAdapterCallback itemtouchcallback) {
        super(itemtouchcallback);
    }

    //进行侧滑拖动
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

}
