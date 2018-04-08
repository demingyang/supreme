package com.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * 日期格式转换类
 *
 */
public final class DateUtil {
	private static final Log log = LogFactory.getLog(DateUtil.class);

	/**
	 * 比较精度到年
	 */
	public static final int COMPARE_UNIT_YEAR = 1;
	/**
	 * 比较精度到月
	 */
	public static final int COMPARE_UNIT_MONTH = 2;
	/**
	 * 比较精度到周
	 */
	public static final int COMPARE_UNIT_WEEK = 3;
	/**
	 * 比较精度到日
	 */
	public static final int COMPARE_UNIT_DAY = 5;
	/**
	 * 比较精度到时
	 */
	public static final int COMPARE_UNIT_HOUR = 10;
	/**
	 * 比较精度到分
	 */
	public static final int COMPARE_UNIT_MINUTE = 12;
	/**
	 * 比较精度到秒
	 */
	public static final int COMPARE_UNIT_SECOND = 13;

	private DateUtil() {
	}

	/**
	 * 将日期对象转换为yyyy-MM-dd HH:mm:ss格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy-MM-dd HH:mm:ss格式字符串
	 */
	public static String toStringYmdHmsWthH(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
	}

	/**
	 * 将日期对象转换为yyyy-MM-dd HH:mm:ss SSS格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy-MM-dd HH:mm:ss SSS格式字符串
	 */
	public static String toStringYmdHmsWthHS(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(date);
	}

	/**
	 * 将日期对象转换为yyyy-MM-dd格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy-MM-dd格式字符串
	 */
	public static String toStringYmdWthH(Date date) {
		if(date!=null){
			return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		}
		return null;
	}

	/**
	 * 将日期对象转换为yyyy/MM/dd格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy/MM/dd格式字符串
	 */
	public static String toStringYmdWthB(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyy/MM/dd")).format(date);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyy-MM-dd HH:mm:ss格式字符串
	 * @return 日期对象或null
	 */
	public static Date toDateYmdHmsWthH(String dateStr) {
		try {
			if(StringUtils.isBlank(dateStr)){
				return null;
			}
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	public static Date toDateHmsWthH(String dateStr) {
		try {
			if(StringUtils.isBlank(dateStr)){
				return null;
			}
			return (new SimpleDateFormat("HH:mm:ss")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将yyyy-MM-dd格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyy-MM-dd格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYmdWthH(String dateStr) {
		try {
			if(StringUtils.isBlank(dateStr)){
				return null;
			}
			return (new SimpleDateFormat("yyyy-MM-dd")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为yyyyMMddHHmmss格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyyMMddHHmmss格式字符串
	 */
	public static String toStringYmdHms(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
	}

	/**
	 * 将yyyyMMddHHmmss格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyyMMddHHmmss格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYmdHms(String dateStr) {
		if ( StringUtils.isBlank(dateStr)  ) {
			return null;
		}
		try {
			SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmmss");
			return dfm.parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为yyyyMMdd格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyyMMdd格式字符串
	 */
	public static String toStringYmd(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyyMMdd")).format(date);
	}

	/**
	 * 将日期对象转换为yyMMdd格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyMMdd格式字符串
	 */
	public static String toStringYYmd(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyMMdd")).format(date);
	}

	/**
	 * 将yyyyMMdd格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyyMMdd格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYmd(String dateStr) {
		if ( StringUtils.isBlank(dateStr)  ) {
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyyMMdd")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为yyyyMM格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyyMM格式字符串
	 */
	public static String toStringYm(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyyyMM")).format(date);
	}

	/**
	 * 将日期对象转换为yyyy-MM格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy-MM格式字符串
	 */
	public static String toStringYcrm(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyyy-MM")).format(date);
	}

	/**
	 * 将yyyyMM格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyyMM格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYm(String dateStr) {
		if ( StringUtils.isBlank(dateStr)  ) {
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyyMM")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将yyyyMM格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyy-MM格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYcrm(String dateStr) {
		if ( StringUtils.isBlank(dateStr)  ) {
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyy-MM")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为yyyy格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyy格式字符串
	 */
	public static String toStringY(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyyy")).format(date);
	}

	/**
	 * 将yyyy格式字符串转换为日期对象
	 *
	 * @param dateStr
	 *            yyyy格式字符串
	 * @return 日期对象
	 */
	public static Date toDateY(String dateStr) {
		if ( StringUtils.isBlank(dateStr)  ) {
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyy")).parse(dateStr);
		} catch (ParseException e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 将日期对象转换为yyyy年MM月dd日格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyyMM格式字符串
	 */
	public static String toStringYmdwithChinese(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
	}

	/**
	 * 将日期对象转换为yyyy年MM月dd日HH24时MI分SS秒格式字符串
	 *
	 * @param date
	 *            时间对象
	 * @return yyyyMM格式字符串
	 */
	public static String toStringYmdwithsfm(Date date) {
		if ( date == null ) {
			return null;
		}
		return (new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")).format(date);
	}

	/**
	 * 比较当前日期(数据库日期)是否大于等于指定日期
	 *
	 * @param str
	 *            待比较日期参数
	 * @return boolean
	 * @throws java.text.ParseException
	 */
	public static boolean dateCompare(Object str) {
		if ( str == null ) {
			return false;
		}
		boolean bea = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String isDate = simpleDateFormat.format(new Date());

		Date date1 = null;
		Date date0;
		try {
			if (str instanceof Date) {
				date1 = simpleDateFormat.parse(DateUtil.toStringYmdWthH((Date) str));
			}
			if (str instanceof String) {
				date1 = simpleDateFormat.parse((String) str);
			}
			date0 = simpleDateFormat.parse(isDate);
			if (date0.after(date1) || date0.equals(date1)) {
				bea = true;
			}
		} catch (ParseException e) {
			log.error("", e);

		}
		return bea;
	}

	/**
	 * 比较两个日期的大小
	 * <p/>
	 * 
	 * <pre>
	 *  1、日期参数为空表示无穷小
	 * </pre>
	 *
	 * @param inDate1
	 *            第一个日期参数 @param inDate2 第二个日期参数 @return 处理结果 0:相等, -1:inDate1 <
	 *            inDate2, 1:inDate1 > inDate2 @throws
	 */
	public static int dateCompare(Date inDate1, Date inDate2) {
		return dateCompare(inDate1, inDate2, Calendar.SECOND);
	}

	/**
	 * 比较日期大小
	 *
	 * @param inDate1
	 *            日期1
	 * @param inDate2
	 *            日期2
	 * @param unit
	 *            比较精度 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
	 * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
	 */
	public static int dateCompare(Date inDate1, Date inDate2, int unit) {
		// 字符空验证
		if (inDate1 == null && inDate2 == null) {
			// 两个日期都为空返回相等
			return 0;
		} else if (inDate1 == null) {
			// 日期1为空，日期2不为空返回-1
			return -1;
		} else if (inDate2 == null) {
			// 日期1不为空，日期为空返回2
			return 1;
		}
		return DateUtil.truncate(inDate1, unit).compareTo(DateUtil.truncate(inDate2, unit));
	}

	/**
	 * 调整时间, 按照需要调整的单位调整时间
	 * <p/>
	 * 
	 * <pre>
	 * 例如:保留到日期的年change("20120511154440", DateUtil.YEAE, 2);返回：20140511154440<br/>
	 * </pre>
	 *
	 * @param inDate
	 *            传入日志
	 * @param unit
	 *            调整单位 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
	 * @param amount
	 *            调整数量
	 * @return 调整后的日期
	 */
	public static Date change(Date inDate, int unit, int amount) {
		if ( inDate == null ) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inDate);
		calendar.add(unit, amount);
		return calendar.getTime();
	}

	/**
	 * 按照精度要求对日期进行舍取
	 * <p/>
	 * 
	 * <pre>
	 * 例如:保留到日期的年truncate("20120511154440", DateUtil.YEAE);返回：20120101000000<br/>
	 * </pre>
	 *
	 * @param inDate
	 *            输入日期
	 * @param unit
	 *            单位 年：1 ;月：2;日：5; 时：10; 分：12;秒：13
	 * @return 处理后的日期
	 */
	public static Date truncate(Date inDate, int unit) {
		if ( inDate == null ) {
			return null;
		}
		return DateUtils.truncate(inDate, unit);
	}

	/**
	 * 按照指定的单位获取指定日志的最后值
	 * <p/>
	 * 
	 * <pre>
	 * 例如:保留到日期的年last("20120511154440", DateUtil.YEAE);返回：20121231235959<br/>
	 * </pre>
	 *
	 * @param inDate
	 *            输入日期
	 * @param unit
	 *            单位 年：1 ;月：2;日：5; 时：10; 分：12;秒：13
	 * @return 处理后的日期
	 */
	public static Date last(Date inDate, int unit) {
		if ( inDate == null ) {
			return null;
		}
		Date tempDate = truncate(inDate, unit);
		tempDate = change(tempDate, unit, 1);
		return change(tempDate, Calendar.MILLISECOND, -1);

	}

	/**
	 * 比较两个日期的大小
	 * <p/>
	 * 
	 * <pre>
	 *  1、日期参数为空表示无穷小
	 * </pre>
	 *
	 * @param inDate1
	 *            第一个日期参数 @param inDate2 第二个日期参数 @param unit 单位 年：1 ;月：2;日：5;
	 *            时：10; 分：12;秒：13 @return 处理结果 0:相等, -1:inDate1<inDate2,
	 *            1:inDate1>inDate2 @throws
	 */
	public static int truncateComare(Date inDate1, Date inDate2, int unit) {
		if (inDate1 == null && inDate2 == null) {
			// 两个日期都为空返回相等
			return 0;
		} else if (inDate1 == null) {
			// 日期1为空，日期2不为空返回-1
			return -1;
		} else if (inDate2 == null) {
			// 日期1不为空，日期为空返回2
			return 1;
		} else {
			Date tempDate1 = truncate(inDate1, unit);
			Date tempDate2 = truncate(inDate2, unit);
			return tempDate1.compareTo(tempDate2);
		}

	}

	/**
	 * 将日期格式化为指定的格式
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式化模式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if ( StringUtils.isBlank(pattern) || date == null ) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 获取某个日期段内所有月份的集合
	 * 
	 * @param start
	 *            开始日期 格式如：20130904，不可小于6位
	 * @param end
	 *            结束日期 格式如：20130906，不可小于6位
	 * @return List 月份集合
	 */
	public static List<String> intervalMonths(String start, String end) {
		if ( StringUtils.isBlank(start) || StringUtils.isBlank(end) || start.length()<6 || end.length()<6 ) {
			return null;
		}
		List<String> list = intervalMonthsList(start, end);
		return list;
	}

	/**
	 * 获取某个日期段内所有月份的集合
	 * 
	 * @param start
	 *            开始日期 date类型
	 * @param end
	 *            结束日期 date类型
	 * @return List 月份集合
	 */
	public static List<String> intervalMonths(Date start, Date end) {
		if ( start == null || end == null) {
			return null;
		}
		List<String> list = intervalMonthsList(toStringYcrm(start), toStringYcrm(end));
		return list;
	}

	/**
	 * 获取某个日期段内所有月份的集合
	 * 
	 * @param start
	 *            开始日期 格式如：20130904，不可小于6位
	 * @param end
	 *            结束日期 格式如：20130906，不可小于6位
	 * @return List 月份集合
	 */
	private static List<String> intervalMonthsList(String start, String end) {
		if (StringUtils.isBlank(start)|| StringUtils.isBlank(end)|| start.length()<6 || end.length()<6) {
			return null;
		}
		// 判断入参字符串长度是否大于等于六位
		if (start.length() >= 6 && end.length() >= 6) {
			start = start.substring(0, 4) + "-" + start.substring(4, 6);
			end = end.substring(0, 4) + "-" + end.substring(4, 6);
		} else {
			log.error("日期入参有误，请修改入参日期字符串！");
		}
		String splitSign = "-";
		// 判断YYYY-MM时间格式的正则表达式
		String regex = "\\d{4}" + splitSign + "(([0][1-9])|([1][012]))";
		if (!start.matches(regex) || !end.matches(regex))
			;
		List<String> list = new ArrayList<String>();
		// start大于end日期时，互换
		if (start.compareTo(end) > 0) {
			String temp = start;
			start = end;
			end = temp;
		}
		// 从最小月份开始
		String temp = start;
		while (temp.compareTo(start) >= 0 && temp.compareTo(end) <= 0) {
			// 首先加上最小月份,接着计算下一个月份
			list.add(temp.replace("-", ""));
			String[] arr = temp.split(splitSign);
			int year = Integer.valueOf(arr[0]);
			int month = Integer.valueOf(arr[1]) + 1;
			if (month > 12) {
				month = 1;
				year++;
			}
			// 补0操作
			if (month < 10) {// 补0操作
				temp = year + splitSign + "0" + month;
			} else {
				temp = year + splitSign + month;
			}
		}
		return list;
	}

	/**
	 * 计算两个日期时间相差几天,几小时,几分钟
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param format
	 *            时间格式
	 */
	public static String differTime(String startTime, String endTime, String format) throws ParseException {
		if (StringUtils.isBlank(startTime)|| StringUtils.isBlank(endTime)|| StringUtils.isBlank(format) ) {
			return null;
		}
		String differTime = differTimes(startTime, endTime, format);
		return differTime;
	}

	/**
	 * 计算两个日期时间相差几天,几小时,几分钟
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param format
	 *            时间格式
	 */
	public static String differTime(Date startTime, Date endTime, String format) throws ParseException {
		if (startTime == null|| endTime == null || StringUtils.isBlank(format) ) {
			return null;
		}
		String differTime = differTimes(format(startTime, format), format(endTime, format), format);
		return differTime;
	}

	/**
	 * 将yyyy/MM/dd HH:mm:ss格式字符串转换为日期对象
	 * 
	 * @param dateStr
	 *            yyyy/MM/dd HH:mm:ss格式字符串
	 * @return 日期对象
	 */
	public static Date toDateYmdHmsWthS(String dateStr) {
		if (StringUtils.isBlank(dateStr) ) {
			return null;
		}
		try {
			return (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算两个日期时间相差几天,几小时,几分钟
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param format
	 *            时间格式
	 */
	private static String differTimes(String startTime, String endTime, String format) throws ParseException {
		if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime) || StringUtils.isBlank(format) ) {
			return null;
		}

		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数long diff;try {
		// 获得两个时间的毫秒时间差异
		long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		// System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		String outDate = "";
		if (day != 0) {
			outDate = day + "天前";
		} else if (day == 0 && hour != 0) {
			outDate = hour + "小时前";
		} else if (day == 0 && hour == 0) {
			outDate = min + "分钟" + sec + "秒前";
		} else if (day == 0 && hour == 0 && min == 0) {
			outDate = sec + "秒前";
		}
		return outDate;
	}

	/**
	 * 计算两个日期时间相差几天,几小时,几分钟 如果开始时间大于结束时间，则直接返回0|0|0|0
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return String dd|HH|mm|ss
	 */
	public static String differTimes(Date startTime, Date endTime) {
		if (startTime == null || endTime == null || startTime.after(endTime)) {
			return "0|0|0|0";
		}
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数long diff;try {
		// 获得两个时间的毫秒时间差异
		long diff = endTime.getTime() - startTime.getTime();
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		// System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		StringBuffer sb = new StringBuffer();
		return sb.append(day).append("|").append(hour).append("|").append(min).append("|").append(sec).toString();
	}

	/**
	 * 
	 * 判断时间是否有效
	 * 
	 * @param oldTime
	 *            long 旧时间
	 * @param timeoutMin
	 *            超时分钟
	 * @return boolean 有效返回true；失效返回false
	 */
	public static boolean isValidate(long oldTime, int timeoutMin) {
		return ((oldTime + timeoutMin * 60 * 1000) >= getTimeInMillis()) ? true : false;
	}

	/**
	 * 
	 * 获取当前时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long getTimeInMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 获得几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @author zhangsong
	 * @date 2015年8月26日 下午3:14:30
	 */
	public static Date getDateBefore(Date d, int day) {
		if(d == null){
			return null;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 获得几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @author zhangsong
	 * @date 2015年8月26日 下午3:14:08
	 */
	public static Date getDateAfter(Date d, int day) {
		if(d == null){
			return null;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 判断今天是星期几
	 * 
	 * @return
	 * @author zhangsong
	 * @date 2015年8月27日 下午7:10:16
	 */
	public static Integer whatDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek - 1;
	}

	/**
	 * 根据指定日期判断星期几
	 * 
	 * @param date
	 * @return
	 * @author zhangsong
	 * @date 2015年8月27日 下午8:02:39
	 */
	public static Integer getWeek(Date date) {
		if(date == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return week_index;
	}

	/**
	 * 将String类型"yyyy-MM-dd"转成int类型yyyyMMdd
	 * 
	 * @param ymd
	 * @return
	 * @author zhangsong
	 * @date 2015年9月1日 下午5:04:39
	 */
	public static Integer intYmd(String ymd) {
		if (StringUtils.isBlank(ymd)) {
			return null;
		}
		int parseInt = Integer.parseInt(ymd.replace("-", ""));
		return parseInt;
	}

	/**
	 * 将Integer类型yyyyMMdd转成String类型"yyyy-MM-dd"
	 * 
	 * @param ymd
	 * @return
	 * @author zhangsong
	 * @throws ParseException
	 * @date 2015年9月1日 下午5:08:51
	 */
	public static String strYmd(Integer ymd) {
		if(ymd == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date;
		String strDate = null;
		try {
			date = sdf.parse(ymd + "");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			strDate = sdf1.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * 根据传入的日期字符串和转换格式，格式化日期字符串
	 * 
	 * @param strDate
	 *            日期字符串 如 2015-09-17 17：00：45
	 * @param formatStr
	 *            格式化字符串，如 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @author fangyy
	 * @date 2015年9月17日 下午5:00:45
	 */
	public static Date strToDate(String strDate, String formatStr) {
		if (StringUtils.isBlank(formatStr)) {
			return null;
		}

		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据传入的日期和转换格式，将日期转换成字符串输出
	 * 
	 * @param date
	 *            要格式化的日期
	 * @param formatStr
	 *            格式化字符串，如 yyyy-MM-dd HH:mm:ss
	 * @return 格式化后的字符串 2015-09-17 17：00：45
	 * @author fangyy
	 * @date 2015年9月17日 下午5:00:45
	 */
	public static String dateToStr(Date date, String formatStr) {

		if (StringUtils.isBlank(formatStr)) {
			return null;
		}

		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(formatStr).format(date);

	}

	/**
	 * 凌晨
	 * 
	 * @param date
	 * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
	 *       1 返回yyyy-MM-dd 23:59:59日期
	 * @return
	 */
	public static Date weeHours(Date date, int flag) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		// 时分秒（毫秒数）
		long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
		// 凌晨00:00:00
		cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

		if (flag == 0) {
			return cal.getTime();
		} else if (flag == 1) {
			// 凌晨23:59:59
			cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
		}
		return cal.getTime();
	}

	/**
	 * 获取本月第一天
	 * @return
	 */
	public static Date getFirstday(){
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return cale.getTime();
	}

	/**
	 * 获取本月最后一天
	 * @return
	 */
	public static Date getLastday(){
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return cale.getTime();
	}

}
