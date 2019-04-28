package com.zb.daily.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.hjq.toast.ToastUtils;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsTransferDialogAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.AssetsTransferDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.AssetsTransfer;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/24 10:27
 * @Description: 资产转账页面
 */
public class AssetsTransferActivity extends BaseActivity {

    private LinearLayout outLinearLayout;
    private LinearLayout inLinearLayout;
    private LinearLayout dateLinearLayout;
    //返回按钮
    private Button preButton;
    //保存按钮
    private Button saveButton;
    //转出资产账户
    private Assets outAssets = null;
    //转入资产账户
    private Assets inAssets = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_transfer);

        outLinearLayout = findViewById(R.id.activity_assets_transfer_out);
        inLinearLayout = findViewById(R.id.activity_assets_transfer_in);
        dateLinearLayout = findViewById(R.id.activity_assets_transfer_date);
        outTextView = findViewById(R.id.activity_assets_transfer_out_text);
        inTextView = findViewById(R.id.activity_assets_transfer_in_text);
        dateTextView = findViewById(R.id.activity_assets_transfer_date_text);
        moneyText = findViewById(R.id.activity_assets_transfer_money_text);
        remarkText = findViewById(R.id.activity_assets_transfer_remark_text);
        preButton = findViewById(R.id.activity_assets_transfer_btn_pre);
        saveButton = findViewById(R.id.activity_assets_transfer_btn_save);

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

        //返回上一个活动
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if (inAssets.getId() == outAssets.getId()){
                    ToastUtils.show("转入账户与转出账户不能相同");
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
                    if (outAssets.getType() == 1 && inAssets.getType() == 1){
                        assetsDao.removeBalance(outAssets, money);
                        assetsDao.addBalance(inAssets, money);
                    }
                    if (outAssets.getType() == 2 && inAssets.getType() == 2){
                        assetsDao.addBalance(outAssets, money);
                        assetsDao.removeBalance(inAssets, money);
                    }
                    if (outAssets.getType() == 1 && inAssets.getType() == 2){
                        assetsDao.removeBalance(outAssets, money);
                        assetsDao.removeBalance(inAssets, money);
                    }
                    if (outAssets.getType() == 2 && inAssets.getType() == 1){
                        assetsDao.addBalance(outAssets, money);
                        assetsDao.addBalance(inAssets, money);
                    }
                    ToastUtils.show("保存成功");
                    MainActivity.actionStart(getApplicationContext(), Constant.TO_ASSETS_FRAGMENT);

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
        AlertDialog.Builder listDialog = new AlertDialog.Builder(this);
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
        AlertDialog.Builder listDialog = new AlertDialog.Builder(this);
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
        View view = LayoutInflater.from(this).inflate(R.layout.activity_assets_transfer_dialog_date, null);
        final DatePicker dateTime = view.findViewById(R.id.activity_assets_transfer_dialog_date_pick);
        dateTime.updateDate(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    //启动本活动
    public static void actionStart(Context context){
        Intent intent = new Intent();
        intent.setClass(context, AssetsTransferActivity.class);
        /*context.startActivity(intent);*/
        ((BaseActivity)context).startActivityForResult(intent,3002);
    }
}
