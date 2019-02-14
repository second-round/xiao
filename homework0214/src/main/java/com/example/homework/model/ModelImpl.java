package com.example.homework.model;

import android.util.Log;

import com.example.homework.callback.MyCallBack;
import com.example.homework.netWork.RetrofitManager;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ModelImpl implements Model{
    @Override
    public void sendMessage(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().post(path, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
    @Override
    public void requestDataGet(String path, final Class clazz, final MyCallBack myCallBack) {

        RetrofitManager.getInstance().get(path, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void requestPut(String tobushop, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().put(tobushop, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void requestDelete(String quxiao, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().delete(quxiao, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void sendMessagePost(String onimage, Map<String, File> map, final Class aClass, final MyCallBack myCallBack) {
        File image = map.get("image");
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
               image );
        MultipartBody.Part part=MultipartBody.Part.createFormData("image",image.getName(),requestBody);
        RetrofitManager.getInstance().postImage(onimage, part, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, aClass);
                myCallBack.CallBack(o);
            }

            @Override
            public void onFail(String error) {
                Log.i("TAG","aaa");
            }
        });
    }

}
