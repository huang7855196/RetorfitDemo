package net.exception;

/**
 * desc:错误基类
 * <p/>
 * author:caowy
 * date:2016-05-06
 */
public class AppException extends Exception{

    private String mMessage;

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     *
     * @param detailMessage
     */
    public AppException(String detailMessage) {
        super(detailMessage);
        mMessage = detailMessage;
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-06
     */
    public AppException() {
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-10
     *
     * @param throwable
     */
    public AppException(Throwable throwable) {
        super(throwable);
    }

    /**
     * desc:
     * <p/>
     * author:caowy
     * date:2016-05-10
     *
     * @param detailMessage
     * @param throwable
     */
    public AppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        mMessage = detailMessage;
    }

    @Override
    public void printStackTrace() {
        if(getCause() != null) {
            getCause().printStackTrace();
        } else {
            super.printStackTrace();
        }
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    /**
     * desc:获取具体得错误异常信息（Toast提示得信息不使用该方法）
     * <p>
     * author: jinyuef
     * date: 2016/09/26
     *
     * @return
     */
    public String getDetailMessage() {
        return getCause() != null ? getCause().toString() : super.getMessage();
    }
}
