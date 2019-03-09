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

    //替换旧的资产列表
    public void replaceOldList(List<Assets> assetsList) {
        List<Assets> tempAssets = findAssetsListByType(assetsList.get(0).getType());

        for (int i=0; i<assetsList.size(); i++){
           Assets temp = new Assets();
           temp.setImageId(assetsList.get(i).getImageId());
           temp.setName(assetsList.get(i).getName());
           temp.setBalance(assetsList.get(i).getBalance());
           temp.setType(assetsList.get(i).getType());
           temp.setRemark(assetsList.get(i).getRemark());

           temp.update(tempAssets.get(i).getId());
        }
    }
}
