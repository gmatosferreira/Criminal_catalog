import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CriminalDataBase {
	
public static void main(String[] arg){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Iniciating CriminalDataBase...");
		
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
			System.out.println("Accessing src/Data/traits.csv to compile a list of traits...");
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
		System.out.println("Acessed the file and compiled names, nationalities, skin colors, crime names and types with sucess!");
		
		//Generate n random criminals
		System.out.print("How many criminals do you want to create? ");
		int n=sc.nextInt();
		System.out.printf("\nGenerating %d criminals...\n", n);
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
		
		try(PrintWriter databaseFile = new PrintWriter(new File("src/Database/criminals.txt"))){
			for(int i=0;i<n;i++) { //Create criminal
				System.out.print(".");
				//Generate random numbers (int and double)
				rand=(int)(Math.random()*27644436)+1; //Between 1 and 27644437 (prime number)
				randD=new Random();
				//Create random name
				tempString1=firstNames.get(rand%firstNames.size())+" "+lastNames.get(rand%lastNames.size());
				for(int m=0;m<rand%3+1;m++) { //Generate names with at least 3 names and a maximum of 5
					rand=(int)(Math.random()*27644436)+1; //Between 1 and 27644437 (prime number)
					tempString1+=" "+lastNames.get(rand%lastNames.size());
				}
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
				criminals.add(tempCriminal);
				databaseFile.printf("%d:\t%s\n",i, tempCriminal.toString());
			}
			databaseFile.close();
			System.out.println("Remember that for testing purposes, the criminals created will be availabe at src/DataBase/criminals.txt.s");
		}catch(IOException e) {
			System.err.printf("ERROR WHILE COPYING DATABASE TO FILE src/Database/CRIMINALS.TXT: %s\n", e.getMessage());
		}
		System.out.printf("\n%d criminals were created with sucess!\n", n);
		
		//Adding climinals to Bloom Filters
		System.out.print("\nGoing to add criminals to Bloom Filters...\nHow many hashFunctions do you want to consider? ");
		int h=sc.nextInt();
		BloomFilter bloom = new BloomFilter(n,h);
		BloomFilter bloomNames = new BloomFilter(n,h);
		BloomFilter bloomNamesSingle = new BloomFilter(n*5,h*2); //At the worst scenarium, everyone will have 5 names
		System.out.printf("\nCreated Bloom Filters to insert %d elements with %d hash functions, with a length of %d.\n", n,h,bloom.getArraySize());
		System.out.println("Adding the criminals to the bloom filters...");
		for(int k=0;k<n;k++) {
			bloom.addElement(criminals.get(k));
			bloomNames.addElement(criminals.get(k).getName().trim().toLowerCase());
			String[] tempNames = criminals.get(k).getName().split(" ");
			for(int o=0; o<tempNames.length; o++) {
				bloomNamesSingle.addElement(tempNames[o].trim().toLowerCase());
			}
			System.out.print(".");
		}
		System.out.println("\nAll the criminals were added to the Bloom Filter with sucess!\n");
		sc.nextLine();
		
		
		//Menu
		int op;
		boolean menu=true;
		do {
			System.out.print("\nPress ENTER to continue...");
			sc.nextLine();
			System.out.println("\n\n----------------MENU----------------");
			System.out.printf("%s", "1 - Check if full name is on the database\n");
			System.out.printf("%s", "2 - Check if a name is on the database\n");
			System.out.printf("%s", "0 - EXIT\n");
			System.out.println("------------------------------------");
			System.out.println("Remember that for testing purposes, the criminals created are availabe at src/DataBase/criminals.txt.s");
			System.out.println("------------------------------------");
			System.out.print("Option: ");
			op=sc.nextInt();
			String search;
			switch(op) {
				case(1):
					System.out.println("\n\nCheck full name------------------------------------");
					System.out.print("What full name are you looking for?\n");
					sc.nextLine();
					search = sc.nextLine();
					search=search.trim().toLowerCase();
					searchBloom(bloomNames,search);
					break;
				case(2):
					System.out.println("\n\nCheck name------------------------------------");
					System.out.print("What name (only one accepted) are you looking for?\n");
					search = sc.next();
					search=search.trim().toLowerCase();
					searchBloom(bloomNamesSingle,search);
					break;
				default:
					System.out.printf("\n\nExiting the program...\n");
					menu=false;
					break;
			}
		}while(menu);
		
		
		//minHash Testing
		System.out.println("\n\nMoving on to MinHash testing...");
		System.out.println("Press ENTER to start...");
		sc.nextLine();
		
		System.out.printf("\nGenerating MinHash signature matrices for the %d criminals...\n", n);
		MinHash minHash = new MinHash(100, criminals); //List of criminals
		System.out.println("Signature matrices created with success!");		
		
		//Generate n random suspects
		System.out.print("\nHow many suspects do you want to create? ");
		n=sc.nextInt();		
		
		System.out.println("\nGenerating list of suspects...\n");

		int cont=1;
		String[] age=null, sk=null, hgt=null, sx=null, cr=null;
		try(Scanner input = new Scanner(new FileReader("src/Data/suspects.txt"))) {
			//System.out.println("Accessing src/Data/suspects.txt to compile a list of traits for suspects...");
			while (input.hasNext()) {
				if(cont==1) age=input.nextLine().split(",");
				if(cont==2) sk=input.nextLine().split(",");
				if(cont==3) hgt=input.nextLine().split(",");
				if(cont==4) sx=input.nextLine().split(",");
				if(cont==5) cr=input.nextLine().split(",");
				cont+=1;
			}
		}catch(IOException e) {
			System.err.printf("ERRO: %s\n", e.getMessage());
		}
		
		
		Set<HashSet<String>> suspects = new HashSet<>();
		Set<String> s = new HashSet<String>();
		Random r=new Random();
		int rNumber;
		for(int i=0;i<n;i++) {
			rNumber=r.nextInt(age.length); s.add(age[rNumber]); //get age
			rNumber=r.nextInt(sk.length); s.add(sk[rNumber]); //get skin color
			rNumber=r.nextInt(hgt.length); s.add(hgt[rNumber]); //get height
			rNumber=r.nextInt(sx.length); s.add(sx[rNumber]); //get sex
			rNumber=r.nextInt(cr.length); s.add(cr[rNumber]); //get crime
			
			suspects.add(new HashSet<String>(s));
			s.clear();
		}
				
		//minHash search menu
		menu=true;
				
		List<Criminal> match = null;
		do {
			System.out.println("\n");
			System.out.println("----------------MENU----------------");
			System.out.println("1 - See matches for Suspects");
			System.out.println("0 - EXIT");
			System.out.println("------------------------------------");
			System.out.print("Option: ");
			op=sc.nextInt();
			switch(op) {
			case(1):
				System.out.println("\nSearch suspects's matches...");
				for(HashSet suspect : suspects) {
					System.out.printf("\n\nSUSPECT: %s\n", suspect.toString());
					match = minHash.getSimilar(0.5, suspect);
					System.out.printf("%d criminals match this suspect!\n", match.size());
					if(match.size()>0) {
						System.out.println("------------------------------------");
						System.out.println("See list of criminals matching this suspect?");
						System.out.println("1 - If yes");
						System.out.println("------------------------------------");
						System.out.print("Option: ");
						n=sc.nextInt();
						if(n==1) {
							for(Criminal m : match) {
								System.out.println(m);
							}
						} else {
							continue;
						}
					}
				}
				break;
			default:
				System.out.printf("\n\nExiting the program...\n");
				menu=false;
				break;
			}
		}while(menu);
		

		System.out.println("\n...End of tests.");
		
		
		sc.close();
	}

	private static <T> void searchBloom(BloomFilter b, T search) {
		if(b.isMember(search))
			System.out.println(search.toString()+" is in the database!");
		else
			System.out.println(search.toString()+" is NOT in the database!");
	}
	
	
	

}
