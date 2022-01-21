package com.ureport.ureportkeep.core.export.pdf.font.impl.fangsong;


import com.ureport.ureportkeep.core.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class FangSongFontRegister implements FontRegister {

	public String getFontName() {
		return "仿宋";
	}

	public String getFontPath() {
		return path() + "fangsong/SIMFANG.TTF";
	}
}
