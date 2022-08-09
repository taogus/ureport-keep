package com.ureport.ureportkeep.core.exception;

/**
 * @Author: summer
 * @Date: 2022/8/9 21:02
 * @Description: 报表启动异常
 **/
public class ReportStartupException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ReportStartupException(String msg) {
        super(msg);
    }

    public ReportStartupException(Exception ex) {
        super(ex);
        ex.printStackTrace();
    }
}
