package com.lv.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lv.demo.decoration.DividerItemDecoration;
import com.lv.demo.view.BottomMenuDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<HashMap<String, String>> listItem;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rec);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
    }


    public void initData() {
        listItem = new ArrayList<>();/*在数组中存放数据*/

        HashMap<String, String> map1 = new HashMap<String, String>();
        HashMap<String, String> map2 = new HashMap<String, String>();
        HashMap<String, String> map3 = new HashMap<String, String>();
        HashMap<String, String> map4 = new HashMap<String, String>();
        HashMap<String, String> map5 = new HashMap<String, String>();
        HashMap<String, String> map6 = new HashMap<String, String>();

        map1.put("ItemTitle", "美国谷歌公司已发出");
        map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
        listItem.add(map1);

        map2.put("ItemTitle", "国际顺丰已收入");
        map2.put("ItemText", "等待中转");
        listItem.add(map2);

        map3.put("ItemTitle", "国际顺丰转件中");
        map3.put("ItemText", "下一站中国");
        listItem.add(map3);

        map4.put("ItemTitle", "中国顺丰已收入");
        map4.put("ItemText", "下一站广州华南理工大学");
        listItem.add(map4);

        map5.put("ItemTitle", "中国顺丰派件中");
        map5.put("ItemText", "等待派件");
        listItem.add(map5);

        map6.put("ItemTitle", "华南理工大学已签收");
        map6.put("ItemText", "收件人:Carson");
        listItem.add(map6);
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    HomeActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.title.setText(listItem.get(position).get("ItemTitle"));
            holder.text.setText(listItem.get(position).get("ItemText"));
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView title, text;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.Itemtitle);
                text = (TextView) view.findViewById(R.id.Itemtext);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomMenuDialog dialog = new BottomMenuDialog.BottomMenuBuilder(getSupportFragmentManager())
                                .addItems(Arrays.asList("趴在", "啊说的话", "取消"))
                                .build();
                        dialog.show();
                    }
                });
            }
        }
    }

}