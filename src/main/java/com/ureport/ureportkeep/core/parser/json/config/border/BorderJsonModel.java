package com.ureport.ureportkeep.core.parser.json.config.border;

import com.ureport.ureportkeep.core.parser.json.JsonModel;

/**
 * @Author: summer
 * @Date: 2022/12/2 21:11
 * @Description:
 **/
public interface BorderJsonModel extends JsonModel {

    /**
     * 边框范围类型
     */
    enum RangeType {
        range, cell;
    }

}
