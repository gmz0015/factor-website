package org.noah.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取当前时间
     *
     * @param stringFormat
     * @return
     */
    public static synchronized String getCurrentDate(String stringFormat) {
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        return format.format(new Date());
    }

    /**
     * 时间格式转化
     *
     * @param srcDate
     * @param srcFormat
     * @param destFormat
     * @return
     */
    public static synchronized String convertFromDateFormat(String srcDate, String srcFormat, String destFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(srcFormat);
            Date destDate = format.parse(srcDate);
            format = new SimpleDateFormat(destFormat);
            return format.format(destDate);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @param stringFormat
     * @return
     */
    public static synchronized String getDateWithDateFormat(Date date, String stringFormat) {
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        return format.format(date);
    }

    /**
     * 时间格式化
     * @param timestamp
     * @param stringFormat
     * @return
     */
    public static synchronized String getDateWithDateFormat(long timestamp, String stringFormat) {
        SimpleDateFormat format = new SimpleDateFormat(stringFormat);
        return format.format(new Date(timestamp));
    }

    /**
     * 判断日期是否有效
     *
     * @param date
     * @param stringFormat
     * @return
     */
    public static synchronized boolean isValidDate(String date, String stringFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(stringFormat);
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param stringFormat
     * @return
     */
    public static synchronized Date getDateFromStringWithDateFormat(String date, String stringFormat) {
        try {
            DateFormat format = new SimpleDateFormat(stringFormat);
            format.setLenient(false);
            return format.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取时间差（单位：秒）
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static long getSecondDelay(Date fromDate, Date toDate) {
        return (Math.abs(toDate.getTime() - fromDate.getTime())) / 1000;
    }

    /**
     * @Desc 获取两个时间之间的间隔天数
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int getBetweenDays(Date fromDate, Date toDate) {
        int betweenDays = (int) (getSecondDelay(fromDate, toDate) / 24 / 3600) + 1;
        return betweenDays;
    }

    /**
     * 获取间隔的时间
     *
     * @param date
     * @param length
     * @param cal
     * @return
     */
    public static Date getDate(Date date, int length, int cal) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(cal, length);
        return calendar.getTime();
    }

    /**
     * 时间比较
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean compareDay(Date source, Date target) {
        return target.compareTo(source) > 0 ? true : false;
    }

    /**
     * LocalDateTime转毫秒时间戳
     *
     * @param localDateTime LocalDateTime
     * @return 时间戳
     */
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zoneId).toInstant();
            return instant.toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date转LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        try {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            return Date.from(zdt.toInstant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
