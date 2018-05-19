

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WRbufferTest {

	public static void main(String[] args) {
		try {
			FileInputStream fis=new FileInputStream("a1.txt");
			InputStreamReader isr=new InputStreamReader(fis);
			FileOutputStream fos=new FileOutputStream("a1_new.txt");
			OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
			BufferedReader br=new BufferedReader(isr);
			/*BufferedWriter bw=new BufferedWriter(osw);*/
			PrintWriter pw=new PrintWriter(osw,true);
			String str;
			try {
				while((str=br.readLine()) !=null){
					pw.println(str);
				}
				/*bw.flush();
				bw.close();*/
				pw.close();
				br.close();
				osw.close();
				fos.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

}
