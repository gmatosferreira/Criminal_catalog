import java.util.Set;

public class SimilarSearch {
	
	private String name;
	private Set<String> dataSet;
	
	
	public SimilarSearch(String name, Set<String> dataSet) {
			this.name=parseData(name);
			this.dataSet=dataSet;
		}


	private String parseData(String d) {
		d = d.replaceAll("[^a-zA-Z0-9 ]+", "");
		return d;
	}
	
	
	public Set<String> getShingle() {
		Set<String> data = null;
		for(String d : dataSet) {
			data.add(parseData(d));
		}
		return data;
	}
	
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		if(name=="") name = "unknow";
		return "Name: " + name + "; " + dataSet;
	}

	
	
}
