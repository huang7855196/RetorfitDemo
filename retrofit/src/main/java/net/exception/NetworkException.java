package net.exception;

/**
 * desc:网络错误
 * <p/>
 * author:caowy
 * date:2016-05-06
 */
public class NetworkException extends AppException {
    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     *
     * @param detailMessage
     */
    public NetworkException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     */
    public NetworkException() {
    }

    public NetworkException(Throwable throwable) {
        super(throwable);
    }

    public NetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
