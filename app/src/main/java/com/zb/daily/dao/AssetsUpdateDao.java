package com.zb.daily.dao;

import com.zb.daily.model.AssetsUpdate;

/**
 * @auther: zb
 * @Date: 2019/4/23 22:21
 * @Description: 数据库资产修改表的crud操作
 */
public class AssetsUpdateDao {

    //保存资产转账
    public boolean saveAssets(AssetsUpdate temp) {
        return temp.save();
    }
}
