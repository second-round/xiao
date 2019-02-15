package com.example.homework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.example.homework.adapter.ByNameAdapter;
import com.example.homework.app.GreendaoUtils;
import com.example.homework.bean.ByName;
import com.example.homework.greendao.UserBean;
import com.example.homework.greendao.UserBeanDao;
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
    private UserBeanDao userBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        userBeanDao = GreendaoUtils.getInstance().getDaoSession().getUserBeanDao();
        boolean connectIsNomarl = isConnectIsNomarl();
        initView();
        if (connectIsNomarl){
            initData();
            initRecy();
        }else {
            List<UserBean> userBeans = userBeanDao.loadAll();
            intoAdapter(userBeans);
        }
    }

    private void initView() {
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManagers.setOrientation(LinearLayoutManager.VERTICAL);
        byname.setLayoutManager(gridLayoutManagers);
        byNameAdapter = new ByNameAdapter(MainActivity.this);
        byname.setAdapter(byNameAdapter);
    }

    private void intoAdapter(List<UserBean> userBeans) {
        ByName byName=new ByName();
        for (int i = 0; i < userBeans.size(); i++) {
            ByName.ResultBean resultBean=new ByName.ResultBean();
            resultBean.setCommodityName(userBeans.get(i).getName());
            resultBean.setMasterPic(userBeans.get(i).getUri());
            byName.setResult(resultBean);
        }
        byNameAdapter.setData(byName.getResult());
    }

    private void initRecy() {
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
            intoDao(result);
            byNameAdapter.setData(byName.getResult());
        }
    }

    private void intoDao(List<ByName.ResultBean> result) {
        for (int i = 0; i < result.size(); i++) {
            UserBean userBean=new UserBean();
            userBean.setName(result.get(i).getCommodityName());
            userBean.setUri(result.get(i).getMasterPic());
            insert(userBean);
        }
    }
    private void insert(UserBean resultBean) {
        userBeanDao.insert(resultBean);
    }


    private boolean isConnectIsNomarl() {
        ConnectivityManager manager = (ConnectivityManager) MainActivity.this
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//        if (info != null && info.isAvailable()) {
//            String intentName = info.getTypeName();
//            Log.i("通了没！", "当前网络名称：" + intentName);
//            return true;
//        } else {
//            Log.i("通了没！", "没有可用网络");
//            return false;
//        }
    }

}