package net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * desc:
 * Created by huangxy on 2018/7/30.
 */
public class ContextUtils {
    public ContextUtils() {
    }

    public static boolean startService(final Context context, final Intent intent) {
        try {
            UiHandler.post(new Runnable() {
                public void run() {
                    context.startService(intent);
                }
            });
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean startActivity(Context context, Class<?> activityCls) {
        if(context != null && activityCls != null) {
            try {
                Intent e = new Intent(context, activityCls);
                return startActivity(context, e);
            } catch (Exception var3) {
                var3.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean startActivity(final Context context, final Intent intent) {
        try {
            UiHandler.post(new Runnable() {
                public void run() {
                    context.startActivity(intent);
                }
            });
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean startActivityForResult(final Activity activity, final Intent intent, final int requestCode) {
        try {
            UiHandler.post(new Runnable() {
                public void run() {
                    activity.startActivityForResult(intent, requestCode);
                }
            });
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static boolean registerReceiver(final Context context, final BroadcastReceiver receiver, final IntentFilter filter) {
        try {
            UiHandler.post(new Runnable() {
                public void run() {
                    context.registerReceiver(receiver, filter);
                }
            });
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static boolean unregisterReceiver(final Context context, final BroadcastReceiver receiver) {
        try {
            UiHandler.post(new Runnable() {
                public void run() {
                    context.unregisterReceiver(receiver);
                }
            });
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }
}
