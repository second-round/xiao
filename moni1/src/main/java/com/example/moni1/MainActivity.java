package com.example.moni1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moni1.adapter.ByNameAdapter;
import com.example.moni1.app.GreendaoUtils;
import com.example.moni1.bean.ByName;
import com.example.moni1.bean.GoodsBean;
import com.example.moni1.greendao.UserBean;
import com.example.moni1.netWork.Constant;
import com.example.moni1.persenter.PersenterImpl;
import com.example.moni1.view.IView;
import com.example.moni1greendao.UserBeanDao;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.choose)
    ImageView choose;
    @BindView(R.id.edits)
    EditText edits;
    @BindView(R.id.huans)
    ImageView huans;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.recy_pin)
    XRecyclerView byname;
    int page;
    private ByNameAdapter byNameAdapter;
    private List<ByName.ResultBean> result;
    private UserBeanDao userBeanDao;
    private PersenterImpl persenter;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        page = 1;
        name="手机";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        userBeanDao = GreendaoUtils.getInstance().getDaoSession().getUserBeanDao();
        initView();
        boolean connectIsNomarl = isConnectIsNomarl();
        if (connectIsNomarl) {
            initData();
        } else {
            List<UserBean> userBeans = userBeanDao.loadAll();
            intoAdapter(userBeans);
        }


    }

    private void intoAdapter(List<UserBean> userBeans) {
        ByName byName = new ByName();
        for (int i = 0; i < userBeans.size(); i++) {
            ByName.ResultBean resultBean = new ByName.ResultBean();
            resultBean.setCommodityName(userBeans.get(i).getName());
            resultBean.setMasterPic(userBeans.get(i).getUri());
            byName.setResult(resultBean);
        }
        byNameAdapter.setData(byName.getResult());
    }

    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String intentName = info.getTypeName();
            Log.i("通了没！", "当前网络名称：" + intentName);
            return true;
        } else {
            Log.i("通了没！", "没有可用网络");
            return false;
        }
    }


    private void initView() {
        persenter = new PersenterImpl(this);
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManagers.setOrientation(LinearLayoutManager.VERTICAL);
        byname.setLayoutManager(gridLayoutManagers);
        byNameAdapter = new ByNameAdapter(MainActivity.this);
        byname.setAdapter(byNameAdapter);
        byname.loadMoreComplete();
        byname.refreshComplete();
        byNameAdapter.result(new ByNameAdapter.Cicklistener() {
            @Override
            public void setonclicklisener(int index) {
                persenter.sendGet(Constant.QUERY_ID+"?commodityId="+result.get(index).getCommodityId(),GoodsBean.class);

            }
        });

        byname.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });

    }

    private void initData() {
        persenter.sendGet(Constant.QUERY_NAME + "?keyword="+name+"&page=" + page + "+&count=8", ByName.class);
    }

    @OnClick(R.id.huans)
    public void onViewClicked() {
        name = edits.getText().toString();
        page=1;
        initData();
    }

    @Override
    public void requesta(Object data) {
        if (data instanceof ByName) {
            final ByName byNames = (ByName) data;
            result = byNames.getResult();
            if (page == 1) {
                byNameAdapter.setData(byNames.getResult());
            } else {
                byNameAdapter.addData(byNames.getResult());
            }
            byname.refreshComplete();
            byname.loadMoreComplete();
            page++;
            intoDao(result);
        }
        if (data instanceof GoodsBean){
            GoodsBean goodsBean= (GoodsBean) data;
            Intent intent=new Intent(MainActivity.this,MyInfoActivity.class);
            EventBus.getDefault().postSticky(goodsBean);
            startActivity(intent);
        }

    }

    private void intoDao(List<ByName.ResultBean> result) {
        for (int i = 0; i < result.size(); i++) {
            UserBean userBean = new UserBean();
            userBean.setName(result.get(i).getCommodityName());
            userBean.setUri(result.get(i).getMasterPic());
            insert(userBean);
        }
    }

    private void insert(UserBean resultBean) {
        userBeanDao.insert(resultBean);
    }

}