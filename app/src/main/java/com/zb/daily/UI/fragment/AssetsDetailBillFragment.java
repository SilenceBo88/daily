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
import com.zb.daily.adapter.AssetsDetailBillListAdapter;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.Record;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/4/24 22:26
 * @Description: 资产详情的的账单页面
 */
@SuppressLint("ValidFragment")
public class AssetsDetailBillFragment extends Fragment {

    public FragmentActivity activity;

    private RecordDao recordDao = new RecordDao();

    private Integer assetsId;

    public AssetsDetailBillFragment(Integer assetsId) {
        this.assetsId = assetsId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_assets_detail_bill, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Record> recordList = recordDao.findRecordListByAssetsId(assetsId);

        //支出的list适配
        RecyclerView recyclerView = activity.findViewById(R.id.fragment_assets_detail_bill_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        AssetsDetailBillListAdapter adapter = new AssetsDetailBillListAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }
}
