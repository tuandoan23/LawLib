package com.example.luat.contract;

import android.content.Context;

import com.example.luat.base.DBHelper;
import com.example.luat.model.Law;
import com.example.luat.model.LawContent;

import java.util.ArrayList;

public interface LawContract {
    interface LawListView {
        void getLawListSuccess(ArrayList<Law> laws);
        void getLawListError();
    }

    interface LawContentView {
        void getLawSuccess(LawContent lawContent);
        void getLawError();
    }

    interface LawListPresenter{
        void getLawListByType(Context context, String type);
    }

    interface LawContentPresenter{
        void getLaw(Context context, String key);
    }
}
