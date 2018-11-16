package fcul.pco.eurosplit.domain;
/*
 * @author Fábio Neves
 */
import java.time.LocalDateTime;

import fcul.pco.eurosplit.domain.Date;

public class Date {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public Date(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	public String toString() {
		return (this.year + "#" + this.month + "#" + this.day + "#" + this.hour + "#" + this.minute);
	}
	
	public static Date fromString(String date){
		String[] split_date = date.split("#");
		int year = Integer.parseInt(split_date[0]);
		int month = Integer.parseInt(split_date[1]);
		int day = Integer.parseInt(split_date[2]);
		int hour = Integer.parseInt(split_date[3]);
		int minute = Integer.parseInt(split_date[4]);
		
		return new Date(year, month, day, hour, minute);
	}
	
	public static String dateNow() {
		StringBuilder date =  new StringBuilder();
		date.append(LocalDateTime.now().getYear());
		date.append("#");
		date.append(LocalDateTime.now().getMonthValue());
		date.append("#");
		date.append(LocalDateTime.now().getDayOfMonth());
		date.append("#");
		date.append(LocalDateTime.now().getHour());
		date.append("#");
		date.append(LocalDateTime.now().getMinute());
		
		return date.toString();
	}
	
}
