package net.rxsubscriber;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import net.bean.ResponseBean;
import net.exception.AppException;
import net.exception.DataException;
import net.exception.ExceptionFactory;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * desc:默认的RX订阅者，有对ResponseBean对象进行空值和错误处理，可显示加载提示
 * author:huangxy
 * date:2018/7/30
 */
public abstract class RxSubscriber<T> implements Observer<T>, HttpRequestListener {


    /**
     * 返回的数据的次序
     */
    private int mItemIndex = 0;

    /**
     * 检查ResponseBean的空数据
     */
    private boolean mCheckNullData = false;

    /**
     * 错误计数
     */
    private int mErrorCount;


    /**
     * 显示对话框用的context
     */
    private WeakReference<Context> mContextRef;


    /**
     * 是否显示加载提示
     */
    private boolean mShowProgress;

    /**
     * 提示文字
     */
    private String mProgressMessage;

    /**
     * 提示可取消
     */
    private boolean mProgressCancelable;


    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            final Context context = mContextRef != null ? mContextRef.get() : null;
            if (context != null) {
                AlertDialog progressDialog = ProgressDialogHelper.getInstance().create(mContextRef.get(), RxSubscriber.this
                        .toString(), mProgressMessage, false, mProgressCancelable);
                //手动关闭加载框，取消任务
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        try {
                            onCanceled();
                            hideProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                ProgressDialogHelper.getInstance().show(RxSubscriber.this.toString());
            }
        }
    };

    /**
     * 显示加载提示
     *
     * author:caowy
     * date:2016-05-11
     *
     * @param context
     * @param message
     * @param cancelable 手动关闭加载提示会自动取消任务
     * @return
     */
    public RxSubscriber<T> showProgress(Context context, String message, boolean cancelable) {
        mShowProgress = true;
        mProgressMessage = message;
        mProgressCancelable = cancelable;
        mContextRef = new WeakReference<>(context);
        return this;
    }

    /**
     * 显示加载提示
     * author:caowy
     * date:2016-08-25
     *
     * @param context
     * @param messageId
     * @param cancelable
     * @return
     */
    public RxSubscriber<T> showProgress(Context context, int messageId, boolean cancelable) {
        try {
            mShowProgress = true;
            mProgressMessage = context.getString(messageId);
            mProgressCancelable = cancelable;
            mContextRef = new WeakReference<>(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void cancel() {

    }

    /**
     * 显示加载框
     * author:caowy
     * date:2016-08-25
     */
    private void showProgress() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            final Context context = mContextRef != null ? mContextRef.get() : null;
            if (context != null) {
                AlertDialog progressDialog = ProgressDialogHelper.getInstance().create(mContextRef.get(), RxSubscriber.this
                        .toString(), mProgressMessage, false, mProgressCancelable);
                //手动关闭加载框，取消任务
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        try {
                            onCanceled();
                            hideProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                ProgressDialogHelper.getInstance().show(RxSubscriber.this.toString());
            }
        }else{
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 关闭加载提示
     *
     * author:caowy
     * date:2016-05-12
     */
    private void hideProgress() {
        ProgressDialogHelper.getInstance().dismissWithRemove(RxSubscriber.this.toString());
        mContextRef = null;
    }

    /**
     * 检查ResponseBean的data空值
     * @return
     */
    public RxSubscriber<T> checkNullData(){
        mCheckNullData = true;
        return this;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if(mShowProgress) {
            showProgress();
        }
    }

    @Override
    public void onComplete() {
        if(mShowProgress) {
            hideProgress();
        }
    }

    /**
     * 不要重写这个方法
     * @param e
     */
    @Override
    public final void onError(Throwable e) {
        try {
            AppException exception = ExceptionFactory.create(e);
            exception.printStackTrace();
            onError(exception, mItemIndex, mErrorCount + 1);
            mItemIndex++;
            mErrorCount++;
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if(mShowProgress) {
                hideProgress();
            }
        }
    }

    @Override
    public final void onNext(T t) {
        try {
            Exception exception = checkError(t);
            if(exception == null) {
                onNext(t, mItemIndex);
                mItemIndex++;
            } else {
                exception.printStackTrace();
                onError(exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    /**
     * 检查错误
     * author:caowy
     * date:2016-08-25
     *
     * @param t
     * @return
     */
    private Exception checkError(T t) {
        if (t == null) {
            return new NullPointerException("数据是空的");
        }
        if (t instanceof ResponseBean) {
            ResponseBean resp = (ResponseBean) t;
            //服务端返回的数据内容是空的
            if (!resp.isSuccess() || (mCheckNullData && resp.getResult() == null)) {
                DataException dataException = new DataException(resp.getCode(), resp.getMsg(),
                        resp.getResult());
                return dataException;
            }
        }
        return null;
    }

    /**
     * 错误回调
     * @param e 错误信息
     * @param itemIndex 本次数据错误的次序
     * @param errorCount 目前已经出错的总数
     */
    protected void onError(AppException e, int itemIndex, int errorCount) {
    }

    /**
     * desc:返回数据
     * author:caowy
     * date:2016-05-10
     *
     * @param t
     * @param itemIndex 数据的次序，从0开始
     */
    protected abstract void onNext(T t, int itemIndex) throws InterruptedException;
}
