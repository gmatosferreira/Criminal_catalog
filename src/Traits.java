
public class Traits {

	private Double height;
	private char sex;
	private Country Country;
	private String mother;
	private String father;
	
	//Construtor
	public Traits(Double height, char sex, Country Country, String mother, String father) {
		this.height=height;
		this.sex=sex;
		this.Country=Country;
		this.mother=mother;
		this.father=father;
	}	
	
	public Traits(Double height, char sex, Country Country) {
		this(height, sex, Country, null, null);
	}

	
	//Getters and Setters
	public Double getHeight() {
		return height;
	}

	public void setAltura(Double height) {
		this.height = height;
	}

	public char getSexo() {
		return sex;
	}

	public void setSexo(char sex) {
		this.sex = sex;
	}

	public Country getNacionalidade() {
		
		return Country;
	}

	public void setNacionalidade(Country Country) {
		this.Country = Country;
	}

	public String getMae() {
		return mother;
	}

	public void setMae(String mother) {
		this.mother = mother;
	}

	public String getPai() {
		return father;
	}

	public void setPai(String father) {
		this.father = father;
	}
	
	
	
	
}
