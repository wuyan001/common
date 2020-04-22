package com.lanshi.utils.net;


import com.lanshi.utils.base.utils.BLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


public class SCMHttpInterceptor implements Interceptor {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private boolean enableDebug;
  private String TAG = "http";

  public SCMHttpInterceptor(boolean enableDebug) {
    this.enableDebug = enableDebug;
  }

  public Request addHeader(Request.Builder builder) {
  //  builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    //builder.addHeader("shhServiceAddress", "xkdhc.test.fxscm.net");

//   String shhServiceAddress = PrefUtils.getInstance().getString(MainApp.context, Constans.sp_ip, "");
//    Request request = builder.addHeader("shhServiceAddress",  shhServiceAddress)//基础资料下载
//            .build();
    Request request = builder.build();
    return  request;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request_old = chain.request();

    Request request = addHeader(request_old.newBuilder());



    // Response response = chain.proceed(request);
    Response response = chain.proceed(request);
    BLog.i("\n -------------------- scm http log start --------------------");

    if (enableDebug) {
      Headers headers = request.headers();
      int headerSize = headers.size();
      BLog.i("-----请求头-----");
      for (int i = 0; i < headerSize; i++) {
        BLog.i(headers.name(i) + ":" + headers.value(i));
      }

      BLog.i(TAG, "-----请求参数-----");
      BLog.i(TAG, "url: \n" + request.url());

      Charset charset = UTF8;

      RequestBody requestBody = request.body();

      if (requestBody != null) {
        MediaType contentType = requestBody.contentType();
        BLog.i(TAG, "Content-Type: \n" + contentType);

        if (requestBody instanceof FormBody) {
          FormBody formBody = (FormBody) requestBody;
          int size = formBody.size();
          for (int i = 0; i < size; i++) {
            BLog.i(TAG,
                   "FormBody: " + formBody.encodedName(i) + "=" + formBody.encodedValue(i) + "\n");
          }
        }

        //打印请求体中的参数
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        if (contentType != null) {
          charset = contentType.charset(UTF8);
        }
        BLog.i(TAG, buffer.readString(charset));
      }

      // 响应体部分============================================
      BLog.i(TAG, "-----响应参数-----");

      ResponseBody responseBody = response.body();
      long contentLength = responseBody.contentLength();
      BLog.i(TAG, "responseCode: \n" + response.code() + " " + response.message());

      BufferedSource source = responseBody.source();
      // Buffer the entire body.
      source.request(Long.MAX_VALUE);
      Buffer responseBuffer = source.buffer();
      BLog.i(TAG,
             responseBuffer.clone()
                           .readString(charset));

      BLog.i("-------------------- scm http log end --------------------");

      MediaType responseContentType = responseBody.contentType();
      if (responseContentType != null) {
        try {
          charset = responseContentType.charset(UTF8);
        } catch (UnsupportedCharsetException e) {
          BLog.i("Couldn't decode the response body; charset is likely malformed.");
          BLog.i("<-- END HTTP");

          return response;
        }
      }

      if (contentLength != 0) {
        String reposeUrl = response.request()
                                   .url()
                                   .toString();
        String reposeStr = responseBuffer.clone()
                                         .readString(charset);

        if (reposeUrl != null && reposeUrl.contains("getCommonPageData")) {
          try {
            JSONObject jsonObject = new JSONObject(reposeStr);
            if (jsonObject.has("fcodes")) {
              String fcodesStr = jsonObject.getJSONArray("fcodes")
                                           .toString();
//              SpUtils.getInstance()
//                     .put("fcodes", fcodesStr);
              BLog.i(TAG, "fcodesStr:\n" + fcodesStr);
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else if (reposeUrl != null && reposeUrl.contains("getUserFcodes")) {
          try {
            JSONObject jsonObject = new JSONObject(reposeStr);
            if (jsonObject.has("param")) {
              String paramStr = jsonObject.getJSONObject("param")
                                          .toString();
//              SpUtils.getInstance()
//                     .put("params", paramStr);
              BLog.i(TAG, "param:\n" + paramStr);
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        } else if (reposeUrl != null && reposeUrl.contains("changeShop")) {
          try {
            JSONObject jsonObject = new JSONObject(reposeStr);
            if (jsonObject.has("data")) {
              String dataStr = jsonObject.getJSONObject("data")
                                         .toString();
//              SpUtils.getInstance()
//                     .put("shop_auth", dataStr);
              BLog.i(TAG, "data:\n" + dataStr);
            }
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      }
    }

    return response;
  }


}
