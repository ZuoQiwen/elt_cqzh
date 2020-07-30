package com.dfwy.common.expection;

/**
 * 业务异常
 * 
 * @author dfwy
 */
public class RemoteAccessException extends RuntimeException
{

    protected final String message;

    public RemoteAccessException(String message)
    {
        this.message = "接口远程调用异常："+message;
    }

    public RemoteAccessException(String message, Throwable e)
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
