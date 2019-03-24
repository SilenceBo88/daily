package com.zb.daily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.model.Assets;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/24 10:48
 * @Description: 资产转账页面中弹出框的list适配器
 */
public class AssetsDialogAdapter extends BaseAdapter {

    private List<Assets> assetsList;
    private LayoutInflater inflater;
    private Context mContext;

    public AssetsDialogAdapter(Context context, List<Assets> list){
        this.mContext = context;
        this.assetsList = list;
        inflater = LayoutInflater.from(mContext);
    }

    //初始化item中的属性
    class ViewHolder{
        ImageView assetsImage;
        TextView assetsName;
        TextView assetsBalance;
        TextView assetsRemark;

        public ViewHolder(View view) {
            assetsImage = view.findViewById( R.id.item_assets_transfer_dialogList_image);
            assetsName = view.findViewById(R.id.item_assets_transfer_dialogList_name);
            assetsBalance = view.findViewById(R.id.item_assets_transfer_dialogList_balance);
            assetsRemark = view.findViewById(R.id.item_assets_transfer_dialogList_remark);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hv;
        if (null==convertView){
            convertView = inflater.inflate(R.layout.item_assets_transfer_dialogList,null,false);
            hv = new ViewHolder(convertView);
            convertView.setTag(hv);
        }else {
            hv = (ViewHolder)convertView.getTag();
        }

        //绑定数据到item
        if (assetsList!=null && !assetsList.isEmpty()){
            hv.assetsName.setText(assetsList.get(position).getName());
            hv.assetsBalance.setText(assetsList.get(position).getBalance().toString());
            if (!assetsList.get(position).getRemark().isEmpty()){
                hv.assetsRemark.setText(assetsList.get(position).getRemark());
            }else {
                hv.assetsRemark.setVisibility(View.GONE);
            }
            Glide.with(mContext).load(assetsList.get(position).getImageId()).into(hv.assetsImage);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return assetsList.size();
    }

    @Override
    public Object getItem(int position) {
        return assetsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
