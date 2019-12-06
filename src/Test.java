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
		
		//Test bloom filters IN DEVELOPMENT
		System.out.println("\nIniciating test to Bloom Filters... (IN DEVELOPMENT)");
		
		sc.close();
	}

}
