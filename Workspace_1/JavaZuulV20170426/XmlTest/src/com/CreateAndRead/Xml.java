package com.CreateAndRead;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml {
	
	private void UpdateXml(Document document, String path) throws TransformerException {
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(document), new StreamResult(new File(path)));
		
	}
	
	private DocumentBuilder getDocumentbBuilder(){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return db;
	}
	
	private void CreateXml() throws TransformerException{
		
		DocumentBuilder db = getDocumentbBuilder();
		Document document = db.newDocument();
		document.setXmlStandalone(true);
		
		Element library = document.createElement("library");
		Element book1 = document.createElement("book");
		book1.setAttribute("ISBN", "00001");
		Element book2 = document.createElement("book");
		book2.setAttribute("ISBN", "00002");
		Element book1Name = document.createElement("bookName");
		book1Name.setTextContent("Java");
		Element book2Name = document.createElement("bookName");
		book2Name.setTextContent("C++");
		
		book1.appendChild(book1Name);
		book2.appendChild(book2Name);
		library.appendChild(book1);
		library.appendChild(book2);
		document.appendChild(library);
		
		UpdateXml(document, "library.xml");
		
	}
	
	private void ReadXml() throws SAXException, IOException {
		
			DocumentBuilder db = getDocumentbBuilder();
			Document document = db.parse("library.xml");
			NodeList bookList = document.getElementsByTagName("book");
			System.out.println("һ����" + bookList.getLength() + "����");
			for (int i = 0; i < bookList.getLength(); i++) {
				System.out.println("=================���濪ʼ������" + (i + 1) + "���������=================");
				Node book = bookList.item(i);
				NamedNodeMap attrs = book.getAttributes();
				System.out.println("�� " + (i + 1) + "���鹲��" + attrs.getLength() + "������");
				for (int j = 0; j < attrs.getLength(); j++) {
					Node attr = attrs.item(j);
					System.out.print("��������" + attr.getNodeName());
					System.out.println("--����ֵ" + attr.getNodeValue());
				}
				NodeList childNodes = book.getChildNodes();
				System.out.println("��" + (i+1) + "���鹲��" + 
				childNodes.getLength() + "���ӽڵ�");
				for (int k = 0; k < childNodes.getLength(); k++) {
					if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
						System.out.print("��" + (k + 1) + "���ڵ�Ľڵ�����" 
						+ childNodes.item(k).getNodeName());
						System.out.println("--�ڵ�ֵ�ǣ�" + childNodes.item(k).getFirstChild().getNodeValue());
					}
				}
				System.out.println("======================����������" + (i + 1) + "���������=================");
			}
		
	}
	
	public static void main(String[] args) throws TransformerException, SAXException, IOException {
		
		Xml xml = new Xml();
		xml.CreateXml();
		xml.ReadXml();

	}

}

