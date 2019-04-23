package com.zb.daily.UI.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.MainActivity;
import com.zb.daily.adapter.AssetsTransferDialogAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.AssetsTransferDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.AssetsTransfer;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/02 20:43
 * @Description: 记一笔的收入页面
 */
public class IndexTransferFragment extends Fragment {

    public FragmentActivity activity;

    private LinearLayout outLinearLayout;
    private LinearLayout inLinearLayout;
    private LinearLayout dateLinearLayout;
    //保存按钮
    private Button saveButton;
    //转出资产账户
    private Assets outAssets;
    //转入资产账户
    private Assets inAssets;
    private TextView outTextView;
    private TextView inTextView;
    private TextView dateTextView;
    private EditText moneyText;
    private EditText remarkText;
    //资产弹出框
    private AssetsTransferDialogAdapter listAdapter;
    private AssetsDao assetsDao = new AssetsDao();
    private List<Assets> assetsList;
    private AssetsTransferDao assetsTransferDao = new AssetsTransferDao();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_index_transfer, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        outLinearLayout = activity.findViewById(R.id.fragment_index_transfer_out);
        inLinearLayout = activity.findViewById(R.id.fragment_index_transfer_in);
        dateLinearLayout = activity.findViewById(R.id.fragment_index_transfer_date);
        outTextView = activity.findViewById(R.id.fragment_index_transfer_out_text);
        inTextView = activity.findViewById(R.id.fragment_index_transfer_in_text);
        dateTextView = activity.findViewById(R.id.fragment_index_transfer_date_text);
        moneyText = activity.findViewById(R.id.fragment_index_transfer_money_text);
        remarkText = activity.findViewById(R.id.fragment_index_transfer_remark_text);
        saveButton = activity.findViewById(R.id.fragment_index_transfer_btn_save);

        outLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOutAdapterDialog();
            }
        });
        inLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInAdapterDialog();
            }
        });
        dateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        //保存
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == outAssets){
                    ToastUtils.show("转出账户不能为空");
                    return;
                }
                if (null == inAssets){
                    ToastUtils.show("转入账户不能为空");
                    return;
                }
                String money = moneyText.getText().toString().trim();
                if (money.isEmpty()){
                    ToastUtils.show("金额不能为空");
                    return;
                }
                String date = dateTextView.getText().toString().trim();
                if (date.isEmpty()){
                    ToastUtils.show("日期不能为空");
                    return;
                }
                String remark = "";
                remark = remarkText.getText().toString().trim();

                AssetsTransfer assetsTransfer = new AssetsTransfer();
                assetsTransfer.setFromId(outAssets.getId());
                assetsTransfer.setToId(inAssets.getId());
                assetsTransfer.setMoney(Double.valueOf(money));
                assetsTransfer.setDate(date);
                assetsTransfer.setRemark(remark);

                if (assetsTransferDao.saveAssets(assetsTransfer)){
                    ToastUtils.show("保存成功");
                    MainActivity.actionStart(activity, Constant.TO_INDEX_FRAGMENT);
                    activity.finish();
                }else {
                    ToastUtils.show("保存失败");
                }
            }
        });

        assetsList = assetsDao.findAssetsList();
        listAdapter = new AssetsTransferDialogAdapter(MyApplication.getContext(), assetsList);
    }

    //转出账户弹出框
    private void showOutAdapterDialog() {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(activity);
        listDialog.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                outAssets = assetsList.get(which);
                outTextView.setText(outAssets.getName());
            }
        });
        listDialog.show();
    }

    //转入账户弹出框
    private void showInAdapterDialog() {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(activity);
        listDialog.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                inAssets = assetsList.get(which);
                inTextView.setText(inAssets.getName());
            }
        });
        listDialog.show();
    }

    //日期弹出框
    private void showDateDialog() {
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_assets_transfer_dialog_date, null);
        final DatePicker dateTime = view.findViewById(R.id.activity_assets_transfer_dialog_date_pick);
        dateTime.updateDate(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int month = dateTime.getMonth() + 1;
                String monthStr = "";
                if (month < 10){
                    monthStr = "0" + month;
                }else {
                    monthStr = "" + month;
                }

                int day = dateTime.getDayOfMonth();
                String dayStr = "";
                if (day < 10){
                    dayStr = "0" + day;
                }else {
                    dayStr = "" + day;
                }

                String time = "" + dateTime.getYear() + "-" + monthStr + "-" + dayStr;
                dateTextView.setText(time);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
