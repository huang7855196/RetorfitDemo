package net.jsonconverter;

import com.alibaba.fastjson.JSON;

import net.bean.BaseBean;
import net.bean.ResponseBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by dayaa on 16/1/20.
 */
final class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;
    private Annotation[] annotations;
    private Charset charset;

    public FastjsonResponseBodyConverter(Type type, Annotation[] annotations, Charset charset) {
        this.type = type;
        this.annotations = annotations;
        this.charset = charset;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String apiName = "";
        try {
            String valueString = value.string();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type subType = parameterizedType.getActualTypeArguments()[0];
                BaseBean baseBean = JSON.parseObject(valueString, BaseBean.class);
                Object data = JSON.parseObject(baseBean.getResult(), subType);
                ResponseBean responseBean = new ResponseBean();
                responseBean.setMsg(baseBean.getMsg());
                responseBean.setCode(baseBean.getCode());
                responseBean.setResult(data);
                return (T) responseBean;
            } else if (type == ResponseBean.class) {
                BaseBean baseBean = JSON.parseObject(valueString, BaseBean.class);
                ResponseBean responseBean = new ResponseBean();
                responseBean.setMsg(baseBean.getMsg());
                responseBean.setCode(baseBean.getCode());
                responseBean.setResult(baseBean.getResult());
                return (T) responseBean;
            } else {
                return JSON.parseObject(valueString, type);
            }
        } finally{
            value.close();
        }
    }

}
