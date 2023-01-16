package com.ureport.ureportkeep.core.parser.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @Author: summer
 * @Date: 2022/11/27 11:04
 * @Description: json解析模型层
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public interface JsonModel extends Serializable {

}
