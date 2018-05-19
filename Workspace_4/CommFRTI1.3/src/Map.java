import java.util.ArrayList;


public class Map {
	public ArrayList<String> TargetInput=new ArrayList<String>();
	public ArrayList<String> TargetOutput=new ArrayList<String>();
	public ArrayList<String> MermberRelation=new ArrayList<String>();
	public ArrayList<String> getMermberRelation() {
		return MermberRelation;
	}

	public void setMermberRelation(ArrayList<String> mermberRelation) {
		MermberRelation = mermberRelation;
	}

	public ArrayList<String> getTargetInput() {
		return TargetInput;
	}

	public void setTargetInput(ArrayList<String> targetInput) {
		TargetInput = targetInput;
	}

	public ArrayList<String> getTargetOutput() {
		return TargetOutput;
	}

	public void setTargetOutput(ArrayList<String> targetOutput) {
		TargetOutput = targetOutput;
	}


}
