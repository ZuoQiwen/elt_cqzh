package com.dfwy.common.utils;

import java.util.HashMap;

/**
 * Description: <br/>
 * date: 2019/12/24 15:37<br/>
 *
 * @author zuoqiwen<br />
 * @since JDK 1.8
 */
public class Result extends HashMap<String, Object> {

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";



    /**
     * 状态类型
     */
    public enum Type
    {
        SUCCESS("1010"),
        FAILED("1020"),
        PARAM_ERROR("1030");
        private final String value;

        Type(String value)
        {
            this.value = value;
        }

        public String value()
        {
            return this.value;
        }
    }

    public Result()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public Result(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    public Result(String type, String msg, Object data)
    {
        super.put(CODE_TAG, type);
        super.put(MSG_TAG, msg);
        if (data!=null)
        {
            super.put(DATA_TAG, data);
        }
    }

    public static Result success()
    {

        return Result.success(null);
    }

    public static Result success(Object data)
    {

        return success("操作成功", data);
    }

    public static Result success(String msg, Object data)
    {
        return new Result(Type.SUCCESS.value, msg, data);
    }
    public static Result fail(String msg, Object data)
    {
        return new Result(Type.FAILED.value, msg, data);
    }
    public static Result fail(String msg, String code, Object data)
    {
        return new Result(code, msg, data);
    }
    public static Result fail(String msg)
    {
        return new Result(Type.FAILED.value, msg, null);
    }

    public static Result paramError(String msg)
    {
        return new Result(Type.PARAM_ERROR.value, msg, null);
    }

    public Object getData(){
        return this.get(DATA_TAG);
    }
    public String getMsg(){
        return String.valueOf(this.get(MSG_TAG));
    }


}
