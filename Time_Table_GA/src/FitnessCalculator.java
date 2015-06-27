import java.io.*;
import java.lang.*;
import java.util.*;

public class FitnessCalculator {

	FitnessCalculator(generatechromosome O, Input I) {
		take = I;
		gench = O;
		C = take.takeinput.get(0).c;
		COL = O.col;
		ROW = O.row;
		this.x = O.pop;
	}

	// method to check constraint Course cannot have more than one lecture per
	// day
	public ArrayList<Vector<Integer>> onelecday(int k) {
		int conflictday = 0;
		A = new ArrayList<Vector<Integer>>();

		for (int i = 0; i < C; i++) {
			Vector<Integer> VEC = new Vector<Integer>(0, 1);
			A.add(VEC);
		}

		for (int i = 0; i < COL; i++) {
			cou = take.takeinput.get(0).getcourse(i);

			for (int j = 0; j < ROW; j++) {
				int pos = gench.population.get(k).POP[j][i];
				if (pos == 1) {
					day = take.takeinput.get(0).getday(j);
					if (A.get(cou).size() == 0) {
						A.get(cou).add(day);
					} else {
						boolean add=false;
						for (int c = 0; c < A.get(cou).size(); c++) {
							if (A.get(cou).get(c) == day) {
								conflictday += 10;
								add=true;
							} 
						}
						if(!add){
								A.get(cou).add(day);
						}
					}
				}
			}
		}
		return A;
	}

	// method to check the there should be 3 lecture per week
	public int threelecweek(int s) {
		int conflictweek = 0;
		ArrayList<Vector<Integer>> X;
		X = onelecday(s);
		for (int i = 0; i < take.takeinput.get(0).c; i++) {
			if (X.get(i).size() == 3) {
				conflictweek += 0;
			} else {
				conflictweek += 10;
			}
		}
		return conflictweek;
	}

	// Method to calculate the fitness value
	public void fitnessvalue() {
		fitness = new int [x];
		for (int u = 0; u < x; u++) {
			
			fitness[u] = threelecweek(u);
			System.out.println("The Fitness value is \t" + fitness[u]);
		}
	}

	int C, COL = 0, ROW, x, day, cou;
	int [] fitness;
	Input take;
	generatechromosome gench;
	ArrayList<Vector<Integer>> A;

}
