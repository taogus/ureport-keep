package com.ureport.ureportkeep.core.parser.impl.page;

import com.ureport.ureportkeep.core.definition.HeaderFooterDefinition;
import com.ureport.ureportkeep.core.parser.Parser;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

/**
 * @Author: summer
 * @Date: 2022/8/9 20:48
 * @Description:
 **/
@Component
public class PageFooterParse extends PageHeaderFooterParser implements Parser<HeaderFooterDefinition> {
    @Override
    public HeaderFooterDefinition parse(Element element) {
        return super.pageParse(element);
    }

    @Override
    public String getName() {
        return "footer";
    }
}
