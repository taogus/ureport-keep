package com.ureport.ureportkeep.core.export.pdf.font.impl.heiti;


import com.ureport.ureportkeep.core.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class HeiTiFontRegister implements FontRegister {

	public String getFontName() {
		return "黑体";
	}

	public String getFontPath() {
		return path() + "heiti/SIMHEI.TTF";
	}
}
