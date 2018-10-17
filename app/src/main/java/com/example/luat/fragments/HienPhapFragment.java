package com.example.luat.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.luat.PresenterIplm.LawListPresenterIplm;
import com.example.luat.R;
import com.example.luat.Utils;
import com.example.luat.adapter.LawsAdapter;
import com.example.luat.base.BaseFragment;
import com.example.luat.base.DBHelper;
import com.example.luat.contract.LawContract;
import com.example.luat.model.Law;

import java.util.ArrayList;

import butterknife.BindView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class HienPhapFragment extends BaseFragment implements LawContract.LawListView {
    private LawContract.LawListPresenter presenter;
    private LawsAdapter lawsAdapter;
    private ArrayList<Law> laws = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.rvHienPhap)
    RecyclerView rvHienPhap;

    @Override
    public int initLayout() {
        return R.layout.fragment_hienphap;
    }

    @Override
    public void getExtraData() {

    }

    @Override
    public void createPresenter() {
        presenter = new LawListPresenterIplm(this);
    }

    @Override
    public void createAdapter() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_PORTRAIT) {
            linearLayoutManager = new LinearLayoutManager(this.getContext());
            rvHienPhap.setLayoutManager(linearLayoutManager);
        } else if (orientation == ORIENTATION_LANDSCAPE){
            gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
            rvHienPhap.setLayoutManager(gridLayoutManager);
        }
        lawsAdapter = new LawsAdapter(laws, this.getContext());
        rvHienPhap.setAdapter(lawsAdapter);
    }

    @Override
    public void loadData() {
        presenter.getLawListByType(this.getContext(), Utils.HIEN_PHAP);
    }

    @Override
    public void getLawListSuccess(ArrayList<Law> laws) {
        this.laws.addAll(laws);
        lawsAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLawListError() {

    }
}
