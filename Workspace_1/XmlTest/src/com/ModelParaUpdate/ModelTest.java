package com.ModelParaUpdate;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class ModelTest {
	
private void UpdateXml(Document document, String path) throws TransformerException {
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(document), new StreamResult(new File(path)));
		
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
			
			JSONArray jobj = JSONArray.fromObject(json);
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
		private static String docToXML(Document doc){		
	          
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

	
	public void Update(String model) {
		
		String xml=model;
//		System.out.println(xml);
		Document document=xmlToDoc(xml);
//		System.out.println("444");
		
		NodeList memberList=document.getElementsByTagName("Member");
		for (int i = 0; i < memberList.getLength(); i++) {
				Node memberNode = memberList.item(i);
				NodeList modelList = memberNode.getChildNodes();
				for(int j=0;j<modelList.getLength();j++)					
					if (modelList.item(j).getNodeName().equals("Id")
							&&modelList.item(j).getFirstChild().getNodeValue().equals("1")) {
						System.out.println(j+":"+modelList.item(j).getNodeName()
						+":"+modelList.item(j).getFirstChild().getNodeValue());						
				for (int k = 0; k < modelList.getLength(); k++) {
					Node modelNode = modelList.item(k);
					NodeList paralistList=modelNode.getChildNodes();					
					if (modelList.item(k).getNodeName().equals("model")) {
						for (int l = 0; l < paralistList.getLength(); l++) {
							Node paralistNode = paralistList.item(l);
							NodeList paraInfoList=paralistNode.getChildNodes();			
							for (int m = 0; m < paraInfoList.getLength(); m++) {
								Node paraInfoNode = paraInfoList.item(m);
								NodeList paraList = paraInfoNode.getChildNodes();
								for (int n = 0; n < paraList.getLength(); n++) {
									if (paraList.item(n).getNodeName().equals("mpid")
											&&paraList.item(n).getTextContent().equals("480")) {
										for (int n2 = 0; n2 < paraList.getLength(); n2++) {
											if (!paraList.item(n2).getNodeName().equals("#text")
													&&paraList.item(n2).getFirstChild()!=null) {
												System.out.println(n2+":"+paraList.item(n2).getNodeName()
														+":"+paraList.item(n2).getFirstChild().getNodeValue());
											}									
										}
								}
								
										
									}
								}
							}
					}
					
					}
				}
			}
		}
	public void findPara(String modelXml) throws TransformerException {
		
		Document doc=xmlToDoc(modelXml);

		NodeList infoList=doc.getElementsByTagName("Model_Para_Info");
		for (int i = 0; i < infoList.getLength(); i++) {
			Node info = infoList.item(i);
			NodeList paralist=info.getChildNodes();
			for (int j = 0; j < paralist.getLength(); j++) {
				Node para=paralist.item(j);
				if (para.getNodeName().equals("mpid")) {
					
					if (para.getFirstChild()!=null) {	
						String mpid=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								mpid);
					}else {
						para.setNodeValue("");
						String mpid=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								mpid);
					}
					
				}
				if (para.getNodeName().equals("para_Label")) {
					
					if (para.getFirstChild()!=null) {	
						String para_Label=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								para_Label);
					}else {
						para.setNodeValue("");
						String para_Label=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								para_Label);
					}
					
				}
				if (para.getNodeName().equals("para_physicsunit")) {
					
					if (para.getFirstChild()!=null) {	
						String para_physicsunit=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								para_physicsunit);
					}else {
						para.setTextContent("111111");
						String para_physicsunit=para.getFirstChild().getNodeValue();
						System.out.println(para.getNodeName()+":"+
								para_physicsunit);
						
					}
					
				}
			}
			
		}
      UpdateXml(doc, "test.xml");

	}

	public static void main(String[] args) throws TransformerException {
		ModelTest mt=new ModelTest();
		DocumentBuilder db = getDocumentbBuilder();
		try {
			Document doc = db.parse("test.xml");
			String xml=docToXML(doc);
			mt.findPara(xml);
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
