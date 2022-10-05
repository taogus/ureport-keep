package com.ureport.ureportkeep.controller.express;

import com.ureport.ureportkeep.console.common.R;
import com.ureport.ureportkeep.core.dsl.ReportParserLexer;
import com.ureport.ureportkeep.core.dsl.ReportParserParser;
import com.ureport.ureportkeep.core.expression.ErrorInfo;
import com.ureport.ureportkeep.core.expression.ScriptErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/9/25 22:13
 * @Description:
 **/
@RestController
@RequestMapping(value = "/api/express")
public class ExpressController {

    /**
     * 表达式合法校验
     *
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/scriptValidation", method = RequestMethod.GET)
    public R scriptValidation(@RequestParam String content) throws ServletException, IOException {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(content);
        ReportParserLexer lexer = new ReportParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ReportParserParser parser = new ReportParserParser(tokenStream);
        ScriptErrorListener errorListener = new ScriptErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.expression();
        List<ErrorInfo> infos = errorListener.getInfos();

        return R.ok().success(infos);
    }

    /**
     * 过滤条件表达式合法校验
     *
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/conditionScriptValidation", method = RequestMethod.GET)
    public R conditionScriptValidation(@RequestParam String content) throws ServletException, IOException {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(content);
        ReportParserLexer lexer = new ReportParserLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ReportParserParser parser = new ReportParserParser(tokenStream);
        ScriptErrorListener errorListener = new ScriptErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.expr();
        List<ErrorInfo> infos = errorListener.getInfos();

        return R.ok().success(infos);
    }

}
