package com.wilhelmaoi.sunote.exception;
import com.wilhelmaoi.sunote.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/4/1 00:31
 */


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handleServiceException(ServiceException e) {
        return Result.error(e.getMessage()); // 这里返回 Result 格式的错误信息
    }
}
