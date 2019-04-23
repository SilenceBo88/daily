package com.zb.daily.dao;

import com.zb.daily.model.AssetsTransfer;

/**
 * @auther: zb
 * @Date: 2019/4/23 21:42
 * @Description: 数据库资产转账表的crud操作
 */
public class AssetsTransferDao {

    //保存资产转账
    public boolean saveAssets(AssetsTransfer temp) {
        return temp.save();
    }
}
