package com.zb.daily.dao;

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
}
