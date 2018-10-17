package com.example.luat.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luat.CopyDBEvent;
import com.example.luat.model.Law;
import com.example.luat.model.LawContent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import static android.content.Context.MODE_PRIVATE;
import static com.example.luat.base.DBHelper.DATABASE_NAME;

public class Requests {
    private Context context;
    private static DBHelper db;
//    SharedPreferences prefs = null;
    private static final String DB_TABLE = "data_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KEY = "keyData";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DATE_ISSUED = "date_issued";
    private static final String COLUMN_DATE_UPDATED = "date_updated";
    private static final String COLUMN_DATA = "data";
    public Requests(Context context) {
        this.context = context;
        db = new DBHelper(context);
        db.openDataBase();
    }

    public static io.reactivex.Observable<ArrayList<Law>> getLawList(Context context, String type){
        ArrayList<Law> laws = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DB_TABLE + " WHERE type = " + type + " ORDER BY " + COLUMN_ID + " DESC";
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                String key = cursor.getString(cursor.getColumnIndex(COLUMN_KEY));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String date_issued = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_ISSUED));
                String date_updated = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_UPDATED));
                Law law = new Law(key, title, date_issued, date_updated);
                laws.add(law);
            } while (cursor.moveToNext());
        }
        sqlDB.close();
        return io.reactivex.Observable.just(laws);
    }

    public static io.reactivex.Observable<LawContent> getLaw(Context context, String key){
        LawContent lawContent = new LawContent();
        String selectQuery = "SELECT " + COLUMN_KEY + ", " + COLUMN_TITLE + ", " + COLUMN_TYPE + ", " + COLUMN_DATA +" FROM " + DB_TABLE + " WHERE keyData = '" + key + "'";
        Log.d("query", selectQuery);
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                String keyL = cursor.getString(cursor.getColumnIndex(COLUMN_KEY));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                lawContent = new LawContent(keyL, title, type, data);
            } while (cursor.moveToNext());
        }
        sqlDB.close();
        return io.reactivex.Observable.just(lawContent);
    }

}
