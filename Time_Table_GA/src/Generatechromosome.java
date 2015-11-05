/*
 * class of Time-Table-IIITV
 */

import java.util.*;

public class Generatechromosome {

	// TODO Write code to generate the code for generation of chromosomes
	public Generatechromosome(InputData I, EnterEvents E, int nop) {
		this.ID = I;
		this.EV = E;
		this.nop = nop;
		m = EV.SlotSeq.size();
		n = EV.events.size();
		p = EV.SpaceSeq.size();

		totalevents = EV.events.size();
	}

	// method to fill3d chromosomes-matrix
	int[][][] fillmatrix() {
		int[][][] chromosome = new int[m][n][p];
		
		String[] PLACE = new String[2];
		NSP = new Nospace();
		boolean firsttime = true;
		int index = 0, tsr = 0;

		for (int i = 0; i < totalevents; i++) {
			boolean Scheduled = false;

			do {
				// get total number of places where it can be scheduled
				int q = getplacesize(EV.Places, i);
				if (q == 0) {
					Scheduled = true;
					NSP.NotScheduled.add(i);
				} else {
					ArrayList<Places> AllPlaceGen = new ArrayList<Places>(1);

					// Generate random number from 0-q to get a suitable placeto
					// schedule the event
					int gp = RandGen.nextInt(q);
					PLACE = getplace(gp, EV.Places, i);
					if (firsttime) {
						Places PLP = new Places();
						PLP.Building = PLACE[0];
						PLP.rooms.add(PLACE[1]);
						AllPlaceGen.add(PLP);
						firsttime = false;
					} else {

						// to check that all place is generated or not
						if (EV.Places.get(i).PLACE.size() == AllPlaceGen.size()) {
							NSP.NotScheduled.add(i);
						} else {
							for (int g = 0; g < AllPlaceGen.size(); g++) {
								Places PLP = new Places();
								if (PLACE[0] == AllPlaceGen.get(g).Building) {
									boolean exist = true;
									for (int c = 0; c < AllPlaceGen.get(g).rooms
											.size(); c++) {
										if (PLACE[1].equals(PLACE[1])) {
											exist = true;
										} else {
											exist = false;
											c = AllPlaceGen.get(g).rooms.size();
										}

									}
									if (exist == false) {
										PLP.Building = PLACE[0];
										PLP.rooms.add(PLACE[1]);
										AllPlaceGen.add(PLP);

									}

								}
							}
						}
					}

					// pass PLACE to "getspaceindex" to get index of a 2d matrix
					// from 3d matrix
					index = getspaceindex(PLACE);

					// get occurrence of activity per week
					int occ = EV.events.get(i).occurence;
					int [] A = new int[occ];

					// get time slot required to complete the activities
					tsr = gettimeslots(i);

					int[] B = new int[occ];
					// initialize the b array with -1
					for (int f = 0; f < occ; f++) {
						B[f] = -1;
					}

					// generate random slot
					for (int j = 0; j < occ; j++) {

						GO: {
							A[j] = RandGen.nextInt();
							CHECKDAY CKD = new CHECKDAY();

							// Pass this array to method check-day
							CKD = checkday(A[j], tsr, B);
							boolean nominus = true;
							if (CKD.daydiffer) {
								for (int l = A[j]; l < A[j] + tsr; l++) {
									int d = getslot(l);
									if (chromosome[l][i][index] == -1
											|| checktimeslotmess(d)
											|| doublelec(chromosome, l, i)
											|| doublestudent(chromosome, tsr, i)) {

										nominus = false;

									}
								}
								if (!nominus) {
									break GO;
								}
							} else {
								break GO;
							}
						}
					}

				}
				Scheduled = true;

			} while (!Scheduled); // !add

			// fill -1 on selected timelot and space and for particular activity
			fillminusplus(chromosome, A, i, index, tsr);

		}

		return chromosome;
	}

	// method to check scheduled activities is at right position -- No two
	// activities is in same day and activities is completed on same day // tr =
	// timeslot required

	CHECKDAY checkday(int a, int tr, int[] B) {

		CHECKDAY CHD = new CHECKDAY();
		boolean add = false;
		boolean daydiffer = true;
		int cday = -1;
		int pday = -1;

		// to check that activity completes in one day or not
		for (int i = a; i < a + tr; i++) {
			cday = EV.SlotSeq.get(i).day;
			if (cday == pday) {
				add = true;
			} else {
				add = false;
				i = a + tr;
			}
			pday = cday;
		}

		// check if this day is already used or not
		if (add) {
			for (int i = 0; i < B.length; i++) {
				if (cday == B[i]) {
					daydiffer = false;
				}
			}
		}
		CHD.daydiffer = daydiffer;
		CHD.day = cday;
		return CHD;
	}

	// method to check that any teacher is not double booked in one timeslot
	// or there is no clash of time slot of time slot of any teacher
	boolean doublelec(int[][][] CH, int ts, int event) {
		boolean doublelecturer = false;
		String LEC;
		LEC = getlecturer(event);
		for (int i = 0; i < event; i++) {
			if (LEC == getlecturer(i)) {
				for (int j = 0; j < p; j++) {
					if (CH[ts][event][j] == 1) {
						doublelecturer = true;
					}

				}

			}
		}

		return false;
	}

	// Method to get lecturer involved in any activity
	String getlecturer(int x) {

		String lecturer = null;
		lecturer = EV.events.get(x).teacher;
		return lecturer;
	}

	// method to check that student is not double booked
	boolean doublestudent(int[][][] CH, int ts, int event) {
		boolean doubstu = false;

		int size = EV.events.get(event).student.size();
		Vector<String> STUDENT = new Vector<String>(size, 1);
		Collections.copy(STUDENT, EV.events.get(event).student);

		for (int i = 0; i < event; i++) {
			boolean contained = comparestudents(STUDENT,
					EV.events.get(i).student);
			if (contained) {
				for (int j = 0; j < p; j++) {
					if (CH[ts][event][p] == 1) {
						doubstu = true;
					}
				}
			}
		}

		return doubstu;
	}

	// method to check that student in current vector is contained by other
	// student vector at any other index Target Vector is changing with and
	// current is that will be constant
	boolean comparestudents(Vector<String> current, Vector<String> target) {

		boolean contained = true;

		int s1 = 0, s2 = 0;

		s1 = current.size();
		s2 = target.size();

		for (int i = 0; i < Math.min(s1, s2); i++) {
			if (current.get(i).equals(target.get(i))) {
				contained = true;
				continue;
			} else {
				i = Math.min(s1, s2);
				contained = false;
			}
		}

		return contained;
	}

	// method to get students group of any particular event
	Vector<String> getstudent(int x) {

		int size = EV.events.get(x).student.size();
		Vector<String> STUDENT = new Vector<String>(size, 1);

		Collections.copy(STUDENT, EV.events.get(x).student);

		return STUDENT;

	}

	// check if generated time slot is between 12:00 To 14:00 i.e. n0 class can
	// be scheduled in mess timing
	boolean checktimeslotmess(int x) {
		boolean inbetween = false;
		for (int i = ID.messtiming.get(1).startslot; i < ID.messtiming.get(1).endslot; i++) {
			if (x == i) {
				inbetween = true;
			}
		}
		return inbetween;
	}

	// method to get day and slot
	int getslot(int x) {
		int sl = -1;
		for (int i = 0; i < EV.SlotSeq.size(); i++) {
			if (x == i) {
				sl = EV.SlotSeq.get(i).slot;
			}
		}

		return sl;
	}

	// method to get number of time slot required for this event
	int gettimeslots(int n) {
		int TS = 0;
		TS = (EV.events.get(n).duration) / (ID.timeslot);
		return TS;
	}

	// method to get total number of places available to schedule the event
	int getplacesize(ArrayList<Placelist> PL, int n) {
		int total = 0;

		for (int j = 0; j < PL.get(n).PLACE.size(); j++) {
			total += PL.get(n).PLACE.get(j).rooms.size();
		}
		return total;
	}

	// method to find room to allocate if x random number generated and n is
	// index in events ArrayList
	String[] getplace(int x, ArrayList<Placelist> P, int n) {
		String[] PLA = new String[2];

		int total = 0;
		for (int j = 0; j < P.get(n).PLACE.size(); j++) {
			for (int k = 0; k < P.get(n).PLACE.get(j).rooms.size(); k++) {
				if (x == total) {
					PLA[0] = P.get(n).PLACE.get(j).Building;
					PLA[1] = P.get(n).PLACE.get(j).rooms.get(k);

				}
				total = total + 1;
			}
		}

		return PLA;
	}

	// to get the index of space seq
	int getspaceindex(String[] A) {
		int index = 0;
		for (int i = 0; i < EV.SpaceSeq.size(); i++) {
			if (EV.SpaceSeq.get(i).equals(A[0])
					&& EV.SpaceSeq.get(i).equals(A[1])) {
				index = i;
			}
		}
		return index;
	}

	// Method to fill 1 on selected location in chromosome and -1 on other
	// CH is 3d chromosome matrix , A is matrix having generated time slot ,
	// event is index of activity ,space is index of room where to scheduled and
	// tsr is timeslot required to complete the activity
	void fillminusplus(int[][][] CH, int[] A, int event, int space, int tsr) {
		for (int i = 0; i < A.length; i++) {
			for (int j = A[i]; j < A[i] + tsr; j++) {
				for (int k = event + 1; k < n; k++) {
					CH[j][k][space] = -1;
				}
				CH[j][event][space] = 1;
			}
		}
	}

	// Method To generate the Population
	void generate() {
		Notscheduled = new ArrayList<Nospace>();
		Population = new ArrayList<Chromo>();
		for (int i = 0; i < nop; i++) {
			Chromo CH = new Chromo();
			CH.CHROM = fillmatrix();
			Population.add(CH);
			Notscheduled.add(NSP);
		}
	}

	// To Print the chromosome matrix as one by one 2d matrix
	void Show(int CH[][][]) {
		for (int i = 0; i < CH[0][0].length; i++) {
			for (int j = 0; j < CH[0].length; j++) {
				for (int k = 0; k < CH.length; k++) {
					System.out.print(" " + CH[j][k][i]);
				}
				System.out.print("\n");
			}
			System.out.println("\n");
		}
	}

	// Method to print the Population
	void printpopulation(ArrayList<Chromo> CHM) {

		for (int i = 0; i < CHM.size(); i++) {
			Show(CHM.get(i).CHROM);
		}
	}

	// declaration
	Random RandGen = new Random();

	// field declaration
	int [] A;
	int m, n, p;// matrix dimension
	int totalevents; // total number of events
	int nop; // number of population

	InputData ID;
	EnterEvents EV;
	ArrayList<Chromo> Population;
	ArrayList<Nospace> Notscheduled;
	Nospace NSP;// to store the index of that event which have 0 placelist size

}

// class to store the chromosomes
class Chromo {
	int[][][] CHROM;
}

// class to store the event which are not schedulable in each chromosome
class Nospace {
	Vector<Integer> NotScheduled = new Vector<>(0, 1);
}

// class to check day repetition or activity completes in one day or not and
// also to ensure that activity is scheduled on different day
class CHECKDAY {
	boolean daydiffer;
	int day;
}

// class to store the place allocated to
// TODO may add space allocation index
