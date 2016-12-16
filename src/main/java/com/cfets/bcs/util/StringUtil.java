package com.cfets.bcs.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author XWQ
 * 
 */
public class StringUtil {
  /**
   * Function Name: replace
   * 
   * @param queryStr
   *          原字符串
   * @param oldstr
   *          指定字符串
   * @param replacement
   *          给定字符串
   * @return description:根据给定字符串替换原字符串中存在的指定字符串 Modification History:
   */
  public static String replace(String queryStr, String oldstr, String replacement) {
    return StringUtils.replace(queryStr, oldstr, replacement);
  }

  /**
   * 将字符串转成UTF8格式 Function Name: getUTF8String
   * 
   * @param src
   * @return description: Modification History:
   */
  public static String getUTF8String(String src) {
    try {
      return new String(src.getBytes("ISO8859_1"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return src;
    }
  }

  /**
   * 将字符串转成ISO格式 Function Name: getISOString
   * 
   * @param src
   * @return description: Modification History:
   */
  public static String getISOString(String src) {
    try {
      return new String(src.getBytes("UTF-8"), "ISO8859-1");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return src;
    }
  }

  /**
   * html代码转义 Function Name: FullHTMLEncode
   * 
   * @param aTagFragment
   * @return
   */
  public static String fullHTMLEncode(String aTagFragment) {
    if (aTagFragment == null) {
      return "";
    }
    final StringBuffer result = new StringBuffer();
    final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
    char character = iterator.current();
    while (character != StringCharacterIterator.DONE) {
      if (character == '<') {
        result.append("&lt;");
      } else if (character == '>') {
        result.append("&gt;");
      } else if (character == '\"') {
        result.append("&quot;");
      } else if (character == '\'') {
        result.append("&#039;");
      } else if (character == '\\') {
        result.append("&#092;");
      } else if (character == '&') {
        result.append("&amp;");
      } else {
        result.append(character);
      }
      character = iterator.next();
    }
    return result.toString();
  }

  /**
   * 校验是否含有html代码 Function Name: haveHTMLcode
   * 
   * @param aTagFragment
   * @return
   */
  public static boolean haveHTMLcode(String aTagFragment) {
    if (aTagFragment == null) {
      return false;
    }
    final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
    char character = iterator.current();
    while (character != StringCharacterIterator.DONE) {
      if (character == '<') {
        return true;
      } else if (character == '>') {
        return true;
      } else if (character == '\"') {
        return true;
      } else if (character == '\'') {
        return true;
      } else if (character == '\\') {
        return true;
      } else if (character == '&') {
        return true;
      }
      character = iterator.next();
    }
    return false;
  }

  /**
   * Function Name: objToString
   * 
   * @param obj
   * @return 返回String数据 description: 将Object转成string类型 Modification History:
   */
  public static String objToString(Object obj) {
    String str = "";
    if (obj != null) {
      str = obj + "";
    }
    return str;
  }

  /**
   * 
   * Function Name: objToBigDecimal
   * 
   * @param obj
   * @return 返回BigDecimal数据 description: 将Object转成BigDecimal类型 Modification
   *         History:
   */
  public static BigDecimal objToBigDecimal(Object obj) {
    BigDecimal bigDec = null;
    if (obj != null) {
      bigDec = (BigDecimal) obj;
    }
    return bigDec;
  }

  /**
   * 
   * Function Name: objToDouble
   * 
   * @param obj
   * @return 返回double数据 description:将Object转成double类型 Modification History:
   */
  public static Double objToDouble(Object obj) {
    Double doubleObj = null;
    if (obj != null) {
      doubleObj = (Double) obj;
    }
    return doubleObj;
  }

  /**
   * 
   * Function Name: objToDate
   * 
   * @param obj
   * @return 返回Date数据 description:将Object转成Date类型 Modification History:
   */
  public static Date objToDate(Object obj) {
    Date date = null;
    if (obj != null) {
      date = (Date) obj;
    }
    // else if (obj != null) {
    // date = DateUtil.getDate(obj + "", "yyyy-MM-dd");
    // }

    return date;
  }

  /**
   * 
   * Function Name: objToInt
   * 
   * @param obj
   * @return 返回double数据 description:将Object转成int类型 Modification History:
   */
  public static int objToInt(Object obj) {
    Integer integer = null;
    if (obj != null) {
      integer = (Integer) obj;
    }
    return integer;
  }

  /**
   * 
   * Function Name: objToTimeStamp
   * 
   * @param obj
   * @return 返回TimeStamp数据 description:将Object转成TimeStamp类型 Modification
   *         History:
   */
  public static Timestamp objToTimeStamp(Object obj) {
    Timestamp tt = null;
    if (obj != null) {
      tt = (Timestamp) obj;
    }
    return tt;
  }

  /**
   * 
   * Function Name: objToTimeLong
   * 
   * @param obj
   * @return 返回Long数据 description:将Object转成Long类型 Modification History:
   */
  public static Long objToTimeLong(Object obj) {
    Long l = null;
    if (obj != null) {
      l = (Long) obj;
    }
    return l;
  }

  /**
   * 格式化价格的输出，目前控制强制保留两位小数 Function Name: formatPrice
   * 
   * @param number
   * @return description: Modification History:
   */
  public static String formatPrice(double number) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 格式化价格的输出，目前控制强制保留两位小数 Function Name: formatPrice
   * 
   * @param number
   * @return description: Modification History:
   */
  public static String formatPrice(BigDecimal number) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 格式化量的输出，不显示小数 Function Name: formatAmount
   * 
   * @param amount
   * @return description: Modification History:
   */
  public static String formatAmount(double amount) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(0);
    nf.setGroupingUsed(false);
    return nf.format(amount);
  }

  /**
   * 格式化量的输出，不显示小数 Function Name: formatAmount
   * 
   * @param amount
   * @return description: Modification History:
   */
  public static String formatAmount(BigDecimal amount) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(0);
    nf.setGroupingUsed(false);
    return nf.format(amount);
  }

  /**
   * 根据小数位数格式化数字，强制显示val位小数位.不四舍五入 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatBigDecimal(BigDecimal number, int val, boolean isForceMax) {
    DecimalFormat nf = new DecimalFormat();
    nf.setMaximumFractionDigits(val);
    if (isForceMax)
      nf.setMinimumFractionDigits(val);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化数字，强制显示val位小数位.四舍五入,千分符显示 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatBigDecimal(BigDecimal number, int val) {
    DecimalFormat nf = new DecimalFormat();
    nf.setMaximumFractionDigits(val);
    nf.setMinimumFractionDigits(val);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    nf.setGroupingUsed(true);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化数字，强制显示val位小数位d. 四舍五入处理 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumber(double number, int val) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setMinimumFractionDigits(val);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 根据设置格式化数字，val为最多的小数位数，末尾不自动补0 Function Name: formatNumberByMaxFrac
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumberByMaxFrac(double number, int val) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化数字，强制显示val位小数位,千分符显示 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumberUseGrp(BigDecimal number, int val) {
    if (number == null)
      return "";
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setMinimumFractionDigits(val);
    nf.setGroupingUsed(true);
    return nf.format(number);
  }

  /**
   * 根据设置格式化数字，val为最多的小数位数，末尾不自动补0,千分符显示 Function Name: formatNumberByMaxFrac
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumberByMaxFracUseGrp(BigDecimal number, int val) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setGroupingUsed(true);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化数字，val为最多的小数位数，末尾不自动补0, Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumberByMaxFrac(BigDecimal number, int val) {
    if (number == null)
      return "";
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化数字，强制显示val位小数位 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatNumber(BigDecimal number, int val) {
    if (number == null)
      return "";
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setMinimumFractionDigits(val);
    return nf.format(number);
  }

  /**
   * 根据小数位数格式化Imix数字，至少显示val位小数位 Function Name: formatNumber
   * 
   * @param number
   * @param val
   * @return description: Modification History:
   */
  public static String formatImixNumber(double number, int val) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(val);
    nf.setMinimumFractionDigits(val);
    nf.setGroupingUsed(false);
    return nf.format(number);
  }

  /**
   * 获取UTF8编码下字符的长度 Function Name: getStrLength
   * 
   * @param str
   * @return description: Modification History:
   */
  public static int getStrLengthInUtf8(String str) {
    return getStrLength(str, "UTF-8");
  }

  /**
   * 根据字符集获取字符长度 Function Name: getStrLength
   * 
   * @param str
   * @param charset
   * @return description: Modification History:
   */
  private static int getStrLength(String str, String charset) {
    int length = 0;
    try {
      length = str.getBytes(charset).length;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return length;
  }

  /**
   * 根据最大长度以utf8格式截取字符串 Function Name: cuteStrByUTF8
   * 
   * @param str
   * @return description: Modification History:
   */
  public static String cutStrByUTF8(String str, int maxSize) {
    String utf8Str = "";
    try {
      byte[] tmp = str.getBytes("UTF-8");
      if (maxSize < tmp.length)
        utf8Str = new String(tmp, 0, maxSize, "UTF-8");
      else
        utf8Str = new String(tmp, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return utf8Str;
  }

  /**
   * 将字符串转换为byte数组
   * 
   * @param str
   * @return
   */
  public static byte[] getByteInStr(String str) {
    if (isEmpty(str))
      return null;
    try {
      return str.getBytes("UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Function Name: getStrFromByte 将数组转换为字符串
   * 
   * @param b
   * @return
   */
  public static String getStrFromByte(byte[] b) {
    if (null == b || b.length == 0)
      return "";
    try {
      return new String(b, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Function Name: isBlank 判断字符串是否为空（包含空格）
   * 
   * @param str
   * @return
   */
  public static boolean isBlank(String str) {
    return StringUtils.isBlank(str);
  }

  /**
   * Function Name: isEmpty 判断字符串是否为空（不包含空格）
   * 
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return StringUtils.isEmpty(str);
  }

  /**
   * 手工转码，用于后台response中写回中文等场景
   * 
   * @param url
   * @return
   */
  public static String encodeUrl(String url) {
    try {
      return new String(url.getBytes("GBK"), "ISO8859-1");
      // return URLEncoder.encode(url,"GBK");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Function Name: formatNumberStr Description： 数值字符串转为BigDecimal，按照保留精度，四舍五入
   * 
   * @param numberStr
   *          数值字符串
   * @param scale
   *          保留小数位数（四舍五入）
   * @return BigDecimal
   */
  public static BigDecimal formatNumberStr(String numberStr, Integer scale) {
    BigDecimal bigVal = null;
    try {
      numberStr = uncomma(numberStr);
      bigVal = new BigDecimal(numberStr);
      if (null == scale)
        return bigVal;
      return bigVal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 将lcm2库中ISO编码转为GBK，防止中文乱码 Function Name :
   * 
   * @param str
   * @return Description : Modification History :
   */
  public static String getGBKString(String str) {
    try {
      if (!StringUtils.isEmpty(str))
        return new String(str.getBytes("ISO-8859-1"), "GBK");
      else
        return "";
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  // @Author XWQ begin

  /**
   * 判断字符串是否以特定的字符串结尾
   * 
   * @param str
   *          字符串
   * @param endStr
   *          结束字符串
   * @return 包含：true 不包含：false
   */
  public static Boolean isEndWithString(String str, String endStr) {
    return str.endsWith(endStr);
  }

  /**
   * 判断字符是否是中文字符
   * 
   * @param c
   *          字符
   * @return
   */
  /*
   * public static boolean isChinese(char c) { Character.UnicodeBlock ub =
   * Character.UnicodeBlock.of(c); if (ub ==
   * Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub ==
   * Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub ==
   * Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub ==
   * Character.UnicodeBlock.GENERAL_PUNCTUATION || ub ==
   * Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub ==
   * Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) { return true; }
   * return false; }
   * 
   * public static boolean isChinese(String s) { char[] ch = s.toCharArray();
   * for (int i = 0; i < ch.length; i++) { if (isChinese(ch[i])) { return true;
   * } } return false; }
   */

  /**
   * 判断字符串是否含中文字符
   * 
   * @param str
   *          字符串
   * 
   * @return
   */
  public static boolean isChinese(String str) {
    String regEx = "[\u4e00-\u9fa5\uFF00-\uFFFF]";

    Pattern pattern = Pattern.compile(regEx);

    Matcher matcher = pattern.matcher(str);

    if (matcher.find()) {
      return true;
    }

    return false;
  }

  /**
   * 判断是否是数字
   * 
   * @param str
   * @return
   */
  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    int sz = str.length();
    for (int i = 0; i < sz; ++i) {
      if ('.' == str.charAt(i)) {
        continue;
      }
      if (!(Character.isDigit(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

  /***
   * 字符串中只包含数字
   */
  public static boolean isDigital(String str) {
    try {
      Long.parseLong(str);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 功能描述：统计字符串的长度，中文占两个字节，其他即英文占一个字节
   * 
   * @param str
   * @return
   */
  public static int getStrLength(String str) {
    int len = 0;
    for (int i = 0; i < str.length(); i++) {
      String s = str.substring(i, i + 1);
      String reg = "[^[\u4E00-\u9FA5\uFF00-\uFFFF]$]";
      if (s.matches(reg)) {
        len = len + 2;
      } else {
        len = len + 1;
      }
    }

    return len;
  }

  /**
   * 将数字转换成汉字大写
   * 
   * @param numStr
   *          两位小数的字符串数字，如：11200.00
   * @return 壹万壹仟贰佰圆整
   */
  public static String num2Chinese(String numStr) {
    boolean isNegative = false;
    if (numStr.substring(0, 1).equals("-")) {
      isNegative = true;
      numStr = numStr.substring(1);
    }

    String CNY_CH[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    String CNY_UNIT[] = { "圆", "万", "亿", "兆" };

    String partA;
    String partB;
    if (numStr.indexOf(".") != -1) {
      partA = numStr.substring(0, numStr.indexOf("."));
      partB = numStr.substring(numStr.indexOf(".") + 1);
      if (partB.length() == 1)
        partB = partB + "0";
      if (partB.length() == 0)
        partB = partB + "00";
    } else {
      partA = numStr;
      partB = "";
    }
    if (partA.length() % 4 != 0) {
      int len = 4 - partA.length() % 4;
      for (int i = 0; i < len; i++)
        partA = "0" + partA;

    }
    String retString = "";
    boolean zFlag = false;
    boolean zzFlag = false;
    if (!partA.equals("0000")) {
      for (int i = 0; i < partA.length() / 4; i++) {
        String part = partA.substring(i * 4, i * 4 + 4);
        String tranOut = "";
        String flag = "";
        for (int j = 0; j < 4; j++) {
          if (Integer.parseInt(part.substring(j, j + 1)) == 0) {
            flag = flag + "0";
            if (j == 0) {
              zzFlag = true;
            }
          } else {
            flag = flag + "1";
            zzFlag = false;
          }
        }
        String buffer = "";
        if (Integer.parseInt(part.substring(0, 1)) == 0) {
          if (i != 0)
            buffer = "零";
        } else {
          if (zFlag) {
            tranOut = tranOut + "零";
          } else if (zzFlag) {
            System.out.println("2");
            tranOut = tranOut + "零";
          }
          tranOut = tranOut + CNY_CH[Integer.parseInt(part.substring(0, 1))] + "仟";
        }
        zFlag = false;
        if (Integer.parseInt(part.substring(1, 2)) == 0) {
          if (flag.substring(0, 1).equals("1") && buffer.equals(""))
            buffer = "零";
        } else {
          tranOut = tranOut + buffer + CNY_CH[Integer.parseInt(part.substring(1, 2))] + "佰";
          buffer = "";
        }
        if (Integer.parseInt(part.substring(2, 3)) == 0) {
          if (flag.substring(1, 2).equals("1") && buffer.equals("")) {
            buffer = "零";
          }
        } else {
          tranOut = tranOut + buffer + CNY_CH[Integer.parseInt(part.substring(2, 3))] + "拾";
          buffer = "";
        }
        if (Integer.parseInt(part.substring(3, 4)) != 0) {
          tranOut = tranOut + buffer + CNY_CH[Integer.parseInt(part.substring(3, 4))];
        }
        if (flag.equals("0000") && i != partA.length() / 4 - 1) {
          zFlag = true;
        } else {
          tranOut = tranOut + CNY_UNIT[partA.length() / 4 - i - 1];
        }
        retString = retString + tranOut;
        if (retString.substring(retString.length() - 2).equals("零圆")) {
          retString = retString.substring(0, retString.length() - 2) + "圆";
        }
      }

    }
    if (partB.length() == 0) {
      retString = retString + "整";
    } else {
      String buffer = "";
      if (!partB.substring(0, 1).equals("0")) {
        retString = retString + CNY_CH[Integer.parseInt(partB.substring(0, 1))] + "角";
      } else if (retString.length() > 0) {
        buffer = "零";
      }
      if (!partB.substring(1, 2).equals("0"))
        retString = retString + buffer + CNY_CH[Integer.parseInt(partB.substring(1, 2))] + "分";
      else
        retString = retString + "整";
    }

    if (isNegative) {
      retString = "负" + retString;
    }

    return retString;
  }

  // 获取数字的大写形式
  public static String getUpper(int i) {
    switch (i) {
    case 1:
      return "一";
    case 2:
      return "二";
    case 3:
      return "三";
    case 4:
      return "四";
    case 5:
      return "五";
    case 6:
      return "六";
    case 7:
      return "七";
    case 8:
      return "八";
    case 9:
      return "九";
    case 10:
      return "十";
    case 11:
      return "十一";
    case 12:
      return "十二";
    case 13:
      return "十三";
    default:
      return Integer.toString(i);
    }
  }

  public static String uncomma(String tradeFeeSum) {
    return tradeFeeSum.replace(",", "");
  }

  public static boolean isNull(String str) {
    if (str == null || str.trim().length() == 0) {
      return true;
    }
    return false;
  }

  public static String lowerFirstChar(String entityJavaName) {

    char[] chars = new char[1];
    String str = entityJavaName;
    chars[0] = str.charAt(0);
    String temp = new String(chars);
    if (chars[0] >= 'A' && chars[0] <= 'Z') {// 当为字母时，则转换为小写
      str = str.replaceFirst(temp, temp.toLowerCase());
    }

    return str;
  }

  public static String upperFirstChar(String entityJavaName) {
    char[] chars = new char[1];
    String str = entityJavaName;
    chars[0] = str.charAt(0);
    String temp = new String(chars);
    if (chars[0] >= 'a' && chars[0] <= 'z') {// 当为字母时，则转换为大写
      str = str.replaceFirst(temp, temp.toUpperCase());
    }

    return str;
  }
}
