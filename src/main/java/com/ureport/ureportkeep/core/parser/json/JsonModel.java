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

    /**
     * 超级链接打开窗口类型
     */
    enum WindowOption {
        CURRENT_WINDOW(1),
        NEW_WINDOW(2),
        PARENT_WINDOW(3),
        TOP_WINDOW(4)
        ;
        private int type;

        WindowOption(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
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
