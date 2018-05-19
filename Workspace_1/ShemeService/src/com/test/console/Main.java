package com.test.console;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.processors.DefaultDefaultValueProcessor;
import net.sf.json.xml.XMLSerializer;

public class Main {
	ShemeService ss=new ShemeService();
	
private static DocumentBuilder getDocumentbBuilder(){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("getDocumentbBuilder()Exception:ParserConfigurationException");
			e.printStackTrace();
		}
		
		return dBuilder;
	}
	
	//JSON格式数据转化为XML格式数据
	private static String jsonToXML(JSON json){
		
		JSONObject jobj = JSONObject.fromObject(json);
		XMLSerializer xSerializer = new XMLSerializer();
		String xml=xSerializer.write(jobj);
		
		return xml;
	}
	
	//XML格式数据转化为JSON格式数据
	private static JSON xmlToJSON(String xml){
		
		JSON json=null;
		XMLSerializer xSerializer = new XMLSerializer();
		json= xSerializer.read(xml);
		
		return json;
	}
	
	//document对象转化为XML
	private static String doc2ToXML(Document doc){		
          
        Source source = new DOMSource(doc);  
        StringWriter stringWriter = new StringWriter();  
        Result result = new StreamResult(stringWriter);  
        TransformerFactory factory = TransformerFactory.newInstance();  
        try {
        	Transformer transformer = factory.newTransformer();  
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
			transformer.transform(source, result);
			} catch (TransformerConfigurationException e) {
				System.out.println("doc2ToXML()Exception:TransformerConfigurationException");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println("doc2ToXML()Exception:IllegalArgumentException");
				e.printStackTrace();
			} catch (TransformerException e) {
				System.out.println("doc2ToXML()Exception:TransformerException");
				e.printStackTrace();
			}
            String xml=stringWriter.getBuffer().toString();
            return xml;
            
    }
	
	//XML格式字符串保存到document对象中	
	private static Document xmlToDoc(String xml) {
		
		StringReader stringReader = new StringReader(xml);
		InputSource inputSource = new InputSource(stringReader);
		DocumentBuilder dBuilder=getDocumentbBuilder();
		Document document=null;
		try {
			document = dBuilder.parse(inputSource);
		} catch (SAXException e) {
			System.out.println("xmlToDocument()Exception:SAXExceptionException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("xmlToDocument()Exception:IOException");
			e.printStackTrace();
		}
		return document;				
	}
	
	private  int findMax(String table) {
			DBService dbService=new DBService("admin", "root");
			List<Integer> ID = new ArrayList<Integer>();
			String sql="select SCHEME_ID from "+table;
			int max=0;
			try {
			ResultSet rs=dbService.ExecuteQuery(sql);
			   while(rs.next())
			   {
				   ID.add(Integer.valueOf(rs.getString("SCHEME_ID")));
			   }
			   max = ID.get(0);
			   for (int i = 0; i < ID.size(); i++)
			   {
			       if (ID.get(i) > max)
			       {
			           max = ID.get(i);
			       }
			   }
			} catch (NumberFormatException e) {
				System.out.println("findMax()Exception:NumberFormatException");
				e.printStackTrace();
			} catch (SQLException e) {
			System.out.println("findMax()Exception:SQLException");
				e.printStackTrace();
		}
			return max;
	   }
	
	public void add() {
		
		try {
			DocumentBuilder db = getDocumentbBuilder();
			Document document = db.parse("NewFile.xml");
			String xml=doc2ToXML(document);
			if (ss.AddSheme(xml) != null) {
				System.out.println("Add successful!");
			}
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pubulish(String shemeId) {
		
		Document document=ss.shemeDoc.get(shemeId);
		String xml=doc2ToXML(document);
		if (ss.PublishSheme(xml)) {
			System.out.println("Publish successful!");
		}	
		
	}
	
	public void open(String shemeId) {
		
		String xml=ss.OpenSheme(shemeId);
		System.out.println(xml);
		
	}
	
	public void delete(String shemeId) {
		
		if (ss.DeleteSheme(shemeId)) {
			System.out.println("Delete successful!");
		}
		
		
	}
	
	

	public static void main(String[] args) {
		Main m=new Main();
		m.add();
		int max=m.findMax("SIMU_SCHEME");
		String shemeId=String.valueOf(max);
		m.pubulish(shemeId);
		m.open(shemeId);
		m.delete(shemeId);
		JSON json=m.ss.GetAllShemeInfo();
		System.out.println(json);
	}

}
