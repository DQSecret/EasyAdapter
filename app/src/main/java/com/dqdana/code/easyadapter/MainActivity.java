package com.dqdana.code.easyadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dqdana.code.easyadapter.userinfo.UserInfo;
import com.dqdana.code.easyadapter.userinfo.UserInfoViewHolder;

import java.util.ArrayList;
import java.util.List;

import support.ui.adapters.EasyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EasyRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        loadData();
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EasyRecyclerAdapter(this, UserInfo.class, UserInfoViewHolder.class);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadData() {
        List<UserInfo> dataList = new ArrayList<UserInfo>() {{
            add(new UserInfo("DQ1", 24));
            add(new UserInfo("DQ2", 25));
            add(new UserInfo("DQ3", 26));
            add(new UserInfo("DQ4", 27));
            add(new UserInfo("DQ5", 28));
            add(new UserInfo("DQ6", 29));
        }};
        mAdapter.addAll(dataList);
    }
}