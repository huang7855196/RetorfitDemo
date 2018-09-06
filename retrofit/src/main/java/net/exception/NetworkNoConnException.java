package net.exception;

/**
 * desc:
 * <p/>
 * author:caowy
 * date:2016-05-06
 */
public class NetworkNoConnException extends NetworkException {
    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     *
     * @param detailMessage
     */
    public NetworkNoConnException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     */
    public NetworkNoConnException() {
    }
}
