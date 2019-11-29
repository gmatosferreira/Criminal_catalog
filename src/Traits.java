
public class Traits {

	private Double height;
	private char sex;
	private String nationality;
	private String skinColor;
	
	//Constructor
	public Traits(Double height, char sex, String nationality, String skinColor) {
		this.height=height;
		this.sex=sex;
		this.nationality=nationality;
		this.skinColor=skinColor;	
	}
	
	// Getters
	public Double getHeight() {
		return height;
	}
	public char getSex() {
		return sex;
	}
	public String getNationality() {
		return nationality;
	}
	public String getSkinColor() {
		return skinColor;
	}

	// Override method toString()
	public String toString() {
		return "Sex: " + sex + ", Nationality: " + nationality + ", Skin color: " + skinColor + ", Height: " + height;
	}
	
	
}
