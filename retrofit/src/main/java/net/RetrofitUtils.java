package net;

import android.util.Log;

import net.jsonconverter.FastjsonConverterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Interceptor;
import retrofit2.RequestBuilder;
import retrofit2.Retrofit;
import retrofit2.http.ApiName;
import retrofit2.http.Field;
import retrofit2.rxjava2adapter.RxJava2CallAdapterFactory;

/**
 * desc:
 * Created by huangxy on 2018/8/28.
 */
public class RetrofitUtils {
    private String TAG = "RetrofitUtils";
    private Retrofit mRetrofit;
    private static RetrofitUtils instance;
    private HashMap<Class, Object> mApiCache = new HashMap<>();

    private RetrofitUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .addConverterFactory(FastjsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .setInterceptor(new MyInterceptor())
                .client(builder.build()).build();
    }

    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }
    
    private  <T> T getApiInstance(Class<T> api){
        Object apiImpl = mApiCache.get(api);
        if(apiImpl == null) {
            apiImpl = mRetrofit.create(api);
            mApiCache.put(api, apiImpl);
        }
        return (T) apiImpl;
    }
    

    public static <T> T create(Class<T> api) {
        return getInstance().getApiInstance(api);
    }

    public class MyInterceptor implements Interceptor {

        @Override
        public void intercept(RequestBuilder builder, Method method, Object[] args) {
            ApiName annotation = method.getAnnotation(ApiName.class);
            String value = annotation.value();
            //builder.addHeader();
            Log.e(TAG,"接口名 = "+value);
            Log.e("resultValue", "[" + value + "] >>>> args: " + getDataJson(method, args));
        }

        private String getDataJson(Method method, Object[] args) {
            Annotation[][] paramAnnos = method.getParameterAnnotations();
            HashMap<String, Object> paramMap = new HashMap<>();
            if (paramAnnos != null) {
                int argCount = paramAnnos != null ? paramAnnos.length : 0;
                for (int i = 0; i < argCount; i++) {
                    Annotation[] oneParamAnnos = paramAnnos[i];
                    for (int j = 0; j < oneParamAnnos.length; j++) {
                        Annotation annotation = oneParamAnnos[j];
                        if (annotation instanceof Field) {
                            String name = ((Field) annotation).value();
                            Object value = args[i];
                            paramMap.put(name, value);
                            break;
                        }
                    }
                }
            }
            return paramMap.size() > 0 ? paramMap.toString() : "{}";
        }
    }
}
