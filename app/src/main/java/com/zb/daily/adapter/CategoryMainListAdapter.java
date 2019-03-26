package com.zb.daily.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.AssetsDetailActivity;
import com.zb.daily.UI.MainActivity;
import com.zb.daily.UI.helper.ItemTouchHelperAdapterCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.dao.CategoryDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;

import java.util.Collections;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 18:01
 * @Description: 分类管理页面的list适配器
 */
public class CategoryMainListAdapter extends RecyclerView.Adapter<CategoryMainListAdapter.ViewHolder> implements ItemTouchHelperAdapterCallback {

    private Context mContext;
    private List<Category> mCategoryList;
    //用来实现长按交换item顺序
    private StartDragListener startDragListener;
    private CategoryDao categoryDao = new CategoryDao();
    private int clickPosition = -1;

    public CategoryMainListAdapter(List<Category> categoryList, StartDragListener startDragListener) {
        mCategoryList = categoryList;
        this.startDragListener = startDragListener;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView categoryImage;
        TextView categoryName;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            categoryImage = view.findViewById( R.id.item_category_main_list_image);
            categoryName = view.findViewById(R.id.item_category_main_list_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_main_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //list中的每个item的点击事件，打开资产详情页面
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition = holder.getAdapterPosition();
                showListDialog();
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
        Category category = mCategoryList.get(position);
        holder.categoryName.setText(category.getName());
        Glide.with(mContext).load(category.getImageId()).into(holder.categoryImage);
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    //接口回调的方法,来执行移动之后的操作（实现长按交换item顺序）
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //数据发生改变  两个数据交换位置
        Collections.swap(mCategoryList, fromPosition, toPosition);
        //刷新数据
        notifyItemMoved(fromPosition, toPosition);
        //替换旧的分类列表
        categoryDao.replaceOldList(mCategoryList);
        return true;
    }

    @Override
    public void onItemDelete(int position) {
    }

    //获取资产列表
    public List<Category> getCategoryList(){
        return mCategoryList;
    }

    private void showListDialog() {
        final String[] items = { "修改","删除"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    ToastUtils.show("你点击了" + items[which]);
                }
                if (which == 1){
                    showDeleteCategoryDialog();
                }
            }
        });
        listDialog.show();
    }

    //删除资产确定框
    private void showDeleteCategoryDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        normalDialog.setTitle("删除分类");
        normalDialog.setMessage("确定要删除该分类么，不会删除该分类下的已有收支记录");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Category category = mCategoryList.get(clickPosition);
                        mCategoryList.remove(clickPosition);
                        notifyItemRemoved(clickPosition);
                        if (categoryDao.deleteCategory(category.getId())){
                            ToastUtils.show("删除成功");
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
