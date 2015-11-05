/*
 * Class of Time-Table IIITV
 */
import java.util.*;
import java.text.ParseException;


public class Tester {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		

		
		int [][][] A=new int [3][4][6];
		System.out.println(" rows : "+A.length + "Col : "+A[0].length+ "roof : "+A[0][0].length);
		
		
		
		
		
		// TODO Auto-generated method stub
		InputData INPUT = new InputData();
		INPUT.setsapce();
		INPUT.setday();
		INPUT.sethours();
		INPUT.setcourse();
		INPUT.setlecturer();
		INPUT.setstudent();
		INPUT.settimeslots();
		INPUT.setmesstiming();
		int tt = INPUT.timeslot;
		System.out.println("Value of tt is  "+tt);
		
		EnterEvents EV = new EnterEvents(INPUT);
		EV.generateslotseq();
		EV.generatespaceseq();
		EV.setcourseevent();
		
	}
	
}
