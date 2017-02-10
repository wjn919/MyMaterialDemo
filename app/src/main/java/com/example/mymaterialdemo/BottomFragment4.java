package com.example.mymaterialdemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utils.CommonListAdapter;
import com.example.utils.ListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjn on 2017/2/9.
 */


public class BottomFragment4 extends Fragment {


    private List<String> mDataList;
    private String agrs1;
    private MyAdapter mAdapter;


    public static BottomFragment4 newInstance(String param1) {
        BottomFragment4 fragment = new BottomFragment4();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BottomFragment4() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation4, container, false);
        Bundle bundle = getArguments();
        agrs1 = bundle.getString("agrs1");
        ListView lv = (ListView) view.findViewById(R.id.lv_bottom_nav);
        initData();
        mAdapter = new MyAdapter(getActivity(),mDataList,R.layout.bottom_nav_item);
        lv.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        mDataList = new ArrayList();
        for(int i = 0;i<50;i++){
            mDataList.add(agrs1+i);
        }
    }

    class MyAdapter extends CommonListAdapter<String>{

        public MyAdapter(Context context, List<String> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ListViewHolder holder, String s, int postion) {

        }
    }


}

