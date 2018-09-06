package test.hxy.com.testretrofitdemo;


import io.reactivex.Observable;
import retrofit2.http.ApiName;
import retrofit2.http.Field;

/**
 * desc:
 * Created by huangxy on 2018/8/28.
 */
public interface Api {

    @ApiName("index")
    Observable<String> getLogain(@Field("type") String type, @Field("key") String key);
}
