package net.rxsubscriber;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retrofit.R;

import java.util.concurrent.ConcurrentHashMap;


/**
 * desc:
 * Created by huangxy on 2018/7/30.
 */
public class ProgressDialogHelper {
    private static ProgressDialogHelper mInstance;
    private ConcurrentHashMap<String, AlertDialog> mHashMap = new ConcurrentHashMap();

    public ProgressDialogHelper() {
    }

    public static ProgressDialogHelper getInstance() {
        if(mInstance == null) {
            Class var0 = ProgressDialogHelper.class;
            synchronized(ProgressDialogHelper.class) {
                if(mInstance == null) {
                    mInstance = new ProgressDialogHelper();
                }
            }
        }

        return mInstance;
    }

    public AlertDialog createDefault(Context context, String tag, String msg) {
        return this.create(context, tag, msg, false, false);
    }

    public AlertDialog createDefault(Context context, String tag) {
        return this.create(context, tag, (String)null, false, false);
    }

    public AlertDialog create(Context context, String tag, String msg, boolean touchOutside, boolean cancelable) {
        if(!TextUtils.isEmpty(tag) && context != null) {
            AlertDialog progressDialog = (AlertDialog)this.mHashMap.get(tag);
            if(progressDialog == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.progress_dialog_base, (ViewGroup)null);
                progressDialog = (new AlertDialog.Builder(context, R.style.ProgressDialogStyle)).setView(view).create();
                if(!TextUtils.isEmpty(msg)) {
                    TextView msgTextView = (TextView)view.findViewById(R.id.msg);
                    msgTextView.setVisibility(View.VISIBLE);
                    msgTextView.setText(msg);
                }
            }

            progressDialog.setCanceledOnTouchOutside(touchOutside);
            progressDialog.setCancelable(cancelable);
            this.mHashMap.put(tag, progressDialog);
            return progressDialog;
        } else {
            return null;
        }
    }

    public void setCancelListener(String tag, DialogInterface.OnCancelListener listener) {
        if(!TextUtils.isEmpty(tag)) {
            AlertDialog progressDialog = (AlertDialog)this.mHashMap.get(tag);
            if(progressDialog != null && listener != null) {
                progressDialog.setOnCancelListener(listener);
                this.mHashMap.put(tag, progressDialog);
            }

        }
    }

    public boolean show(String tag) {
        if(TextUtils.isEmpty(tag)) {
            return false;
        } else {
            AlertDialog progressDialog = (AlertDialog)this.mHashMap.get(tag);
            if(progressDialog != null) {
                try {
                    if(!progressDialog.isShowing()) {
                        progressDialog.show();
                        return true;
                    }
                } catch (Exception var4) {
                    mInstance = null;
                    var4.printStackTrace();
                }
            }

            return false;
        }
    }

    public void dismissWithRemove(String tag) {
        this.dismiss(tag);
        this.remove(tag);
    }

    public boolean dismiss(String tag) {
        if(TextUtils.isEmpty(tag)) {
            return false;
        } else {
            AlertDialog progressDialog = (AlertDialog)this.mHashMap.get(tag);
            if(progressDialog != null && progressDialog.isShowing()) {
                try {
                    progressDialog.dismiss();
                    return true;
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            return false;
        }
    }

    public void remove(String tag) {
        if(!TextUtils.isEmpty(tag)) {
            try {
                this.mHashMap.remove(tag);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    public void clear() {
        if(this.mHashMap != null && this.mHashMap.size() > 0) {
            this.mHashMap.clear();
        }

    }
}
