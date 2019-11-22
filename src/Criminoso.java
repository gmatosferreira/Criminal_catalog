import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

public class Criminoso {
	private String nome;
	private int cc;
	private Data dataNasc;
	private Set<Crime> crimes;
	
	//Construtor
	public Pessoa(String nome, int cc, Data dataNasc, Set<Crime> crimes) {
		this.nome=nome;
		this.cc=cc;
		this.dataNasc=dataNasc;
		this.crimes=crimes;		
	}
	
	public Pessoa(String nome, int cc, Data dataNasc) {
		this(nome,cc,dataNasc,new HashSet<Crime>());
	}
	
	//Métodos de seleção
	public String getName() {
		return this.nome;
	}
	public int getCC() {
		return this.cc;
	}
	public Data getDataNasc() {
		return this.dataNasc;
	}

	public String toString() {
		return nome + ", CC: " + cc + " Data Nasc.: " + dataNasc;
	}

	@Override
	public int hashCode() {
		return cc+dataNasc.hashCode()+nome.toLowerCase().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!(obj instanceof Pessoa))
			return false;
		Pessoa other = (Pessoa) obj;
		if (cc != other.cc)
			return false;
		if (dataNasc == null) {
			if (other.dataNasc != null)
				return false;
		} else if (!dataNasc.equals(other.dataNasc))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equalsIgnoreCase(other.nome))
			return false;
		return true;
	}

	
	
	
	
	
}
