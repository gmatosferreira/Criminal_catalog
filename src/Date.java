import java.util.Calendar;
import java.util.Random;

public class Date implements Comparable<Date>{
	private int dd;
	private int mm;
	private int yy;
	
	//Construtor
	public Date(int dd, int mm, int yy) {
		this.dd=dd;
		this.mm=mm;
		this.yy=yy;
	}
	
	//Métodos estáticos
	public static Date todayDate() {
	    Calendar now = Calendar.getInstance();
	    return new Date(now.get(Calendar.DATE), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR));
	}
	
	public static int numberDaysOfMonth(int m, int year) {
		int days[]= {31,0,31,30,31,30,31,31,30,31,30,31};
		if(m!=2) {
			return days[m-1];	
		}else { //If february
			//Check if is leap year
			boolean leap;
	        if(year % 4 == 0)
	        {
	            if( year % 100 == 0)
	            {
	                // year is divisible by 400, hence the year is a leap year
	                if ( year % 400 == 0)
	                    leap = true;
	                else
	                    leap = false;
	            }
	            else
	                leap = true;
	        }
	        else
	            leap = false;
	        
	        //Return number of days
	        if(leap)
	        	return 29;
	        else
	        	return 28;
		}
	}
	
	public static Date randomDate(Date since, Date until) {
		Random r = new Random();
		//return r.nextInt((max - min) + 1) + min;
		if(since.compareTo(until)==0) { //If they are the same date, return that date
			return since;
		}else if (since.compareTo(until)>0) {
			return null; //Since must be older than until
		}else { //Otherwise
			int year=1, month=1, day=1;
			if(since.getY()==until.getY()) { //If years are equal
				year=since.getY();
				if(since.getM()==until.getM()) { //If months are equal
					month=since.getM();
					day=r.nextInt((until.getD() - since.getD()) + 1) + since.getD(); //Determine a random day between the day since and the day until
				}else { //If months are different
					month=r.nextInt((until.getM() - since.getM()) + 1) + since.getM(); //Determine a random month between the month since and until
					if(month==since.getM()) { //If random month is the same as the month since 
						day=r.nextInt((Date.numberDaysOfMonth(since.getM(), year) - since.getD()) + 1) + since.getD(); //Determine a random day between the day since and the last day of month
					}else if (month==until.getM()) { //If random month is the same as the month until
						day=r.nextInt((until.getD() - 1) + 1) + 1; //Determine a day between 1 and the day until
					}else { //Otherwise
						day=r.nextInt((Date.numberDaysOfMonth(since.getM(), year) - 1) + 1) + 1; //Get a random number between 1 and the number of days of the month 
					}
				}
			}else { //If years are not equal
				year=r.nextInt((until.getY() - since.getY()) + 1) + since.getY(); //Generate a random year between the years since and until
				if(year==since.getY()) {//If year is the same as the year since
					month=r.nextInt((12 - since.getM()) + 1) + since.getM(); //Generate a month between month since and 12
					if(month==since.getM()) { //If random month is the same as the month since 
						day=r.nextInt((Date.numberDaysOfMonth(since.getM(), year) - since.getD()) + 1) + since.getD(); //Determine a random day between the day since and the last day of month
					}else { //Otherwise
						day=r.nextInt((Date.numberDaysOfMonth(since.getM(), year) - 1) + 1) + 1; //Get a random number between 1 and the number of days of the month 
					}
				}else if (year==until.getY()){//If year is the same as the year until
					month=r.nextInt((until.getM() - 1) + 1) + 1; //Generate a month between 1 and month until
					if (month==until.getM()) { //If random month is the same as the month until
						day=r.nextInt((until.getD() - 1) + 1) + 1; //Determine a day between 1 and the day until
					}else { //Otherwise
						day=r.nextInt((Date.numberDaysOfMonth(since.getM(), year) - 1) + 1) + 1; //Get a random number between 1 and the number of days of the month 
					}
				}
			}
			return new Date(day,month,year);
		}
	}
	
	//Getters
	public int getD() {
		return dd;
	}
	
	public int getM() {
		return mm;
	}
	
	public int getY() {
		return yy;
	}
	
	//Other methods	
	public String toString() {
		return this.dd+"/"+this.mm+"/"+this.yy;
	}
	
	public int compareTo(Date other) {
		//return 0 if equal, <1 if smaller and >1 of greater 
		int c = yy-other.getY();
		if(c!=0) return c;
		c = mm-other.getM();
		if(c!=0) return c;
		return dd-other.getD();
	}

	@Override
	public int hashCode() {
		//Número de dias desde o ano zero (kind of)
		return dd+mm*31+yy*365;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof Date))
			return false;
		Date other = (Date) obj;
		if (yy != other.yy)
			return false;
		if (dd != other.dd)
			return false;
		if (mm != other.mm)
			return false;
		return true;
	}

	
	
}
