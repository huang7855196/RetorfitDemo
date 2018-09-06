package net;

import android.app.Application;
import android.content.Context;

/**
 * desc:
 * Created by huangxy on 2018/8/28.
 */
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUtils.getInstance();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
