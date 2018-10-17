package com.example.luat.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;
import static com.example.luat.base.DBHelper.DATABASE_NAME;
import static com.example.luat.base.DBHelper.DB_PATH;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_PATH = null;
    public static final String DATABASE_NAME = "db";
    private SQLiteDatabase myDataBase;
    private final Context context;
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        createFile();
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

//    public void createDataBase() throws IOException {
//        try {
//            this.getReadableDatabase();
//            copyDataBase();
////            copydatabase();
//        } catch (IOException e) {
//            throw new Error("Unable to create database");
//        }
//    }

    private void createFile(){
        File file = new File(DB_PATH + DATABASE_NAME);
        if (!file.exists()) {
            Log.d("status", "create file db");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyDataBase() throws IOException {
        OutputStream myOutput = new FileOutputStream(DB_PATH + DATABASE_NAME);
        Log.d("output",DB_PATH + DATABASE_NAME );
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();

    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

//    private void copyDataBase() throws IOException {
//        CopyAsyncTask myAsyncTask = new CopyAsyncTask(this.context);
//        myAsyncTask.execute();
//        Log.d("status", "DONE");
//    }


    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }
}


