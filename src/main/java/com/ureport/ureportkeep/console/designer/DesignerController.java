package com.ureport.ureportkeep.console.designer;

import com.ureport.ureportkeep.core.export.ReportRender;
import com.ureport.ureportkeep.core.parser.ReportParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: summer
 * @Date: 2022/1/12 21:20
 * @Description:
 **/
@Controller
@RequestMapping(value = "/designer")
public class DesignerController {

    @Autowired
    private ReportRender reportRender;

    @Autowired
    private ReportParser reportParser;

    /**
     * 报表设计器首页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/designer", method = RequestMethod.GET)
    public String designer(HttpServletRequest request, Model model) {
        //application
        ServletContext context = request.getServletContext();
        context.setAttribute("contextPath",request.getContextPath());

        return "designer";
    }

}
