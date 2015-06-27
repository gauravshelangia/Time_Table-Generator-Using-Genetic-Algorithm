import java.lang.*;
import java.util.*;


@SuppressWarnings("unused")
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
		
		// Take input
		Input INPUT = new Input();
		INPUT.takeinput();
		
		
		
		// To generate the initial population
		@SuppressWarnings("resource")
		Scanner GP = new Scanner(System.in);
		int Psize;
		System.out.println("Enter the population size");
		Psize = GP.nextInt();
		generatechromosome gench = new generatechromosome(INPUT, Psize);
		gench.generate();
		gench.displaychromosome();
		
		// To calculate the fitness value of population
		FitnessCalculator fit = new FitnessCalculator(gench,INPUT);
		fit.fitnessvalue();
	
		// Mutator class operation
		Mutator mut = new Mutator(gench,INPUT);
		float P;
		int [][] AM;
		System.out.println("enter the mutation probability");
		P = GP.nextFloat();
		System.out.println("the mutation probability" +P );
//
//		while(fit.){
//			
//		}
//		mut.mutate(gench.population.get(1).POP, P);
//		fit.gench.show(AM);
		
		
	}

}
