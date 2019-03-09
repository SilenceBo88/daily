package com.zb.daily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;
/**
 * @auther: zb
 * @Date: 2019/3/9 18:01
 * @Description: 新建资产页面
 */
public class AddAssetsDatilActivity extends AppCompatActivity {

    //返回按钮
    private Button addDatilPreButton;
    //保存按钮
    private Button addDatilSaveButton;
    //图片
    private ImageView addDatilimageView;
    //名称
    private EditText addDatilName;
    //备注
    private EditText addDatilRemark;
    //余额
    private EditText addDatilBalance;

    private AssetsDao assetsDao = new AssetsDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets_datil);

        addDatilPreButton = findViewById(R.id.assets_add_datil_btn_pre);
        addDatilSaveButton = findViewById(R.id.assets_add_datil_btn_save);
        addDatilimageView = findViewById(R.id.assets_add_datil_image);
        addDatilName = findViewById(R.id.assets_add_datil_name);
        addDatilRemark = findViewById(R.id.assets_add_datil_remark);
        addDatilBalance = findViewById(R.id.assets_add_datil_balance);

        int id = getIntent().getIntExtra("id", 0);
        Assets assets = assetsDao.findAssetsById(id);

        addDatilimageView.setImageResource(assets.getImageId());
        addDatilName.setText(assets.getName());

        addDatilPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssetsDatilActivity.this.finish();
            }
        });
    }
}
