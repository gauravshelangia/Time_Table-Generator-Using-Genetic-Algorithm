/*
 * Class of Time-Table IIITV
 */

import java.lang.*;
import java.util.*;

public class Mutator {

	Mutator(InputData INP, EnterEvents EV, Generatechromosome GCH) {

		this.INP = INP;
		this.EV = EV;
		this.GCH = GCH;

	}

	int[][][] mutate(int[][][] C) {
		int[][][] CH;
		int totalevents = EV.events.size();
		CH = C;

		System.out.println("Enter the mutation probability");
		Pm = IN.nextFloat();
		// number of column to replace noc
		noc = (int) Math.ceil(Pm * totalevents);

		// take a array to sore the columns to reschedule
		int[] A = generaterandom(noc, totalevents);

		// TODO find the place where it has scheduled
		for (int i = 0; i < A.length; i++) {
			resetones(C, A[i]);
			regenerateones(C, A[i]);

		}

		return CH;
	}

	// generate the random number noc and store them in a array (sorted array)
	int[] generaterandom(int noc, int total) {
		int[] A = new int[noc];
		boolean exist = false;

		for (int i = 0; i < noc; i++) {
			int x = Randgen.nextInt(total);
			if (i == 0) {
				A[i] = x;
			} else {

				for (int j = 0; j < i; j++)
					if (x == A[j]) {
						exist = true;
						j = i;
					} else {
						continue;
					}
			}

			if (!exist) {
				A[i] = x;
			}
		}

		// sort the array
		Arrays.sort(A);

		return A;
	}

	// Method to reset ones and minus one in zeroes in chromosome
	void resetones(int[][][] CHR, int event) {
		int m = CHR.length;
		int n = CHR[0].length;
		int p = CHR[0][0].length;

		for (int i = 0; i < p; i++) {
			for (int j = 0; j < m; j++) {
				if (CHR[j][event][p] == 1) {

					// all ones and minus one are now zero
					for (int k = event; k < n; k++) {
						CHR[j][k][i] = 0;
					}

					// end the outer for loop
					i = p;
				}
			}
		}
	}

	// Method to regenerate one OR reschedule particular activity
	void regenerateones(int[][][] C, int event) {

		int m = C.length;
		int n = C[0].length;
		int p = C[0][0].length;
		
		
	}

	// declaration of scanner and
	Scanner IN = new Scanner(System.in);
	Random Randgen = new Random();

	// declaration of variable
	float Pm;
	int noc;// number of column to reschedule by mutation

	// Declaration of object of other class
	InputData INP;
	EnterEvents EV;
	Generatechromosome GCH;

}
