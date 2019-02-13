package com.example.homework0213;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homework0213.butterknife.BindView;
import com.example.homework0213.butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<String> list = new ArrayList();
    @BindView(R.id.simple)
    SimpleDraweeView simple;

    @BindView(R.id.round_xing)
    Button round_xing;

    @BindView(R.id.round)
    Button round;

    @BindView(R.id.as)
    Button as;

    @BindView(R.id.dong)
    Button dong;

    @BindView(R.id.get)
    Button get;

    @BindView(R.id.fan)
    Button fan;
    String uri="http://www.zhaoapi.cn/images/quarter/ad1.png";
    String gif_image="http://www.zhaoapi.cn/images/girl.gif";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
dong.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, "aaa",Toast.LENGTH_SHORT).show();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                //设置uri,加载本地的gif资源
                .setUri(Uri.parse(gif_image))
                .build();
        //设置Controller
        simple.setController(draweeController);
    }
});
        round_xing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundingParams rp=new RoundingParams();
                rp.setRoundAsCircle(true);
                GenericDraweeHierarchy hierarchy=GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setRoundingParams(rp)
                        .build();
                simple.setHierarchy(hierarchy);
                Uri uri1=Uri.parse(uri);
                simple.setImageURI(uri1);
            }
        });
        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundingParams rp1=new RoundingParams();
                rp1.setCornersRadius(50);
                GenericDraweeHierarchy hierarchy1=GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setRoundingParams(rp1)
                        .build();
                simple.setHierarchy(hierarchy1);
                Uri uri11=Uri.parse(uri);
                simple.setImageURI(uri11);
            }
        });
        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simple.setAspectRatio(1.5f);
            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DraweeViewAnnotation draweeViewAnnotation=new DraweeViewAnnotation();
                Class<DraweeViewAnnotation> c=DraweeViewAnnotation.class;
                try {

                    Method method=c.getMethod("execute",new Class[]{});
                    if(method.isAnnotationPresent(MyAnnotation.class)){
                        MyAnnotation myAnnotation=method.getAnnotation(MyAnnotation.class);
                        try {
                            //利用反射获取值
                            method.invoke(draweeViewAnnotation,new Object[]{});
                            String mName = myAnnotation.name();
                            Toast.makeText(MainActivity.this, mName,Toast.LENGTH_SHORT).show();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add("array");
                //获得集合对象的Class类
                Class cl =list.getClass();
                //从集合Class类中获取add()方法 参数为object
                Method method = null;
                try {
                    method = cl.getMethod("add", Object.class);
                    //方法唤醒并调用 传入集合对象和需要存储的元素
                    method.invoke(list, 123);
                    method.invoke(list, 456);
                    method.invoke(list, 789);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("TAG",list.toString());
            }
        });

    }



}
