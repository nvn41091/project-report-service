package com.nvn41091.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nvn41091.configuration.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

public class DataUtil {

    public static String sqlInjection(String str) {
        if (StringUtils.isEmpty(str)) return str;
        str = str.trim().toLowerCase()
                .replace("%", Constants.DEFAULT_ESCAPE_CHAR + "%")
                .replace("_", Constants.DEFAULT_ESCAPE_CHAR + "_");
        return str;
    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

    private static final String PHONE_PATTERN = "^[0-9]*$";
    static private String saltSHA256 = "1";
    static private String AES = "AES";
    static private String DES = "DES";
    private static final String MISS_ENVIREMENT_SETTING = "{0} must be set in environment variable";
    static final String YYYY_PT = "yyyy";
    static final String YYYYmm_PT = "yyyyMM";

    /**
     * Copy du lieu tu bean sang bean moi
     * Luu y chi copy duoc cac doi tuong o ngoai cung, list se duoc copy theo tham chieu
     * <p>
     * Chi dung duoc cho cac bean java, khong dung duoc voi cac doi tuong dang nhu String, Integer, Long...
     *
     * @param source
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */
    public static boolean isNullOrZero(Long value) {
        return (value == null || value.equals(0L));
    }
    public static boolean isNullOrZero(Double value) {
        return (value == null || value.equals(0.0d));
    }

    public static boolean isNullOrZero(Integer value) {
        return (value == null || value.equals(0));
    }

    /*
     * Kiem tra Long bi null hoac zero
     *
     * @param value
     * @return
     */


    /**
     * Upper first character
     *
     * @param input
     * @return
     */
    public static String upperFirstChar(String input) {
        if (DataUtil.isNullOrEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }


    public static Long safeToLong(Object obj1, Long defaultValue) {
        Long result = defaultValue;
        if (obj1 != null) {
            if (obj1 instanceof BigDecimal) {
                return ((BigDecimal) obj1).longValue();
            }
            if (obj1 instanceof BigInteger) {
                return ((BigInteger) obj1).longValue();
            }
            try {
                result = Long.parseLong(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, null);
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        Double result = defaultValue;
        if (obj1 != null) {
            try {
                result = Double.parseDouble(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }


    public static Short safeToShort(Object obj1, Short defaultValue) {
        Short result = defaultValue;
        if (obj1 != null) {
            try {
                result = Short.parseShort(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1
     * @param defaultValue
     * @return
     * @author phuvk
     */
    public static int safeToInt(Object obj1, int defaultValue) {
        int result = defaultValue;
        if (obj1 != null) {
            try {
                result = Integer.parseInt(obj1.toString());
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }

        return result;
    }

    /**
     * @param obj1 Object
     * @return int
     */
    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }

    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null || obj1.toString().isEmpty()) {
            return defaultValue;
        }

        return obj1.toString();
    }
    public static Boolean safeToBoolean(Object obj1) {
        if (obj1 == null || obj1 instanceof Boolean) {
            return (Boolean) obj1;
        }
        if (safeToLong(obj1).equals(1L) || safeToLong(obj1).equals(0L)) {
            return safeToLong(obj1).equals(1L) ? Boolean.TRUE : Boolean.FALSE;
        }
        return false;
    }


    /**
     * @param obj1 Object
     * @return String
     */
    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }


    /**
     * safe equal
     *
     * @param obj1 String
     * @param obj2 String
     * @return boolean
     */
    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == obj2) return true;
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }

    /**
     * check null or empty
     * Su dung ma nguon cua thu vien StringUtils trong apache common lang
     *
     * @param cs String
     * @return boolean
     */
    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object obj) {
        return obj == null || obj.toString().isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Ham nay mac du nhan tham so truyen vao la object nhung gan nhu chi hoat dong cho doi tuong la string
     * Chuyen sang dung isNullOrEmpty thay the
     *
     * @param obj1
     * @return
     */
    @Deprecated
    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || "".equals(obj1.toString().trim());
    }

    public static BigInteger length(BigInteger from, BigInteger to) {
        return to.subtract(from).add(BigInteger.ONE);
    }

    public static BigDecimal add(BigDecimal number1, BigDecimal number2, BigDecimal... numbers) {
        List<BigDecimal> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Long add(Long number1, Long number2, Long... numbers) {
        List<Long> realNumbers = Lists.newArrayList(number1, number2);
        if (!DataUtil.isNullOrEmpty(numbers)) {
            Collections.addAll(realNumbers, numbers);
        }
        return realNumbers.stream()
                .filter(x -> x != null)
                .reduce(0L, (x, y) -> x + y);
    }

    /**
     * add
     *
     * @param obj1 BigDecimal
     * @param obj2 BigDecimal
     * @return BigDecimal
     */
    public static BigInteger add(BigInteger obj1, BigInteger obj2) {
        if (obj1 == null) {
            return obj2;
        } else if (obj2 == null) {
            return obj1;
        }

        return obj1.add(obj2);
    }


    /**
     * Collect values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> List<T> collectProperty(Collection<?> source, String propertyName, Class<T> returnClass) {
        List<T> propertyValues = Lists.newArrayList();
        try {
            String getMethodName = "toLocale" + upperFirstChar(propertyName);
            for (Object x : source) {
                Class<?> clazz = x.getClass();
                Method getMethod = clazz.getMethod(getMethodName);
                Object propertyValue = getMethod.invoke(x);
                if (propertyValue != null && returnClass.isAssignableFrom(propertyValue.getClass())) {
                    propertyValues.add(returnClass.cast(propertyValue));
                }
            }
            return propertyValues;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * Collect distinct values of a property from an object list instead of doing a for:each then call a getter
     * Consider using stream -> map -> collect of java 8 instead
     *
     * @param source       object list
     * @param propertyName name of property
     * @param returnClass  class of property
     * @return value list of property
     */
    @Deprecated
    public static <T> Set<T> collectUniqueProperty(Collection<?> source, String propertyName, Class<T> returnClass) {
        List<T> propertyValues = collectProperty(source, propertyName, returnClass);
        return Sets.newHashSet(propertyValues);
    }

    public static boolean isNullObject(Object obj1) {
        if (obj1 == null) {
            return true;
        }
        if (obj1 instanceof String) {
            return isNullOrEmpty(obj1.toString());
        }
        return false;
    }


    public static boolean isCollection(Object ob) {
        return ob instanceof Collection || ob instanceof Map;
    }

    public static String makeLikeParam(String s) {
        if (StringUtils.isEmpty(s)) return null;
        s = s.trim().toLowerCase().replace("!", Constants.DEFAULT_ESCAPE_CHAR + "!")
                .replace("%", Constants.DEFAULT_ESCAPE_CHAR + "%")
                .replace("_", Constants.DEFAULT_ESCAPE_CHAR + "_");
        return "%" + s + "%";
    }

    /**
     * @param date
     * @param format yyyyMMdd, yyyyMMddhhmmss,yyyyMMddHHmmssSSS only
     * @return
     */
    public static Integer getDateInt(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return Integer.parseInt(dateStr);
    }

    /**
     * @param date
     * @param format yyyyMMdd, yyyyMMddhhmmss,yyyyMMddHHmmssSSS only
     * @return
     */
    public static Long getDateLong(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return Long.parseLong(dateStr);
    }

//    public static Integer getAbsoluteDate(Integer date, Integer relativeTime, Integer timeType) throws ParseException {
//        if (date == null) return 0;
//        if (relativeTime == null) return date;
//        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_YYYYMMDD);
//        Date newDate = sdf.parse(date.toString());
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(newDate);
//        if (Constants.TIME_TYPE_DATE.equals(timeType) || Constants.TIME_TYPE_DATE.toString().equals(timeType)) {
//            cal.add(Calendar.DATE, relativeTime);
//        } else if (Constants.TIME_TYPE_MONTH.equals(timeType) || Constants.TIME_TYPE_MONTH.toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, relativeTime);
//        } else if (Constants.TIME_TYPE_QUARTER.equals(timeType) || Constants.TIME_TYPE_QUARTER.toString().equals(timeType)) {
//            cal.add(Calendar.MONTH, relativeTime * 3);
//        } else if (Constants.TIME_TYPE_YEAR.equals(timeType) || Constants.TIME_TYPE_YEAR.toString().equals(timeType)) {
//            cal.add(Calendar.YEAR, relativeTime);
//        }
//        return Integer.parseInt(sdf.format(cal.getTime()));
//    }

    private static void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static Date getFirstDateOfMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Thang 1 thi calendar.MONTH = 0
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        resetTime(cal);
        return cal.getTime();
    }

    public static Date getAbsoluteDate(Date date, Integer relativeTime, Object timeType) {
        if (relativeTime == null) return date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (Constants.TIME_TYPE_DATE.equals(timeType) || Constants.TIME_TYPE_DATE.toString().equals(timeType)) {
            cal.add(Calendar.DATE, relativeTime);
        } else if (Constants.TIME_TYPE_MONTH.equals(timeType) || Constants.TIME_TYPE_MONTH.toString().equals(timeType)) {
            cal.add(Calendar.MONTH, relativeTime);
        } else if (Constants.TIME_TYPE_QUARTER.equals(timeType) || Constants.TIME_TYPE_QUARTER.toString().equals(timeType)) {
            cal.add(Calendar.MONTH, (relativeTime) * 3);
        } else if (Constants.TIME_TYPE_YEAR.equals(timeType) || Constants.TIME_TYPE_YEAR.toString().equals(timeType)) {
            cal.add(Calendar.YEAR, relativeTime);
        }
        return cal.getTime();
    }

    public static boolean isDate(String str, String format) {
        if (StringUtils.isEmpty(str)) return false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(str);
            return str.equals(sdf.format(date));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static Date getDatePattern(String date, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    public static String formatDatePattern(Integer prdId, String pattern) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(prdId.toString());

            SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
            result = sdf2.format(date);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static String formatQuarterPattern(Integer prdId) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(prdId.toString());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            String result2 = sdf2.format(date);

            result = (date.getMonth() / 3 + 1) + "/" + result2;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static String formatDatePattern(Long prdId, String pattern) {
        return formatDatePattern(prdId.intValue(), pattern);
    }

    public static String formatQuarterPattern(Long prdId) {
        return formatQuarterPattern(prdId.intValue());
    }

    public static Date add(Date fromDate, int num, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(type, num);
        return cal.getTime();
    }

    public static String dateToString(Date fromDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(fromDate);
    }

    public static String dateToStringQuater(Date fromDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(fromDate);
        return (fromDate.getMonth() / 3 + 1) + "/" + year;
    }

    public static String getTimeValue(Date date, Integer timeType) {
        SimpleDateFormat YYYY = new SimpleDateFormat(YYYY_PT);
        SimpleDateFormat YYYYMM = new SimpleDateFormat(YYYYmm_PT);
        String value = null;
        if (Constants.TIME_TYPE_YEAR.equals(timeType)) {
            value = YYYY.format(date);
        } else if (Constants.TIME_TYPE_MONTH.equals(timeType)) {
            value = YYYYMM.format(date);
        } else if (Constants.TIME_TYPE_QUARTER.equals(timeType)) {
            value = YYYY.format(date);
            int quarter = date.getMonth() / 3 + 1;
            value = value + "" + quarter;
        }
        return value;
    }


    public static String getExternalUrl(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
            logger.error(ignored.getMessage(), ignored);
        }
        return String.format("%s://%s:%s%s", protocol, hostAddress, serverPort, contextPath);
    }

    public static Instant toInstant(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof java.sql.Date) {
            java.sql.Date obj = (java.sql.Date) object;
            LocalDate localDate = obj.toLocalDate();
            Instant i = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            return i;
        } else if (object instanceof java.util.Date) {
            return Instant.ofEpochMilli(((java.util.Date) object).getTime());
        } else if (object instanceof Timestamp) {
            return Instant.ofEpochMilli(((Timestamp) object).getTime());
        }
        return null;
    }





}
