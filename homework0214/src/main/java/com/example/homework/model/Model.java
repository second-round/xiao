package com.example.homework.model;

import com.example.homework.callback.MyCallBack;

import java.io.File;
import java.util.Map;

public interface Model {
    void sendMessage(String path, Map<String, String> map, Class clazz, MyCallBack myCallBack);
    void requestDataGet(String path, final Class clazz, final MyCallBack myCallBack);

    void requestPut(String tobushop, Map<String, String> map, Class clazz, MyCallBack myCallBack);

    void requestDelete(String quxiao, Map<String, String> map, Class clazz, MyCallBack myCallBack);

    void sendMessagePost(String onimage, Map<String, File> map, Class aClass, MyCallBack myCallBack);
}
