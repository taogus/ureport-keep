package com.ureport.ureportkeep.core.parser.json.enums;

/**
 * @Author: summer
 * @Date: 2023/1/8 21:05
 * @Description: 超级链接打开窗口类型
 **/
public enum WindowOption {

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
