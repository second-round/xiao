package com.example.moni1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ImageActivity;
import com.example.moni1.bean.GoodsBean;
import com.example.moni1.bean.ShopCarAddBean;
import com.example.moni1.bean.ShopResultBean;
import com.example.moni1.bean.ShowShoppingBean;
import com.example.moni1.netWork.Constant;
import com.example.moni1.persenter.PersenterImpl;
import com.example.moni1.view.IView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.saleNum)
    TextView saleNum;
    @BindView(R.id.commodityName)
    TextView commodityName;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.shopAdd)
    ImageView shopAdd;
    @BindView(R.id.shopBuy)
    ImageView shopBuy;
    private GoodsBean goodsBean;
    private int commodityId;
    private PersenterImpl persenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persenter=new PersenterImpl(this);

    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(GoodsBean goodsBean) {

            this.goodsBean =goodsBean;
            commodityId = goodsBean.getResult().getCommodityId();
            load();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @SuppressLint("JavascriptInterface")
    private void load() {
        String details = goodsBean.getResult().getDetails();
        String picture = goodsBean.getResult().getPicture();
        String[] split = picture.split(",");
        List<String> list = Arrays.asList(split);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        String js = "<script type=\"text/javascript\">"+
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        webview.loadDataWithBaseURL(null, details+js, "text/html", "utf-8", null);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.start();
        price.setText("￥" + goodsBean.getResult().getPrice() + "");
        commodityName.setText(goodsBean.getResult().getCommodityName());
        weight.setText(goodsBean.getResult().getWeight() + "kg");
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                EventBus.getDefault().postSticky(goodsBean.getResult().getPicture());
                startActivity(new Intent(MyInfoActivity.this, ImageActivity.class));
            }
        });

    }
    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }
    @OnClick(R.id.shopAdd)
    public void onViewClicked() {
        ShopResultBean shopResultBean=new ShopResultBean(goodsBean.getResult().getCommodityId(),1);
        String s = new Gson().toJson(shopResultBean);
        Map<String,String> map=new HashMap<>();
        map.put("data",s);
        persenter.onPutStartRequest(Constant.TOBUSHOP,map,ShopCarAddBean.class);

    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void requesta(Object data) {
        if (data instanceof ShowShoppingBean){
            ShowShoppingBean showShoppingBean= (ShowShoppingBean) data;
            Toast.makeText(this,showShoppingBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
