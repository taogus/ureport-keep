package com.ureport.ureportkeep.core.exception;

/**
 * @Author: summer
 * @Date: 2022/10/30 21:17
 * @Description:
 **/
public class ReportManageException extends RuntimeException {
    private static final long serialVersionUID = 2970559370876740683L;
    public ReportManageException(String msg) {
        super(msg);
    }
    public ReportManageException(Exception ex) {
        super(ex);
        ex.printStackTrace();
    }
}
