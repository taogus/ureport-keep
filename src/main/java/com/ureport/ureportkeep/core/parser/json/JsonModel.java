package com.ureport.ureportkeep.core.parser.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: summer
 * @Date: 2022/11/27 11:04
 * @Description: json解析模型层
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public interface JsonModel extends Serializable {

    /**
     * 超级链接打开窗口类型
     */
    enum WindowOption {
        CURRENT_WINDOW(1, "_self"),
        NEW_WINDOW(2, "_blank"),
        PARENT_WINDOW(3, "_parent"),
        TOP_WINDOW(4, "_top")
        ;
        private int type;

        private String target;

        public String getTarget() {
            return target;
        }

        WindowOption(int type, String target) {
            this.type = type;
            this.target = target;
        }

        public int getType() {
            return type;
        }

        public static WindowOption parse(int type) {
            WindowOption[] values = WindowOption.values();
            for (WindowOption value : values) {
                if (type == value.type) {
                    return value;
                }
            }

            return CURRENT_WINDOW;
        }
    }

    /**
     * 条件属性左值类型
     */
    enum ConditionLeftValueType {
        curr,  // 当前值
        property, // 属性
        expr // 表达式
        ;
    }

    /**
     * 图片来源
     */
    enum ImageSource {
        upload, // 上传图片
        path,  // 图片路径
        expression // 表达式
    }

    /**
     * 条形码来源类型
     */
    enum BarCodeSource {
        TEXT,  // 文本
        EXPRESS,  // 表达式
    }

    /**
     * 条形码格式
     */
    enum BarCodeFormat {
        QR_CODE,  // 二维码
        CODE_128,
        AZTEC,
        CODEBAR,
        CODE_39,
        CODE_93,
        DATA_MATRIX,
        EAN_8,
        EAN_13,
        ITF,
        PDF_417,
        UPC_E,
        UPC_A
    }

}
