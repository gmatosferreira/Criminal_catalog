import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MinHash {

	private int numHash;
	private List<Criminal> criminals;
	private int[][] minHashTraits;
	private List<Criminal> similar;
	

	public MinHash(int numHash, List<Criminal> criminals){
		this.numHash = numHash;
		this.criminals = criminals;
		minHashTraits = new int[criminals.size()][numHash];

        makeMinHash(criminals);
    }
	
	public void makeMinHash(List<Criminal> criminals) {
		
		for(int i = 0; i < criminals.size(); i++) {
			for (int j = 0; j < criminals.get(i).getShingleTraits().size(); j++) {
				for(int k = 0; k < numHash; k++) {
					int hk = hashCode(criminals.get(i).getShingleTraits());
					minHashTraits[i][k] = hk;
				}
			}
		}

	}
	
	public int hashCode(Set<String> set) {
		int p = 1007711;
		
		int[] randA = new int[30], randB = new int[30];
		Random r = new Random(p);
		for (int i = 0; i < randA.length; i++) {
			randA[i] = r.nextInt();
            randB[i] = r.nextInt();
		}
		
		long min = Integer.MAX_VALUE;
		for (String str : set) {
			long hk = 0;
			for (int i = 0; i < str.length(); i++) {
				hk += (randA[i]*str.charAt(i) + randB[i]) % p;
			}
			
			if (hk < min)
				min = hk;
		}
		
		return (int)min;
	}
	
	public List<Criminal> getSimilar(double dist, Set<String> setother) {
		List<Criminal> match = new ArrayList<>();
		
		int[] other = new int[numHash];
		for(int k = 0; k < numHash; k++) {
			int hk = hashCode(setother);
			other[k] = hk;
		}

		for (int i = 0; i < minHashTraits.length; i++) {
			double dist2 = getDistance(minHashTraits[i], other);
			if ( dist2 <= dist) {
				match.add(criminals.get(i));
				//System.out.println("Similar " + criminals.get(i).toString());
			}
		}

		return match;
	}
	
	public double getDistance(int[] a, int[] b) {
		int sum = 0;
		
		for (int i = 0; i < numHash; i++) {
			if (a[i] == b[i]) {
				sum++;
			}
		}
		
		return 1 - (double)sum/numHash;
	}
}