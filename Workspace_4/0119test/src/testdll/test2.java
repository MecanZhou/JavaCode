
 package testdll;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;


public class test2 {
		    public static void main(String[] args) throws NativeException, IllegalAccessException {
		    	System.load("C:\\Users\\Administrator\\documents\\visual studio 2012\\Projects\\MyDLL\\Debug\\MyDLL.dll");//dll����·��
		    	JNative jnative=new JNative ("MyDLL","my_strcat");//��һ������Ϊ���������ڶ�������Ϊ������
		    	jnative.setRetVal(Type.STRING);//����ֵ����
		    	jnative.setParameter(0, "Hello");//���õ��ý׶εĲ���
		    	jnative.setParameter(1, "World");
		    	jnative.invoke();//�Ժ������е���
		    	System.out.println(jnative.getRetValAsInt());//��ȡ��������ֵ
			    	    	
		    }

}
