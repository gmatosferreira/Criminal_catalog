
public class BloomFilterTest {
	
	public static void main(String[] arg) {
		System.out.println("Bloom Filter Test Program");
		
		BloomFilter bl = new BloomFilter(20,100);
		System.out.println(bl.getHashNumber());
		System.out.println(bl.getArraySize());
		
		bl.addElement("Alemanha");
		bl.addElement("Espanha");
		bl.addElement("Portugal");
		System.out.println("Added Alemanha, Espanha e Portugal.");
		
		System.out.println("Checking for...");
		System.out.println("Portugal: "+bl.isMember("Portugal"));
		System.out.println("Suiça: "+bl.isMember("Suiça"));
		System.out.println("Argentina: "+bl.isMember("Argentina"));
		System.out.println("Alemanha: "+bl.isMember("Alemanha"));
		System.out.println("Espanha: "+bl.isMember("Espanha"));
		System.out.println("Japão: "+bl.isMember("Japão"));
		System.out.println("Paraguai: "+bl.isMember("Paraguai"));
		System.out.println("Portucale: "+bl.isMember("Portucale"));
		System.out.println("Almanha: "+bl.isMember("Almanha"));
		
	}

}
