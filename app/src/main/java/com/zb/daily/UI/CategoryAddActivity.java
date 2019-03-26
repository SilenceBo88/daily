package com.zb.daily.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjq.toast.ToastUtils;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.CategoryImageListAdapter;
import com.zb.daily.dao.CategoryDao;
import com.zb.daily.model.Category;
import com.zb.daily.util.SPUtil;

import java.util.List;

public class CategoryAddActivity extends BaseActivity {

    //返回按钮
    private Button categoryAddPreButton;
    //保存按钮
    private Button categoryAddSaveButton;
    //图片
    private ImageView categoryAddImageView;
    //名称
    private EditText categoryAddName;
    //类型
    private TextView categoryAddType;

    private LinearLayout linearLayout;

    private CategoryDao categoryDao = new CategoryDao();

    List<Integer> categoryImageList;

    //分类类型
    int addType = 0;
    //默认图标
    int defaultImage = 0;
    //新选择的图标
    int categoryNewImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        categoryAddPreButton = findViewById(R.id.activity_category_add_btn_pre);
        categoryAddSaveButton = findViewById(R.id.activity_category_add_btn_save);
        categoryAddImageView = findViewById(R.id.activity_category_add_image);
        categoryAddName = findViewById(R.id.activity_category_add_name);
        categoryAddType = findViewById(R.id.activity_category_add_type);
        linearLayout = findViewById(R.id.activity_category_add_type_layout);
        defaultImage = R.drawable.category_food;
        categoryNewImage = R.drawable.category_food;

        //分类类型的选择
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });

        //返回按钮
        categoryAddPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取图片数据
        boolean bol = SPUtil.contains(MyApplication.getContext(), Constant.TEXT_CATEGORY_IMAGE_LIST);
        String jsonString = "";
        if (bol){
            jsonString = SPUtil.get(MyApplication.getContext(), Constant.TEXT_CATEGORY_IMAGE_LIST, jsonString).toString();
            categoryImageList = JSONArray.parseArray(jsonString, Integer.class);
        }

        //设置图标list的适配器
        RecyclerView categoryImageRecyclerView = findViewById(R.id.activity_category_add_image_recyclerView);
        StaggeredGridLayoutManager categoryImageLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        categoryImageRecyclerView.setLayoutManager(categoryImageLayoutManager);
        CategoryImageListAdapter categoryImageAdapter = new CategoryImageListAdapter(categoryImageList, defaultImage);
        //adapter与Activity的数据交互
        categoryImageAdapter.setSubClickListener(new CategoryImageListAdapter.SubClickListener() {
            @Override
            public void OnTopicClickListener(View v, int newImage, int position) {
                categoryAddImageView.setImageResource(newImage);
                categoryNewImage = newImage;
            }
        });
        categoryImageRecyclerView.setAdapter(categoryImageAdapter);

        //保存新的分类
        categoryAddSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = categoryAddName.getText().toString();
                if (name.isEmpty()){
                    ToastUtils.show("分类名称不能为空");
                    return;
                }
                if (addType == 0){
                    ToastUtils.show("分类类型不能为空");
                    return;
                }

                Category temp = new Category();
                temp.setImageId(categoryNewImage);
                temp.setName(name);
                temp.setType(addType);

                if (categoryDao.saveCategory(temp)){
                    ToastUtils.show("添加成功");
                    Intent intent = new Intent();
                    intent.putExtra("category_add_return", JSONObject.toJSONString(temp));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //分类类型选择列表
    private void showListDialog() {
        final String[] items = { "支出","收入"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoryAddType.setText(items[which]);
                addType = which + 1;
            }
        });
        listDialog.show();
    }

    //启动本活动
    public static void actionStart(Context context){
        Intent intent = new Intent();
        intent.setClass(context, CategoryAddActivity.class);
        /*context.startActivity(intent);*/
        ((BaseActivity)context).startActivityForResult(intent, 4001);
    }
}
