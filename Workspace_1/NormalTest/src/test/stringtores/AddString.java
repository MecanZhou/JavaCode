/**
 * 
 */
package test.stringtores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Administrator
 * @time 2018年3月22日下午1:25:41
 *
 */
public class AddString {
	 public static void main(String[] args) {
	        File file = new File("a.txt");
	 
	        if (!file.exists()) {
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        //System.out.println(file.getAbsolutePath());
	        try {
	            FileInputStream fileInputStream = new FileInputStream(file);
	 
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
	            StringBuilder stringBuilder = new StringBuilder();
	            while (bufferedReader.ready()) {
	                String line = bufferedReader.readLine();
	                stringBuilder.append(line).append("$");
	                
	            }
	            System.out.println(stringBuilder.toString());
//	            FileOutputStream fos = new FileOutputStream(file);
//	            PrintWriter pw = new PrintWriter(fos);
//	            pw.write(stringBuilder.toString().replaceFirst("\n", "tag\n"));
//	            pw.flush();
//	            pw.close();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	 
	    }
}
