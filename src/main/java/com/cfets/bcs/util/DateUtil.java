package com.cfets.bcs.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author XWQ
 * 
 */
public class DateUtil {

  // three comman date format
  public static final String dashFormat = new String("yyyy-MM-dd");
  public static final String slashForamt = new String("yyyy/MM/dd");
  public static final String ymdFormat = new String("yyyyMMdd");
  public static final String timeFormat = new String("HH:mm:ss");
  public static final String dateTimeFormat = new String("yyyy-MM-dd HH:mm:ss");
  public static final String ignoreSecondFormat = new String("yyyy-MM-dd HH:mm");
  public static final String datetimemarkFormat = new String("yyyyMMddHHmmss"); // 一般用于生成时间戳

  public static final long M_PER_DAY = 1000 * 60 * 60 * 24;

  public static final String[] seasonStartDate = new String[] { "01", "02", "03", "04" };

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * 根据给定的格式对时间类型格式化
   * 
   * @param date
   *          日期
   * @param formatString
   *          日期表达式
   * @return description: Modification History:
   */
  public static String getDateString(Date date, String formatString) {
    try {
      return (date != null) ? new SimpleDateFormat(formatString).format(date) : "";
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * get the default dateString with style "yyyy-MM-dd" 将日期格式化成"yyyy-MM-dd"样式
   * 
   * @param date
   * @return
   */
  public static String getDateString(Date date) {
    return (date != null) ? DateFormatUtils.format(date, dashFormat) : "";
  }

  /**
   * 将日期格式化成"HH:mm:ss"样式
   * 
   * @param date
   * @return
   */
  public static String getTimeString(Timestamp time) {
    return (time != null) ? DateFormatUtils.format(time, timeFormat) : "";
  }

  /**
   * 将日期格式化成 "yyyy-MM-dd HH:mm:ss"样式
   * 
   * @param date
   * @return
   */
  public static String getDateTimeString(Timestamp time) {
    return (time != null) ? DateFormatUtils.format(time, dateTimeFormat) : "";
  }

  /**
   * get the default dateString with style "HH:mm:ss" 将日期格式化成"HH:mm:ss"样式
   * 
   * @param date
   * @return
   */
  public static String getTimeString(String timeStamp) {
    String hour = "", minute = "", second = "";
    try {

      long b = Long.parseLong(timeStamp);
      b = b % Long.parseLong("86400000");// 截取时间hh:mm:ss的毫秒数
      long hour_long = b / Long.parseLong("3600000");
      b = b % Long.parseLong("3600000");
      long mimute_long = b / 60000;
      b = b % 60000;
      long second_long = b / 1000;
      if (hour_long < 10)
        hour = "0" + hour_long;
      else
        hour = "" + hour_long;

      if (mimute_long < 10)
        minute = "0" + mimute_long;
      else
        minute = "" + mimute_long;

      if (second_long < 10)
        second = "0" + second_long;
      else
        second = "" + second_long;

      return hour + ":" + minute + ":" + second;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timeStamp;
  }

  /**
   * get the default dateString with style "yyyy-MM-dd HH:mm:ss" 将日期格式化成
   * "yyyy-MM-dd HH:mm:ss"样式
   * 
   * @param date
   * @return
   */
  public static String getDateTimeString(Date date) {
    return (date != null) ? DateFormatUtils.format(date, dateTimeFormat) : "";
  }

  /**
   * get the default dateString with style "yyyy-MM-dd HH:mm:ss" 将日期格式化成
   * "yyyy-MM-dd HH:mm:ss"样式
   * 
   * @param date
   * @return
   */
  public static String getTimeString(Time time) {
    return (time != null) ? DateFormatUtils.format(time, timeFormat) : "";
  }

  /**
   * use the default date format "yyyy-MM-dd" 将"yyyy-MM-dd"格式的字符串转换为日期格式
   * 
   * @param dStr
   * @return
   */
  public static Date getDate(String dStr) throws ParseException {
    return (StringUtils.isEmpty(dStr)) ? null : getDate(dStr, dashFormat);
  }

  /**
   * timestamp 2 date
   * 
   * @param dStr
   * @return
   */
  public static Date getDate(Timestamp timestamp) {
    Date date = null;
    try {
      date = timestamp;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return date;
  }

  /**
   * use the default date format "yyyy-MM-dd HH:mm:ss" 将"yyyy-MM-dd HH:mm:ss"
   * 格式的字符串转换为日期格式
   * 
   * @param dStr
   * @return
   */
  public static Date getDateTime(String dStr) throws ParseException {
    return (StringUtils.isEmpty(dStr)) ? null : getDate(dStr, dateTimeFormat);
  }

  /**
   * 根据字符串、字符串格式转换为日期
   * 
   * @param dStr
   *          字符串
   * @param formatString
   *          日期表达式
   * @return description: Modification History:
   * @throws ParseException
   */
  public static Date getDate(String dStr, String formatString) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat(formatString);
    format.setLenient(false);
    return format.parse(dStr);
  }

  /**
   * 将时间格式HH：mm：ss拼接在日期中，主要供imix方法使用
   * 
   * @param date
   * @param time
   * @return
   */
  public static Date getDateTime(Date date, String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    sdf.setLenient(false);
    try {
      return sdf.parse(DateUtil.getDateString(date, DateUtil.ymdFormat) + " " + time);
    } catch (Exception e) {
      return date;
    }
  }

  /**
   * 将时间格式HH：mm：ss拼接在日期中，返回时间类型
   * 
   * @param date
   * @param time
   * @return
   */
  public static Timestamp getDateTimeRtnTime(Date date, String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Timestamp timeStamp = null;
    sdf.setLenient(false);
    try {
      String dateStr = sdf.format(date);
      timeStamp = Timestamp.valueOf(dateStr + " " + time);
    } catch (Exception e) {
    }
    return timeStamp;
  }

  /**
   * 获取给定日期当日的00:00:00时间戳，即去除日期中含有的时间数据
   * 
   * @param date
   * @return
   */
  public static Date getDateIgnoreTime(Date date) {
    GregorianCalendar gday = new GregorianCalendar();
    gday.setTime(date);
    gday.set(Calendar.HOUR, 0);
    gday.set(Calendar.HOUR_OF_DAY, 0);
    gday.set(Calendar.MINUTE, 0);
    gday.set(Calendar.SECOND, 0);
    gday.set(Calendar.MILLISECOND, 0);
    return gday.getTime();
  }

  /**
   * 获取当前日期
   */
  public static Date getCurrentDate() {
    return new Date();
  }

  /**
   * 获取当前时间
   */
  public static Timestamp getCurrentTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * convent method to get days after or before 根据天数偏移量计算日期
   * 
   * @param date
   * @param days
   * @return
   */
  public static Date getDateAfter(Date date, int days) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(GregorianCalendar.DATE, days);
    return calendar.getTime();
  }

  /**
   * convent method to get days after or before 根据年、月、日偏移量计算最终日期
   * 
   * @param date
   * @param year
   * @param month
   * @param days
   * @return
   */
  public static Date getDateAfter(Date date, int year, int month, int days) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(GregorianCalendar.YEAR, year);
    calendar.add(GregorianCalendar.MONTH, month);
    calendar.add(GregorianCalendar.DATE, days);
    return calendar.getTime();
  }

  /**
   * 计算两个日期间间隔的天数
   * 
   * @param startDate
   *          第一个日期
   * @param endDate
   *          相比较日期
   * @return
   */
  public static Long computeDateInterval(Date startDate, Date endDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    long startTime = calendar.getTimeInMillis();

    calendar.setTime(endDate);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    long endTime = calendar.getTimeInMillis();

    return (endTime - startTime) / M_PER_DAY;
  }

  /**
   * 获取日期中的年份数值
   * 
   * @param date
   * @return
   */
  public static int getYearForDate(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(GregorianCalendar.YEAR);
  }

  /**
   * 获取日期中的月份数值 calendar从 0 计数，所以需加一，才为实际所称月
   * 
   * @param date
   * @return
   */
  public static int getMonthForDate(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(GregorianCalendar.MONTH) + 1;
  }

  /**
   * 获取日期中的天数值
   * 
   * @param date
   * @return description: Modification History:
   */
  public static int getDayForDate(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    return calendar.get(GregorianCalendar.DATE);
  }

  /**
   * 校验日期格式是否正确
   * 
   * @param str
   * @param formatString
   * @return description: Modification History:
   */
  public static boolean checkDateValidity(String str, String formatString) {
    SimpleDateFormat sdf = new SimpleDateFormat(formatString);
    sdf.setLenient(false);
    try {
      sdf.parse(str);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  /**
   * 判断给定的日期是否是该月最后一天
   * 
   * @param date
   * @return
   */
  public static boolean isLastDayOfMonth(Date date) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    return (cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH));
  }

  /**
   * 校验某日是否在一段日期区间中
   * 
   * @param CompareDate
   *          待比较日期
   * @param date1
   *          前置日期
   * @param date2
   *          后置日期
   * @return
   */
  public static boolean isBetween(Date CompareDate, Date date1, Date date2) {
    if (date2.before(date1)) {
      Date tmp = date1;
      date1 = date2;
      date2 = tmp;
    }
    return !(CompareDate.before(date1) || CompareDate.after(date2));
  }

  /**
   * 日期比较函数
   * 
   * @param date1
   * @param date2
   * @return 比较两个日期的先后, date1>date2返回1， date1==date2返回0, date1<date2返回-1,
   *         date1,date2数据错误返回-2 Modification History:
   */
  public static int compareDate(Date date1, Date date2) {
    if (date1 == null || date2 == null)
      return -2;
    long temp = java.util.TimeZone.getDefault().getRawOffset();
    long result = (date2.getTime() + temp) / M_PER_DAY - (date1.getTime() + temp) / M_PER_DAY;
    if (result > 0) {
      return -1;
    } else if (result < 0) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 校验时间字符是否符合逻辑 HH:mm格式
   * 
   * @param str
   * @return
   */
  public static boolean checkTimeHHmmPattern(String str) {
    Pattern p = Pattern.compile("^([0-1]\\d|2[0-3]):[0-5]\\d$");
    Matcher m = p.matcher(str);
    return m.matches();
  }

  /**
   * 校验时间字符是否符合逻辑 yyyy-MM-dd格式
   * 
   * @param str
   * @return
   */
  public static boolean checkDayYYYYmmDDPattern(String str) {
    String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]"
        + "|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1]"
        + "[0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|"
        + "[3579][26])00))-02-29)";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(str);
    return m.matches();
  }

  /**
   * 新增代码 功能描述:相对应的展示日的对应英文格式：st,nd,rd,th
   * 
   * @param date
   *          需要转换的日期：20150101
   * @param pattern
   *          对应的样式：yyyyMMdd
   * @return 15th,Feb,2015
   */
  public static String toDateDayTh(String date, String pattern) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat("d", Locale.ENGLISH);
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      if (date.endsWith("1") && !date.endsWith("11")) {
        sdfEn = new SimpleDateFormat("d'st',MMM,yyyy", Locale.ENGLISH);
      } else if (date.endsWith("2") && !date.endsWith("12")) {
        sdfEn = new SimpleDateFormat("d'nd',MMM,yyyy", Locale.ENGLISH);
      } else if (date.endsWith("3") && !date.endsWith("13")) {
        sdfEn = new SimpleDateFormat("d'rd',MMM,yyyy", Locale.ENGLISH);
      } else {
        sdfEn = new SimpleDateFormat("d'th',MMM,yyyy", Locale.ENGLISH);
      }
      Date day = sdf.parse(date);
      value = sdfEn.format(day);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 新增代码 功能描述:相对应的展示日的对应英文格式：st,nd,rd,th，并作为数字的上标
   * 
   * @param date
   *          需要转换的日期：20150101
   * @param pattern
   *          对应的样式：yyyyMMdd
   * @return 15<sup>th</sup>,Feb,2015
   */
  public static String toDateDaySupTh(String date, String pattern) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat("d", Locale.ENGLISH);
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      if (date.endsWith("1") && !date.endsWith("11")) {
        sdfEn = new SimpleDateFormat("d'st',MMM,yyyy", Locale.ENGLISH);
      } else if (date.endsWith("2") && !date.endsWith("12")) {
        sdfEn = new SimpleDateFormat("d'nd',MMM,yyyy", Locale.ENGLISH);
      } else if (date.endsWith("3") && !date.endsWith("13")) {
        sdfEn = new SimpleDateFormat("d'rd',MMM,yyyy", Locale.ENGLISH);
      } else {
        sdfEn = new SimpleDateFormat("d'th',MMM,yyyy", Locale.ENGLISH);
      }
      Date day = sdf.parse(date);
      value = sdfEn.format(day);

      value = toDateDaySupTh(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 新增代码 功能描述:相对应的展示日的对应英文格式：st,nd,rd,th，并作为数字的上标
   * 
   * @param date
   *          需要转换的日期：20150101
   * @param pattern
   *          对应的样式：yyyyMMdd
   * @return 15<sup>th</sup>,Feb,2015
   */
  public static String toDateDaySupTh(Date date) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat("d", Locale.ENGLISH);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      if (day == 1 && day != 11) {
        sdfEn = new SimpleDateFormat("d'st',MMM,yyyy", Locale.ENGLISH);
      } else if (day == 2 && day != 12) {
        sdfEn = new SimpleDateFormat("d'nd',MMM,yyyy", Locale.ENGLISH);
      } else if (day == 3 && day != 13) {
        sdfEn = new SimpleDateFormat("d'rd',MMM,yyyy", Locale.ENGLISH);
      } else {
        sdfEn = new SimpleDateFormat("d'th',MMM,yyyy", Locale.ENGLISH);
      }
      value = sdfEn.format(date);
      value = toDateDaySupTh(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 将15th,Feb,2015类似的英文日期的st,nd,rd,th，作为上标返回
   * 
   * @param enDateStr
   *          15th,Feb,2015、1st,Feb,2015
   * @return
   */
  public static String toDateDaySupTh(String enDateStr) {

    boolean isNum = StringUtil.isDigital(enDateStr.substring(0, 2));
    if (isNum) {
      enDateStr = enDateStr.substring(0, 2) + "<sup>" + enDateStr.substring(2, 4) + "</sup>"
          + enDateStr.substring(4, enDateStr.length());
    } else {
      enDateStr = enDateStr.substring(0, 1) + "<sup>" + enDateStr.substring(1, 3) + "</sup>"
          + enDateStr.substring(3, enDateStr.length());
    }

    return enDateStr;
  }

  /**
   * 将15th,Feb,2015类似的英文日期的st,nd,rd,th，作为上标返回
   * 
   * @param enDateStr
   *          15th,Feb,2015、1st,Feb,2015
   * @return 转换后的字符串
   */
  public static String toDateDaySupThString(String dateString) {
    // 匹配英文日期，修改为上标的形式：st/nd/rd/th
    Pattern pattern = Pattern.compile("[0-9]{1,2}((st)|(nd)|(rd)|(th)),[a-zA-Z]{3},[0-9]{4}");
    Matcher matcher = pattern.matcher(dateString);

    while (matcher.find()) {
      String sub = matcher.group();
      int index = dateString.indexOf(sub);

      dateString = dateString.substring(0, index + 1) + DateUtil.toDateDaySupTh(sub.substring(1))
          + dateString.subSequence(index + sub.length(), dateString.length());

      matcher = pattern.matcher(dateString);
    }

    return dateString;
  }

  /**
   * 功能描述：通知单中将日期翻译成指定的英文格式
   * 
   * @param date
   * @param cnDateType
   *          :指定格式
   * @param enDateType
   *          :英文指定的格式
   * @return Dec.2014
   */
  public static String toDateEnMonth(String date, String pattern) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat("MMM.yyyy", Locale.ENGLISH);
      SimpleDateFormat sdfCn = new SimpleDateFormat(pattern);
      value = sdfEn.format(sdfCn.parse(date));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 功能描述：通知单中将日期翻译成指定的英文格式
   * 
   * @param date
   * @param pattern1
   * @param pattern2
   * @return
   */
  public static String toDateEn(String date, String pattern, String patternEN) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat(patternEN, Locale.ENGLISH);
      SimpleDateFormat sdfCn = new SimpleDateFormat(pattern);
      value = sdfEn.format(sdfCn.parse(date));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 功能描述：通知单中将日期翻译成指定的英文格式
   * 
   * @param date
   * @param patternCN
   * @param patternEN
   * @return
   */
  public static String toDateEn(Date date, String patternEN) {
    String value = "";
    try {
      SimpleDateFormat sdfEn = new SimpleDateFormat(patternEN, Locale.ENGLISH);
      value = sdfEn.format(date);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }

  /***
   * 返回指定分隔符的时间字符串
   * 
   * @param dateString
   *          20150108
   * @param splitor
   *          - 或 年月日
   * @return 2015-01-08 2015年01月08日
   */
  public static String addSplit(String dateString, String splitor) {
    if (dateString.length() != 8) {
      return dateString;
    }

    int len = splitor.length();

    if (len == 1) {
      dateString = dateString.substring(0, 4) + splitor + dateString.substring(4, 6) + splitor
          + dateString.substring(6, 8);
    } else if (len == 2) {
      dateString = dateString.substring(0, 4) + splitor.substring(0, 1) + dateString.substring(4, 6)
          + splitor.substring(1, 2) + dateString.substring(6, 8);
    } else if (len == 3) {
      dateString = dateString.substring(0, 4) + splitor.substring(0, 1) + dateString.substring(4, 6)
          + splitor.substring(1, 2) + dateString.substring(6, 8) + splitor.substring(2, 3);
    }

    return dateString;
  }

  /**
   * 传入起始年份、起始季度、截止年份、截止季度，返回该区间所有的批次号集合
   * 
   * @param startYear
   * @param endYear
   * @param startSeason
   * @param endSeason
   * @return
   */
  public static List<String> getCalListFromSeason(Integer startYear, Integer endYear, Integer startSeason,
      Integer endSeason) {
    try {
      List<String> calList = new ArrayList<String>();

      for (int season = startYear * 4 + startSeason - 1; season <= endYear * 4 + endSeason - 1; ++season) {
        int year = season / 4;
        int seasonOfYear = season % 4;
        calList.add(Integer.toString(year) + seasonStartDate[seasonOfYear]);
      }
      return calList;

    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<String>();
    }
  }

  /**
   * 
   * @param date
   * @return
   */
  public static Date parseDate(String date) {
    if (StringUtils.isBlank(date)) {
      return null;
    }

    try {
      return dateFormat.parse(date);
    } catch (ParseException ex) {
      return null;
    }
  }

  /**
   * 
   * @param str
   * @return
   */
  public static Integer parseInt(String str) {
    if (StringUtils.isBlank(str)) {
      return null;
    }

    try {
      return Integer.valueOf(str);
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  /**
   * 获取date当月第一天
   * 
   * @param date
   * @return
   */
  public static Date getMonthFirstDate(Date date) {
    Date firstDate = null;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    firstDate = calendar.getTime();
    return firstDate;
  }

  /**
   * 获取date当月最后一天
   * 
   * @param date
   * @return
   */
  public static Date getMonthLastDate(Date date) {
    Date firstDate = null;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    firstDate = calendar.getTime();
    return firstDate;
  }

  /**
   * 获取两个日期之间的天数
   * 
   * @param strtDt
   * @param endDt
   * @return
   */
  public static int getDaysBetween(Date strtDt, Date endDt) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(strtDt);
    long strtTimeMillis = calendar.getTimeInMillis();

    calendar.setTime(endDt);
    long endTimeMillis = calendar.getTimeInMillis();

    long betweenDays = (endTimeMillis - strtTimeMillis) / (1000 * 3600 * 24);

    return Integer.parseInt(String.valueOf(betweenDays));

  }

  /**
   * 获取两个日期之间的月数差
   * 
   * @param strtDt
   * @param endDt
   * @return
   */
  public static int getMnthsBetween(Date strtDt, Date endDt) {
    Calendar clndr = Calendar.getInstance();
    clndr.setTime(endDt);
    int endMnth = clndr.get(Calendar.MONTH);
    clndr.setTime(strtDt);
    int strtMnth = clndr.get(Calendar.MONTH);
    int intrvlMnth = endMnth - strtMnth;
    return intrvlMnth + 1;

  }

  /**
   * 获取两个日期之间的月数
   * 
   * @param strtDt
   * @param endDt
   * @return
   */
  public static int getMonthsBetween(Date strtDt, Date endDt) {
    Calendar strt = Calendar.getInstance();
    strt.setTime(strtDt);
    Calendar end = Calendar.getInstance();
    end.setTime(endDt);

    int betweenMonths = end.get(Calendar.MONTH) - strt.get(Calendar.MONTH);

    return betweenMonths;

  }

  /**
   * 根据传入日期，返回所属季度第一天
   * 
   * @param dt
   * @return
   */
  public static Date getSnFrstDay(Date dt) {
    Date snFrstDay = null;
    Calendar clndr = Calendar.getInstance();
    clndr.setTime(dt);
    int mnth = clndr.get(Calendar.MONTH);
    int snMnth = -1;
    if (mnth >= 0 && mnth <= 2) {
      snMnth = 0;
    } else if (mnth >= 3 && mnth <= 5) {
      snMnth = 3;
    } else if (mnth >= 6 && mnth <= 8) {
      snMnth = 6;
    } else if (mnth >= 9 && mnth <= 11) {
      snMnth = 9;
    } else {
      snMnth = -1;
    }
    clndr.set(Calendar.MONTH, snMnth);
    clndr.set(Calendar.DAY_OF_MONTH, clndr.getActualMinimum(Calendar.DAY_OF_MONTH));
    clndr.set(Calendar.HOUR_OF_DAY, clndr.getActualMinimum(Calendar.DAY_OF_MONTH));
    clndr.set(Calendar.MINUTE, clndr.getActualMinimum(Calendar.MINUTE));
    clndr.set(Calendar.SECOND, clndr.getActualMinimum(Calendar.SECOND));
    snFrstDay = clndr.getTime();
    return snFrstDay;
  }

  /**
   * 替换掉日期字符串中的特殊字符
   * 
   * @param dateStr
   * @return
   */
  public static String replaceSpecialChar(String dateStr) {
    if (dateStr == null) {
      return dateStr;
    }

    return dateStr.replaceAll("\\W", "");
  }

  /**
   * 获取当前日期月份的季度数 根据当前给出的时间,判断当前的所属季度
   * 
   * @param date
   * @return
   */
  public static String getCurrentSeson(String date) {
    String value = "";
    try {
      int number = Integer.parseInt(date.trim());
      switch (number) {
      case 1:
      case 2:
      case 3:
        value = "1";
        break;
      case 4:
      case 5:
      case 6:
        value = "2";
        break;
      case 7:
      case 8:
      case 9:
        value = "3";
        break;
      case 10:
      case 11:
      case 12:
        value = "4";
        break;
      default:
        value = "1";// 默认第一季
        break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  /**
   * 获取月份的季度数。根据给出的月份,判断其所属季度
   * 
   * @param month
   * @return
   */
  public static String getSeason(String month) {
    String value = "";
    try {
      if (month.startsWith("0")) {
        month = month.substring(1);
      }
      int number = Integer.parseInt(month);
      switch (number) {
      case 1:
      case 2:
      case 3:
        value = "1";
        break;
      case 4:
      case 5:
      case 6:
        value = "2";
        break;
      case 7:
      case 8:
      case 9:
        value = "3";
        break;
      case 10:
      case 11:
      case 12:
        value = "4";
        break;
      default:
        value = "1";// 默认第一季
        break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  /**
   * 获得季度起始日期
   * 
   * @param season
   * @param seperator
   * @return
   */
  public static String getStartDateOfSeason(String season, String seperator) {
    String value = "";
    int seasonNum = Integer.parseInt(season);

    switch (seasonNum) {
    case 1:
      value = "01" + seperator + "01";
      break;
    case 2:
      value = "04" + seperator + "01";
      break;
    case 3:
      value = "07" + seperator + "01";
      break;
    case 4:
      value = "10" + seperator + "01";
      break;

    default:
      value = "01" + seperator + "01";// 默认第一季
      break;
    }

    return value;
  }

  /**
   * 获得季度结束日期
   * 
   * @param season
   * @param seperator
   * @return
   */
  public static String getEndDateOfSeason(String season, String seperator) {
    String value = "";
    int seasonNum = Integer.parseInt(season);

    switch (seasonNum) {
    case 1:
      value = "03" + seperator + "31";
      break;
    case 2:
      value = "06" + seperator + "30";
      break;
    case 3:
      value = "09" + seperator + "30";
      break;
    case 4:
      value = "12" + seperator + "31";
      break;

    default:
      value = "03" + seperator + "31";// 默认第一季
      break;
    }

    return value;
  }

  /**
   * 获得季度截止日期
   * 
   * @param season
   * @param seperator
   * @return 20150101
   */
  public static String getEndDateOfYearSeason(String year, String season) {
    String value = "";
    int seasonNum = Integer.parseInt(season);

    switch (seasonNum) {
    case 1:
      value = year + "0331";
      break;
    case 2:
      value = year + "0630";
      break;
    case 3:
      value = year + "0930";
      break;
    case 4:
      value = year + "1231";
      break;

    default:
      value = year + "0331";// 默认第一季
      break;
    }

    return value;
  }

  /**
   * 获得季度起始日期
   * 
   * @param season
   * @param seperator
   * @return 20150101
   */
  public static String getStartDateOfYearSeason(String year, String season) {
    String value = "";
    int seasonNum = Integer.parseInt(season);

    switch (seasonNum) {
    case 1:
      value = year + "0101";
      break;
    case 2:
      value = year + "0401";
      break;
    case 3:
      value = year + "0701";
      break;
    case 4:
      value = year + "1001";
      break;

    default:
      value = year + "0101";// 默认第一季
      break;
    }

    return value;
  }

  /**
   * 获取上季度和本季度的中文名
   * 
   * @param dateString
   * @return
   */
  public static String[] getLastSeasonCN(String dateString, String seperator) {
    String retString[] = new String[2];
    String month = null;
    String year = null;
    if (seperator.isEmpty()) {
      year = dateString.substring(0, 4);
      month = dateString.substring(4, 6);
    }

    String season = getSeason(month);
    if (season.equals("1")) {
      retString[0] = (Integer.parseInt(year) - 1) + "年第四季度";
      retString[1] = year + "年第一季度";
    } else if (season.equals("2")) {
      retString[0] = year + "年第一季度";
      retString[1] = year + "年第二季度";
    } else if (season.equals("3")) {
      retString[0] = year + "年第二季度";
      retString[1] = year + "年第三季度";
    } else if (season.equals("4")) {
      retString[0] = year + "年第三季度";
      retString[1] = year + "年第四季度";
    }

    return retString;
  }

  /**
   * 获取上季度和本季度英文名
   * 
   * @param dateString
   * @return
   */
  public static String[] getLastSeasonEN(String dateString, String seperator) {
    String retString[] = new String[2];
    String month = null;
    String year = null;
    if (seperator.isEmpty()) {
      year = dateString.substring(0, 4);
      month = dateString.substring(4, 6);
    }

    String season = getSeason(month);
    if (season.equals("1")) {
      retString[0] = "The fourth quarter of " + (Integer.parseInt(year) - 1);
      retString[1] = "The first quarter of " + year;
    } else if (season.equals("2")) {
      retString[0] = "The first quarter of " + year;
      retString[1] = "The second quarter of " + year;
    } else if (season.equals("3")) {
      retString[0] = "The second quarter of " + year;
      retString[1] = "The third quarter of " + year;
    } else if (season.equals("4")) {
      retString[0] = "The third quarter of " + year;
      retString[1] = "The fourth quarter of " + year;
    }

    return retString;
  }

  /**
   * 获取年份季度
   * 
   * @param date
   * @return 2015年第1季度
   */
  public static String getYearSeasonCN(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    String year = String.valueOf(calendar.get(Calendar.YEAR));
    String season = getSeason(String.valueOf(calendar.get(Calendar.MONTH) + 1));

    return year + "年第" + season + "季度";
  }

  /**
   * 获取年份季度
   * 
   * @param dateString
   *          20150105
   * @param pattern
   *          yyyyMMdd
   * @return 2015年第1季度
   */
  public static String getYearSeasonCN(String dateString, String pattern) {

    String value = "";
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      Date date = sdf.parse(dateString);
      value = getYearSeasonCN(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return value;
  }

  /**
   * 根据季度开始日期，获取季度的年月
   * 
   * @param seasonStartDate
   *          季度开始日期
   * @return Jan,2015
   */
  public static String[] getSeasonMonthsCN(Date seasonStartDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(seasonStartDate);

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;

    String[] dateStrs = new String[3];

    dateStrs[0] = year + "年" + month + "月";
    dateStrs[1] = year + "年" + (month + 1) + "月";
    dateStrs[2] = year + "年" + (month + 2) + "月";
    return dateStrs;
  }

  /**
   * 根据季度开始日期，获取季度的年月
   * 
   * @param seasonStartDate
   *          季度开始日期
   * @return Jan,2015、Feb,2015、Mar,2015
   */
  public static String[] getSeasonMonthsEN(Date seasonStartDate) {
    String[] dateStrs = getSeasonMonths(seasonStartDate);

    for (int i = 0; i < dateStrs.length; i++) {
      dateStrs[i] = toDateEn(dateStrs[i], "yyyyMM", "MMM,yyyy");
    }

    return dateStrs;
  }

  /**
   * 根据季度开始日期，获取季度的年月
   * 
   * @param seasonStartDate
   * @return 201501、201502、201503
   */
  public static String[] getSeasonMonths(Date seasonStartDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(seasonStartDate);

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;

    String[] dateStrs = new String[3];

    String monthString = String.valueOf(month);
    if (month < 10) {
      monthString = "0" + month;
    }
    dateStrs[0] = year + monthString + "";

    month++;
    monthString = String.valueOf(month);
    if (month < 10) {
      monthString = "0" + month;
    }
    dateStrs[1] = year + monthString + "";

    month++;
    monthString = String.valueOf(month);
    if (month < 10) {
      monthString = "0" + month;
    }
    dateStrs[2] = year + monthString + "";
    return dateStrs;
  }

  /**
   * 判断起始、截止日是否在同一年，且为1月-12月
   * 
   * @param startDate
   * @param endDate
   * @return
   */
  public static boolean isFullYear(Date startDate, Date endDate) {
    String pattern = "yyyy年MM月";
    String startDateString = DateUtil.getDateString(startDate, pattern);
    String endDateString = DateUtil.getDateString(endDate, pattern);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);

    int startYear = calendar.get(Calendar.YEAR);
    calendar.setTime(endDate);
    int endYear = calendar.get(Calendar.YEAR);

    if (startYear != endYear) {
      return false;
    }

    String allyear_fee_stand = startYear + "年01月-" + startYear + "年12月";

    String feeStand = startDateString + "-" + endDateString;

    return feeStand.equals(allyear_fee_stand);
  }

  public static boolean isFullYearEN(Date startDate, Date endDate) {
    String pattern = "MMM.yyyy";
    String startDateString = toDateEn(startDate, pattern);
    String endDateString = toDateEn(endDate, pattern);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);

    int startYear = calendar.get(Calendar.YEAR);
    calendar.setTime(endDate);
    int endYear = calendar.get(Calendar.YEAR);

    if (startYear != endYear) {
      return false;
    }

    String allyear_fee_stand = "Jan." + startYear + "-Dec." + startYear;

    String feeStand = startDateString + "-" + endDateString;

    return feeStand.equals(allyear_fee_stand);
  }

  /**
   * 根据判断字符串和日期格式，判断是否为日期
   * 
   * @param str
   * @param format
   * @return
   */
  public static boolean isNotDate(String str, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      sdf.parse(str);
      return false;
    } catch (ParseException e) {
      return true;
    }
  }

  /**
   * 获取日期格式的文件路径
   * 
   * @param date
   *          日期
   * @param seperator
   *          分隔符
   * @return
   */
  public static String getDatePath(Date date, String seperator) {
    Date curDate = new Date();
    int year = DateUtil.getYearForDate(curDate);
    int month = DateUtil.getMonthForDate(curDate);
    int day = DateUtil.getDayForDate(curDate);

    return year + seperator + month + seperator + day + seperator;
  }
}
