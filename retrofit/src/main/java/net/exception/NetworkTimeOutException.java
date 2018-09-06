package net.exception;

/**
 * desc:
 * <p/>
 * author:caowy
 * date:2016-05-06
 */
public class NetworkTimeOutException extends NetworkException {
    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     *
     * @param detailMessage
     */
    public NetworkTimeOutException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     */
    public NetworkTimeOutException() {
    }

    public NetworkTimeOutException(Throwable throwable) {
        super(throwable);
    }

    public NetworkTimeOutException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
