

import java.util.*;


class lecturer{

	public String name;
	Vector<String> pr = new Vector<String> (1,1);
	Scanner In = new Scanner(System.in);
	
	public void input(){
		System.out.println("Enter the name of Lecturer");
		In.nextLine();
		String Name = In.nextLine();
		System.out.println("Enter the course taken by this Lecturer");
		pr.add(In.nextLine());
		int x;
		System.out.println("Enter 1 to enter more courses and 0 to exit");
		x = In.nextInt();
		while(x==1){
			System.out.println("Enter the course taken by this Lecturer");
			In.nextLine();
			pr.add(In.nextLine());
			System.out.println("Enter 1 to enter more courses and 0 to exit");
			x = In.nextInt();
			
			}	
		}
	}
	


public class InputData {
	
	InputData(){
		
	}

		// method to input rooms
		
		public void Room(){
			System.out.println("Enter the total number of rooms ");
			r=IN.nextInt();
			int[] Rooms = new int[r];
			for (int i = 0 ;i<r;i++){
				System.out.println("Enter <"+ i +">the room number ");
				Rooms[i]=IN.nextInt();
			}
		}
	
		public void Day(){
			System.out.println("Enter the number of Working days");
			d = IN.nextInt();
		}
		
		public void TimeSlot(){
			System.out.println("Enter the number of timeslots");
			h = IN.nextInt();
		}
		
		public void Courses(){
			System.out.println("Enter the number of courses ");
			c = IN.nextInt();
			String [] courses = new String [c];
			IN.nextLine();
			for(int i=0;i<c;i++){
				System.out.println("Enter < " +i+"th > Course"); 
				courses[i]=IN.nextLine();
			}
			
		}
		
		public void Faculty(){
			System.out.println("Enter the number of lecturers");
			l = IN.nextInt();
			/*ArrayList<lecturer>*/
			
			for(int i=0;i<l;i++){
				lecturer L = new lecturer();
				L.input();
				lec.add(L);
			}
		}
		
		
		public int MatrixCol(){
			int NT=0,N,NOL,R;
			NOL = lec.size();
			//System.out.println("Size of arraylist :\n"+(NOL));
			for(int i=0;i<NOL;i++){
				NT += (lec.get(i).pr).size();
				//System.out.println("Courses taken by lecturer <"+i+"> is "+N);
			}
			R=this.r;
			N = R*NT;
			return N;
		}
		
		public int MatrixRow(){
			int Row;
			Row=h*d;
			return Row;
		}
     
		// field declaration 
		int c,d,h,l,r;
		Scanner IN = new Scanner(System.in);
		public ArrayList<lecturer> lec = new ArrayList<lecturer> (); ;
		
		
		
	}



