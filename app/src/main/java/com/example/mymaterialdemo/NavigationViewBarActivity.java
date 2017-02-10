package com.example.mymaterialdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

/**
 * Created by wjn on 2017/2/9.
 */

public class NavigationViewBarActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    LinearLayout llcontent;
    BottomNavigationBar bottomnavigationbar;
    private BadgeItem badgeItem;
    private Toolbar toolbar;
    int lastSelectedPosition = 0;
    private BottomFragment mBottomFragment;
    private BottomFragment2 mBottomFragment2;
    private BottomFragment3 mBottomFragment3;
    private BottomFragment4 mBottomFragment4;
    private BadgeItem badgeItem1;
    private BadgeItem badgeItem2;
    private BottomNavigationItem item1;
    private BottomNavigationItem item2;
    private BottomNavigationItem item3;
    private BottomNavigationItem item4;
    private BadgeItem badgeItem3;
    private BadgeItem badgeItem4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        initView();
        init();
    }


    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        llcontent = (LinearLayout) findViewById(R.id.ll_content);
        bottomnavigationbar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
    }

    private void init() {
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
        toolbar.setTitle("底部导航例子");
        //底部导航可以自动隐藏
        bottomnavigationbar.setAutoHideEnabled(true);//自动隐藏

        //底部导航模式
        //BottomNavigationBar.MODE_SHIFTING;
        //BottomNavigationBar.MODE_FIXED;
        //BottomNavigationBar.MODE_DEFAULT;
        bottomnavigationbar.setMode(BottomNavigationBar.MODE_FIXED);

        //底部导航风格
        // BottomNavigationBar.BACKGROUND_STYLE_DEFAULT;
        // BottomNavigationBar.BACKGROUND_STYLE_RIPPLE
        // BottomNavigationBar.BACKGROUND_STYLE_STATIC
        bottomnavigationbar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);


        bottomnavigationbar.setBarBackgroundColor(R.color.white);//背景颜色
        bottomnavigationbar.setInActiveColor(R.color.nav_gray);//未选中时的颜色
        bottomnavigationbar.setActiveColor(R.color.colorPrimaryDark);//选中时的颜色

        //我们还可以设置角标
        badgeItem1 = new BadgeItem().setBackgroundColor(Color.RED).setText("3").setHideOnSelect(true);//角标
        badgeItem2 = new BadgeItem().setBackgroundColor(Color.RED).setText("4").setHideOnSelect(true);//角标
        badgeItem3 = new BadgeItem().setBackgroundColor(Color.RED).setText("5").setHideOnSelect(true);//角标
        badgeItem4 = new BadgeItem().setBackgroundColor(Color.RED).setText("9").setHideOnSelect(true);//角标
        item1 = new BottomNavigationItem(R.mipmap.ic_launcher, "Home");
        item2 = new BottomNavigationItem(R.mipmap.ic_launcher, "Books");
        item3 = new BottomNavigationItem(R.mipmap.ic_launcher, "Music");
        item4 = new BottomNavigationItem(R.mipmap.ic_launcher, "Games");
        //添加选项卡
        bottomnavigationbar
                .addItem(item1.setBadgeItem(badgeItem1))
                .addItem(item2.setBadgeItem(badgeItem2))
                .addItem(item3.setBadgeItem(badgeItem3))
                .addItem(item4.setBadgeItem(badgeItem4))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        bottomnavigationbar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mBottomFragment = BottomFragment.newInstance("位置");
        transaction.replace(R.id.ll_content, mBottomFragment);
        transaction.commit();
    }

    boolean isSelected[] = {true, false, false, false};

    @Override
    public void onTabSelected(int position) {

        Log.d("位置：", "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                isSelected[0] = true;
                if (mBottomFragment == null) {
                    mBottomFragment = BottomFragment.newInstance("位置");
                }
                transaction.replace(R.id.ll_content, mBottomFragment);
                break;
            case 1:
                isSelected[1] = true;
                if (mBottomFragment2 == null) {
                    mBottomFragment2 = BottomFragment2.newInstance("发现");
                }
                transaction.replace(R.id.ll_content, mBottomFragment2);
                break;
            case 2:
                isSelected[2] = true;
                if (mBottomFragment3 == null) {
                    mBottomFragment3 = BottomFragment3.newInstance("爱好");
                }
                transaction.replace(R.id.ll_content, mBottomFragment3);
                break;
            case 3:
                isSelected[3] = true;
                if (mBottomFragment4 == null) {
                    mBottomFragment4 = BottomFragment4.newInstance("图书");
                }
                transaction.replace(R.id.ll_content, mBottomFragment4);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        if (isSelected[0] == true) {
            badgeItem1.hide();
        }
        if (isSelected[1] == true) {
            badgeItem2.hide();
        }
        if (isSelected[2] == true) {
            badgeItem3.hide();
        }
        if (isSelected[3] == true) {
            badgeItem4.hide();
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
}
