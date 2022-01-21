package com.ureport.ureportkeep.core.export.pdf.font.impl.kaiti;


import com.ureport.ureportkeep.core.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class KaiTiFontRegister implements FontRegister {

	public String getFontName() {
		return "楷体";
	}

	public String getFontPath() {
		return path() + "kaiti/SIMKAI.TTF";
	}
}
