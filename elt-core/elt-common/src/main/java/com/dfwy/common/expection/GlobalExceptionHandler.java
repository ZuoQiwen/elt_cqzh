package com.dfwy.common.expection;

import com.dfwy.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Description: 全局异常处理 <bMethodArgumentNotValidExceptionr/>
 * date: 2019/12/24 14:46<br/>
 *
 * @author zuoqiwen<br />
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 请求方式错误
     * @param e
     * @return
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public Result handleException(HttpRequestMethodNotSupportedException e)
    {
        log.error(e.getMessage(), e);
        return Result.fail("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 参数校验错误，参数不符合要求  @RequestBody @validate
     * @param e
     * @return
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Result handleException(MethodArgumentNotValidException e)
    {

        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        //log.error(message);
        return Result.paramError("参数错误："+message);
    }

    /**
     * 参数校验错误，参数不符合要求  @RequestParam @validate
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        //log.error(message, e);
        return Result.paramError("参数错误："+message);
    }
    /**
     * 参数异常  手动抛出
     * @param e
     * @return
     */
    @ExceptionHandler({ IllegalArgumentException.class })
    public Result IllegalArgumentException(IllegalArgumentException e)
    {
        log.error(e.getMessage(), e);
        return Result.paramError(e.getMessage());
    }

    /**
     * @RequestBody json转换成对象时json解析异常
     * @param e
     * @return
     */
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public Result handleException(HttpMessageNotReadableException e)
    {
        log.error(e.getMessage(), e);
        return Result.paramError("json格式错误："+e.getMessage());
    }
    @ExceptionHandler({ RuntimeException.class })
    public Result runtimeException(RuntimeException e)
    {
        log.error(e.getMessage(), e);
        return Result.fail("系统运行时异常");
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return Result.fail("服务器错误");
    }
}
