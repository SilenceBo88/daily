package com.zb.daily.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zb.daily.R;
import com.zb.daily.model.Assets;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/24 10:48
 * @Description:
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hv;
        if (null==convertView){
            convertView = inflater.inflate(R.layout.item_dialog_assets,null,false);
            hv = new ViewHolder(convertView);
            convertView.setTag(hv);

        }else {
            hv = (ViewHolder)convertView.getTag();
        }
        //一定要判刑断下数据源是否为空，否则很大几率就crash了
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

    class ViewHolder{
        ImageView assetsImage;
        TextView assetsName;
        TextView assetsBalance;
        TextView assetsRemark;

        public ViewHolder(View view) {
            assetsImage = view.findViewById( R.id.assets_image);
            assetsName = view.findViewById(R.id.assets_name);
            assetsBalance = view.findViewById(R.id.assets_balance);
            assetsRemark = view.findViewById(R.id.assets_remark);
        }
    }
}
