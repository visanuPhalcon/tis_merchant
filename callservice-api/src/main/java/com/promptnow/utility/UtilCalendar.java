package com.promptnow.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class UtilCalendar {
	//   @SuppressLint("NewApi")
	private static Calendar c = Calendar.getInstance();
	
	public static void setCalendar(long tms){
		c.setTimeInMillis(tms);
	} 
	
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeFormat(String format){
		long tempOldDate = c.getTimeInMillis();
		c.setTimeInMillis((System.currentTimeMillis()));
		//2014-02-02 11:11:11
		Date date = c.getTime();
		String s = new SimpleDateFormat(format).format(date);
		//2014-02-02 11:11:11
		c.setTimeInMillis(tempOldDate);
		return s;
	}
	
	public static void setCalendar(int y, int m, int d){
		c.set(y, m-1, d);
	}
	
	public static void setCalendar(int year, int month, int day, int hourOfDay, int minute){
		c.set(year, (month-1), day, hourOfDay, minute);
	}
	
	public static void setCalendar(String date){
		if(date.length()==8){
			c.set(
					Integer.parseInt(date.substring(0,4)), 
					(Integer.parseInt(date.substring(4,6))-1), 
							Integer.parseInt(date.substring(6,8)));
		}else{
			UtilLog.e("can't set date because length != 8");
		}
	} 
	
	public static String getStringYYYYMMDD(){
		return getYear()+getMonth()+getDay();
	}
	
	public static String get12Time(){ 
		String am_pm;
		if(c.get(Calendar.AM_PM) == 0) am_pm = "AM"; 
		else am_pm = "PM";
		return c.get(Calendar.HOUR)+"."+c.get(Calendar.MINUTE)+" "+am_pm;
	}
	
	public static String getStringAmountDay(){
		return ""+c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	@SuppressWarnings("static-access")
	public static String getStringAmountDayInMounthNum(int amountMonth){
		long tempOldDate = c.getTimeInMillis();
		int numDays = 0;
		for(int index=0; index<amountMonth; index++){
			numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.set(Calendar.MONTH, c.get(c.MONTH)-1);
		}
		c.setTimeInMillis(tempOldDate);	// reset
		return ""+numDays;
	}
	
	public static String getStringAmountDayInTheMounth(int theMonth){
		long tempOldDate = c.getTimeInMillis();
		int numDays = 0;
		if(theMonth>0){
//			c.set(Calendar.MONTH, c.get(c.MONTH));
			c.set(Calendar.MONTH, theMonth-1);
			numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return ""+numDays;
	}
	
	public static int getIntAmountDayInTheMounth(int theYear, int theMonth){
		long tempOldDate = c.getTimeInMillis();
		int numDays = 0;
		if(theMonth>0){
//			c.set(Calendar.MONTH, c.get(c.MONTH));
			c.set(theYear, (theMonth-1), 1);
			numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return numDays;
	}
	
	public static String getStringAmountDayInTheMounth(String sTheMonth){
		long tempOldDate = c.getTimeInMillis();
		int numDays = 0;
		int theMonth = Integer.parseInt(sTheMonth);
		if(theMonth>0){
//			c.set(Calendar.MONTH, c.get(c.MONTH));
			c.set(Calendar.MONTH, theMonth-1);
			numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return ""+numDays;
	}
	
	public static String getStringAmountDayInTheMounth(String sTheMonth, int monthBefore){
		long tempOldDate = c.getTimeInMillis();
		int numDays = 0;
		int theMonth = Integer.parseInt(sTheMonth);
		if(theMonth>0){
			for(int index=0; index<=monthBefore; index++){ 
				c.set(Calendar.MONTH, theMonth-1);
				numDays += c.getActualMaximum(Calendar.DAY_OF_MONTH);
				theMonth -= 1;
			}
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return ""+numDays;
	}
	
	@SuppressWarnings("static-access")
	public static String getStringAmountDayInMounthNum(int monthBefore, String date){ 
		int numDays = 0;
		int lastDay = 0;
		if(date.length()==8){
			long tempOldDate = c.getTimeInMillis();
			int lastYear = Integer.parseInt(date.substring(0, 4));
			int lastMonth = Integer.parseInt(date.substring(4, 6));
			lastDay = Integer.parseInt(date.substring(6, 8));
			UtilLog.d(lastYear+" - "+lastMonth+" - "+lastDay);
			c.set(lastYear, lastMonth, lastDay);
			
			for(int index=0; index<monthBefore; index++){
				c.set(Calendar.MONTH, c.get(c.MONTH)-1);
				numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
			}
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return ""+(numDays+lastDay);
	}
	
	public static int getIntAmountDayInMounthNum(int monthBefore, String date){ 
		int numDays = 0;
		int lastDay = 0;
		if(date.length()==8){
			long tempOldDate = c.getTimeInMillis();
			int lastYear = Integer.parseInt(date.substring(0, 4));
			int lastMonth = Integer.parseInt(date.substring(4, 6));
			lastDay = Integer.parseInt(date.substring(6, 8));
			UtilLog.d(lastYear+" - "+lastMonth+" - "+lastDay);
			c.set(lastYear, lastMonth, lastDay);
			
			for(int index=0; index<monthBefore; index++){
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
				numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
			}
			c.setTimeInMillis(tempOldDate);	// reset
		}
		return (numDays+lastDay);
	}
	
	public static int getIntAmountDayInMounthNum(int monthBefore, long lDate){ 
		int numDays = 0;
		int lastDay = 0;
			long tempOldDate = c.getTimeInMillis();
			
			UtilLog.d(" - "+lastDay);
			c.setTimeInMillis(lDate);
			lastDay = Integer.parseInt(UtilCalendar.getDay());
			for(int index=0; index<monthBefore; index++){
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
				numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
			}
			c.setTimeInMillis(tempOldDate);	// reset
		return (numDays+lastDay);
	}
	
	public static String getStringAmountDayInMonthNum(int monthBefore, long lDate){ 
		return Integer.toString(getIntAmountDayInMounthNum(monthBefore, lDate));
	}
	
	//list.get(0) == lastSeekDate,  list(1) == amountDay
	public static List<String> getListAmountDayInMounthNum(int monthBefore, long lDate){ 
		List<String> list = new ArrayList<String>();
		int numDays = 0;
		int lastDay = 0;
			long tempOldDate = c.getTimeInMillis();
			c.setTimeInMillis(lDate);
			lastDay = Integer.parseInt(UtilCalendar.getDay());
			UtilLog.d(" - "+lastDay);
			for(int index=0; index<monthBefore; index++){
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
				numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
			}
			list.add(getStringYYYYMMlastDD());
			c.setTimeInMillis(tempOldDate);	// reset
			list.add(""+(numDays+lastDay));
		return list;
	}
	
	//list.get(0) == lastSeekDate,  list(1) == amountDay
	public static List<String> getListAmountDayInMonthNum(int monthBefore, String date){ 
		List<String> list = new ArrayList<String>();
		int numDays = 0;
		int lastDay = 0;
			long tempOldDate = c.getTimeInMillis();
			
			setCalendar(date);
			lastDay = Integer.parseInt(UtilCalendar.getDay());
			UtilLog.d(" - "+lastDay);
			for(int index=0; index<monthBefore; index++){
				setCalendar(getStringYYYYMMDDAfterLastDay(getStringYYYYMMlastDD()));
				UtilLog.d(""+getStringYYYYMMlastDD()); 
				numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
			} 
			setCalendar(getStringYYYYMMDDAfterLastDay(getStringYYYYMMlastDD()));
			UtilLog.d(""+getStringYYYYMMlastDD());  
			list.add(getStringYYYYMMlastDD());
			
			c.setTimeInMillis(tempOldDate);	// reset
			list.add(""+(numDays+lastDay));
		return list;
	}
	
	//list.get(0) == lastSeekDate,  list(1) == amountMonth
	public static List<String> getListAmountMonthInYearNum(int yearBefore, String date){ 
		List<String> list = new ArrayList<String>();
		int numMonths = 0;
		int tempYear; 
			long tempOldDate = c.getTimeInMillis();
			setCalendar(date);
			tempYear = Integer.parseInt(getYear()); 
			for(int index=0; index<yearBefore; index++){ 
				numMonths+= 12; 
				tempYear--; 
				setCalendar(yearToString(tempYear)+getMonth()+getDay()); 
			}  
			UtilLog.d(""+getStringYYYYMMlastDD());  
			tempYear--; 
			setCalendar(yearToString(tempYear)+getMonth()+getDay()); 
			list.add(getStringYYYYMMlastDD()); 
			c.setTimeInMillis(tempOldDate);	// reset
			list.add(""+(numMonths + Integer.parseInt(getMonth())));
		return list;
	}
	
	public static String getStringYYYYMMlastDD() { 
		return getYear()+getMonth()+c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static String getStringYYYYMMlastDD(String dateIn) { 
		long tempDate = getLongTMS();
		setCalendar(dateIn); 
		String s = getYear()+getMonth()+c.getActualMaximum(Calendar.DAY_OF_MONTH);
		setCalendar(tempDate);
		return s;
	}
	
	public static long getLongYYYYMMlastDD(String dateIn) { 
		long tempDate = getLongTMS();
		setCalendar(dateIn);
		setCalendar(getYear()+getMonth()+c.getActualMaximum(Calendar.DAY_OF_MONTH));
		long d = getLongTMS();
		setCalendar(tempDate);
		return d;
	}

	@SuppressWarnings("static-access")
	public static String getStringAmountDayInMounthNum(int monthBefore, int lastYear, int lasMonth, int lastDay){ 
		int numDays = 0; 
		long tempOldDate = c.getTimeInMillis();  
		c.set(lastYear, lasMonth, lastDay);
			
		for(int index=0; index<monthBefore; index++){
			c.set(Calendar.MONTH, c.get(c.MONTH)-1);
			numDays+= c.getActualMaximum(Calendar.DAY_OF_MONTH); 
		}
		c.setTimeInMillis(tempOldDate);	// reset 
		return ""+(numDays+lastDay);
	}
	
	@SuppressWarnings("static-access")
	public static String getDay(){
		String s = ""+c.get(c.DATE);
		if(s.length()==1) s= "0"+s;
		return s;
	}
	
	@SuppressWarnings("static-access")
	public static String getMonth(){
		String s = ""+(c.get(c.MONTH)+1);
		if(s.length()==1) s= "0"+s;
		return s;
	} 	
	
	@SuppressWarnings("static-access")
	public static String getYear(){
		return ""+c.get(c.YEAR);
	}
	
	public static int getIntDay(){ 
		return Integer.parseInt(getDay());
	}
	
	public static int getIntMonth(){ 
		return Integer.parseInt(getMonth());
	} 	
	
	public static int getIntYear(){
		return Integer.parseInt(getYear());
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getDateFullName(){
		return c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getMonthFullName(){
		return c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getDateShortName(){
		return c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getMonthShortName(){
		return c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
	}

	public static String getMonthShortName3(){ 
		String s = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US); 
		return (String) s.subSequence(0, 3);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getMonthShortName3(String theMonth){ 
		String tempDate = getStringYYYYMMDD();
		setCalendar(getYear()+(theMonth)+"01");
		String s = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
		setCalendar(tempDate);	//	reset
		return (String) s.subSequence(0, 3);
	}
	
	public static String getDateShortName3(){
		String s = getDateFullName();
		return s.substring(0, 3);
	}
	
//	private static String getFormat(String displayName) {
//		return Integer.toString(c.MONTH);
//	}
//
//	private String[] Mmm = {
//							"Jan", "Feb", "Mar", "Apr", "May", "Jun",
//							"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
//							};
	
	public static long getLongTMS(){
		return c.getTimeInMillis();
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getDateShort2Name(){
		String s = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US); 
		return (String) s.subSequence(0, 2);
	}
	
	public static String monthOrDayToString(int i){
		String s = Integer.toString(i);
		if(s.length()==1) s= "0"+s;
		return s;
	}
	
	public static String yearToString(int i){
		String s = Integer.toString(i);
		if(s.length()<=3) {
			for(int index=0; index<(4-s.length()); index++){
				s= "0"+s;
			}
		}
		return s;
	}
	
	public static String getStringYYYYMMDDAfterLastDay(long ltm) {
		long tempOldDate = getLongTMS();
		c.setTimeInMillis(ltm);
		int day = Integer.parseInt(UtilCalendar.getDay());
		int month = Integer.parseInt(UtilCalendar.getMonth());
		int year = Integer.parseInt(UtilCalendar.getYear());
		if(month==1){
			month = 12;
//			log("month : "+month); 
			year --;
			day = getIntAmountDayInTheMounth(year, month);
		}else{
			month--;
//			log("month : "+month); 
			day = getIntAmountDayInTheMounth(year, month);
//			log("day : "+day);
		}
		String sDay = Integer.toString(day);
		String sMonth = Integer.toString(month);
		String sYear = Integer.toString(year);
		if(sDay.length()<2) sDay="0"+sDay;
		if(sMonth.length()<2) sMonth="0"+sMonth;
//		log(sYear+" - "+sMonth+" - "+sDay);
		c.setTimeInMillis(tempOldDate);	//	reset
		return sYear+sMonth+sDay;
	} 
	 
	
	public static int getIntYYYYMMDDAfterLastDay(long ltm) {
		return Integer.parseInt(getStringYYYYMMDDAfterLastDay(ltm));
	}
	
	public static int getIntYYYYMMDDAfterLastDay(String lastSeekDate) {
		long tempOldDate = getLongTMS();
		UtilCalendar.setCalendar(lastSeekDate);
		long tempLtm = UtilCalendar.getLongTMS();
		UtilCalendar.setCalendar(tempOldDate);	// reset
		return Integer.parseInt(getStringYYYYMMDDAfterLastDay(tempLtm));
	}
	
	public static String getStringYYYYMMDDAfterLastDay(String lastSeekDate) {
		long tempOldDate = getLongTMS();
		UtilCalendar.setCalendar(lastSeekDate);
		long tempLtm = UtilCalendar.getLongTMS();
		UtilCalendar.setCalendar(tempOldDate);	// reset
		return getStringYYYYMMDDAfterLastDay(tempLtm);
	}
	
	public static String test(String lastSeekDate) {
		long tempOldDate = getLongTMS();
		UtilCalendar.setCalendar(lastSeekDate);
		long tempLtm = UtilCalendar.getLongTMS();
		UtilCalendar.setCalendar(tempOldDate);	// reset
		return getStringYYYYMMDDAfterLastDay(tempLtm);
	}
	
	public static void main(String[] args){
		UtilCalendar.setCalendar(System.currentTimeMillis());
		int theYear = 2013;
		int theMonth = 12;
		List<String> list = UtilCalendar.getListAmountDayInMounthNum(theYear ,theMonth);
		UtilLog.d("month : "+theMonth+" has : "+list.get(0)+", "+list.get(1));
	}
}
