package retrofit2;

import java.lang.reflect.Method;

/**
 * Created by cwy on 2016/5/6.
 */
public interface Interceptor {
    void intercept(RequestBuilder builder, Method method, Object[] args);
}
