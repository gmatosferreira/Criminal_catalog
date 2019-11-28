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
	public int getCC() {
		return this.id;
	}
	public Date getDataNasc() {
		return this.dateOfBirth;
	}
	public Set<Crime> getCrimes() {
		return crimes;
	}
	
	
	public String toString() {
		return name + ", id: " + id + " Date of Birth: " + dateOfBirth;
	}
	
	
}
