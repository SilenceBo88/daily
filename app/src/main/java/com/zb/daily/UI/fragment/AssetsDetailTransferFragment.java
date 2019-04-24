package com.zb.daily.UI.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsDetailTransferListAdapter;
import com.zb.daily.dao.AssetsTransferDao;
import com.zb.daily.model.AssetsTransfer;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 22:26
 * @Description: 资产详情的的转账页面
 */
@SuppressLint("ValidFragment")
public class AssetsDetailTransferFragment  extends Fragment {

    public FragmentActivity activity;

    private AssetsTransferDao assetsTransferDao = new AssetsTransferDao();

    private Integer assetsId;

    public AssetsDetailTransferFragment(Integer assetsId) {
        this.assetsId = assetsId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_assets_detail_transfer, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<AssetsTransfer> assetsTransferList = assetsTransferDao.findAssetsTransferListByAssetsId(assetsId);

        //支出的list适配
        RecyclerView recyclerView = activity.findViewById(R.id.fragment_assets_detail_transfer_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        AssetsDetailTransferListAdapter adapter = new AssetsDetailTransferListAdapter(assetsTransferList);
        recyclerView.setAdapter(adapter);
    }
}
