/*
 * class of Time-Table IITV
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.rmi.CORBA.Tie;

public class InputData {

	// take input for room with its capacity , type(lecture,lab) and building
	void setsapce() {
		space = new ArrayList<Space>();
		System.out.println("Enter the total number of building : ");
		buildings = IN.nextInt();

		for (int j = 0; j < buildings; j++) {
			Space SPACE = new Space();

			System.out.println("Enter the name of Building :" + (j + 1));
			IN.nextLine();
			SPACE.building = IN.nextLine();
			space.add(SPACE);

			System.out.println("Enter the total number room in this building ");
			rooms = IN.nextInt();
			IN.nextLine();
			for (int i = 0; i < rooms; i++) {
				Contents CON = new Contents();
				System.out.println("Enter the Room id of : " + (i + 1));
				CON.Roomid = IN.nextLine();

				System.out.println("Enter capacity of this room : ");
				CON.capacity = IN.nextInt();

				System.out
						.println("Enter the type of room \n 0 : For lecture hall , \n1 : For Computer Lab room , "
								+ "\n2 : For Physics Lab , \n3 : For Electronics Lab ");
				CON.roomtype = IN.nextInt();
				// add in array-list of space class - buildingrooms
				SPACE.buildingrooms.add(CON);
				IN.nextLine();
			}

		}

	}

	// take input for working days
	void setday() {
		System.out.println("Enter total number of working days : ");
		day = IN.nextInt();

	}

	// take input for working hours starting time and closing time
	void sethours() throws ParseException {
		String StartTime, EndTime;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		System.out
				.println("Enter the Start Time of College including mess timings : ");
		IN.nextLine();
		StartTime = IN.nextLine();
		System.out
				.println("Enter the Closing Time of College excluding mess timings : ");
		EndTime = IN.nextLine();

		Date STime = (Date) sdf.parse(StartTime);
		Date ETime = (Date) sdf.parse(EndTime);

		String START = sdf.format(STime);
		String END = sdf.format(ETime);

		// set start minutes of college
		StartMinutes = STime.getTime() / (1000 * 60);

		long diff = STime.getTime() - ETime.getTime();// diff is in millisecond
		Workingminutes = -diff / (1000 * 60);
		System.out.println("Start time is :" + START + " And EndTime Is : "
				+ END);
		System.out.println("Working Minutes are :" + Workingminutes);

	}

	// Calculate the time slots per week or number of row in chromosomes matrix
	void settimeslots() {
		System.out
				.println("Enter the minimum time slot used (like bus route is of 30 minutes)");
		timeslot = IN.nextInt();
		slotsperday = Workingminutes / timeslot;

	}

	// take input for course with type lecture , lab , tutorial
	void setcourse() {
		courses = new ArrayList<Course>();
		System.out.println("Enter the number of courses ");
		noc = IN.nextInt(); // noc = number of courses
		for (int i = 0; i < noc; i++) {
			Course COU = new Course();
			System.out.println("Enter ( " + i + "th ) Course name : ");
			IN.nextLine();
			COU.coursename = IN.nextLine();
			System.out.println("Enter the course code : ");
			COU.coursecode = IN.nextLine();
			// add course name and course code to array List
			courses.add(COU);
		}
	}

	// take input for teachers with preferred course
	void setlecturer() {
		lecturers = new ArrayList<Lecturer>();
		System.out.println("Enter the total number of lecturer : ");
		// nol=number of lecturer
		nol = IN.nextInt();
		for (int i = 0; i < nol; i++) {
			Lecturer LEC = new Lecturer();
			System.out.print("Enter the name of lecturer : ");
			IN.nextLine();
			LEC.lecturername = IN.nextLine();

			int x;
			do {
				System.out
						.println("Enter the course taken by this lecturer : ");
				IN.nextLine();
				String CC = IN.nextLine(); // Current course
				LEC.COURSES.add(CC);

				System.out
						.println("Enter 1 to enter more courses and 0 to exit : ");
				x = IN.nextInt();
			} while (x == 1);

		}
	}

	// take input for batch - branch -
	void setstudent() {
		Students = new ArrayList<Student>();
		System.out.println("Enter the total number of batch : ");
		nob = IN.nextInt(); // nob = number of batches
		int nosb, nosbr, nosg;
		for (int i = 0; i < nob; i++) {
			Student ST = new Student();
			System.out.println("Enter the (" + (i + 1) + ") batch : ");
			IN.nextLine();
			ST.Batch = IN.nextLine();
			System.out.println("Enter the number of student in this batch : ");
			nosb = IN.nextInt();
			ST.nos = nosb;

			Students.add(ST);
			System.out.println("Enter the number of branch for this batch : ");
			int nobr = IN.nextInt();
			for (int j = 0; j < nobr; j++) {
				BRANCH BR = new BRANCH();
				System.out.println("Enter the Branch : " + (j + 1));
				IN.nextLine();
				String branch = IN.nextLine();
				BR.branchname = branch;
				System.out
						.println("Enter the number of student in this branch : ");
				nosbr = IN.nextInt();
				System.out.println("Value of nosbr and nosb " + nosbr + ""
						+ nosb);
				while (nosbr > nosb) {
					System.out
							.println("Branch cann't have student more than batch : Re-enter the Strength of branch");
					nosbr = IN.nextInt();
				}
				nosb = nosb - nosbr;
				BR.nos = nosbr;

				ST.Branch.add(BR);
				System.out
						.println("Enter the total number of groups in this branch : ");
				int groups;
				groups = IN.nextInt();
				int k = 0;
				while (k < groups) {
					GROUP GR = new GROUP();
					System.out.println("Enter the group " + k);
					IN.nextLine();
					GR.groupname = IN.nextLine();
					System.out
							.println("Enter the number of student in this group : ");
					nosg = IN.nextInt();
					while (nosg > nosbr) {
						System.out
								.println("Batch cann't have student more than students in its batch : Re-enter the number of stuent :");
						nosg = IN.nextInt();
					}
					GR.nos = nosg;
					nosbr = nosbr - nosg;
					ST.Branch.get(j).Group.add(GR);
					System.out.println("Student in group : "
							+ ST.Branch.get(j).Group.get(k).nos);
					k++;
				}
			}

		}
	}

	// take input for bus and capacity
	void setbus() {
		bus = new ArrayList<Bus>();
		System.out.println("Enter the total number of buses : ");
		buses = IN.nextInt();

		for (int i = 0; i < buses; i++) {
			Bus B = new Bus();
			System.out.println("Enter the bus number : ");
			String num = IN.nextLine();
			B.busnumber = num;
			System.out.println("Enter the capacity of bus " + num);
			B.capacity = IN.nextInt();
			bus.add(B);
		}
	}

	// take input for mess-timing
	void setmesstiming() throws ParseException {
		int[] a = new int[2];
		messtiming = new ArrayList<Mess>();

		// for breakfast
		System.out.println("Enter Breakfast Timing : ");
		Mess MS = new Mess();
		MS.messtype = 1;
		a = messtimeslots();
		MS.startslot = a[0];
		MS.endslot = a[1];
		System.out.println("Start and End Time Slots are (for breakfast ) : "
				+ a[0] + " and " + a[1]);
		messtiming.add(MS);

		// For Lunch Timing
		System.out.println("Enter Lunch Timing : ");
		MS.messtype = 2;
		a = messtimeslots();
		MS.startslot = a[0];
		MS.endslot = a[1];
		System.out.println("Start and End Time Slots are (for breakfast ) : "
				+ a[0] + " and " + a[1]);
		messtiming.add(MS);

		// For Lunch Snacks
		System.out.println("Enter Evening Snacks Timing : ");
		MS.messtype = 3;
		a = messtimeslots();
		MS.startslot = a[0];
		MS.endslot = a[1];
		System.out.println("Start and End Time Slots are (for breakfast ) : "
				+ a[0] + " and " + a[1]);
		messtiming.add(MS);

	}

	// method to get starting timeslot and end timeslot for mess
	int[] messtimeslots() throws ParseException {
		String StartTime, EndTime;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		IN.nextLine();
		System.out.println("From : ");
		StartTime = IN.nextLine();
		System.out.println("STARTTIME : " + StartTime);

		System.out.println("To : ");
		EndTime = IN.nextLine();
		System.out.println("STARTTIME : " + EndTime);

		Date STime = null, ETime = null;
		try {
			STime = (Date) sdf.parse(StartTime);
			ETime = (Date) sdf.parse(EndTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff = STime.getTime();
		long Smin = diff / (1000 * 60);
		System.out.println("The diff and Smin are : " + diff + "and" + Smin);
		int ST = (int) ((Smin - StartMinutes) / timeslot) + 1;
		int ET = (int) (ETime.getTime() / (1000 * 60) - StartMinutes)
				/ timeslot + 1;

		int[] a = new int[2];
		a[0] = ST;
		a[1] = ET;
		return a;

	}

	// field declaration
	Scanner IN = new Scanner(System.in);

	int day, rooms, noc, nol, nob, buildings, timeslot, buses;
	long Workingminutes, slotsperday;
	long StartMinutes;
	ArrayList<Space> space;
	ArrayList<Course> courses;
	ArrayList<Lecturer> lecturers;
	ArrayList<Student> Students;
	ArrayList<Bus> bus;
	ArrayList<Mess> messtiming;

}

// class to take space (building , rooms , capacity , type (lab or lecture hall
class Space {
	String building;
	ArrayList<Contents> buildingrooms = new ArrayList<Contents>();
}

// to set rooms in a building and their type with capacity
class Contents {
	String Roomid;
	int capacity;
	int roomtype;
}

// class to take input for courses - course name and courses code
class Course {
	String coursename;
	String coursecode;
}

// class to take input for lecture - name , preferred courses , availability
class Lecturer {
	String lecturername;
	Vector<String> COURSES = new Vector<String>(0, 1);
	// TODO add something for time availability of teacher
	// TODO add something for time availability of teacher
}

// Class to take input student data - Batch/year , Branch , Groups
class Student {
	String Batch;
	int nos;// number of students
	ArrayList<BRANCH> Branch = new ArrayList<BRANCH>();

	String getbatch() {
		return Batch;
	}
}

// class to take input for branch and number of student in branches
class BRANCH {
	String branchname;
	int nos;// number of student
	ArrayList<GROUP> Group = new ArrayList<GROUP>();

	String getbranch() {
		return branchname;
	}
}

// class to take input for groups and number of student in each group
class GROUP {
	String groupname;
	int nos;// number of student

	String getgroup() {
		return groupname;
	}
}

// class to store the bus and its capacity
class Bus {
	String busnumber;
	int capacity;
}

// class to store the mess timing
class Mess {
	int messtype;
	int startslot;
	int endslot;
}