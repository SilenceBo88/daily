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
import com.zb.daily.model.Category;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 18:01
 * @Description: 记录页面的分类的list适配器
 */
public class RecordCategoryListAdapter extends RecyclerView.Adapter<RecordCategoryListAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mCategoryImageList;
    //默认选择的分类
    int defaultImage = 0;
    //点击的分类
    ImageView clickImageView = null;
    //实现与Activity的数据传输
    private SubClickListener subClickListener;

    public RecordCategoryListAdapter(List<Category> categoryImageList, int defaultImage) {
        mCategoryImageList = categoryImageList;
        this.defaultImage = defaultImage;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImageTrue;
        ImageView categoryImage;
        TextView categoryText;

        public ViewHolder(View view) {
            super(view);
            categoryImage = view.findViewById( R.id.item_record_category_list_image);
            categoryImageTrue = view.findViewById( R.id.item_record_category_list_true);
            categoryText = view.findViewById(R.id.item_record_category_list_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_category_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //list中的每个item的点击事件，控制小对勾的显示与修改activity中的小图标
        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImageView.setVisibility(View.GONE);
                clickImageView = holder.categoryImageTrue;
                clickImageView.setVisibility(View.VISIBLE);
                //修改activity中的小图标
                subClickListener.OnTopicClickListener(v, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int image = mCategoryImageList.get(position).getImageId();
        Glide.with(mContext).load(image).into(holder.categoryImage);
        holder.categoryText.setText(mCategoryImageList.get(position).getName());
        if (image == defaultImage){
            clickImageView = holder.categoryImageTrue;
            clickImageView.setVisibility(View.VISIBLE);
        }
    }

    //获取item数量
    @Override
    public int getItemCount() {
        return mCategoryImageList.size();
    }

    //获取资产列表
    public List<Category> getCategoryList(){
        return mCategoryImageList;
    }

    //传数据到activity的接口
    public interface SubClickListener {
        void OnTopicClickListener(View v, int position);
    }

    public void setSubClickListener(SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }

}
