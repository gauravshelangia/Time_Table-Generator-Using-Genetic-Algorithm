import java.lang.*;
import java.util.*;

//class To store sequence of column / Room-lecturer-courses combination
class ColSeq {
	int Room;
	int LEC;
	int COU;
}

// class to store the sequence of row / day-slots combination
class RowSeq {
	int day;
	int slot;
}

public class InputData {

	InputData() {

	}

	// method to input rooms

	public void Room() {
		System.out.println("Enter the total number of rooms ");
		r = IN.nextInt();
		Rooms = new int[r];
		for (int i = 0; i < r; i++) {
			System.out.println("Enter <" + i + ">the room number(ID) ");
			Rooms[i] = IN.nextInt();
		}
	}

	// To enter the total number of rooms
	public void Day() {
		System.out.println("Enter the number of Working days");
		d = IN.nextInt();
	}

	// To enter the number of time slots per day
	public void TimeSlot() {
		System.out.println("Enter the number of timeslots");
		h = IN.nextInt();
	}

	// To enter the total number of courses and their course code
	public void Courses() {
		System.out.println("Enter the number of courses ");
		c = IN.nextInt();
		courses = new String[c];
		IN.nextLine();
		for (int i = 0; i < c; i++) {
			System.out.println("Enter < " + i + "th > Course Code");
			courses[i] = IN.nextLine();
		}

	}

	// To enter the total number of faculty with their name and preferred
	// courses
	public void Faculty() {
		System.out.println("Enter the number of lecturers");
		l = IN.nextInt();
		for (int i = 0; i < l; i++) {
			lecturer L = new lecturer();
			L.input();
			lec.add(L);
		}
	}

	// To find the column number of matrix
	public int MatrixCol() {
		int N, NOL, R;
		NOL = lec.size();
		for (int i = 0; i < NOL; i++) {
			// System.out.println("value of lec.get(i).pr).size() "
			// + (lec.get(i).pr).size());
			lc += (lec.get(i).pr).size();
		}
		R = this.r;
		N = R * lc;
		return N;
	}

	// To find the number of rows of matrix
	public int MatrixRow() {
		int Row;
		Row = h * d;
		return Row;
	}

	// To generate column sequence
	public void generatecolseq() {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < l; j++) {
				for (int k = 0; k < (lec.get(j).pr.size()); k++) {
					ColSeq cseq = new ColSeq();
					cseq.Room = i;
					cseq.LEC = j;
					// System.out.println("Value of lec.get(j).ccode.get(k) "+lec.get(j).ccode.get(k));
					cseq.COU = lec.get(j).ccode.get(k);
					colseq.add(cseq);
				}
			}
		}
	}

	// To generate the row sequence
	public void generaterowseq() {
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < h; j++) {
				RowSeq rseq = new RowSeq();
				rseq.day = i;
				rseq.slot = j;
				rowseq.add(rseq);
			}
		}
	}

	// method to get course number
	public int getcourse(int x) {
		int courseno = colseq.get(x).COU;
		return courseno;
	}

	// method to get day number
	public int getday(int x) {
		int dayno = rowseq.get(x).day;
		return dayno;
	}

	// method to get room number
	public int getroom(int w) {
		int room;
		room = colseq.get(w).Room;
		return room;
	}

	// method to get lecturer number
	public int getlecturer(int w) {
		int lecturer;
		lecturer = colseq.get(w).LEC;
		return lecturer;
	}

	// method to get slot number
		public int getslot(int w) {
			int slot;
			slot = rowseq.get(w).slot;
			return slot;
		}
		
		
		
		
		
		
	// field declaration
	public int c, d, h, l, r, lc;
	String[] courses;
	int[] Rooms;
	Scanner IN = new Scanner(System.in);
	public ArrayList<lecturer> lec = new ArrayList<lecturer>();
	public ArrayList<ColSeq> colseq = new ArrayList<ColSeq>();
	public ArrayList<RowSeq> rowseq = new ArrayList<RowSeq>();

	// class to take the input of lecturer and their perspective course
	class lecturer {

		String name;
		Vector<String> pr = new Vector<String>(0, 1);
		Vector<Integer> ccode = new Vector<Integer>(0, 1);
		Scanner In = new Scanner(System.in);

		public void input() {
			boolean add = false;
			int code = 0;
			String course;
			System.out.println("Enter the name of Lecturer");
			// In.nextLine();
			@SuppressWarnings("unused")
			String Name = In.nextLine();
			System.out.println("Enter the course taken by this Lecturer");
			course = In.nextLine();
			for (int i = 0; i < c; i++) {
				if (course.equals(courses[i])) {
					add = true;
					code = i;
				}
			}
			if (add) {
				pr.add(In.nextLine());
				ccode.add(code);
			} else {
				System.out.println("Course doesnot exist");
			}
			int x;
			System.out.println("Enter 1 to enter more courses and 0 to exit");
			x = In.nextInt();
			while (x == 1) {
				add = false;
				System.out.println("Enter the course taken by this Lecturer");
				In.nextLine();
				course = In.nextLine();
				for (int i = 0; i < c; i++) {
					if (course.equals(courses[i])) {
						add = true;
						code = i;
					}
				}
				if (add) {
					pr.add(In.nextLine());
					ccode.add(code);
					System.out.println("Course added");
				} else {
					System.out.println("Course doesnot exist");
				}
				System.out
						.println("Enter 1 to enter more courses and 0 to exit");
				x = In.nextInt();

			}
		}
	}

}