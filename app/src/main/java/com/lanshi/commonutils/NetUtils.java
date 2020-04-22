package com.lanshi.commonutils;

import com.lanshi.utils.net.SCMAPIUtil;

import retrofit2.Retrofit;

public class NetUtils {

    private static  NetService apiService;

    public void init(){
        SCMAPIUtil instance = SCMAPIUtil.getInstance();
        Retrofit retrofit = instance.init("abc");
        apiService = retrofit.create(NetService.class);
    }
   public static  NetService  getService(){
        return apiService;
   }
}
