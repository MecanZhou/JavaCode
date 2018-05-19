
 package testdll;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;


public class test2 {
		    public static void main(String[] args) throws NativeException, IllegalAccessException {
		    	System.load("C:\\Users\\Administrator\\documents\\visual studio 2012\\Projects\\MyDLL\\Debug\\MyDLL.dll");//dll加载路径
		    	JNative jnative=new JNative ("MyDLL","my_strcat");//第一个参数为工程名，第二个参数为函数名
		    	jnative.setRetVal(Type.STRING);//输入值类型
		    	jnative.setParameter(0, "Hello");//设置调用阶段的参数
		    	jnative.setParameter(1, "World");
		    	jnative.invoke();//对函数进行调用
		    	System.out.println(jnative.getRetValAsInt());//获取函数返回值
			    	    	
		    }

}
