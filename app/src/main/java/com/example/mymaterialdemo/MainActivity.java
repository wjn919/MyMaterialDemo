package com.example.mymaterialdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.BaseApplication;
import com.example.api.testApi;
import com.example.entity.Book;
import com.example.entity.BookResponse;
import com.example.http.HttpUtils;
import com.example.utils.AlertUtils;
import com.example.utils.CommonListAdapter;
import com.example.utils.CommonRecyclerAdapter;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.ListViewHolder;
import com.example.utils.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.mymaterialdemo.R.string.search;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String currentKey="三生三世十里桃花" ;
    private String lastKey="三生三世十里桃花";
    private RecyclerView mRecyclerview;
    private List<Book> booklist = new ArrayList<>();
    private MyMainAdapter mAdapter;
    private View vContent;
    private View vContent2;
    private View vContent3;
    private ListView mList;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private List<String> exampleList = new ArrayList<>();
    private MyExampleAdapter mExampleAdapter;
    private List<Book> bookExampleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        initData();
        loadData();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //书籍的layout
        vContent = LayoutInflater.from(this).inflate(R.layout.content_book, null);
        //例子
        vContent2 = LayoutInflater.from(this).inflate(R.layout.content_example, null);
        //关于
        vContent3 = LayoutInflater.from(this).inflate(R.layout.content_about, null);
        ((FrameLayout) findViewById(R.id.fragment_content)).addView(vContent);
        mRecyclerview = (RecyclerView) vContent.findViewById(R.id.recyclerView);

        mList = (ListView)vContent2.findViewById(R.id.lv_list);
    }

    private void init() {
        //设置toolbar
        setSupportActionBar(toolbar);


        //侧边栏
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //RecyclerView
        mRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MyMainAdapter(this, booklist,R.layout.book_list_item);
        mExampleAdapter = new MyExampleAdapter(this,exampleList,R.layout.example_list_item);


    }



    private void initData() {
        exampleList.add("TextInputLayout");
        exampleList.add("视差特效 collapsingToolbar&TabLayout");
        exampleList.add("卡片效果 CardView");
        exampleList.add("底部导航 BottomNavigationBar");
        exampleList.add("材料设计 dialog");
        exampleList.add("升级版toast SnackBar");
        mList.setAdapter(mExampleAdapter);
         //右下角搜索
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 showMaterialDialog();
            }
        });

        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
               Book book =  booklist.get(pos);
                Snackbar.make(itemView,book.getTitle(),Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,BookDetailsActivity.class);
                intent.putExtra("book",book);
                ImageView iv = (ImageView) itemView.findViewById(R.id.ivBook);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,iv,getResources().getString(R.string.iv_share_transication)).toBundle());
                }
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              if(exampleList.get(i).equals("TextInputLayout")){
                  startActivity(new Intent(MainActivity.this, TextInputActivity.class));

              }else if(exampleList.get(i).equals("视差特效 collapsingToolbar&TabLayout")){
                  Intent intent = new Intent(MainActivity.this,BookDetailsActivity.class);
                  intent.putExtra("book",bookExampleList.get(0));
                  startActivity(intent);


              }else if(exampleList.get(i).equals("卡片效果 CardView")){
                  Intent intent = new Intent(MainActivity.this,CardViewActivity.class);
                  intent.putExtra("book",bookExampleList.get(0));
                  startActivity(intent);

              }else if(exampleList.get(i).equals("底部导航 BottomNavigationBar")){
                  Intent intent = new Intent(MainActivity.this,NavigationViewBarActivity.class);
                  startActivity(intent);

              }else if(exampleList.get(i).equals("材料设计 dialog")){
                  startActivity(new Intent(MainActivity.this,MaterialDialogActivity.class));

              }else if(exampleList.get(i).equals("升级版toast SnackBar")){
                  startActivity(new Intent(MainActivity.this,SnackBarActivity.class));


              }


            }


        });
    }



    private void loadData() {
        testApi api = HttpUtils.getInstance(BaseApplication.Server_Url).create(testApi.class);
        Map<String, String > params = new HashMap<>();
        params.put("q", currentKey);
        params.put("start","0");
        params.put("end","50");

        api.getBooks(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookResponse>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        AlertUtils.showLoadingDialog(MainActivity.this);
                    }

                    @Override
                    public void onCompleted() {
                        AlertUtils.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        AlertUtils.dismissLoadingDialog();
                        AlertUtils.showToast(MainActivity.this,e.getMessage());
                        Log.e("Tag",e.getMessage());
                    }

                    @Override
                    public void onNext(BookResponse bookResponse) {
                        List<Book> books = bookResponse.getBooks();
                        if(books.size()==0){
                           AlertUtils.showToast(MainActivity.this,"您搜索的书籍不存在");
                           currentKey = lastKey;
                           loadData();
                        }else{
                            booklist.clear();
                            booklist.addAll(books);
                            mRecyclerview.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            //随便存储一本书，为了例子中的视差特效做演示
                            bookExampleList.clear();
                            bookExampleList.add(booklist.get(0));

                        }


                    }
                });

    }


    private void showMaterialDialog() {
        new MaterialDialog.Builder(this)
                .title(search)
                //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            lastKey = currentKey;
                            currentKey = input.toString();
                            loadData();


                        }
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_item_book) {
            ((FrameLayout) findViewById(R.id.fragment_content)).removeAllViews();
            ((FrameLayout) findViewById(R.id.fragment_content)).addView(vContent);
            fab.setVisibility(View.VISIBLE);
            // Handle the camera action
            AlertUtils.showToast(this,"书籍");
            lastKey = currentKey;
            currentKey = "三生三世十里桃花";
            loadData();
        } else if (id == R.id.navigation_item_example) {
            ((FrameLayout) findViewById(R.id.fragment_content)).removeAllViews();
            ((FrameLayout) findViewById(R.id.fragment_content)).addView(vContent2);
            fab.setVisibility(View.GONE);
            AlertUtils.showToast(this,"例子");

        } else if (id == R.id.navigation_item_about) {
            ((FrameLayout) findViewById(R.id.fragment_content)).removeAllViews();
            ((FrameLayout) findViewById(R.id.fragment_content)).addView(vContent3);
            fab.setVisibility(View.GONE);
            AlertUtils.showToast(this,"关于");


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyMainAdapter extends CommonRecyclerAdapter<Book> {


        public MyMainAdapter(Context ctx, List<Book> list, int LayoutId) {
            super(ctx, list, LayoutId);
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Book book) {
            holder.setText(R.id.tvTitle,book.getTitle());
            String desc = "作者: " + (book.getAuthor().length > 0 ? book.getAuthor()[0] : "") + "\n副标题: " + book.getSubtitle()
                    + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
            holder.setText(R.id.tvDesc,desc);
            final ImageView iv = (ImageView) holder.getView(R.id.ivBook);

            ImageLoaderUtil.load(MainActivity.this,book.getImage(),iv);
        }


    }

    private class MyExampleAdapter extends CommonListAdapter<String> {

        public MyExampleAdapter(Context context, List<String> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ListViewHolder holder, String s, int postion) {
                holder.setText(R.id.tv_example_text,s);
        }
    }
}
