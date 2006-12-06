package edu.ucsd.itd.mines.util;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Text formatting utility.
 */
public class FormattingUtil {
    private static String DEFAULT_DATE_FORMAT = "yyyy-M-d hh:mm:ss aa";

    public static String dateToStr(Date d) {
        return dateToStr(d, DEFAULT_DATE_FORMAT);
    }

    public static String dateToStr(Date d, String format) {
       DateFormat formatter = new SimpleDateFormat(format);
       String s = null;

       if(d != null) {
           s = formatter.format(d);
       }

       return s;
    }
}
