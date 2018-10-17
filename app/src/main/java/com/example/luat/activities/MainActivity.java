package com.example.luat.activities;

import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luat.CopyDBEvent;
import com.example.luat.R;
import com.example.luat.adapter.ViewPagerAdapter;
import com.example.luat.base.BaseActivity;
import com.example.luat.base.CopyAsyncTask;
import com.example.luat.base.DBHelper;
import com.example.luat.fragments.BoLuatFragment;
import com.example.luat.fragments.BoLuatSuaDoiFragment;
import com.example.luat.fragments.DuThaoLuatFragment;
import com.example.luat.fragments.HienPhapFragment;
import com.example.luat.fragments.LuatFragment;
import com.example.luat.fragments.LuatSuaDoiFragment;
import com.example.luat.fragments.NghiDinhFragment;
import com.example.luat.fragments.NghiDinhSuaDoiFragment;
import com.example.luat.fragments.VanBanHopNhatFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public DBHelper db;
    SharedPreferences prefs = null;
    private boolean isCompleted;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tab)
    TabLayout tab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvProgress)
    AppCompatTextView tvProgress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.llProgress)
    LinearLayoutCompat llProgress;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void getExtraData() {
    }

    @Override
    public void createPresenter() {
    }

    @Override
    public void createAdapter() {
        setSupportActionBar(toolbar);
        tab.setupWithViewPager(viewPager);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        db = new DBHelper(this);
        prefs = this.getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            Log.d("status", "run first");
            try {
                createDataBase();
            } catch (IOException e) {
                throw new Error("Unable to create database");
            }
            prefs.edit().putBoolean("firstrun", false).commit();
        } else {
            llProgress.setVisibility(View.GONE);
            setupViewPager(viewPager);
        }
    }

    @Override
    public void loadData() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HienPhapFragment(), "Hiến Pháp");
        adapter.addFragment(new BoLuatFragment(), "Bộ Luật");
        adapter.addFragment(new LuatFragment(), "Luật");
        adapter.addFragment(new NghiDinhFragment(), "Nghị Định");
        adapter.addFragment(new LuatSuaDoiFragment(), "Luật Sửa Đổi");
        adapter.addFragment(new BoLuatSuaDoiFragment(), "Bộ Luật Sửa Đổi");
        adapter.addFragment(new VanBanHopNhatFragment(), "Văn Bản Hợp Nhất");
        adapter.addFragment(new NghiDinhSuaDoiFragment(), "Nghị Định Sửa Đổi");
        adapter.addFragment(new DuThaoLuatFragment(), "Dự Thảo Luật");
        viewPager.setAdapter(adapter);
    }
    public void createDataBase() throws IOException {
        try {
            db.getReadableDatabase();
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCopyDBEvent(CopyDBEvent copyDBEvent){
        isCompleted = copyDBEvent.isCompleted;
        if (isCompleted) {
            try {
                setupViewPager(viewPager);
                db.openDataBase();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }
    }

    private void copyDataBase() throws IOException {
        CopyAsyncTask myAsyncTask = new CopyAsyncTask(this);
        myAsyncTask.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
