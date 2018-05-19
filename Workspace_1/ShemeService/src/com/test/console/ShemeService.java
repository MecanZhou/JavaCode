package com.test.console;
/*
 * 方案处理服务控制台程序测试
 */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class ShemeService {
	
	//HashMap集合，用于存放内存中方案Document、方案版本String对象
	public static Map<String, Document> shemeDoc;
	private static Map<String, String> shemeVer;
	private static DBService dbService; 
	
	//构造函数
	public ShemeService() {
		ShemeService.shemeDoc= new HashMap<String,Document>();
		ShemeService.shemeVer= new HashMap<String, String>();
		dbService=new DBService("admin", "root");
	}
	
	//获取DocumentBuilder对象
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

	//标记磁盘方案版本号
	private static String signShemeVersion(String shemeId) {		
		
		return shemeVer.get(shemeId);
	}
	
	//从内存中提取方案
	private static Document extractSheme(String shemeId) {
		
		String shemeVersion=signShemeVersion(shemeId);
		String path=shemeId+"("+shemeVersion+")";
		DocumentBuilder dBuilder=getDocumentbBuilder();
		Document document=null;
		try {
			document = dBuilder.parse(path);			
		} catch (SAXException e) {
			System.out.println("OpenSheme()Exception:SAXException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("OpenSheme()Exception:IOException");
			e.printStackTrace();
		}
		return document;
	}
	
	//找出数据库中方案表ID最大值
	private static int findMax(String table) {
		
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
	
	//提取内存中各标签值
	private static String extractTagValue(String shemeId,String tagName) {
		
		Document document=shemeDoc.get(shemeId);
		NodeList tagList=document.getElementsByTagName(tagName);
		String tagValue=null;
		if (tagList.item(0).getFirstChild() != null) {
			tagValue=tagList.item(0).getFirstChild().getNodeValue();
			return tagValue;
		}else{
			return null;
		}
		
	}
	
	//新增方案
	public synchronized String AddSheme(String shemeXml) {
		
		Document document=xmlToDoc(shemeXml);
		Element shemeIdElement=(Element) document.getElementsByTagName("shemeId").item(0);
		int shemeId=0;
		int maxId=findMax("SIMU_SCHEME");
		if (maxId!=0) {
			shemeId = maxId+1;
		}		
		String shemeIdStr=null;
		if(shemeId!=0){
			shemeIdStr=String.valueOf(shemeId);
			shemeIdElement.setTextContent(shemeIdStr);;
			shemeDoc.put(shemeIdStr, document);
			
			String[] shemeTag={"shemeId","shemeName","beginTime","endTime",
					"generateDate","shemeAuthor","shemeDesc","shemeFile",
					"isPublish","sponserId"} ;
			String shemeName=extractTagValue(shemeIdStr,shemeTag[1]);
			String beginTime=extractTagValue(shemeIdStr,shemeTag[2]);
			String endTime=extractTagValue(shemeIdStr,shemeTag[3]);
			String generateDate=extractTagValue(shemeIdStr,shemeTag[4]);
			String shemeAuthor=extractTagValue(shemeIdStr,shemeTag[5]);
			String shemeDesc=extractTagValue(shemeIdStr,shemeTag[6]);
			String shemeFile=extractTagValue(shemeIdStr,shemeTag[7]);
			String isPublish=extractTagValue(shemeIdStr,shemeTag[8]);
			String sponserId=extractTagValue(shemeIdStr,shemeTag[9]);
			
			String sql="INSERT INTO SIMU_SCHEME VALUES ('"+shemeId+"','"+shemeName+"','"+
					beginTime+"','"+endTime+"','"+generateDate+"','"+shemeAuthor+
					"','"+shemeDesc+"','"+shemeFile+"','"+isPublish+"','"+sponserId
					+"')";
			dbService.ExecuteInsert(sql);
			return shemeIdStr;
		}else {
			return null;
		}
						
	}
	
	//发布方案，从内存中将方案保存到磁盘，并清除内存中方案
	public synchronized boolean PublishSheme(String shemeXml) {
		
		Document document=xmlToDoc(shemeXml);
		Element shemeIdElement=(Element) document.getElementsByTagName("shemeId").item(0);
		Element shemeVersionElement=(Element) document.getElementsByTagName("shemeVersion").item(0);
		Element isPublishElement=(Element) document.getElementsByTagName("isPublish").item(0);
		String shemeId=shemeIdElement.getFirstChild().getNodeValue();
		Date date=new Date();
		long l=date.getTime();
		String shemeVersion=String.valueOf(l);
		String path=shemeId+"("+shemeVersion+")";
		String sql="UPDATE SIMU_SCHEME SET ISPUBLISH = '1' WHERE SCHEME_ID ="
				+ "'"+shemeId+"'";
				
		Source source = new DOMSource(document);  
        Result result = new StreamResult(new File(path));  
        try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();  
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			shemeVersionElement.setTextContent(shemeVersion);
			isPublishElement.setTextContent("1");
			transformer.transform(source, result);
			shemeVer.put(shemeId, shemeVersion);
			dbService.ExecuteUpdate(sql);
			shemeDoc.remove(shemeId);
		} catch (TransformerConfigurationException e) {
			System.out.println("SaveSheme()Exception:TransformerConfigurationException");
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			System.out.println("SaveSheme()Exception:IllegalArgumentException");
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			System.out.println("SaveSheme()Exception:TransformerFactoryConfigurationError");
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			System.out.println("SaveSheme()Exception:TransformerException");
			e.printStackTrace();
			return false;
		}	
		isPublishElement.setTextContent("true");
		return true;
	}
	
	//打开方案，将磁盘中文件提取到内存中 
	public synchronized String OpenSheme(String shemeId) {
		
//		JSONObject jobj = JSONObject.fromObject(json);		
//		String shemeId=jobj.getString("shemeId");
		
		if (shemeDoc.get(shemeId)!=null) {
			Document document= shemeDoc.get(shemeId);
			String xml=doc2ToXML(document);
			return xml;
		} else {
			Document document= extractSheme(shemeId);
			shemeDoc.put(shemeId, document);
			String xml=doc2ToXML(document);
			
			return xml;
		}		
		
	}
	
	//删除方案
	public synchronized boolean DeleteSheme(String shemeId) {
		
//		JSONObject jobj = JSONObject.fromObject(json);		
//		String shemeId=jobj.getString("shemeId");
		
		Document document=shemeDoc.get(shemeId);
		if (document!=null) {
			shemeDoc.remove(shemeId);
			String sql="DELETE FROM SIMU_SCHEME WHERE SCHEME_ID = "+"'"+shemeId+"'";
			dbService.ExecuteDelete(sql);
			return true;
		} else {
			return false;
		}
		
	}
	
	//修改方案结构
	public synchronized boolean UpdateShemeStruct(String shemeXml) {
		
		Document document=xmlToDoc(shemeXml);
		Element shemeElement=(Element) document.getElementsByTagName("sheme").item(0);
		String shemeId=shemeElement.getAttribute("shemeId");
		if (shemeDoc.get(shemeId)!=null) {
			shemeDoc.remove(shemeId);
			shemeDoc.put(shemeId, document);
			return true;
		} else {
			return false;
		}		
	}
	
	//修改方案参数
	public synchronized boolean UpdateShemeParameter(JSON json) {
		
		JSONObject jobj = JSONObject.fromObject(json);
		String shemeId = jobj.get("shemeId").toString();
		Document document=shemeDoc.get(shemeId);
		UpdatParameter up=new UpdatParameter();
		Document doc=up.Update(jobj, document);
		shemeDoc.put(shemeId, doc);
		return true;
		
	}
	
	//获取方案全部信息
	public synchronized JSON GetAllShemeInfo() {
		
		String sql="SELECT * FROM SIMU_SCHEME";
		ResultSet rs=null;
		try {
			rs=dbService.ExecuteQuery(sql);
		} catch (SQLException e) {
			System.out.println("GetAllShemeInfo()Exception:SQLException");
			e.printStackTrace();
		}
		
		String shemeId="";
		String shemeName="";
		JSONObject joj=new JSONObject();
		
		if (rs!=null) {
			
			try {
				int col = rs.getMetaData().getColumnCount();
				while (rs.next()) {	        	
				    for (int i = 1; i <= col; i++) {
				        if (i==1) {
				            shemeId=rs.getString(i);
				        }
				        if (i==2) {
				            shemeName=rs.getString(i);
				        }
				        joj.put(shemeId, shemeName);
				     }				    
				}
			} catch (SQLException e) {
				System.out.println("GetAllShemeInfo()Exception:SQLException(2)");
				e.printStackTrace();
			}			
		}
		
		JSON json=joj;
		
		return json;
		
	}
	
	//后端服务请求，获取方案文件
	public synchronized String GetShemeFile(String shemeId) {
		
		Document document = extractSheme(shemeId);
		String xml=doc2ToXML(document);
		
		return xml;
	}


}
