package com.lanshi.utils.net.msg;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;


public class ErrorMsgUtil {

  public static ErrorMsg getErrorMsg(Throwable e) {
    ErrorMsg errorMsg = new ErrorMsg(e.getMessage());
    if (e instanceof java.net.ConnectException || e instanceof UnknownHostException) {
      errorMsg.setMsg("网络连接失败");
    } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
      errorMsg.setMsg("网络连接超时");
    } else if (e instanceof IOException) {
      errorMsg.setMsg("网络连接超时");
    }

    return errorMsg;
  }
}
