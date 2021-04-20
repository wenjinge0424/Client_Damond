package lu.eminozandac.ondamondclinet.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtils {

	/*
	 * Time Format for saving
	 */
	public static String DATE_STRING_FORMAT = "yyyy-MM-dd";
	public static String DATE_TIME_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * Date -> yyyyMMdd
	 * Date -> MMdd
	 * Date -> yyyy/MM/dd
	 * Date -> dd/MM/yyyy
	 */
	public static String dateToString(Date date, String strformat) {
		SimpleDateFormat format = new SimpleDateFormat(strformat);
		return format.format(date);
	}

	/*
	 * yyyyMMdd -> Date
	 * MMdd -> Date
	 * yyyy/MM/dd -> Date
	 * dd/MM/yyyy -> Date
	 */
	public static Date stringToDate(String strDate, String strformat) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(strformat);
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public static Date getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        return calendar.getTime();
	}

	public static Date getNextMonthDate(Date fromDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int month = calendar.get(Calendar.MONTH) + 1;
		calendar.set(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date getNextYearDate(Date fromDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int year = calendar.get(Calendar.YEAR) + 1;
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date getNextThreeMonthDate(Date fromDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int month = calendar.get(Calendar.MONTH) + 3;
		calendar.set(Calendar.MONTH, month);
		return calendar.getTime();
	}
}
