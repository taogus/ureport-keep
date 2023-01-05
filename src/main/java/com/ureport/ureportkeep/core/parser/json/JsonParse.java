package com.ureport.ureportkeep.core.parser.json;

/**
 * @Author: summer
 * @Date: 2022/11/27 10:44
 * @Description:
 **/
public interface JsonParse<Definition, T> {

    Definition parse(T parseModel);

}
