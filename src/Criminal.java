import java.util.Set;
import java.util.HashSet;

public class Criminal {
	private String name;
	private int id;
	private Date dateOfBirth;
	private Traits traits;
	private Set<Crime> crimes;
	
	//Constructor
	public Criminal(String name, int id, Date dateOfBirth, Traits traits, Set<Crime> crimes) {
		this.name=name;
		this.id=id;
		this.dateOfBirth=dateOfBirth;
		this.traits=traits;
		this.crimes=crimes;		
	}
	
	public Criminal(String name, int id, Date dateOfBirth, Traits traits) {
		this(name,id,dateOfBirth,traits,new HashSet<Crime>());
	}
	

	//Getters
	public String getName() {
		return this.name;
	}
	public int getId() {
		return this.id;
	}
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}
	public Set<Crime> getCrimes() {
		return crimes;
	}
	public Traits getTraits() {
		return traits;
	}
	
	//Adders
	public void addCrime(Crime c) {
		crimes.add(c);
	}
	

	// Override method toString()
	public String toString() {
		return "Criminal named "+name + " with id " + id + " born on " + dateOfBirth + " with the following traits:\n\t" +traits.toString() + "\n\tAnd "+ crimes.size()+" crimes\n\t"+ crimes.toString();
	}
	
	
	public Set<String> getShingleTraits() {
		Set<String> set = new HashSet<String>();
		
		// enquadra a idade em uma faixa etaria
		String age = "child";
		int dateBirth = dateOfBirth.hashCode();
		Date auxDate = dateOfBirth.todayDate();
		int dateNow = auxDate.hashCode();
		int year = dateNow-dateBirth;
		if (year>6570 && year<=12775) age = "earlyAdult"; //entre 18 e 35 anos
		if (year>12775 && year<=18250) age = "middleAdult"; //entre 35 e 50 anos
		if (year>18250 && year<=23725) age = "lateAdult"; //entre 50 e 65 anos
		if (year>23725) age = "elder"; //acima de 65 anos

		set.add(age);
		set.add(String.valueOf(traits.getSex()));
		set.add(traits.getSkinColor());
		set.add(traits.getHeight().toString());
		set.add(traits.getNationality());
		
		return set;
	}
	
	public Set<String> getShingleCrimes() {
		Set<String> set = new HashSet<String>();

		for (Crime c : crimes) {
			set.add(c.getCategory());
			//set.add(c.getName());
		}

		return set;
	}
	
	
}
