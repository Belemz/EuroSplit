package fcul.pco.eurosplit.domain;


import java.time.LocalDateTime;

/**
 * @author Cláudia Belém
 * @author Fábio Neves
 * <p>
 * This class defines Date instances.
 */

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
	
	/**
	 * Comprises the instance attributes separated by hash key with format y#m#d#h#m.
	 * @returns String
	 */
	public String toString() {
        return (this.year + "-" + this.month + "-" + this.day + "-" + this.hour + "-" + this.minute);
	}
	
	/**
	 * Takes Date.toString() formated date and returns an instance with the same values.
     * @param date is String
	 * @returns Date
	 */
	public static Date fromString(String date){
        String[] split_date = date.split("-");

        int year = Integer.parseInt(split_date[0]);
		int month = Integer.parseInt(split_date[1]);
		int day = Integer.parseInt(split_date[2]);
		int hour = Integer.parseInt(split_date[3]);
		int minute = Integer.parseInt(split_date[4]);

		return new Date(year, month, day, hour, minute);
	}

    /**
     * Date at the time of the call.
     * @returns Date(Year, Month, Day of Month, Hour, Minute)
	 */
    public static Date dateNow() {

        return new Date(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute());
	}
	
}
