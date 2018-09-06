package net.exception;

/**
 * desc: 数据错误
 * <p>
 * author:caowy
 * date:2016-05-16
 */
public class DataException extends AppException {

    /**
     * 0 - 成功。
     * 8 - 话题已经被删除
     * 9 - 系统异常
     */
    private int errorCode = -1;

    private Object data;

    /**
     * desc:
     * <p>
     * author:caowy
     * date:2016-05-16
     *
     * @param detailMessage
     */
    public DataException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public DataException(int errorCode, String detailMessage, Object data){
        super(detailMessage);

        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * desc:
     * <p>
     * author:caowy
     * date:2016-05-16
     */
    public DataException() {
    }

    /**
     * desc:
     * <p>
     * author:caowy
     * date:2016-05-16
     *
     * @param throwable
     */
    public DataException(Throwable throwable) {
        super(throwable);
    }

    /**
     * desc:
     * <p>
     * author:caowy
     * date:2016-05-16
     *
     * @param detailMessage
     * @param throwable
     */
    public DataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getData(){
        return data;
    }
}
