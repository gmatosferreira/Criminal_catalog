public class Crime {
	
	private String name;
	private String category;
	private Date date;
	
	//Construtor
	public Crime(String name, String category, Date dataOcorr) {
		this.name=name;
		this.category=category;
		this.date=dataOcorr;
	}

	//Métodos de seleção
	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}
	
	public String getDate() {
		return date.toString();
	}	
	
}
