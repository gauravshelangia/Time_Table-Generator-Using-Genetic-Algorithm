/*
 * class for Time-Table-IIITV
 */
import java.util.*;

public class FitnessCalculator {

	/**
	 * @param args
	 */
	FitnessCalculator(InputData INP, EnterEvents EV, Generatechromosome GC) {

		this.INP = INP;
		this.EV = EV;
		this.GCS = GC;
		this.m = GC.m;
		this.n = GC.n;
		this.p = GC.p;
	}

	/*
	 * Methods to check the soft constraints
	 */

	// SOFT-CONSTRAINTS 1: To check that all lab course are scheduled in evening
	// or after Lunch
	int checklabtiming(int[][][] CH) {
		/*
		 * int x, y, z; x = CH.length; y = CH[0].length; z = CH[0][1].length;
		 */

		int noc = 0;// number of conflicts in this constraints

		for (int i = 0; i < n; i++) {

			int type = geteventtype(i);
			if (type == 2 || type == 3 || type == 4) {

				for (int j = 0; j < p; j++) {

					for (int k = 0; k < m; k++) {
						if (CH[i][j][k] == 1) {

							boolean yon;
							yon = checktimeslotmessevening(k);
							if (yon) {
								System.out.println("Yes its after Lunch : ");
							} else {
								noc = noc + 1;
							}

							// end outer loop which traverse along space index
							j = p;

						}
					}

				}

			}

		}

		return noc;
	}

	// method to get the activity type
	int geteventtype(int x) {
		int type = -1;

		type = EV.events.get(x).eventtype;

		return type;
	}

	// method to check that scheduled time is after lunch or not for lab course
	boolean checktimeslotmessevening(int x) {
		boolean afterlunch = false;
		if (x > INP.messtiming.get(1).endslot) {
			afterlunch = true;
		}

		return afterlunch;
	}

	/*
	 * Method to check that all lecturer are scheduled in mornings hours It will
	 * return the number of conflicts for this constraints
	 */
	int checklecturetiming(int[][][] CH) {

		int noc = 0;// number of conflicts in this constraints

		for (int i = 0; i < n; i++) {

			int type = geteventtype(i);
			if (type == 0) {

				int tsr = GCS.gettimeslots(i);

				for (int j = 0; j < p; j++) {

					for (int k = 0; k < m; k++) {
						if (CH[i][j][k] == 1) {

							boolean yon;
							yon = checktimeslotmessmorning(k);

							if (yon) {
								System.out.println("Yes its after Lunch : ");
							} else {
								noc = noc + 1;
							}

							// increment by timeslot required to complete this
							// activity
							k = k + tsr;

							// end outer loop which traverse along space index
							j = p;

						}
					}

				}

			}

		}
		return 0;

	}

	// method to check that scheduled time is before lunch time
	boolean checktimeslotmessmorning(int x) {
		boolean inmorning = false;

		if (x < INP.messtiming.get(1).startslot) {
			inmorning = true;
		}

		return inmorning;
	}

	/*
	 * method to check that if there is consecutive schedule of any lecturer of
	 * any lecturer or not . To ensure that that there should be a time gap
	 * between two to class of a lecturer
	 */

	int checklecgap(int[][][] CH) {
		int con = 0;
		// declare the checked vector to store the checked teacher
		CheckedTeacher = new ArrayList<String>(1);
		// add something so that .size will return 1
		CheckedTeacher.add("NOTHING");

		for (int i = 0; i < n; i++) {

			DAYSTART = new ArrayList<daystart>(1);
			DAYEND = new ArrayList<dayend>(1);

			// same fill something in the DAYSTART and DAYEND
			daystart d1 = new daystart();
			d1.day = -1;
			d1.startslot.add(-1);
			DAYSTART.add(d1);

			dayend d2 = new dayend();
			d2.day = -1;
			d2.endslot.add(-1);
			DAYEND.add(d2);

			String teacher = null;
			teacher = getteacher(i);
			// check if teacher is already checked or not
			boolean exist = false;
			for (int t = 0; t < CheckedTeacher.size(); t++) {

				if (teacher.equals(CheckedTeacher.get(t))) {
					exist = true;
				}
				// TODO Do something to delete the nothing filled in
				// CheckedTeacher arraylist
			}
			if (!exist) {

				// first add this teaher to checkedteacher arraylist
				CheckedTeacher.add(teacher);

				// find all index this teacher is engaged
				for (int j = i; j < n; j++) {

					if (teacher.equals(getteacher(j))) {
						for (int k = 0; k < p; k++) {
							for (int l = 0; l < m; l++) {
								if (CH[l][j][k] == 1) {

									// day when this is scheduled
									int day = getday(j);
									// get timeslot(Start time slot )
									int startslot = GCS.getslot(k);

									// get time-slots required to complete
									// the activity
									int tsr = GCS.gettimeslots(j);
									// increase the l by tsr as these all
									// are 1
									l = l + tsr - 1;

									// find end slot
									int endslot = startslot + tsr - 1;

									// store all start slot of this teacher
									for (int DS = 0; DS < DAYSTART.size(); DS++) {

										if ((DAYSTART.get(DS).day) == day) {
											DAYSTART.get(DS).startslot
													.add(startslot);
										} else {
											daystart dst = new daystart();
											dst.day = day;
											dst.startslot.add(startslot);
										}

										if (DAYSTART.get(DS).day == -1) {
											DAYSTART.remove(DS);
										}
									}

									// store all end slot of this teacher
									for (int DE = 0; DE < DAYEND.size(); DE++) {
										if ((DAYEND.get(DE).day) == day) {
											DAYEND.get(DE).endslot.add(endslot);
										} else {
											dayend ded = new dayend();
											ded.day = day;
											ded.endslot.add(endslot);
										}

										if (DAYEND.get(DE).day == -1) {
											DAYEND.remove(DE);

										}
									}

									// end the upper loop which traverse in
									// space sequence
									k = p;
								}
							}
						}

					}

				}

				// Sort the DAYEND ArrayList slot wise in each day

				for (int SAE = 0; SAE < DAYEND.size(); SAE++) {

					Collections.sort(DAYEND.get(SAE).endslot);
				}

				// Sort the DAYEND ArrayList slot wise in each day

				for (int SAS = 0; SAS < DAYSTART.size(); SAS++) {

					Collections.sort(DAYSTART.get(SAS).startslot);
				}

				// After sorting find the difference
				// between start and end slot of each
				// day which should be = 0 if it is -ve
				// means same activity or if +ve means
				// there is a gap between two schedule

				for (int ES = 0; ES < DAYEND.size(); ES++) {
					for (int SS = 0; SS < DAYSTART.size(); SS++) {
						if (DAYEND.get(ES).day == DAYSTART.get(SS).day) {
							for (int ESV = 0; ESV < DAYEND.get(ES).endslot
									.size(); ESV++) {
								for (int SSV = 0; SSV < DAYSTART.get(SS).startslot
										.size(); SSV++) {
									int diff = DAYEND.get(ES).endslot.get(ESV)
											- DAYSTART.get(SS).startslot
													.get(SSV);
									if (diff == 0) {
										con = con + 1;
									}
								}
							}
						}
					}
				}

			}

		}

		return con;

	}

	// method to getday at any slot in chromosome matrix
	private int getday(int x) {

		int day = -1;
		day = EV.SlotSeq.get(x).day;

		return day;
	}

	// method to get the teacher at any index of 3d chromosome matrix
	private String getteacher(int x) {
		String Teacher = null;

		Teacher = EV.events.get(x).teacher;

		return Teacher;
	}

	/*
	 * method to check if there is consecutive class of any student or not To
	 * ensure that there should be a time gap between two activity of any
	 * student group , branch , batch
	 */

	int checkgapstudent(int[][][] CH) {
		int noc = 0;

		// initialize array list with initial size 1
		CheckedStudent = new ArrayList<STUDENT>(1);

		for (int i = 0; i < n; i++) {

			DAYSTART = new ArrayList<daystart>(1);
			DAYEND = new ArrayList<dayend>(1);

			// same fill something in the DAYSTART and DAYEND
			daystart d1 = new daystart();
			d1.day = -1;
			d1.startslot.add(-1);
			DAYSTART.add(d1);

			dayend d2 = new dayend();
			d2.day = -1;
			d2.endslot.add(-1);
			DAYEND.add(d2);

			// get the student at any index of that activity
			int size = getstudent(i).size();
			Vector<String> ST = new Vector<String>(size, 1);

			Collections.copy(ST, getstudent(i));

			boolean exist = checkstudent(CheckedStudent, ST);

			if (!exist) {

				for (int j = i; j < n; j++) {
					boolean contained = comparestudents(ST, getstudent(j));

					if (contained) {
						for (int k = 0; k < p; k++) {
							for (int l = 0; l < m; l++) {
								if (CH[l][j][k] == 1) {

									// day when this is scheduled
									int day = getday(j);
									// get timeslot(Start time slot )
									int startslot = GCS.getslot(k);

									// get time-slots required to complete
									// the activity
									int tsr = GCS.gettimeslots(j);
									// increase the l by tsr as these all
									// are 1
									l = l + tsr - 1;

									// find end slot
									int endslot = startslot + tsr - 1;

									// store all start slot of this teacher
									for (int DS = 0; DS < DAYSTART.size(); DS++) {

										if ((DAYSTART.get(DS).day) == day) {
											DAYSTART.get(DS).startslot
													.add(startslot);
										} else {
											daystart dst = new daystart();
											dst.day = day;
											dst.startslot.add(startslot);
										}

										if (DAYSTART.get(DS).day == -1) {
											DAYSTART.remove(DS);
										}
									}

									// store all end slot of this teacher
									for (int DE = 0; DE < DAYEND.size(); DE++) {
										if ((DAYEND.get(DE).day) == day) {
											DAYEND.get(DE).endslot.add(endslot);
										} else {
											dayend ded = new dayend();
											ded.day = day;
											ded.endslot.add(endslot);
										}

										if (DAYEND.get(DE).day == -1) {
											DAYEND.remove(DE);

										}
									}

									// end the upper loop which traverse in
									// space sequence
									k = p;
								}
							}

						}
					}

				}

				// Sort the DAYEND ArrayList slot wise in each day

				for (int SAE = 0; SAE < DAYEND.size(); SAE++) {

					Collections.sort(DAYEND.get(SAE).endslot);
				}

				// Sort the DAYEND ArrayList slot wise in each day

				for (int SAS = 0; SAS < DAYSTART.size(); SAS++) {

					Collections.sort(DAYSTART.get(SAS).startslot);
				}

				// After sorting find the difference
				// between start and end slot of each
				// day which should be = 0 if it is -ve
				// means same activity or if +ve means
				// there is a gap between two schedule

				for (int ES = 0; ES < DAYEND.size(); ES++) {
					for (int SS = 0; SS < DAYSTART.size(); SS++) {
						if (DAYEND.get(ES).day == DAYSTART.get(SS).day) {
							for (int ESV = 0; ESV < DAYEND.get(ES).endslot
									.size(); ESV++) {
								for (int SSV = 0; SSV < DAYSTART.get(SS).startslot
										.size(); SSV++) {
									int diff = DAYEND.get(ES).endslot.get(ESV)
											- DAYSTART.get(SS).startslot
													.get(SSV);
									if (diff == 0) {
										noc = noc + 1;
									}
								}
							}
						}
					}
				}

			}
		}

		return noc;

	}

	// method to check if current student is already checked or not
	boolean checkstudent(ArrayList<STUDENT> STU, Vector<String> CSV) {
		boolean exist = true;

		if (STU.size() == 0) {
			exist = false;
		} else {
			int count = 0;
			// check for all student in checked array list
			for (int i = 0; i < STU.size(); i++) {
				int s1 = 0;
				int s2 = 0;

				s1 = STU.get(i).student.size();
				s2 = CSV.size();

				for (int j = 0; j < Math.min(s1, s2); j++) {

					// first compare batch then branch and then group
					if (CSV.get(j).equals(STU.get(i).student.get(j))) {
						continue;

					} else {
						j = Math.min(s1, s2);
						count = count + 1;
					}

				}

			}
			// count should be equal to STU.size() means this student doesn't
			// exist at any place in STU So
			if (count == STU.size()) {
				exist = false;
			}

		}
		if (!exist) {
			STUDENT STT = new STUDENT();
			Collections.copy(STT.student, CSV);
			STU.add(STT);
		}

		return exist;
	}

	// method to check that student in current vector is contained by other
	// student vector at any other index Target Vector is changing continosly
	// and current is fixed
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

	// method to get the student in any particular acitvity
	Vector<String> getstudent(int x) {

		Vector<String> STU = new Vector<String>(0, 1);

		Collections.copy(STU, EV.events.get(x).student);

		return STU;

	}

	/*
	 * Method to check constraints : Junior batch should schedule everyday in
	 * morning ::: Junior batch = 2015 // for IIITV
	 */

	int checkmorningbatch(int[][][] CH) {
		int noc = 0;

		for (int i = 0; i < n; i++) {

			int size = getstudent(i).size();
			Vector<String> ST = new Vector<>(size, 1);

			Collections.copy(ST, getstudent(i));

			boolean batch15;
			batch15 = check2015batch(ST);

			int tspd = (int) INP.slotsperday;
			if (batch15) {
				for (int j = 0; j < m; j = j + tspd) {
					if (j == 1) {

					} else {
						noc = noc + 1;
					}

					/*
					 * to check 2nd slot also if(j+1==1){ means scheduled on
					 * second slot }
					 */

				}

			}
		}

		return noc;
	}

	// method to check students are of junior 2015 batch or not
	boolean check2015batch(Vector<String> CST) {
		boolean batch15 = false;

		if (CST.get(0).equals(2015)) {
			batch15 = true;
			System.out.println("Yes I am in 15 batch");
		}

		return batch15;
	}

	/*
	 * Method for constraints : Maximum hours that a lecturer can take per day
	 * or per week
	 */

	// variable declaration for this class
	int m, n, p;

	// Vector , ArrayList etc declaration
	ArrayList<String> CheckedTeacher; // to store the teacher which are checked
	ArrayList<STUDENT> CheckedStudent; // to store the teacher which are checked

	ArrayList<daystart> DAYSTART;
	ArrayList<dayend> DAYEND;

	// Declare all the class used in this class
	InputData INP;
	EnterEvents EV;
	Generatechromosome GCS;
}

// class to store the day and time slot( Start Time Slot )
class daystart {
	int day;
	Vector<Integer> startslot = new Vector<>(0, 1);
}

// class to store the day and time slot( Start Time Slot )
class dayend {
	int day;
	Vector<Integer> endslot = new Vector<>(0, 1);
}

// class to store the checked student
class STUDENT {

	Vector<String> student = new Vector<String>(1, 1);
}