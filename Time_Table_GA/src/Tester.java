import java.lang.*;
import java.util.*;


public class Tester {
	public static void main (String args[]){
		
		//To input the Information
		/*InputData info = new InputData();
		info.Room();
		info.Day();
		info.TimeSlot();
		info.Courses();
		info.Faculty();
		*/
		
		// Generate the population
		Scanner GP = new Scanner(System.in);
		int Psize;
		System.out.println("Enter the population size");
		Psize = GP.nextInt();
		generatechromosome gench = new generatechromosome(Psize);
		//generatechromosome genchr = new generatechromosome();
		gench.generate();
		// display population
		gench.displaychromosome();
		
		
	}

}
