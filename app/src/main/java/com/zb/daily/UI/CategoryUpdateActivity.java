package com.zb.daily.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
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

public class CategoryUpdateActivity extends BaseActivity {

    //返回按钮
    private Button categoryUpdatePreButton;
    //保存按钮
    private Button categoryUpdateSaveButton;
    //图片
    private ImageView categoryUpdateImageView;
    //名称
    private EditText categoryUpdateName;

    private CategoryDao categoryDao = new CategoryDao();

    private Category category;

    int position;

    List<Integer> categoryImageList;

    //默认图标
    int defaultImage = 0;
    //新选择的图标
    int categoryNewImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_update);

        categoryUpdatePreButton = findViewById(R.id.activity_category_update_btn_pre);
        categoryUpdateSaveButton = findViewById(R.id.activity_category_update_btn_save);
        categoryUpdateImageView = findViewById(R.id.activity_category_update_image);
        categoryUpdateName = findViewById(R.id.activity_category_update_name);

        //获取活动传递的数据
        String jsonStr = getIntent().getStringExtra("category");
        category = JSON.parseObject(jsonStr, Category.class);
        position = getIntent().getIntExtra("position", -1);
        defaultImage = category.getImageId();
        categoryNewImage = category.getImageId();
        categoryUpdateImageView.setImageResource(defaultImage);
        categoryUpdateName.setText(category.getName());

        //返回按钮
        categoryUpdatePreButton.setOnClickListener(new View.OnClickListener() {
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
        RecyclerView categoryImageRecyclerView = findViewById(R.id.activity_category_update_image_recyclerView);
        StaggeredGridLayoutManager categoryImageLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        categoryImageRecyclerView.setLayoutManager(categoryImageLayoutManager);
        CategoryImageListAdapter categoryImageAdapter = new CategoryImageListAdapter(categoryImageList, defaultImage);
        //adapter与Activity的数据交互
        categoryImageAdapter.setSubClickListener(new CategoryImageListAdapter.SubClickListener() {
            @Override
            public void OnTopicClickListener(View v, int newImage, int position) {
                categoryUpdateImageView.setImageResource(newImage);
                categoryNewImage = newImage;
            }
        });
        categoryImageRecyclerView.setAdapter(categoryImageAdapter);

        //保存新的分类
        categoryUpdateSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = categoryUpdateName.getText().toString();
                if (name.isEmpty()){
                    ToastUtils.show("分类名称不能为空");
                    return;
                }

                Category temp = new Category();
                temp.setId(category.getId());
                temp.setImageId(categoryNewImage);
                temp.setName(name);
                temp.setType(category.getType());

                if (categoryDao.updateCategory(temp)){
                    ToastUtils.show("修改成功");
                    Intent intent = new Intent();
                    intent.putExtra("category_update_return", JSONObject.toJSONString(temp));
                    intent.putExtra("position_update_return", position);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //启动本活动
    public static void actionStart(Context context, Category category, int position){
        Intent intent = new Intent();
        intent.setClass(context, CategoryUpdateActivity.class);
        intent.putExtra("category", JSONObject.toJSONString(category));
        intent.putExtra("position", position);
        /*context.startActivity(intent);*/
        ((BaseActivity)context).startActivityForResult(intent, 4002);
    }
}
