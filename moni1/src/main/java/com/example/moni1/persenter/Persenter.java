package com.example.moni1.persenter;

import java.io.File;
import java.util.Map;

public interface Persenter {
    void sendMessage(String Path, Map<String, String> map, Class clazz);

    void sendGet(String zhuBanner, Class clazz);

    void onPutStartRequest(String tobushop, Map<String, String> map, Class clazz);

    void sendMessageDelete(String quxiao, Map<String, String> map, Class clazz);

    void sendMessagePost(String onimage, Map<String, File> map, Class aClass);
}
