package com.example.xiao;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity{
    Button round;
    Button round_xing;
    Button as;
    Button jian;
    Button ci;
    Button gif;
    Button listen;
    Button okhttp;
    SimpleDraweeView simple;
    private String uri="http://www.photosohu.com/uploadfile/2015/0821/20150821083904929.jpg";
    private String uri_dong="https://i03piccdn.sogoucdn.com/620c68218453814b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        listener();
    }

    private void listener() {
        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundingParams rp=new RoundingParams();
                rp.setCornersRadius(50);
                GenericDraweeHierarchy hierarchy=GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setRoundingParams(rp)
                        .build();
                simple.setHierarchy(hierarchy);
                Uri uri1=Uri.parse(uri);
                simple.setImageURI(uri1);
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
        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri1=Uri.parse(uri);
                simple.setImageURI(uri1);

            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri1=Uri.parse(uri);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri1)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(simple.getController())
                        .setImageRequest(request)
                        .build();
                simple.setController(controller);
            }
        });
        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(MainActivity.this)
                        .setBaseDirectoryName("images")
                        .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                        .build();
                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(MainActivity.this)
                        //将DiskCacheConfig设置给ImagePipelineConfig
                        .setMainDiskCacheConfig(diskCacheConfig)
                        .build();
                Fresco.initialize(MainActivity.this,config);
            }
        });
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        round=findViewById(R.id.round);
        round_xing=findViewById(R.id.round_xing);
        as=findViewById(R.id.as);
        jian=findViewById(R.id.jian);
        ci=findViewById(R.id.ci);
        gif=findViewById(R.id.gif);
        listen=findViewById(R.id.listen);
        okhttp=findViewById(R.id.okhttp);
        simple=findViewById(R.id.simple);
        Uri uri1=Uri.parse(uri);
        simple.setImageURI(uri1);
    }
}
