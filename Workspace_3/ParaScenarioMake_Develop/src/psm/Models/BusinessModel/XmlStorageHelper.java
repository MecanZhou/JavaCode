package psm.Models.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XmlStorageHelper {
	 /// <summary>
    /// ��ȡXML�ļ�����ͬ�ڵ㼯�ϵ�ֵ
    /// </summary>
    /// <param name="path">�ļ�·��</param>
    /// <param name="nodeName">ָ���ڵ�</param>
    /// <returns></returns>
	public static List<String> GetNodeInfoByNodeName(String path, String nodeName) throws ParserConfigurationException, SAXException, IOException
    {
		
       List<String> nodeText=new ArrayList<String>();
      // nodeText.clear();
       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       DocumentBuilder db = dbf.newDocumentBuilder();
       Document doc =db.parse(path);
       Element root=doc.getDocumentElement();
       NodeList nodeList = root.getElementsByTagName(nodeName);
       if(nodeList != null && nodeList.getLength()>0){
    	   for(int i=0;i< nodeList.getLength();i++)
    	   {
    		   nodeText.add(nodeList.item(i).getTextContent());
    	   }
       }
       return nodeText;
    }

}
