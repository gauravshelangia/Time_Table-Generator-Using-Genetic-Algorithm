import java.util.*;

class Population {
	public int[][] POP;
}

public class generatechromosome {

	// constructor to take input
	generatechromosome(Input O, int x) {
		inp = O;
		col = O.takeinput.get(0).MatrixCol();
		row = O.takeinput.get(0).MatrixRow();
		this.pop = x;
	}

	// empty constructor
	generatechromosome() {

	}

	// method to fill the matrix
	public int[][] fillmatrix() {
		int[][] chromosome = new int[row][col];
		Random randomGenerator = new Random();
		//System.out.println("Value of Row and Col = " + row + " ," + col);
		GO: for (int i = 0; i < col; i++) {
			int trow;
			// To check that time slot is free or not and lecturer is not double
			// booked
			boolean mat = true;
			boolean dlec = true, minus;
			minus = allminus(chromosome, i);
			//System.out.println("Here for this col , value of minus" + i + "and"
			//		+ minus);
			if (!(minus)) {
				//System.out.println("value of minus ");
				// regenerate the matrix
				//System.out
				//		.println("Here for this col , restart the fill matrix"
					//			+ i);
				i = -1;
				if (i == -1) {
					for (int x = 0; x < row; x++) {
						for (int y = 0; y < col; y++) {
							chromosome[x][y] = 0;
							;
						}
					}
				}
				continue GO;
			} else {
				Vector<Integer> rejected = new Vector<Integer>(1, 1);
				do {
					trow = randomGenerator.nextInt(row);
					// System.out.println("value of trow is = "+trow);
					if (rejected.size() == row) {

						i = -1;
						if (i == -1) {
							for (int x = 0; x < row; x++) {
								for (int y = 0; y < col; y++) {
									chromosome[x][y] = 0;
									;
								}
							}
						}
						continue GO;
					}

					if (allchecked(rejected, trow)) {
						rejected.add(trow);
					}

					mat = matvaluecheck(chromosome, trow, i);
					dlec = doublelec(chromosome, trow, i);
//					System.out.println("Matrix index where would  be 1 " + trow
//							+ "and " + i);
					// show(chromosome);

				} while (!(mat && dlec));

//				System.out
//						.println("Row number and column number where should be 1 "
//								+ trow + "and " + i);
				chromosome[trow][i] = 1;
				// fill -1 such that there would be no two lecture in the same
				// room at same time -1 \n -1 \n -1
				int DAY = (inp.takeinput.get(0).getday(trow) + 1)
						* inp.takeinput.get(0).h;
				for (int j = trow + 1; j < DAY; j++) {
					chromosome[j][i] = -1;
				}
				// fill -1 along row -1 -1 -1 -1
				int q = (inp.takeinput.get(0).getroom(i) + 1)
						* inp.takeinput.get(0).lc;
				for (int a = i + 1; a < q; a++) {
					chromosome[trow][a] = -1;
				}
				// show(chromosome);
				// System.out.println("\n");
			}
		}
		return chromosome;
	}

	// Method to verify that lecturer is not double booked
	public boolean doublelec(int A[][], int TROW, int TCOL) {
		int lcurrent, lpast;
		lcurrent = inp.takeinput.get(0).getlecturer(TCOL);
		boolean doublecheck = true;
		if (TCOL == 0) {
			doublecheck = true;
		} else {
			for (int x = 0; x < TCOL; x++) {
				if (A[TROW][x] == 1) {
					lpast = inp.takeinput.get(0).getlecturer(x);
					if (lcurrent == lpast) {
						doublecheck = false;
						x = TCOL;
					}
				}
			}
		}
		return doublecheck;
	}

	// method to find value at any matrix index
	public boolean matvaluecheck(int a[][], int i, int j) {
		if (a[i][j] == -1) {
			return false;
		} else {
			return true;
		}
	}

	// Check if all -1 is in the column
	boolean allminus(int A[][], int c) {
		boolean all = true;
		int count = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i][c] == -1) {
				count++;
			}
		}
//		System.out.println("Value of count is = " + count);
		if (count == A.length) {
			all = false;
		}
		return all;
	}

	// To check that all row are randomly generated and create new chromosome
	boolean allchecked(Vector<Integer> V, int q) {
		boolean allcheck = true;
//		System.out.println("Value of vector size is = " + V.size());
		for (int i = 0; i < V.size(); i++) {
			if (q == V.get(i)) {
				allcheck = false;
			}
		}
		return allcheck;
	}

	// generate chromosomes
	public void generate() {
		for (int i = 0; i < pop; i++) {
			Population P = new Population();
			P.POP = fillmatrix();
			population.add(P);
		}
	}

	// print matrix
	public void show(int a[][]) {
		for (int j = 0; j < row; j++) {
			for (int k = 0; k < col; k++) {
				System.out.print("  " + a[j][k]);
			}
			System.out.print("\n");
		}
	}

	// function to display the population
	public void displaychromosome() {
		for (int i = 0; i < pop; i++) {
			System.out.println("Population number = " + i);
			show(population.get(i).POP);
			System.out.println("\n");
		}
	}

	int row;
	int col;
	int pop;
	Input inp;

	// public ArrayList<InputData> Inp = new ArrayList<InputData>();
	public ArrayList<Population> population = new ArrayList<Population>();

}
