public class Crime {

	private String nome;
	private String tipo;
	private int id;
	private Data dataOcorr;
	
	//Construtor
	public Crime(String nome, String tipo, int id, Data dataOcorr) {
		this.nome=nome;
		this.tipo=tipo;
		this.id=id;
		this.dataOcorr=dataOcorr;
	}
	

	//Métodos de seleção
	public String getNome() {
		return this.nome;
	}
	public String getTipo() {
		return this.tipo;
	}
	public int getId() {
		return this.id;
	}
	public Data getDataOcorr() {
		return this.dataOcorr;
	}

	public String toString() {
		return nome + ", Tipo: " + tipo + ", Id: " + id + " Data Ocorr: " + dataOcorr;
	}


	
}
