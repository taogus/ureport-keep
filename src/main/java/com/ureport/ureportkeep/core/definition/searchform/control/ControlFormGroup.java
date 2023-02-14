package com.ureport.ureportkeep.core.definition.searchform.control;

import com.ureport.ureportkeep.core.definition.searchform.Component;
import com.ureport.ureportkeep.core.definition.searchform.RenderContext;
import com.ureport.ureportkeep.core.definition.searchform.SearchForm;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Component> components = super.getComponents().stream().filter(c -> c instanceof FormControl).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(components)) {
            return null;
        }
        StringBuilder html = new StringBuilder();
        html.append("<form class=\"layui-form layui-form-pane\" style='margin-top:10px;margin-bottom:10px'>");
        int count = 1;
        for (Component component : components) {
            String label = ((FormControl) component).getLabel();

            boolean rowFlag = count == 1 || count % 3 == 0;
            if (rowFlag) {
                html.append("<div class=\"layui-row layui-col-space" + colSpance + "\">");
            }
            html.append("<div class=\"layui-col-xs"+ pc +" layui-col-xs" + app + " layui-col-sm"+ pad +"\">");
            html.append("<label class='layui-form-label' style='background-color: white;'>");
            html.append("<span style=\"font-size: 20px;\">").append(label).append("</span>");
            html.append("</label>");
            html.append("<div class=\"layui-input-block\">");
            html.append(component.toHtml(context));
            html.append("</div>");
            html.append("</div>");
            if (rowFlag) {
                html.append("</div>");
            }

            count ++;
        }
        html.append("</form>");

        html.append("<div class=\"layui-row\">");
        html.append("<div class=\"layui-col-xs3\">");
        html.append("<button id='submit-button' type=\"button\" class=\"layui-btn layui-btn-normal\">查询</button>");
        html.append("</div>");
        html.append("</div>");
        return html.toString();
    }

    @Override
    public String toJs(RenderContext context) {
        return super.toJs(context);
    }
}
