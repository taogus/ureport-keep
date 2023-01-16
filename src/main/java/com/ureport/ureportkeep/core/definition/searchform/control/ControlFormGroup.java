package com.ureport.ureportkeep.core.definition.searchform.control;

import com.ureport.ureportkeep.core.definition.searchform.Component;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.SearchForm;

/**
 * @Author: summer
 * @Date: 2023/1/11 20:20
 * @Description:
 **/
public class ControlFormGroup extends SearchForm {

    private final int
            pc = 3,     // 桌面
            pad = 3,    // 平板
            app = 3;    // 移动

    private final int colSpance = 10;   // 列间距

    @Override
    public String toHtml(RenderContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form class=\"layui-form layui-form-pane\" style='margin-top:10px;margin-bottom:10px'>");
        int count = 1;
        for (Component component : super.getComponents()) {
            boolean rowFlag = count == 1 || count % 3 == 0;
            if (rowFlag) {
                sb.append("<div class=\"layui-row layui-col-space" + colSpance + "\">");
            }
            sb.append("<div class=\"layui-col-xs"+ pc +" layui-col-xs" + app + " layui-col-sm"+ pad +"\">");
            sb.append(component.toHtml(context));
            sb.append("</div>");
            if (rowFlag) {
                sb.append("</div>");
            }

            count ++;
        }
        sb.append("</form>");

        sb.append("<div class=\"layui-row\">");
        sb.append("<div class=\"layui-col-xs3\">");
        sb.append("<button type=\"button\" class=\"layui-btn layui-btn-normal\">查询</button>");
        sb.append("</div>");
        sb.append("</div>");
        return sb.toString();
    }

    @Override
    public String toJs(RenderContext context) {
        return super.toJs(context);
    }
}
