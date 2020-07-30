package com.dfwy.common.expection;

/**
 * 业务异常
 * 
 * @author dfwy
 */
public class JsonSerializationException extends RuntimeException
{

    protected final String message;

    public JsonSerializationException(String message)
    {
        this.message = "Json序列化异常："+message;
    }

    public JsonSerializationException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
