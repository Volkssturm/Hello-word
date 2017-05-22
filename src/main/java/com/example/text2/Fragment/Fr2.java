package com.example.text2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import static com.example.text2.Fragment.Fr2.HomeAdapter.*;

/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * <p>
 * 2017/5/3
 */

public class Fr2 extends Fragment  {
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ArrayList<String> mDatas;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipelt);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.fr2_lv);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );

//        mRecyclerView.addItemDecoration(new ItemDecorationss(getActivity()));
        mRecyclerView.addItemDecoration(new ItemDecoration(getActivity(),
                ItemDecoration.VERTICAL_LIST));

        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //我在List最前面加入一条数据
                mDatas.add(0, "嘿，我是“下拉刷新”生出来的");

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

    }
    private void loadMoreData(){
        for (int i =0; i <3; i++){
            mDatas.add("嘿，我是“上拉加载”生出来的"+i);
            Log.e("loadMoreData: ",i+"" );
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter.notifyDataSetChanged();
                    }
                });


            }
        }.start();
        }
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'T'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.f2_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.item_num);
            }
        }
    }
}
