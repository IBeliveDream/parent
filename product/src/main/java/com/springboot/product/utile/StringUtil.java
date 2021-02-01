package com.springboot.product.utile;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil.java
 * 
 * @author common
 *
 */
public class StringUtil {

    /** 空文字列 */
    public static final String EMPTY_STR = "";

    /** 编码 */
    public static final String CHARSETNAME = "UTF-8";

    /**
     * 长度检查
     * 
     * @param str 待检查字段
     * @param length 允许长度
     * @return 检查结果
     */
    public static boolean overLength(String str, int length) {

        if (nullToEmpty(str).length() > length) {
            return true;
        }
        return false;
    }

    /**
     * 长度检查（字节数）
     * 
     * @param str 待检查字段
     * @param length 允许长度
     * @return 检查结果
     */
    public static boolean overLengthByByte(String str, int length) {

        int len = 0;
        try {
            len = nullToEmpty(str).getBytes(CHARSETNAME).length;
        } catch (Exception e) {
            return true;
        }
        if (len > length) {
            return true;
        }
        return false;
    }

    /**
     * 文字列转数字
     * 
     * @param str 对象文字列
     * @return 转换结果
     */
    public static int string2int(String str) {

        int num = -1;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
        return num;
    }
    /**
     * 文字列转数字
     *
     * @param str 对象文字列
     * @return 转换结果
     */
    public static int string2intForBatch(String str) {

        int num = 0;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
        return num;
    }

    /**
     * null转空文字列
     * 
     * @param str 待变换文字列
     * @return 转换结果
     */
    public static String nullToEmpty(String str) {

        return nullToValue(str, EMPTY_STR);
    }

    /**
     * null转指定文字列
     * 
     * @param str 待变换文字列
     * @param replacement 变换文字列
     * @return 转换结果
     */
    public static String nullToValue(String str, String replacement) {

        if (null == str || EMPTY_STR.equals(str.trim()) || "NULL".equals(str.trim().toUpperCase())) {
            return replacement;
        }
        return str;
    }

    /**
     * 空文字列判定
     * 
     * @param str 待判定文字列
     * @return 判定结果 空文字列：true
     */
    public static boolean isEmpty(String str) {

        return (nullToEmpty(str).length() == 0);
    }

    /**
     * 去文字列右空格（全半角）
     * 
     * @param str 待处理文字列
     * @return 处理结果
     */
    public static String rtrim(String str) {

        return nullToEmpty(str).replaceAll("[ �@]+$", EMPTY_STR);
    }

    /**
     * 去文字列左空格（全半角）
     * 
     * @param str 待处理文字列
     * @return 处理结果
     */
    public static String ltrim(String str) {

        return nullToEmpty(str).replaceAll("^[ �@]+", EMPTY_STR);
    }

    /**
     * 去文字列左右空格（全半角）
     * 
     * @param str 待处理文字列
     * @return 处理结果
     */
    public static String alltrim(String str) {

        return ltrim(rtrim(str));
    }

    /**
     * 从左侧补足指定文字列
     * 
     * @param s 待处理文字列
     * @param len 检查长度
     * @param pad 补足文字列
     * @return 处理结果
     */
    public static String lpad(String s, int len, String pad) {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(pad);
        }
        buf.append(s);

        return buf.substring(buf.length() - len).toString();

    }
    
    /**
     * 转换成字符串，空时传空字符串
     * @param object 对象
     * @return 字符串
     */
    public static String valueOf(Object object){
        String str = EMPTY_STR;
        if(object!=null){
            str = alltrim(String.valueOf(object));
        }
        return str;
    }

    /**
     * 文字列转数字
     *
     * @param str 对象文字列
     * @return 转换结果
     */
    public static BigDecimal string2BigDecimal(String str) {

        BigDecimal num = null;
        try {
            num = new BigDecimal(str);
        } catch (Exception e) {
            num = new BigDecimal(0);
        }
        return num;
    }

    /**
     * 文字列转数字
     *
     * @param str 对象文字列
     * @return 转换结果
     */
    public static int string2intStatus(String str) {

        int num = -1;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
        return num;
    }

    /**
     * 字符串转Double
     * @param str 对象
     * @return 字符串
     */
    public static double String2double(String str) {

        double num = 0.0;
        try {
            num = Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0;
        }
        return num;
    }

    /**
     * 根据长度截取字符串
     * @param str
     * @return
     */
    public static String subStringByIndex(String str, int startIndex,int endIndex) {
        String resultStr = str;
        if (!isEmpty(resultStr) && resultStr.length() >= endIndex) {
            resultStr = str.substring(startIndex, endIndex);
        } else {
            resultStr = str;
        }
        return resultStr;
    }

    /**
     * 全角转半角
     *
     * @param input　String
     * @return 半角字符串
     */
    public static String ToDBC(String input) {

        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if(!isChineseChar(String.valueOf(c[i]))){
                if (c[i] == '\u3000') {
                    c[i] = ' ';
                } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                    c[i] = (char) (c[i] - 65248);
                }
            }
        }
        String returnString = new String(c);

        return returnString;
    }

    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 汉字判定
     * @param str String
     * @return boolean
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;

        Matcher m = p.matcher(str);

        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 数字判定
     * @param strNum String
     * @return boolean
     */
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }


    /**
     * 替换反斜线为中划线
     * @param input
     * @return 替换后字符串
     */
    public static String replaceSlash(String input){
        return input.replaceAll("/", "-");
    }


    /**
     * 数字整数位及小数位判定
     * @param strNum
     * @param integer
     * @param decimal
     * @return
     */
    public static boolean numCheck(String strNum, int integer, int decimal) {
        if(strNum != null && !strNum.isEmpty()) {
            String[] numStr = strNum.split("\\.");

            if (numStr.length == 1) {
                if (numStr[0].length() > integer) {
                    return true;
                }
            } else if (numStr.length == 2) {
                if (numStr[0].length() > integer || numStr[1].length() > decimal) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String str = "1,,,2,3,";
        String[] strs = str.split(",");
        System.out.println(Arrays.asList(strs));
        System.out.println(strs.length);
    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
