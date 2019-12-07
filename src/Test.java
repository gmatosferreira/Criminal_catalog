import java.util.*;

import java.io.FileReader;
import java.io.IOException;

public class Test {
	
	public static void main(String[] arg){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Test program is running...");
		
		//Get info from tails.csv
		List<String> firstNames= new ArrayList<>();
		List<String> lastNames= new ArrayList<>();
		List<String> nationality= new ArrayList<>();
		Map<String, ArrayList<String>> crimesNamesAndTypes=new HashMap<>();
		List<String> skinColor= new ArrayList<>();
		
		String[] line;
		String crimeTemp="";
		ArrayList<String> crimeTypeTempList;
		try(Scanner input = new Scanner(new FileReader("src/Data/traits.csv"))) {
			while (input.hasNext()) {
				line=input.nextLine().split(",");
				firstNames.add(line[0]); //First name
				if(line.length>1) { //Last name
					lastNames.add(line[1]);
				}
				if(line.length>2) { //Nationality
					nationality.add(line[2]);
				}
				if(line.length>3) { //Type and name of crimes
					if(line[3].charAt(0)!='/') { //Type
						crimesNamesAndTypes.put(line[3], new ArrayList<>());
						crimeTemp=line[3];
						crimeTypeTempList=new ArrayList<>();
					}else { //Name
						crimeTypeTempList=crimesNamesAndTypes.get(crimeTemp);
						crimeTypeTempList.add(line[3].split("/")[1]);
						crimesNamesAndTypes.put(crimeTemp,crimeTypeTempList);
					}
				}
				if(line.length>4) { //Skin color
					skinColor.add(line[4]);
				}
			}
		}catch(IOException e) {
			System.err.printf("ERRO: %s\n", e.getMessage());
		}
		System.out.println("Acessed the file traits.csv and compiled names, nationalities, skin colors and crime names and types with sucess...");
		
		//Generate n random criminals
		int n=100;
		System.out.printf("\nGoing to generate %d criminals...\nPress ENTER to continue", n);
		sc.nextLine();
		Random randD;
		int rand, tempId;
		char tempSex;
		Double tempHeight;
		String tempString1, tempString2, tempString3;
		List<Criminal> criminals= new ArrayList<>();		
		HashSet<Integer> idsRecord = new HashSet<>();
		Date tempDate;
		Traits tempTraits;
		Criminal tempCriminal;
		Crime tempCrime;
		List<String> crimesNames=new ArrayList<String>(crimesNamesAndTypes.keySet());
		List<String> tempCrimesTypes;
		for(int i=0;i<n;i++) { //Create criminal
			System.out.printf("%d:\t",i+1);
			//Generate random numbers (int and double)
			rand=(int)(Math.random()*27644436)+1; //Between 1 and 27644437 (prime number)
			randD=new Random();
			//Create random name
			tempString1=firstNames.get(rand%firstNames.size())+" "+lastNames.get(rand%lastNames.size());
			//Generate random id (different from all generated before)
			do {
				tempId = (int)(Math.random()*899999999)+100000000;
			} while (idsRecord.contains(tempId));
			//Generate random sex based on the portuguese distribution (48% are male and 52 are female - source:PORDATA)
			tempSex = (rand%100<48) ? 'm' : 'f';
			//Generate random height, based on the portuguese distribution with a variance of 10cm (175 for male and 164 for female - source: Wikipedia)
			tempHeight = (tempSex=='m') ?  1.65 + (1.85 - 1.65) * randD.nextDouble() : 1.54 + (1.74 - 1.54) * randD.nextDouble();
			tempHeight= Math.floor(tempHeight * 100) / 100; //Truncate number to only 2 decimal digits
			//Generate random nationality
			tempString2=nationality.get(rand%nationality.size());
			//Generate random skincolor
			tempString3=skinColor.get(rand%skinColor.size());
			//Create traits
			tempTraits=new Traits(tempHeight, tempSex, tempString2, tempString3);
			//Generate random date of birth, based on sex and it's medium lifespan (78 for man and 83 for woman in 2017 - source:PORDATA)
			tempDate= (tempSex=='m') ? Date.randomDate(new Date(1,1,1922), new Date(31,12,2000)) : Date.randomDate(new Date(1,1,1917), new Date(31,12,2000));
			//Create criminal
			tempCriminal = new Criminal(tempString1, tempId, tempDate, tempTraits);
			//Add crimes
			for(int j=0; j<1+rand%5; j++) {
				rand=(int)(Math.random()*27644436)+1; //Between 1 and 27644437 (prime number)
				tempString1=crimesNames.get(rand%crimesNames.size()); //Get random category
				tempCrimesTypes=crimesNamesAndTypes.get(tempString1); //Get names of that category
				tempString2=tempCrimesTypes.get(rand%tempCrimesTypes.size()); //Get random name, inside category
				tempDate=Date.randomDate(new Date(tempCriminal.getDateOfBirth().getD(),tempCriminal.getDateOfBirth().getM(),tempCriminal.getDateOfBirth().getY()+18), Date.todayDate()); //Get random date between age 18 and today 
				tempCrime=new Crime(tempString2, tempString1, tempDate);
				tempCriminal.addCrime(tempCrime);
			}
			System.out.printf("%s\n",tempCriminal.toString());
			criminals.add(tempCriminal);
		}
		System.out.printf("Generated %d criminals!\n", n);
		
		//Test bloom filters
		boolean bloom=true;
		System.out.println("\nIniciating test to Bloom Filters... (IN DEVELOPMENT)\nPress ENTER to continue...");
		sc.nextLine();
		BloomFilter b1 = new BloomFilter(10,3);
		System.out.printf("Created Bloom Filter to insert %d elements with %d hash functions, which has a length of %d.\n", 10,3,b1.getArraySize());
		System.out.print("Adding the criminals at indices ");
		for(int k=0;k<100;k+=10) {
			b1.addElement(criminals.get(k));
			System.out.printf(" %d ",k);
		}
		System.out.println("to the bloom filter...");
		System.out.println("Criminals added, now checking if the elements 0,5,10,15,20,25,...,85,90,95 are present...\n");
		for(int l=0;l<100;l+=5) {
			if(l%10==0) {
				if(!b1.isMember(criminals.get(l))) {
					System.out.printf("\tERROR! Criminal at index %d with id %d was added but not identitied!\n",l,criminals.get(l).getId());
					bloom=false;
				}else {
					System.out.printf("\t%d present, as expected\n",l);
				}
			}else {
				if(b1.isMember(criminals.get(l))) {
					System.out.printf("\tERROR! Criminal at index %d with id %d was iddentified and should not have been added!\n",l,criminals.get(l).getId());
					bloom=false;
				}else {
					System.out.printf("\t%d not present, as expected\n",l);
				}
			}
		}
		
		if(bloom)
			System.out.println("All tests passed!");
		else
			System.out.println("Bloom filter has errors!");
		
		//minHash Testing
		System.out.println("\nMoving on to minHash testing...\nPress ENTER to start...");
		sc.nextLine();
		
		//List of criminals
		MinHash minHash = new MinHash(100, criminals);
		
		//List of suspects to be searched by Phisical Traits
		Set<HashSet<String>> suspectsT = new HashSet<>();
		Set<String> s = new HashSet<String>();
		s.add("earlyAdult"); s.add("m"); s.add("lightBrown"); s.add("1.83"); suspectsT.add(new HashSet<String>(s));
		s.clear(); s.add("middleAdult"); s.add("m"); s.add("creamWhite"); s.add("1.62"); suspectsT.add(new HashSet<String>(s));
		s.clear(); s.add("lateAdult"); s.add("m"); s.add("darkBrown"); s.add("1.74"); suspectsT.add(new HashSet<String>(s));
		s.clear(); s.add("elder"); s.add("m"); s.add("white"); s.add("1.8"); suspectsT.add(new HashSet<String>(s));
		s.clear(); s.add("middleAdult"); s.add("f"); s.add("brown"); s.add("1.5"); suspectsT.add(new HashSet<String>(s));
		
		//List of suspects to be searched by Crimes
		Set<HashSet<String>> suspectsC = new HashSet<>();
		s.clear(); /*s.add("Money forgery");*/ s.add("Economic crime"); /*s.add("Vandalism"); s.add("Offence against property");*/
		suspectsC.add(new HashSet<String>(s));
		s.clear(); /*s.add("Fire");*/ s.add("Public crimes"); /*s.add("Arson");*/ s.add("Offence against property"); /*s.add("Calumny");*/ s.add("Offence against honor");
		suspectsC.add(new HashSet<String>(s));

		
		//minHash search menu
		System.out.println("\n---- Search suspectsT in Criminal Catalog ----");
		System.out.println("1 - to start search by Crimes");
		System.out.println("2 - to start search by Phisical Traits");
		int option = sc.nextInt();
		if(option==1){
			System.out.println("\n-> Search by Crimes");
			System.out.printf("\nNumber of suspects: %d", suspectsC.size());
			for(HashSet suspect : suspectsC) {
				System.out.printf("\nSUSPECT: %s\n", suspect.toString());
				minHash.getSimilar(0.8, suspect, true);
				
			}
		}
		
		if(option==2){
			System.out.println("\n-> Search by Phisical Traits");
			System.out.printf("\nNumber of suspects: %d", suspectsT.size());
			for(HashSet suspect : suspectsT) {
				System.out.printf("\nSUSPECT: %s\n", suspect.toString());
				minHash.getSimilar(0.1, suspect, true);
				
			}
		}
		System.out.println("\n...End of tests.");
		sc.close();
	}

}
