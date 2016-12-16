package com.cfets.bcs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtilTest {
  public static void main(String[] args) {
    String html = "<p>&<是通知单&<</p>";
    System.out.println(StringEscapeUtils.escapeHtml4(html));

    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    System.out.println(sdf.format(date));
  }

}
