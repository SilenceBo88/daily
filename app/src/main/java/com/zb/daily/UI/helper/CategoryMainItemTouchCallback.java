package com.zb.daily.UI.helper;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.adapter.CategoryMainListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.CategoryDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 21:15
 * @Description: 分类主页面的触摸帮助类
 */
public class CategoryMainItemTouchCallback extends MyItemTouchCallback{

    public CategoryMainItemTouchCallback(ItemTouchHelperAdapterCallback itemtouchcallback) {
        super(itemtouchcallback);
    }

    //进行侧滑拖动
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

}
