import java.util.Random;

public class LocalTests {

	public static void main(String[] arg) {
		
		int min=10;
		int max=10;
		Random r = new Random();
		System.out.printf("Inteiro aleatrório entre %d e %d é %d.\n",min,max,r.nextInt((max - min) + 1) + min);
		//https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
		
		for(int i=0; i<20;i++) {
			System.out.printf("Random date: %s\n", Date.randomDate(new Date(6,12,2019), new Date(8,12,2020)));		
		}
		//Random double
		Double rangeMin=1.5, rangeMax=3.7;
		System.out.printf("Random double %f\n",rangeMin + (rangeMax - rangeMin) * r.nextDouble());
		//https://stackoverflow.com/questions/3680637/generate-a-random-double-in-a-range/32808589
		
		Criminal c = new Criminal("Goncalo",123, new Date(30,11,2000), new Traits(1.70,'m',"Portugues","Caucasiano"));
		System.out.printf("%s\n",c.getDateOfBirth());
		System.out.printf("%s\n",new Date(c.getDateOfBirth().getD(),c.getDateOfBirth().getM(),c.getDateOfBirth().getY()+18));
		System.out.printf("%s\n",c.getDateOfBirth());
		
		System.out.println("Assertions");
		assert false;
		
	}
}
