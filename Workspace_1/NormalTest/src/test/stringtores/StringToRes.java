package test.stringtores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
 * �������ַ������ö�ջ����ʽ������
 * ֧�������������ȼ�����֧�����ż���
 * ��ջ����#Ϊ����������
 */
public class StringToRes {
	
	//�ж��ַ��Ƿ�Ϊ������
	public boolean isOpr(String s){
		
		if ("+".equals(s)||"-".equals(s)||"*".equals(s)||"/".equals(s)) {
			return true;
		}
		return false;
	}
	
	//�жϲ��������ȼ�
	public int priority(String s) {
		
		if ("+".equals(s)||"-".equals(s)) {
			return 1;
		} else {
			return 2;
		}
	}
	
	//��ջ�ڵ��ַ�ճ���ַ���
	public void linkString(Stack<String> stack) {
		
		if (stack.peek().equals("#")) {
			return;
		}
		StringBuilder number = new StringBuilder();
		while (true) {
			String s = stack.peek();
			if (s.equals("#")){ 
				break;
			}
			number.insert(0, s);
			stack.pop();
		}
			stack.push(number.toString());
			stack.push("#");
			number.delete(0, number.length());
	}
	
	//��������ʽ
	public void calculate(Stack<String> numStack, Stack<String> oprStack) {
		
		double res=0;
		//�����Ҳ������ϵ�#������תΪdouble����
		numStack.pop();
		double rightNum = Double.parseDouble(numStack.pop());
		//������������ϵ�#������תΪdouble����
		numStack.pop();
		double leftNum = Double.parseDouble(numStack.pop());
		String opr = oprStack.pop();
		switch(opr){
			case "+": res = leftNum + rightNum;break;
			case "-": res = leftNum - rightNum;break;
			case "*": res = leftNum * rightNum;break;
			case "/": res = leftNum / rightNum;break;
			default:break;
		}
		
		//��������ѹջ
		numStack.push(String.valueOf(res));
		numStack.push("#");
	}
	
	//�����ַ������м������
	public String stringToRes(String str) {
		
		Stack<String> numStack = new Stack<String>();
		numStack.push("#");
		Stack<String> oprStack = new Stack<String>();
		String[] ss= new String[str.length()];
		for(int i=0;i<str.length();i++){
			ss[i] = str.substring(i, i+1);
		}
		
		for (String s : ss) {
			if (isOpr(s)) {
				linkString(numStack);
				if (oprStack.isEmpty()) {
					oprStack.push(s);
				} else {
					while (!oprStack.isEmpty()&&priority(s)<=priority(oprStack.peek())) {
						linkString(numStack);
						calculate(numStack, oprStack);
					}
					oprStack.push(s);
				}
			}else {
				numStack.push(s);
			}
		}
		while (!oprStack.isEmpty()) {
			linkString(numStack);
			calculate(numStack, oprStack);
		}
		numStack.pop();
		return numStack.peek();
	}

	public static void main(String[] args) {
		System.out.println("Please Input the Character String:");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String characterString = null;
		try {
			characterString = bReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringToRes stringToRes=new StringToRes();
		System.out.println("The Result of This String is:"+stringToRes.stringToRes(characterString));
	}

}
