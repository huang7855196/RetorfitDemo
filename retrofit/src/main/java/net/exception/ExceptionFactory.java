package net.exception;

import com.retrofit.R;

import net.MyApplication;
import net.NetworkUtils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ExceptionFactory {
    public static AppException create(Throwable t) {
        if (t != null) {
            if (!NetworkUtils.isNetworkAvailable() || t instanceof ConnectException) {
                return new NetworkNoConnException(MyApplication.getContext().getString(R.string.exception_none_net_msg));
            } else if (t instanceof SocketTimeoutException || t instanceof
                    SocketException) {
                return new NetworkTimeOutException(MyApplication.getContext().getString(R.string.exception_fail_net_msg), t);
            } else if(t instanceof AppException) {
                return (AppException) t;
            } else {
                return new AppException(MyApplication.getContext().getString(R.string.exception_fail_api_msg), t);
            }
        }
        return new AppException();
    }
}
