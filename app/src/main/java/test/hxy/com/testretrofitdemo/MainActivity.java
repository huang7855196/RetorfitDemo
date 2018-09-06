package test.hxy.com.testretrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.RetrofitUtils;
import net.rxsubscriber.RxSubscriber;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitUtils.getInstance();
        tv = (TextView) findViewById(R.id.tv);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitUtils.create(Api.class).getLogain("top", "fa217eba9755b2486b67ab2db1271587").subscribe(new RxSubscriber<String>() {
                    @Override
                    protected void onNext(String s, int itemIndex) throws InterruptedException {
                        tv.setText(s);
                    }
                }.showProgress(MainActivity.this,"请稍候",false));
            }
        });
    }
}
