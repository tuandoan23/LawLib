package com.example.luat.PresenterIplm;

import android.content.Context;

import com.example.luat.base.DBHelper;
import com.example.luat.base.Requests;
import com.example.luat.contract.LawContract;
import com.example.luat.model.Law;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LawListPresenterIplm implements LawContract.LawListPresenter {
    private LawContract.LawListView lawListView;
    private ArrayList<Law> lawArrayList = new ArrayList<>();

    public LawListPresenterIplm(LawContract.LawListView lawListView) {
        this.lawListView = lawListView;
    }

    @Override
    public void getLawListByType(Context context, String type) {
        Requests requests = new Requests(context);
        Observable<ArrayList<Law>> getLawListRequest = requests.getLawList(context, type);
        getLawListRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Law>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Law> laws) {
                        lawArrayList = laws;
                    }

                    @Override
                    public void onError(Throwable e) {
                        lawListView.getLawListError();
                    }

                    @Override
                    public void onComplete() {
                        lawListView.getLawListSuccess(lawArrayList);
                    }
                });
    }
}
