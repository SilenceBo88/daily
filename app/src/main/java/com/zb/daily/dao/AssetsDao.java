package com.zb.daily.dao;

import android.content.ContentValues;
import com.zb.daily.model.Assets;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/25 21:34
 * @Description: 资产数据库操作
 */
public class AssetsDao {

    //保存资产集合
    public void saveAssetsList(List<Assets> assetsList){
        DataSupport.saveAll(assetsList);
    }

    //按照资产类型查询资产集合
    public List<Assets> findAssetsListByType(Integer type){
        List<Assets> assetsList = DataSupport.where("type = ?", type.toString()).find(Assets.class);
        return assetsList;
    }

    public void swapAssetsList(Assets assets1, Assets assets2){
        ContentValues values1 = new ContentValues();
        values1.put("imageId", assets2.getImageId());
        values1.put("name", assets2.getName());
        values1.put("balance", assets2.getBalance());
        values1.put("type", assets2.getType());
        values1.put("remark", assets2.getRemark());

        ContentValues values2 = new ContentValues();
        values2.put("imageId", assets1.getImageId());
        values2.put("name", assets1.getName());
        values2.put("balance", assets1.getBalance());
        values2.put("type", assets1.getType());
        values2.put("remark", assets1.getRemark());

        DataSupport.update(Assets.class, values1, assets1.getId());
        DataSupport.update(Assets.class, values2, assets2.getId());
    }
}
