import java.util.ArrayList;


public class Combineparameter {
		ArrayList<String> CombineValue=new ArrayList<String>();//��¼���е���ϳ�Ա֮����ڲ���ϵ
		ArrayList< String> InsideMemberID=new ArrayList<String>();//�ڲ���Ա��ID
		ArrayList< String> OutputMemID=new ArrayList<String>();//�����Ա��ID
		ArrayList< String> InputMemID=new ArrayList<String>();//�����Ա��ID
		
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
