public class BloomFilter {
	
	private int nElements=0; //Number of elements added
	private int elementsInsert; //Max number of elements that will be inserted
	private boolean[] bl; //Bloom filter (true for 1 and flase for 0)
	private int[] hashMultiplier;
	private int[] hashIncrementer;
	
	
	//Constructor
	public BloomFilter(int elementsInsert, int arraySize) {
		this.elementsInsert=elementsInsert;
		this.bl=new boolean[arraySize];
		int nHash=(int) Math.round(arraySize*Math.log(2)/elementsInsert);
		hashMultiplier=new int[nHash];
		hashIncrementer=new int[nHash];
		for(int i=0; i<nHash; i++) {
			hashMultiplier[i]=(int) Math.round(Math.random()*27644437); //One of Bell primes
			hashIncrementer[i]=(int) Math.round(Math.random()*16769023); //One of Carol primes
		}
	}
	
	//Methods
	public <T> boolean addElement(T element) {
		if(nElements+1<=elementsInsert) { //Verify if number of elements to insert isn't greater than max value
			nElements++;
			int hCode=element.hashCode();
			for(int i=0; i<hashMultiplier.length; i++) {
				int indice=Math.abs((hCode*hashMultiplier[i]+hashIncrementer[i])%bl.length);
				bl[indice]=true;
			}
		}else {
			return false;
		}
		return true;
	}
	
	public <T> boolean isMember(T element) {
		int hCode=element.hashCode();
		for(int i=0; i<hashMultiplier.length; i++) {
			int indice=Math.abs((hCode*hashMultiplier[i]+hashIncrementer[i])%bl.length);
			if(!bl[indice]) {
				return false;
			}
		}
		return true;
	}
	
	//Getters
	public int getHashNumber() {
		return hashMultiplier.length;
	}
	
	public int getArraySize() {
		return bl.length;
	}

}
