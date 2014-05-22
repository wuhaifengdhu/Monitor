package com.whf.date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentCalendar {
	
	private static Calendar curTime=Calendar.getInstance();


	public static Calendar getCurrentCalendar(){
		return curTime;
	}
	public static void setCurrentCalendar(String seconds){
	    Date curDate;
		try{
			curDate=new Date(Long.valueOf(seconds+"000").longValue());
		}catch(Exception e){
			curDate=new Date();
		}
		curTime=Calendar.getInstance();
		curTime.setTime(curDate);
	}
	
	public static void setCurrentCalendar(){
		curTime=Calendar.getInstance();
		curTime.setTime(new Date(Long.valueOf("1448927600").longValue()));
	}
	
	public static long getCurrentTimeTotalSecond(){
		return curTime.getTimeInMillis()/1000;
	}
	


	public static int getCurrentYear(){
		return curTime.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonthInt(){
		return curTime.get(Calendar.DAY_OF_MONTH);
	}
	

	public static int getCurrentDay(){
		return curTime.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentHour(){
		return curTime.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getCurrentMinute(){
		return curTime.get(Calendar.MINUTE);
	}
	
	public static int getCurrentSecond(){
		return curTime.get(Calendar.SECOND);
	}


	public static String getCurrentDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MMM,dd",Locale.ENGLISH);
		return sdf.format(curTime.getTime());
	}

	public static String getCurrentMonth(){
		SimpleDateFormat sdf=new SimpleDateFormat("MMM",Locale.ENGLISH);
		return sdf.format(curTime.getTime());
	}
	
}