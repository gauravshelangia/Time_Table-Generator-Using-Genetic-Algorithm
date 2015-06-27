import java.util.*;


public class Input {
	Input(){
	}
	
	public void takeinput(){
		info = new InputData();
		info.Room();
		info.Day();
		info.TimeSlot();
		info.Courses();
		info.Faculty();
//		col = info.MatrixCol();
//		row = info.MatrixRow();
		info.generatecolseq();
		info.generaterowseq();
		takeinput.add(info);
	}
	
	InputData info = new InputData();
	
	public ArrayList<InputData> takeinput = new ArrayList<InputData>();
}
