package org.springframework.boot.FormulaService;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.json.JSONObject;

public class UpdatePara {
	
public Document Update(JSONObject jobj,Document document) {
		
		String memberId=jobj.get("MemberId").toString();
		String mpid=jobj.get("mpid").toString();
		String para_Type=jobj.get("para_Type").toString();
		String para_Defa_Value=jobj.get("para_Defa_Value").toString();
		String para_Label=jobj.get("para_Label").toString();
		String para_physicsunit=jobj.get("para_physicsunit").toString();
		String para_Key=jobj.get("para_Key").toString();
		String basic_Type=jobj.get("basic_Type").toString();
		String para_Lenth=jobj.get("para_Lenth").toString();
		String para_Desc=jobj.get("para_Desc").toString();
		
		
		NodeList memberList=document.getElementsByTagName("Member");
		for (int i = 0; i < memberList.getLength(); i++) {
				Node memberNode = memberList.item(i);
				NodeList modelList = memberNode.getChildNodes();
				for(int j=0;j<modelList.getLength();j++)					
					if (modelList.item(j).getNodeName().equals(memberId)
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
											&&paraList.item(n).getFirstChild().getNodeValue().equals(mpid)) {
										for (int n2 = 0; n2 < paraList.getLength(); n2++) {
											if (paraList.item(n2).getNodeName().equals("para_Type")
													&&para_Type.equals(null)) {
												paraList.item(n2).setTextContent(para_Type);
											}
											if (paraList.item(n2).getNodeName().equals("para_Defa_Value")
													&&para_Defa_Value.equals(null)) {
												paraList.item(n2).setTextContent(para_Defa_Value);
											}
											if (paraList.item(n2).getNodeName().equals("para_Label")
													&&para_Label.equals(null)) {
												paraList.item(n2).setTextContent(para_Label);
											}
											if (paraList.item(n2).getNodeName().equals("para_physicsunit")
													&&para_physicsunit.equals(null)) {
												paraList.item(n2).setTextContent(para_physicsunit);
											}
											if (paraList.item(n2).getNodeName().equals("para_Key")
													&&para_Key.equals(null)) {
												paraList.item(n2).setTextContent(para_Key);
											}
											if (paraList.item(n2).getNodeName().equals("basic_Type")
													&&basic_Type.equals(null)) {
												paraList.item(n2).setTextContent(basic_Type);
											}
											if (paraList.item(n2).getNodeName().equals("para_Lenth")
													&&para_Lenth.equals(null)) {
												paraList.item(n2).setTextContent(para_Lenth);
											}
											if (paraList.item(n2).getNodeName().equals("para_Desc")
													&&para_Desc.equals(null)) {
												paraList.item(n2).setTextContent(para_Desc);
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
		Document doc=document;
		
		return doc;
	}

}
