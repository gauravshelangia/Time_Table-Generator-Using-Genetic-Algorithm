/*
 * Class of Time-Table IIITV
 */
import java.util.*;
import java.lang.*;

public class EnterEvents {

	public EnterEvents(InputData INP) {
		this.INP = INP;
	}

	// method to generate space sequence - building-rooms (Z-axis of 3D Matrix)
	void generatespaceseq() {
		SpaceSeq = new ArrayList<Spaceseq>();
		b = INP.buildings;
		for (int i = 0; i < b; i++) {
			r = INP.space.get(i).buildingrooms.size();
			for (int j = 0; j < r; j++) {
				Spaceseq SPS = new Spaceseq();
				SPS.building = i ;
				SPS.room = j ;
				SpaceSeq.add(SPS);

			}
		}
	}

	// method to generate day-slots sequence (Rows in 3D Matrix)
	void generateslotseq() {
		SlotSeq = new ArrayList<Slotseq>();
		d = INP.day;
		ts = (int)INP.slotsperday;

		for (int i = 0; i < d; i++) {
			for (int j = 0; j < ts; j++) {
				Slotseq SLS = new Slotseq();
				SLS.day = i ;
				SLS.slot = j ;
				SlotSeq.add(SLS);
			}
		}
	}

	// take input events like bus-route and mess event
	void setotherevent() {
		otherevent = new ArrayList<OtherEvent>();
		System.out
				.println("Enter the total number of activities like mess or route : ");
		int nooe = IN.nextInt();
		for (int i = 0; i < nooe; i++) {
			OtherEvent OE = new OtherEvent();
			String BH, BR, GP;

			System.out
					.println("Enter the type of activities : \n 0 : Transport \n 1 : mess-Breakfast \n "
							+ "2 : mess-Lunch \n 3 : mess-Dinner )");
			OE.otype = IN.nextInt();
			System.out
					.println("Enter the students for which this activities is scheduled : ");
			System.out
					.println("Enter choice :\n 0 : For a Batch \n 1 : Branch of any batch \n 2 : Group in the Branch ");
			int ch = IN.nextInt();
			switch (ch) {
			case 0:
				System.out.println("Enter the batch : ");
				BH = IN.nextLine();
				OE.student.add(BH);
				OE.nost = getstudentbybatch(BH);
				break;
			case 1:
				System.out.println("Enter the batch : ");
				BH = IN.nextLine();
				OE.student.add(BH);
				System.out.println("Enter the Branch in this batch : ");
				BR = IN.nextLine();
				OE.student.add(BR);
				OE.nost = getstudentbybranch(BH, BR);
				break;
			case 2:
				System.out.println("Enter the batch : ");
				BH = IN.nextLine();
				OE.student.add(BH);
				System.out.println("Enter the Branch in this batch : ");
				BR = IN.nextLine();
				OE.student.add(BR);
				System.out.println("Enter the Group in this branch : ");
				GP = IN.nextLine();
				OE.student.add(GP);

				OE.nost = getstudentbygroup(BH, BR, GP);
				break;
			}
			otherevent.add(OE);
		}

	}

	// method to take input events like classes , lab , tutorial ,
	void setcourseevent() {
		events = new ArrayList<Event>();
		System.out.println("Enter the total number of event :");
		int E = IN.nextInt();

		for (int i = 0; i < E; i++) {
			Event EV = new Event();
			String BH, BR, GP;
			System.out.println("Enter the teacher name :");
			IN.nextLine();
			EV.teacher = IN.nextLine();
			System.out.println("Enter the course code :");
			IN.nextLine();
			EV.coursecode = IN.nextLine();
			System.out.println("Enter the type of course/evet : ");
			System.out
					.println("Enter the type of activities \n0 : For lecture , \n1 : For Computer Lab , "
							+ "\n2 : For Physics Lab , \n3 : For Electronics Lab \n4 : For Tutorial : ");
			EV.eventtype = IN.nextInt();
			System.out
					.println("Enter the duration of this event (in minutes ): ");
			EV.duration = IN.nextInt();
			System.out
					.println("Enter the students for which this teacher and course is allocated : ");
			System.out
					.println("Enter choice :\n 0 : For a Batch \n 1 : Branch of any batch \n 2 : Group in the Branch ");
			int ch = IN.nextInt();
			switch (ch) {
			case 0:
				System.out.println("Enter the batch : ");
				IN.nextLine();
				BH = IN.nextLine();
				EV.student.add(BH);
				EV.nose = getstudentbybatch(BH);
				break;
			case 1:
				System.out.println("Enter the batch : ");
				IN.nextLine();
				BH = IN.nextLine();
				EV.student.add(BH);
				System.out.println("Enter the Branch in this batch : ");
				IN.nextLine();
				BR = IN.nextLine();
				EV.student.add(BR);
				EV.nose = getstudentbybranch(BH, BR);
				break;
			case 2:
				System.out.println("Enter the batch : ");
				IN.nextLine();
				BH = IN.nextLine();
				EV.student.add(BH);
				System.out.println("Enter the Branch in this batch : ");
				IN.nextLine();
				BR = IN.nextLine();
				EV.student.add(BR);
				System.out.println("Enter the Group in this branch : ");
				IN.nextLine();
				GP = IN.nextLine();
				EV.student.add(GP);

				EV.nose = getstudentbygroup(BH, BR, GP);
				break;
			}
			
			
			System.out.println("Enter the occurence of this activities perweek : ");
			EV.occurence = IN.nextInt();
			events.add(EV);
		}
	}

	// make place-list of events having room and
	void setplacelist() {
		// set place for every event
		Places = new ArrayList<>();
		int e = events.size();
		for (int i = 0; i < e; i++) {
			Placelist PL = new Placelist();
			int et = events.get(i).eventtype;
			for (int j = 0; j < INP.space.size(); j++) {
				
				for (int k = 0; k < INP.space.get(j).buildingrooms.size(); k++) {
					if (et == INP.space.get(j).buildingrooms.get(k).roomtype
							&& INP.space.get(j).buildingrooms.get(k).capacity > events
									.get(i).nose) {
						Places PP = new Places();
						PP.Building = INP.space.get(j).building;
						PP.rooms.add(INP.space.get(j).buildingrooms.get(k).Roomid);
						PL.PLACE.add(PP);
						
					}
				}
			}
			Places.add(PL);
		}

	}
	
	
	
	// method to get the student by batch
	int getstudentbybatch(String batch) {
		int nostudent = 0;
		for (int j = 0; j < INP.Students.size(); j++) {
			if (INP.Students.get(j).getbatch().contains(batch)) {
				System.out.println("Index of batch 2013 is : "
						+ INP.Students.get(j).nos);
				nostudent = INP.Students.get(j).nos;
			}
		}
		return nostudent;
	}

	
	// method to get number of student by branch
	int getstudentbybranch(String batch, String branch) {
		int nostudent = 0;
		for (int j = 0; j < INP.Students.size(); j++) {
			if (INP.Students.get(j).getbatch().contains(batch)) {
				for (int k = 0; k < INP.Students.get(j).Branch.size(); k++) {
					if (INP.Students.get(j).Branch.get(k).getbranch()
							.equals(branch)) {
						System.out.println("Index of batch 2013 is : "
								+ INP.Students.get(j).Branch.get(k).nos);
						nostudent = INP.Students.get(j).Branch.get(k).nos;

					}
				}
			}
		}
		return nostudent;
	}

	// method to get student number by group
	int getstudentbygroup(String batch, String branch, String group) {
		int nostudent = 0;
		for (int j = 0; j < INP.Students.size(); j++) {
			if (INP.Students.get(j).getbatch().contains(batch)) {
				for (int k = 0; k < INP.Students.get(j).Branch.size(); k++) {
					if (INP.Students.get(j).Branch.get(k).getbranch()
							.equals(branch)) {
						for (int l = 0; l < INP.Students.get(j).Branch.get(k).Group
								.size(); l++) {
							if (INP.Students.get(j).Branch.get(k).Group.get(l).groupname
									.contains(group)) {
								nostudent = INP.Students.get(j).Branch.get(k).nos;
							}
						}
					}
				}
			}
		}
		return nostudent;
	}

	// field declaration
	int d, ts, b, r;
	Scanner IN = new Scanner(System.in);
	ArrayList<Spaceseq> SpaceSeq;// = new ArrayList<Spaceseq>();
	ArrayList<Slotseq> SlotSeq;// = new ArrayList<Slotseq>();
	ArrayList<Event> events;
	ArrayList<OtherEvent> otherevent;
	ArrayList<Placelist> Places;
	InputData INP;

}

// class to store event
class Event {
	String teacher;
	String coursecode;
	int eventtype;
	int duration;
	int occurence;
	int nose;// number of student in this event
	Vector<String> student = new Vector<String>(0, 1);
}

// class for other events
class OtherEvent {
	int otype;
	int nost;
	Vector<String> student = new Vector<String>(0, 1);
}

// class to store the slot sequence
class Slotseq {
	int day;
	int slot;
}

// class to store the space sequence
class Spaceseq {
	int building;
	int room;
}



class Placelist{
	ArrayList<Places> PLACE = new ArrayList<Places>();
}
// class to store the place-list of events
class Places {
	String Building;
	ArrayList<String> rooms = new ArrayList<String>(1);
}
