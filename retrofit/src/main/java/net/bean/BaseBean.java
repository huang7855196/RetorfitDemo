package net.bean;

import java.io.Serializable;

/**
 * bean的基类
 * Created by zhouL on 2016/2/7.
 */
public class BaseBean implements Serializable {

    /** 请求状态：0 成功，1 失败，7 需要用户登陆， 8 非法请求；9 交易失败 */
    private int code = 1;

    /** 显示给用户的提示消息 */
    private String msg = "";

    private String data;

    private String result;


    /** 获取状态码 */
    public int getCode() {
        return code;
    }

    /** 设置状态码 */
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



}


