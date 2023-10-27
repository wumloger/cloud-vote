package top.wml.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wml.resp.CommonResp;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResp BusinessException(BusinessException e){
        return CommonResp.fail(e.getMessage());
    }
}
