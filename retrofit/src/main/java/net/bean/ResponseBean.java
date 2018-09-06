package net.bean;

import java.io.Serializable;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author ZhongDaFeng
 */
public class ResponseBean<T> implements Serializable{

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    private T result;

    /**
     * 是否成功(这里约定200)
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 200 ? true : false;
    }

    public String toString() {
       //String response = "[http response]" + "{\"code\": " + code + ",\"msg\":" + msg + ",\"result\":" + new Gson().toJson(result) + "}";
        return "";
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
