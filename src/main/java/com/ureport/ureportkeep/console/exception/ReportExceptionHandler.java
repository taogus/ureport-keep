package com.ureport.ureportkeep.console.exception;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.exception.ReportException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: summer
 * @Date: 2022/2/11 22:20
 * @Description:
 **/
@ControllerAdvice
public class ReportExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ReportException.class)
    public R globalException(HttpServletResponse response, ReportException ex){
        
        String message = ex.getMessage();
        return R.error(message);
    }

}
