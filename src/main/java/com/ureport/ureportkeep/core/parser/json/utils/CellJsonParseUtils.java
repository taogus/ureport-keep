package com.ureport.ureportkeep.core.parser.json.utils;

/**
 * @Author: summer
 * @Date: 2022/12/28 21:41
 * @Description:
 **/
public class CellJsonParseUtils {

    /**
     * 转换单元格名称
     *
     * @param row 从1开始
     * @param col 从0开始
     * @return
     */
    public static String convertCellName(int row, int col) {
        int ordA = 'A';
        int ordZ = 'Z';
        int len = ordZ - ordA + 1;
        String s = "";
        while (col >= 0) {
            s = ((char) (col % len + ordA)) + s;
            col = (int) Math.floor(col / len) - 1;
        }

        return s + (row);
    }

    /**
     * rgb转换成16进制
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static String rgb2Hex(int r, int g, int b) {
        return String.format("#%02X%02X%02X", r, g, b);
    }

    /**
     * 16进制颜色字符串转换成rgb
     *
     * @param hexStr
     * @return rgb
     */
    public static String hex2RGB(String hexStr) {
        if (hexStr != null && !"".equals(hexStr) && hexStr.length() == 7) {
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(hexStr.substring(1, 3), 16);
            rgb[1] = Integer.valueOf(hexStr.substring(3, 5), 16);
            rgb[2] = Integer.valueOf(hexStr.substring(5, 7), 16);
            return rgb[0] + "," + rgb[1] + "," + rgb[2];
        }
        return null;
    }

}
