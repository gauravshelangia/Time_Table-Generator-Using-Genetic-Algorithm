import java.util.*;

class Population {
	int [][] POP;
}	
public class generatechromosome {
	
	int row;
	int col;
	int pop;
	ArrayList<Population> population = new ArrayList<Population>();
	
	//Set implementation
	// constructor 
	generatechromosome(int x){
		InputData info = new InputData();
		info.Room();
		info.Day();
		info.TimeSlot();
		info.Courses();
		info.Faculty();
		col=info.MatrixCol();
		row=info.MatrixRow();
		this.pop=x;
	}
	

	// method to fill the matrix
	public int[][] fillmatrix(){
		int [][] chromosome = new int[row][col];
		Random randomGenerator = new Random();
		System.out.println("Value of Row and Col = "+row+" ,"+col);
		for(int i=0;i<col;i++){
			int trow = randomGenerator.nextInt(row);
			System.out.println("value of trow = "+trow);
			for(int j=0;j<row;j++){
				if(j==trow){
					chromosome[j][i]=1;
				}
				else{
					chromosome[j][i]=-1;
				}		
			}
		}
	return chromosome;
	}
	

	//generate chromosome
	public void generate(){
		for (int i=0;i<pop;i++){
			Population P = new Population();
			P.POP=fillmatrix();
			population.add(P);
			//show(population.get(i).POP);

			//System.out.println(population.get(i).POP);
		}
	}
	//print  matrix 
	public void show(int a[][] ){
		for(int j=0;j<row;j++){
			for(int k=0;k<col;k++){
				System.out.print("  " +a[j][k]);
			}
			System.out.print("\n");
		}	
	}
	
	// function to display the population
	public void displaychromosome( ){
		show(population.get(0).POP);
		for(int i=0;i<pop;i++){
			System.out.println("Population number = "+i);
			show(population.get(i).POP);
			//System.out.println(population.get(i).POP);
			System.out.println("\n");
		}
	}	
}
