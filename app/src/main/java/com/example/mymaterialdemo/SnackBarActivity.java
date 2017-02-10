package com.example.mymaterialdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;



/**
 * Created by wjn on 2017/2/10.
 */
public class SnackBarActivity extends AppCompatActivity{
    FloatingActionButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        initView();
        init();
    }

    private void init() {
        toolbar.setTitle("升级版本的Toast演示");
        setSupportActionBar(toolbar);
        //决定左上角图标的右侧是否有向左的小箭头,true 有
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //左侧小箭头的返回事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Snackbar.make(view, "升级版toast", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
