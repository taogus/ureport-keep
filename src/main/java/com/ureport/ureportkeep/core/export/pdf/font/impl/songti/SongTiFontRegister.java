package com.ureport.ureportkeep.core.export.pdf.font.impl.songti;

import com.ureport.ureportkeep.core.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class SongTiFontRegister implements FontRegister {

	public String getFontName() {
		return "宋体";
	}

	public String getFontPath() {
		return path() + "songti/SIMSUN.TTC";
	}
}
