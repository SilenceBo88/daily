package com.zb.daily.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.UI.MainActivity;
import com.zb.daily.UI.RecordUpdateActivity;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Record;
import com.zb.daily.util.SPUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 记账页面的list适配器
 */
public class RecordMainListAdapter extends RecyclerView.Adapter<RecordMainListAdapter.ViewHolder>{

    private Context mContext;
    private List<Record> mRecordList;
    private RecordDao recordDao = new RecordDao();
    private HashMap<String, Integer> dateItem = new HashMap<>();
    private double dayIn = 0;
    private double dayOut = 0;
    private int longClickPosition = -1;
    private AssetsDao assetsDao = new AssetsDao();
    //实现与Activity的数据传输
    private RecordMainListAdapter.SubClickListener subClickListener;

    public RecordMainListAdapter(List<Record> recordList) {
        mRecordList = recordList;
    }

    //初始化item中的属性
    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout daySummary;
        TextView dayDate;
        TextView dayIn;
        TextView dayOut;
        CardView cardView;
        ImageView recordImage;
        TextView recordName;
        TextView recordMoney;
        TextView recordRemark;
        TextView recordAssets;

        public ViewHolder(View view) {
            super(view);
            daySummary = view.findViewById(R.id.item_record_main_list_day_summary);
            dayDate = view.findViewById(R.id.item_record_main_list_day_date);
            dayIn = view.findViewById(R.id.item_record_main_list_day_in);
            dayOut = view.findViewById(R.id.item_record_main_list_day_out);
            cardView = view.findViewById(R.id.item_record_main_list_cardView);
            recordImage = view.findViewById( R.id.item_record_main_list_image);
            recordName = view.findViewById(R.id.item_record_main_list_name);
            recordMoney = view.findViewById(R.id.item_record_main_list_money);
            recordRemark = view.findViewById(R.id.item_record_main_list_remark);
            recordAssets = view.findViewById(R.id.item_record_main_list_assets);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_main_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        final boolean checked = (boolean) SPUtil.get(mContext, "record_lock", false);

        //list中的每个item的点击事件，打开修改记录页面
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked){
                    ToastUtils.show("记录已经被锁定");
                    return;
                }
                int position = holder.getAdapterPosition();
                Record record = mRecordList.get(position);
                RecordUpdateActivity.actionStart(mContext, record);
               /* AssetsDetailActivity.actionStart(mContext, assets, position);*/
            }
        });

        //长按触发删除事件
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (checked){
                    ToastUtils.show("记录已经被锁定");
                    return false;
                }
                longClickPosition = holder.getAdapterPosition();
                showDeleteRecordDialog();
                return true;
            }
        });

        return holder;
    }

    //绑定数据到item
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Record record = mRecordList.get(position);

        //是否显示日小结
        if (!dateItem.containsKey(record.getDate())){
            holder.daySummary.setVisibility(View.VISIBLE);
            int count = 1;
            dateItem.put(record.getDate(), count);

            //查询日支出和收入
            dayOut = recordDao.getDaySummary(1, record.getDate());
            dayIn = recordDao.getDaySummary(2, record.getDate());
        }else {
            holder.daySummary.setVisibility(View.GONE);
            int count = dateItem.get(record.getDate()) + 1;
            dateItem.put(record.getDate(), count);
        }

        //设置日小结的值
        holder.dayDate.setText(record.getDate());
        holder.dayIn.setText(String.valueOf(dayIn));
        holder.dayOut.setText(String.valueOf(dayOut));

        //设置记录item的值
        holder.recordName.setText(record.getCategoryName());
        if (record.getType() == 1){
            holder.recordMoney.setText("-"+record.getMoney().toString());
        }else {
            holder.recordMoney.setText("+"+record.getMoney().toString());
        }
        if (!record.getRemark().isEmpty()){
            holder.recordRemark.setVisibility(View.VISIBLE);
            holder.recordRemark.setText(record.getRemark());
        }else {
            holder.recordRemark.setVisibility(View.GONE);
        }
        if (!record.getAssetsName().isEmpty()){
            holder.recordAssets.setVisibility(View.VISIBLE);
            holder.recordAssets.setText(record.getAssetsName());
        }else {
            holder.recordAssets.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(record.getCategoryImageId()).into(holder.recordImage);
    }

    //删除资产确定框
    private void showDeleteRecordDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        normalDialog.setTitle("删除记录");
        normalDialog.setMessage("确定要删除该记录么？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Record record = mRecordList.get(longClickPosition);
                        mRecordList.remove(longClickPosition);
                        dateItem = new HashMap<>();
                        notifyDataSetChanged();
                        Assets assets = record.getAssets();
                        if (recordDao.deleteRecord(record.getId())){
                            if (assets.getType() == 1){
                                if (record.getType() == 1){
                                    assetsDao.addBalance(record.getAssets(), record.getMoney().toString());
                                }else {
                                    assetsDao.removeBalance(record.getAssets(), record.getMoney().toString());
                                }
                            }else {
                                if (record.getType() == 1){
                                    assetsDao.removeBalance(record.getAssets(), record.getMoney().toString());
                                }else {
                                    assetsDao.addBalance(record.getAssets(), record.getMoney().toString());
                                }
                            }
                            ToastUtils.show("删除成功");
                            subClickListener.OnTopicClickListener("change");
                            //MainActivity.actionStart(mContext, Constant.TO_INDEX_FRAGMENT);
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

    //获取item数量
    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    //获取资产列表
    public List<Record> getRecordList(){
        return mRecordList;
    }

    //传数据到activity的接口
    public interface SubClickListener {
        void OnTopicClickListener(String s);
    }

    public void setSubClickListener(RecordMainListAdapter.SubClickListener topicClickListener) {
        this.subClickListener = topicClickListener;
    }
}
