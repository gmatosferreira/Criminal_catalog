import java.util.Calendar;

public class Data implements Comparable<Data>{
	private int dd;
	private int mm;
	private int aa;
	
	//Construtor
	public Data(int dd, int mm, int aa) {
		this.dd=dd;
		this.mm=mm;
		this.aa=aa;
	}
	
	//Métodos estáticos
	public static Data dataHoje() {
	    Calendar now = Calendar.getInstance();
	    return new Data(now.get(Calendar.DATE), now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR));
	}
	
	public String toString() {
		return this.dd+"/"+this.mm+"/"+this.aa;
	}
	
	public int getD() {
		return dd;
	}
	
	public int getM() {
		return mm;
	}
	
	public int getA() {
		return aa;
	}
	
	public int compareTo(Data other) {
		//return 0 if equal, <1 if smaller and >1 of greater 
		int c = aa-other.getA();
		if(c!=0) return c;
		c = mm-other.getM();
		if(c!=0) return c;
		return dd-other.getD();
	}

	@Override
	public int hashCode() {
		//Número de dias desde o ano zero (kind of)
		return dd+mm*31+aa*365;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof Data))
			return false;
		Data other = (Data) obj;
		if (aa != other.aa)
			return false;
		if (dd != other.dd)
			return false;
		if (mm != other.mm)
			return false;
		return true;
	}

	
	
}
