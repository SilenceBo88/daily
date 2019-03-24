package com.zb.daily.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsDialogAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.util.List;

public class TransferAssetsActivity extends AppCompatActivity {

    private LinearLayout outLinearLayout;
    private LinearLayout inLinearLayout;
    private LinearLayout dateLinearLayout;

    private Button preButton;
    private Button saveButton;

    private AssetsDialogAdapter listAdapter;
    private AssetsDao assetsDao = new AssetsDao();
    private List<Assets> assetsList;

    private Assets outAssets;
    private Assets inAssets;

    private TextView outTextView;
    private TextView inTextView;
    private TextView dateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_assets);

        outLinearLayout = findViewById(R.id.assets_transfer_out);
        inLinearLayout = findViewById(R.id.assets_transfer_in);
        dateLinearLayout = findViewById(R.id.assets_transfer_date);
        outTextView = findViewById(R.id.assets_transfer_out_text);
        inTextView = findViewById(R.id.assets_transfer_in_text);
        dateTextView = findViewById(R.id.assets_transfer_date_text);
        preButton = findViewById(R.id.assets_transfer_btn_pre);
        saveButton = findViewById(R.id.assets_transfer_btn_save);

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

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TransferAssetsActivity.this, MainActivity.class);
                intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
                startActivityForResult(intent,1);
            }
        });

        assetsList = assetsDao.findAssetsList();
        listAdapter = new AssetsDialogAdapter(MyApplication.getContext(), assetsList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(TransferAssetsActivity.this, MainActivity.class);
            intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
            startActivityForResult(intent,1);
        }
        return true;
    }

    private void showOutAdapterDialog() {
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
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

    private void showInAdapterDialog() {
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
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

    private void showDateDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);
        final DatePicker dateTime = view.findViewById(R.id.date_pick);
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
}
