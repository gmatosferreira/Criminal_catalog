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
	
	public Criminal(String name, int id, Date dateOfBirth, Traits traits, Crime crimes) {
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
	
	// Override method toString()
	public String toString() {
		return name + "\nId: " + id + ", Date of Birth: " + dateOfBirth + ", " +traits.toString() + crimes.toString();
	}
	
	
}
