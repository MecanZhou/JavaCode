package socketURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.omg.CORBA.portable.InputStream;

public class test01 {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.baidu.com");
			java.io.InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			String data = br.readLine();
			while(data != null){
				System.out.println(data);
				data = br.readLine();
			}
			br.close();
			isr.close();
			is.close();			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
