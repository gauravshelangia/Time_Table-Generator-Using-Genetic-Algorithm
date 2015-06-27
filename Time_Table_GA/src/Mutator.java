import java.util.*;

public class Mutator {

	// constructor
	Mutator(generatechromosome G, Input I) {
		gch = G;
		this.I = I;
	}

	public void mutate(int A[][], float P) {
		r = A.length;
		c = A[0].length;
		int matrix[][] = A;
		nom = (int) Math.ceil(P * c);
		C = new Vector<Integer>(1, 1);
		int ROW;
		// generate the column number which will go under mutation
		// Iterator Cit = C.iterator();
		for (int i = 0; i < nom; i++) {
			int Random = randomgen.nextInt(c);
			boolean add = true;
			for (int d = 0; d < C.size(); d++)
				if (C.get(d) == Random) {
					add = false;
				}
			if (add) {
				C.add(Random);
			}
		}
		// sort the generate column number in order to minimize the conflict of
		// three basic constraints
		Collections.sort(C);
		for (int i = 0; i < C.size(); i++) {
			System.out.println("Column which will go under changes is ="
					+ C.get(i));
		}

		// Mutating the chromosome
		GO: for (int j = 0; j < C.size(); j++) {
			int rowo = makezeroes(matrix, C.get(j));
			System.out.println("Value of rowo is " + rowo);
			int[][] mmatrix = makecompatible(matrix, rowo, C.get(j));
			gch.show(mmatrix);
			// matrix[R][C.get(j)] = 0;
			boolean mat = true;
			boolean dlec = true, minus;
			minus = gch.allminus(matrix, C.get(j));
			
			if (!(minus)) {
				// regenerate the matrix
				j = -1;
				if (j == -1) {
					matrix = A;
					break GO;
				}
			} else {
				Vector<Integer> rejected = new Vector<Integer>(1, 1);
				boolean canadd = true;
				do {
					trow = randomgen.nextInt(r);
					
					if (rejected.size() == r) {
						j = -1;
						if (j == -1) {
							matrix = A;
							break GO;
						}
					}

					if (gch.allchecked(rejected, trow)) {
						rejected.add(trow);
					}

					mat = gch.matvaluecheck(matrix, trow, C.get(j));
					dlec = gch.doublelec(matrix, trow, C.get(j));
					int b = (I.takeinput.get(0).getroom(C.get(j)) + 1)
							* I.takeinput.get(0).lc;

					for (int t = C.get(j) + 1; t < b; t++) {
						if (matrix[trow][t] == 1) {
							canadd = false;
						}
					}

				} while (!(mat && dlec && canadd));

				matrix[trow][C.get(j)] = 1;

				// fill -1 such that there would be no two lecture in the same
				// room at same time -1 \n -1 \n -1
				int DAY = (I.takeinput.get(0).getday(trow) + 1)
						* I.takeinput.get(0).h;

				for (int s = trow + 1; s < DAY; s++) {
					matrix[s][C.get(j)] = -1;
				}

				// fill -1 along row -1 -1 -1 -1
				int q = (I.takeinput.get(0).getroom(C.get(j)) + 1)
						* I.takeinput.get(0).lc;
				
				for (int a = C.get(j) + 1; a < q; a++) {
					matrix[trow][a] = -1;
				}
			}
		}
		A=matrix;
//		return matrix;
	}

	//
	int[][] makecompatible(int A[][], int ro, int co) {
		Vector<Integer> RONE = new Vector<Integer>(0, 1);
		Vector<Integer> CONE = new Vector<Integer>(0, 1);
		// length of matrix;
		int l = (I.takeinput.get(0).getroom(co) + 1) * I.takeinput.get(0).lc;
		int lecom = I.takeinput.get(0).lc;
		// width of matrix
		int w = (I.takeinput.get(0).getday(ro) + 1) * I.takeinput.get(0).h;
		int ts = I.takeinput.get(0).h;
		for (int e = l - lecom; e < l; e++) {
			for (int t = w - ts; t < w; t++) {
				if (A[t][e] == 1) {
					RONE.add(t);
					CONE.add(e);
				}
			}
		}
		
		// fill zeroes in that block
		for (int i = l - lecom; i < l; i++) {
			for (int j = w - ts; j < w; j++) {
				A[j][i] = 0;
				System.out.println("Here it is zero ");
			}
		}
//		gch.show(A);
		// again fill that block
		for (int i = 0; i < RONE.size(); i++) { 
			int coltf = CONE.get(i);
			int rowtf = RONE.get(i);

			A[rowtf][coltf] = 1;
			// fill -1 such that there would be no two lecture in the same
			// room at same time -1 \n -1 \n -1
			for (int s = rowtf + 1; s < w; s++) {
				System.out.println("Value of w is = " + w);
				A[s][coltf] = -1;
			}

			// fill -1 in row
			for (int s = coltf + 1; s < l; s++) {
				A[rowtf][s] = -1;
			}
		}
		return A;
	}

	// make all ones to zeroes of particular column
	public int makezeroes(int d[][], int l) {
		int x = 0;
		for (int i = 0; i < d.length; i++) {
			if (d[i][l] == 1) {
				d[i][l] = 0;
				x = i;
			}
		}
		return x;
	}

	// field declaration
	int r, trow;
	int c;
	int nom;
	Vector<Integer> C;

	Random randomgen = new Random();
	generatechromosome gch;
	Input I;
}
