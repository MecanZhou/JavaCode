package com.XmlToDoc;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.*;
import net.sf.json.xml.XMLSerializer;

public class XmlToDoc {
	
	private static final String STR_JSON = "{\"name\":\"Michael\",\"address\":{\"city\":\"shanghai\",\"street\":\" Changjiang Road \",\"postcode\":100025},\"blog\":\"http://cross.withiter.com\"}";
	
	
	public static String jsonTOXML(String json){
		JSONObject jobj = JSONObject.fromObject(json);
//		String xml =  new XMLSerializer().write(jobj);
		XMLSerializer xSerializer = new XMLSerializer();
		String xml=xSerializer.write(jobj);
		return xml;
	}
	
	public static String xmlToJSON(String xml){
		return new XMLSerializer().read(xml).toString();
	}
	
	public static void UpdateXml(Document document, String path) throws TransformerFactoryConfigurationError, TransformerException{
		
		Source source = new DOMSource(document);  
        Result result = new StreamResult(new File(path));  
        Transformer transformer = TransformerFactory.newInstance().newTransformer();  
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
        transformer.transform(source, result);	
        System.out.println("Created File successful!");
	}
	
	public static void xmlToDoc(String xml) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException{
		StringReader stringReader = new StringReader(xml);
		InputSource inputSource = new InputSource(stringReader);
		DocumentBuilderFactory dfb= DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dfb.newDocumentBuilder();
		Document document=db.parse(inputSource);
		UpdateXml(document, "test.xml");
		
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		String xml=jsonTOXML(STR_JSON);
		System.out.println(xml);
		String json=xmlToJSON(xml);
		System.out.println(json);
		xmlToDoc(xml);
	}

}
