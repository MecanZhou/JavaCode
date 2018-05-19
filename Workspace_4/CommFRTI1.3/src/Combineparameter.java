import java.util.ArrayList;


public class Combineparameter {
		ArrayList<String> CombineValue=new ArrayList<String>();//记录所有的组合成员之间的内部关系
		ArrayList< String> InsideMemberID=new ArrayList<String>();//内部成员的ID
		ArrayList< String> OutputMemID=new ArrayList<String>();//输出成员的ID
		ArrayList< String> InputMemID=new ArrayList<String>();//输入成员的ID
		
		public ArrayList<String> getInsideMemberID() {
			return InsideMemberID;
		}

		public void setInsideMemberID(ArrayList<String> insideMemberID) {
			InsideMemberID = insideMemberID;
		}

		public ArrayList<String> getOutputMemID() {
			return OutputMemID;
		}

		public void setOutputMemID(ArrayList<String> outputMemID) {
			OutputMemID = outputMemID;
		}

		public ArrayList<String> getInputMemID() {
			return InputMemID;
		}

		public void setInputMemID(ArrayList<String> inputMemID) {
			InputMemID = inputMemID;
		}

		public ArrayList<String> getCombineValue() {
			return CombineValue;
		}

		public void setCombineValue(ArrayList<String> combineValue) {
			CombineValue = combineValue;
		}
}
