package com.zb.daily.UI.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsDetailUpdateListAdapter;
import com.zb.daily.dao.AssetsUpdateDao;
import com.zb.daily.model.AssetsUpdate;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 22:26
 * @Description: 资产详情的的账单页面
 */
@SuppressLint("ValidFragment")
public class AssetsDetailUpdateFragment extends Fragment {

    public FragmentActivity activity;

    private AssetsUpdateDao assetsUpdateDao = new AssetsUpdateDao();

    private Integer assetsId;

    public AssetsDetailUpdateFragment(Integer assetsId) {
        this.assetsId = assetsId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_assets_detail_update, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<AssetsUpdate> assetsUpdateList = assetsUpdateDao.findAssetsUpdateListByAssetsId(assetsId);

        //支出的list适配
        RecyclerView recyclerView = activity.findViewById(R.id.fragment_assets_detail_update_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        AssetsDetailUpdateListAdapter adapter = new AssetsDetailUpdateListAdapter(assetsUpdateList);
        recyclerView.setAdapter(adapter);
    }
}
