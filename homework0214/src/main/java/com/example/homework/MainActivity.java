package com.example.homework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.homework.adapter.ByNameAdapter;
import com.example.homework.bean.ByName;
import com.example.homework.netWork.Constant;
import com.example.homework.persenter.PersenterImpl;
import com.example.homework.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IView {
    Unbinder unbinder;
    @BindView(R.id.recy_pin)
    RecyclerView byname;
    private PersenterImpl persenter;

    private ByNameAdapter byNameAdapter;
    private List<ByName.ResultBean> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initData();
        initRecy();
    }

    private void initRecy() {
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManagers.setOrientation(LinearLayoutManager.VERTICAL);
        byname.setLayoutManager(gridLayoutManagers);
        byNameAdapter = new ByNameAdapter(MainActivity.this);
        byname.setAdapter(byNameAdapter);
        byNameAdapter.result(new ByNameAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {

                Toast.makeText(MainActivity.this,result.get(index).getCommodityName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initData() {
        persenter = new PersenterImpl(this);
        persenter.sendGet(Constant.QUERY_NAME + "?keyword=手机&page=1&count=8", ByName.class);
    }
    @Override
    public void requesta(Object data) {
        if (data instanceof ByName) {
            final ByName byName = (ByName) data;
            result = byName.getResult();
            byNameAdapter.setData(byName.getResult());
        }
    }
}