package com.example.homework.persenter;

import com.example.homework.callback.MyCallBack;
import com.example.homework.model.ModelImpl;
import com.example.homework.view.IView;

import java.io.File;
import java.util.Map;

public class PersenterImpl implements Persenter{
    private IView iViewl;
    private ModelImpl model;

    public PersenterImpl(IView iViewl) {
        this.iViewl = iViewl;
        model=new ModelImpl();
    }


    @Override
    public void sendMessage(String path, Map<String, String> map, Class clazz) {
        model.sendMessage(path,map,clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void sendGet(String url, Class clazz) {
        model.requestDataGet(url, clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void onPutStartRequest(String tobushop, Map<String, String> map, Class clazz) {
        model.requestPut(tobushop,map,clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void sendMessageDelete(String quxiao, Map<String, String> map, Class clazz) {
        model.requestDelete(quxiao,map,clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }

    @Override
    public void sendMessagePost(String onimage, Map<String, File> map, Class aClass) {
        model.sendMessagePost(onimage,map,aClass, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iViewl.requesta(data);
            }
        });
    }
}
