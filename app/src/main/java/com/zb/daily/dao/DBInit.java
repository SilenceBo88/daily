package com.zb.daily.dao;

import com.alibaba.fastjson.JSONArray;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;
import com.zb.daily.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/25 21:56
 * @Description: 初始化数据库中的数据，安装app时执行一次
 */
public class DBInit {

    //资产数据初始化
    public static void assetsInit(){

        AssetsDao assetsDao = new AssetsDao();

        //资产集合
        List<Assets> assetsList = new ArrayList<>();

        Assets assets1 = new Assets(R.drawable.assets_cash, "现金", 0.00, 1, "");
        Assets assets2 = new Assets(R.drawable.assets_wechat, "微信", 0.00, 1, "");
        Assets assets3 = new Assets(R.drawable.assets_alipay, "支付宝", 0.00, 1, "");
        Assets assets4 = new Assets(R.drawable.assets_save, "储蓄卡", 0.00, 1, "");
        Assets assets5 = new Assets(R.drawable.assets_recharge, "充值卡", 0.00, 1,"饭卡，公交卡");
        Assets assets6 = new Assets(R.drawable.assets_receipt, "应收账", 0.00, 1,"别人欠我的钱");

        Assets liability1 = new Assets(R.drawable.assets_credit, "信用卡", 0.00, 2, "");
        Assets liability2 = new Assets(R.drawable.assets_flower, "花呗", 0.00, 2, "");
        Assets liability3 = new Assets(R.drawable.assets_jd, "京东白条", 0.00, 2, "");
        Assets liability4 = new Assets(R.drawable.assets_pay, "应付账", 0.00, 2,"我欠别人的钱");

        assetsList.add(assets1);
        assetsList.add(assets2);
        assetsList.add(assets3);
        assetsList.add(assets4);
        assetsList.add(assets5);
        assetsList.add(assets6);
        assetsList.add(liability1);
        assetsList.add(liability2);
        assetsList.add(liability3);
        assetsList.add(liability4);

        //一份保存到数据库，以便正常crud
        assetsDao.saveAssetsList(assetsList);

        //另一份以json格式保存到SharedPreference中，在添加资产页面初始化list时使用
        String jsonString = JSONArray.toJSONString(assetsList);
        SPUtil.put(MyApplication.getContext(), Constant.TEXT_ASSETS_ADD_LIST, jsonString);
    }

    //资产数据初始化
    public static void categoryInit(){
        CategoryDao categoryDao = new CategoryDao();

        //分类集合
        List<Category> categoryList = new ArrayList<>();

        Category outCategory1 = new Category(R.drawable.assets_flower, "餐饮", 1);
        Category outCategory2 = new Category(R.drawable.assets_flower, "日用", 1);
        Category outCategory3 = new Category(R.drawable.assets_flower, "衣服", 1);
        Category outCategory4 = new Category(R.drawable.assets_flower, "医疗", 1);
        Category outCategory5 = new Category(R.drawable.assets_flower, "零食", 1);
        Category outCategory6 = new Category(R.drawable.assets_flower, "礼物", 1);
        Category outCategory7 = new Category(R.drawable.assets_flower, "交通", 1);
        Category outCategory8 = new Category(R.drawable.assets_flower, "电子产品", 1);

        Category inCategory1 = new Category(R.drawable.assets_flower, "薪资", 2);
        Category inCategory2 = new Category(R.drawable.assets_flower, "兼职", 2);
        Category inCategory3 = new Category(R.drawable.assets_flower, "投资", 2);
        Category inCategory4 = new Category(R.drawable.assets_flower, "生活费", 2);

        categoryList.add(outCategory1);
        categoryList.add(outCategory2);
        categoryList.add(outCategory3);
        categoryList.add(outCategory4);
        categoryList.add(outCategory5);
        categoryList.add(outCategory6);
        categoryList.add(outCategory7);
        categoryList.add(outCategory8);
        categoryList.add(inCategory1);
        categoryList.add(inCategory2);
        categoryList.add(inCategory3);
        categoryList.add(inCategory4);

        //保存到数据库，以便正常crud
        categoryDao.saveCategoryList(categoryList);
    }
}
