package com.example.luat.PresenterIplm;

import android.content.Context;

import com.example.luat.base.DBHelper;
import com.example.luat.base.Requests;
import com.example.luat.contract.LawContract;
import com.example.luat.model.Law;
import com.example.luat.model.LawContent;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LawContentPresenterIplm implements LawContract.LawContentPresenter{
    private LawContract.LawContentView lawContentView;
    private LawContent lawContent;

    public LawContentPresenterIplm(LawContract.LawContentView lawContentView) {
        this.lawContentView = lawContentView;
    }

    @Override
    public void getLaw(Context context, String key) {
        Requests requests = new Requests(context);
        Observable<LawContent> getLawListRequest = requests.getLaw(context, key);
        getLawListRequest.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LawContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LawContent law) {
                        lawContent = law;
                    }

                    @Override
                    public void onError(Throwable e) {
                        lawContentView.getLawError();
                    }

                    @Override
                    public void onComplete() {
                        lawContentView.getLawSuccess(lawContent);
                    }
                });

    }
}
