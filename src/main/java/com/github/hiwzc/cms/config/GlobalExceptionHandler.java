package com.github.hiwzc.cms.config;

import com.github.hiwzc.cms.base.Result;
import com.github.hiwzc.cms.base.ResultCode;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> errorHandler(MethodArgumentNotValidException exception) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(":");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
            msg.append("; ");
        }
        msg.setLength(msg.length() - 2);
        return Result.create(ResultCode.PARAM_ERROR.getCode(), msg.toString());
    }

}
