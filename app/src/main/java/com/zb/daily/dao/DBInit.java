package com.zb.daily.dao;

import com.alibaba.fastjson.JSONArray;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.model.Assets;
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
}
