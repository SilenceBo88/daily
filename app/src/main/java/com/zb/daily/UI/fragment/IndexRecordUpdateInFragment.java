package com.zb.daily.UI.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.zb.daily.adapter.RecordCategoryListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.CategoryDao;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;
import com.zb.daily.model.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/22 20:43
 * @Description: 修改记录的收入页面
 */
@SuppressLint("ValidFragment")
public class IndexRecordUpdateInFragment extends Fragment {

    FragmentActivity activity;

    private EditText textMoney;

    private EditText textRemark;

    private LinearLayout dateLayout;

    private TextView dateText;

    private LinearLayout assetsLayout;

    private TextView assetsText;

    private ImageView categoryImage;

    private Button saveButton;

    private AssetsTransferDialogAdapter listAdapter;
    private AssetsDao assetsDao = new AssetsDao();
    private List<Assets> assetsList;
    private Assets currentAssets;
    private Category currentCategory;

    private CategoryDao categoryDao = new CategoryDao();
    private RecordDao recordDao = new RecordDao();
    List<Category> categoryImageList;

    //默认图标
    int defaultImage = 0;
    //默认资产
    int defaultAssets = 1;
    //默认日期
    String defaultDate = "";

    //新选择的图标
    int categoryNewImage = 0;

    Record record;

    public IndexRecordUpdateInFragment(Record record){
        this.record = record;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_index_record_update_in, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textMoney = activity.findViewById(R.id.fragment_index_record_update_in_money_text);
        textRemark = activity.findViewById(R.id.fragment_index_record_update_in_remark_text);
        dateLayout = activity.findViewById(R.id.fragment_index_record_update_in_date);
        dateText = activity.findViewById(R.id.fragment_index_record_update_in_date_text);
        assetsLayout = activity.findViewById(R.id.fragment_index_record_update_in_assets);
        assetsText = activity.findViewById(R.id.fragment_index_record_update_in_assets_text);
        categoryImage = activity.findViewById(R.id.fragment_index_record_update_in_category_image);
        saveButton = activity.findViewById(R.id.fragment_index_record_update_in_btn_save);

        assetsList = assetsDao.findAssetsList();
        listAdapter = new AssetsTransferDialogAdapter(MyApplication.getContext(), assetsList);
        categoryImageList = categoryDao.findCategoryListByType(2);

        defaultDate = record.getDate();
        currentAssets = record.getAssets();
        if (record.getType() == 2){
            currentCategory = record.getCategory();
        }else {
            currentCategory = categoryImageList.get(0);
        }

        defaultImage = currentCategory.getImageId();
        categoryNewImage = currentCategory.getImageId();

        assetsText.setText(currentAssets.getName());
        categoryImage.setImageResource(defaultImage);
        dateText.setText(defaultDate);
        textRemark.setText(record.getRemark());
        textMoney.setText(record.getMoney().toString());

        //设置图标list的适配器
        RecyclerView categoryImageRecyclerView = activity.findViewById(R.id.fragment_index_record_update_in_add_image_recyclerView);
        StaggeredGridLayoutManager categoryImageLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        categoryImageRecyclerView.setLayoutManager(categoryImageLayoutManager);
        RecordCategoryListAdapter categoryImageAdapter = new RecordCategoryListAdapter(categoryImageList, defaultImage);
        //adapter与Activity的数据交互
        categoryImageAdapter.setSubClickListener(new RecordCategoryListAdapter.SubClickListener() {
            @Override
            public void OnTopicClickListener(View v, int position) {
                currentCategory = categoryImageList.get(position);
                categoryImage.setImageResource(currentCategory.getImageId());
                categoryNewImage = currentCategory.getImageId();
            }
        });
        categoryImageRecyclerView.setAdapter(categoryImageAdapter);

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        assetsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetsAdapterDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = textMoney.getText().toString().trim();
                if (money.isEmpty()){
                    ToastUtils.show("金额不能为空");
                    return;
                }
                String remark = textRemark.getText().toString().trim();

                Record temp = new Record(Double.valueOf(money), defaultDate, remark, 2,
                        currentCategory.getId(), currentCategory.getImageId(), currentCategory.getName(),
                        currentAssets.getId(), currentAssets.getName());
                temp.setId(record.getId());
                if (recordDao.updateRecord(temp)){
                    if (currentAssets.getType() == 1){
                        if (record.getType() == 2){
                            assetsDao.removeBalance(record.getAssets(), record.getMoney().toString());
                        }else {
                            assetsDao.addBalance(record.getAssets(), record.getMoney().toString());
                        }
                        Assets assets = assetsDao.findAssetsById(currentAssets.getId());
                        assetsDao.addBalance(assets, money);
                    }else {
                        if (record.getType() == 2){
                            assetsDao.addBalance(record.getAssets(), record.getMoney().toString());
                        }else {
                            assetsDao.removeBalance(record.getAssets(), record.getMoney().toString());
                        }
                        Assets assets = assetsDao.findAssetsById(currentAssets.getId());
                        assetsDao.removeBalance(assets, money);
                    }

                    ToastUtils.show("修改成功");
                    MainActivity.actionStart(activity, Constant.TO_INDEX_FRAGMENT);
                    activity.finish();
                }
            }
        });
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
                defaultDate = time;
                dateText.setText(time);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //账户弹出框
    private void assetsAdapterDialog() {
        AlertDialog.Builder listDialog = new AlertDialog.Builder(activity);
        listDialog.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                currentAssets = assetsList.get(which);
                assetsText.setText(currentAssets.getName());
            }
        });
        listDialog.show();
    }
}
