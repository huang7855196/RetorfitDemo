package retrofit2.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * desc:
 * Created by huangxy on 2018/8/23.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiName {
    /**
     * desc: 接口名
     * author:caowy
     * date:2016-05-11
     *
     * @return
     */
    String value();

    String baseUrl() default "";

    /**
     * desc: 启用Gzip，默认开启
     * author:caowy
     * date:2016-05-11
     *
     * @return
     */
    boolean useGzip() default true;

    /**
     * desc: 请求方式, POST/GET
     * author:caowy
     * date:2016-05-11
     *
     * @return
     */
    String method() default "POST";

    /**
     * desc: 接口版本
     * author:caowy
     * date:2016-05-11
     *
     * @return
     */
    int apiVersion() default -1;

    /**
     * desc: 先调用bootConfig接口
     * author:caowy
     * date:2016-05-18
     *
     * @return
     */
    boolean initBootConfig() default true;

    /**
     * 超时时间，单位毫秒
     * author:caowy
     * date:2016-09-23
     *
     * @return
     */
    long timeout() default -1;
}
