package com.lanshi.utils.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lanshi.utils.net.base.SCMBaseResponse;
import com.lanshi.utils.net.msg.ErrorMsg;
import com.lanshi.utils.net.msg.ErrorMsgUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 供应链模块 请求接口工具类
 */

public class SCMAPIUtil {
  private static SCMAPIUtil ourInstance;
  private Retrofit retrofit;
  /**
   * 超时时间
   */
  private static final int DEFAULT_TIMEOUT = 120;
  public static SCMAPIUtil getInstance() {
    if (ourInstance == null) {
      synchronized (SCMAPIUtil.class) {
        if (ourInstance == null) {
          ourInstance = new SCMAPIUtil();
        }
      }
    }
    return ourInstance;
  }

  public static void setNull(){
    if (ourInstance != null){
      ourInstance = null;
    }
  }




  public static void reset() {
    ourInstance = null;
  }



  public Retrofit  init(String baseurl) {

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    builder.addInterceptor(new SCMHttpInterceptor(true));

    // 设置Gson宽松模式，应对服务器json格式不规范问题
    Gson gson = new GsonBuilder().setLenient()
                                 .create();
    retrofit = new Retrofit.Builder()
        // 开发、动态返回
        .baseUrl(baseurl)
        .client(builder.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return  retrofit;

  }
    /**
     *
     * @param observable   :请求网络,retrofit返回的observable
     * @param netCallback : 通知
     * @param <W> : 网络请求回数据的基类---封装载体
     */
  public <W> void request(Observable<SCMBaseResponse<W>> observable,
                          final NetCallback<W> netCallback) {

    observable.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Subscriber<SCMBaseResponse<W>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                  netCallback.onFailed(ErrorMsgUtil.getErrorMsg(e));
                }

                @Override
                public void onNext(SCMBaseResponse<W> baseResponse) {
                  /**
                   * try--catch---未知错误,先添加,后续查看
                   */
                  if (baseResponse.isResult()) {

                      netCallback.onSuccessed(baseResponse.getData(), baseResponse.getTotal());

                  } else {
                    netCallback.onFailed(new ErrorMsg(baseResponse.getMsg()));
                  }
                }
              });
  }

  public static RequestBody buildRequestBodyByJson(String json) {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
  }

  public static RequestBody buildRequestBodyByObj(Object json) {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                              JsonUtils.parseBean2json(json));
  }

  public interface NetCallback<W> {
    void onFailed(ErrorMsg errorMsg);
    void onSuccessed(W w, int total) ;
  }


}
