package psm.Models.BusinessModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.xml.sax.SAXException;

import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Formula;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.MemberInteraction;
import psm.Models.DataModel.MemberObject;
import psm.Models.DataModel.ModelClass.Model_para_info;

import psm.Models.DataModel.ModelClass.ParaType;

public class FileManage implements InterfaceFileManage {
	public  ArrayList<objectClass> objParameters=new ArrayList<objectClass>();
	
	FrmUpModelManage frmUpModelManage=new FrmUpModelManage();
    
	/**
	 * 创建FOM.XML文件
	 */
	public void CreateFomXml() {
		Formula newFormula = FormulaManage.getFormula();

		String xmlPath = "src\\psm\\Image\\FOM_Template.xml";
		String SavePath = "src\\psm\\FomOrScheme\\";
		DocumentBuilder db = null;
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// 创建DocumentBuilder对象
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		File fileXml = new File(xmlPath);
		try {
			// 解析FOM_Template.xml
			doc = db.parse(fileXml);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element root = doc.getDocumentElement();
		root.setAttribute("xmlns",
				"http://www.sisostds.org/schemas/IEEE1516-2010");
		root.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute(
				"xsi:schemaLocation",
				"http://www.sisostds.org/schemas/IEEE1516-2010 http://www.sisostds.org/schemas/IEEE1516-DIF-2010.xsd");
		NodeList nlf = doc.getElementsByTagName("objectClass");

		for (Member member : newFormula.getMemberList()) {			
			Element eltOc = doc.createElement("objectClass");
			eltOc.setAttribute("ID", member.Id);
			
			for (MemberObject memberObject : newFormula.getMemberObjectList()) {
				if(memberObject.getProduceName().equals(member.Name)&&memberObject.getProducer().getSendToId().size()>0){
					Element eltAttributeName1 = doc.createElement("Name");
					eltAttributeName1.setTextContent("Model"+memberObject.getProducer().Id);
					eltOc.appendChild(eltAttributeName1);
					objectClass ob=new objectClass();
					ob.setName("Model"+memberObject.getProducer().Id);//为FED获取内容
					for(int a=0;a<memberObject.getProducer().getSendToId().size();a++){
						Element eltAttributeName2 = doc.createElement("Attribute");
						eltAttributeName2.setTextContent("isOutput");
						eltOc.appendChild(eltAttributeName2);
						ob.setValue("isOutput");//为FED获取内容
					}
					objParameters.add(ob);//为FED获取内容
				
//				Rectangle[] rects = new Rectangle[4];
//				rects[0] = member.pictureOfMember.LogicalRect[4];
//				rects[1] = member.pictureOfMember.LogicalRect[5];
//				rects[2] = member.pictureOfMember.LogicalRect[6];
//				rects[3] = member.pictureOfMember.LogicalRect[7];
//				for (int i = 0; i < 4; i++) {
//					// 如果该成员为订购者且关系类型为对象类:公布方
//					if (memberObject.getProduceName().equals(member.Name)&& rects[i].contains(memberObject.logicalLine.StartPoint.getX()+2,memberObject.logicalLine.StartPoint.getY()+2)) {
//						if (memberObject.logicalLine.lineAttribute.isInput) {
//							Element eltAttr = doc.createElement("Attribute");
//							eltAttr.setTextContent("isInput");
//							eltOc.appendChild(eltAttr);
//						}
//
//						if (memberObject.logicalLine.lineAttribute.isOutput) {
//							Element eltAttr = doc.createElement("Attribute");
//									eltAttr.setTextContent("isOutput");
//							eltOc.appendChild(eltAttr);
//						}
//						if (memberObject.logicalLine.lineAttribute.isInit) {
//							Element eltAttr = doc.createElement("Attribute");
//							eltAttr.setTextContent("isInit");
//							eltOc.appendChild(eltAttr);
//						}
//						if (memberObject.logicalLine.lineAttribute.isModel) {
//							Element eltAttr = doc.createElement("Attribute");
//							eltAttr.setTextContent("isModel");
//							eltOc.appendChild(eltAttr);
//						}
//					}
//				}				
			}
			if (eltOc.getAttributes().item(0).getNodeValue().equals(member.Id)) {
				for (int k = 0; k < eltOc.getChildNodes().getLength(); k++) {					
					for (int j = 0; j < eltOc.getChildNodes().getLength() - 1; j++) {
						if (eltOc.getChildNodes().item(k).getTextContent().toString().trim().equals(eltOc.getChildNodes().item(j).getTextContent().toString().trim())&&k!=j) {
							eltOc.removeChild(eltOc.getChildNodes().item(j));
							j=j-1;
						}
					}
				}

			}
			nlf.item(1).appendChild(eltOc);
		}			
		
		}
		try {
			transformerXml(newFormula.Name, doc, SavePath, "Fom.xml");
		} 
		catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(new JPanel(), "FOM文件生成成功", "提示",
				JOptionPane.YES_NO_CANCEL_OPTION);
		//生成FED
		try {
		WriteFED(newFormula.Name,SavePath+newFormula.Name+"FED.fed");
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	/**
	 * 创建Som.xml文件
	 * 
	 * @throws TransformerConfigurationException
	 */
	public void CreateSomXml() {
		Formula newFormula = FormulaManage.getFormula();
		List<String> LocationList=RelationManage.GetLocationList();//获取参数信息表
		int schemeID = 0;
		try {
			schemeID = frmUpModelManage.findMax("select * from SCHEME_MODEL_INFO")+1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		TurnToStandardFormula(newFormula);
		
		String xmlPath = "src\\psm\\Image\\Config_Template.xml";
		String SavePath = "src\\psm\\FomOrScheme\\";
		DocumentBuilder db = null;
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// 创建DocumentBuilder对象
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		File fileXml = new File(xmlPath);
		try {
			// 解析FOM_Template.xml
			doc = db.parse(fileXml);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList nlGlobalInfo=doc.getElementsByTagName("GlobalInfo");
		
		NodeList nlScenarioID = doc.getElementsByTagName("ScenarioID");// 返回带有指定标签名的对象集合
		nlScenarioID.item(0).setTextContent(String.valueOf(schemeID));
		NodeList nlFederationName = doc.getElementsByTagName("FederationName");// 返回带有指定标签名的对象集合
		nlFederationName.item(0).setTextContent(newFormula.Name);// 设置联邦名称
		
		Element eltpublishtime=doc.createElement("Public_time");
		eltpublishtime.setTextContent("2014-11-19");		
		nlGlobalInfo.item(0).appendChild(eltpublishtime);
		
		Element eltModlfiedtime=doc.createElement("Modlfied_time");
		eltModlfiedtime.setTextContent("2014-11-19");
		nlGlobalInfo.item(0).appendChild(eltModlfiedtime);
		
		Element eltSchemeAuthor=doc.createElement("SchemeAuthor");
		if(newFormula.Author!=null){
			eltSchemeAuthor.setTextContent(newFormula.Author);
		}else{
			eltSchemeAuthor.setTextContent("admin");
		}
		nlGlobalInfo.item(0).appendChild(eltSchemeAuthor);
		
		Element eltcount=doc.createElement("Count");
		eltcount.setTextContent(String.valueOf(newFormula.getMemberList().size()));
		nlGlobalInfo.item(0).appendChild(eltcount);
		
		for (Member member : newFormula.getMemberList()) {
			NodeList nlModelInfo = doc.getElementsByTagName("ModelInfo");
			Element elt1 = doc.createElement("ModelExeConfig");
			Element eltsub1 = doc.createElement("FederateName");
			eltsub1.setTextContent(member.Name);
			elt1.appendChild(eltsub1);
			Element eltsub2 = doc.createElement("FOM");
			elt1.appendChild(eltsub2);
			Element eltmuid = doc.createElement("Muid");
			eltmuid.setTextContent(String.valueOf(member.Model.Muid));
			elt1.appendChild(eltmuid);
			Element eltsub3 = doc.createElement("MemberID");
			eltsub3.setTextContent(member.Id);
			elt1.appendChild(eltsub3);
			Element eltsub5 = doc.createElement("ModelName");
			eltsub5.setTextContent(member.Model.Model_name);
			elt1.appendChild(eltsub5);
			Element eltdll = doc.createElement("MemberDLL");
			eltdll.setTextContent(member.Model.Model_target);
			elt1.appendChild(eltdll);
			Element eltsub6 = doc.createElement("LocalSettingDesignator");
			eltsub6.setTextContent("1");
			elt1.appendChild(eltsub6);
			Element eltimer=doc.createElement("Timer");
			eltimer.setTextContent(member.memberTimer);
			elt1.appendChild(eltimer);
			Element eltsub7 = doc.createElement("IsTimeRegulating");
			eltsub7.setTextContent(member.IsTimeRegulating ? "1" : "0");
			elt1.appendChild(eltsub7);
			Element eltsub8 = doc.createElement("IsTimeConstrained");
			eltsub8.setTextContent(member.IsTimeConstrained ? "1" : "0");
			elt1.appendChild(eltsub8);
			Element eltsub9 = doc.createElement("Lookahead");
			eltsub9.setTextContent(member.Step);
			elt1.appendChild(eltsub9);
			Element eltsub10 = doc.createElement("PublishObjectClassName");
			elt1.appendChild(eltsub10);
			Element eltsub11 = doc.createElement("SubscribeObjectClassNames");
			elt1.appendChild(eltsub11);
			Element eltsub12 = doc.createElement("PublishInteractionClassNames");
			elt1.appendChild(eltsub12);
			Element eltsub13 = doc.createElement("SubscribeInteractionClassNames");
			elt1.appendChild(eltsub13);
			
			Element eltsub14 = doc.createElement("SynchronizationPoints");
			elt1.appendChild(eltsub14);
			
			Element eltsub15 = doc.createElement("Display");
			Element eltsub16 = doc.createElement("InputSources");
			


			for (MemberObject memberObject : newFormula.getMemberObjectList()) {
				if(memberObject.getConsumerName().equals(member.Name)&&memberObject.getConsumer().getGetFromId().size()>0){
					for(int a=0;a<memberObject.getConsumer().getGetFromId().size();a++){
						Element eltInputSource = doc.createElement("InputSource");
						eltInputSource.setAttribute("id",memberObject.getConsumer().getGetFromId().get(a));// 获取发布方的id
						
						Element eltAttributeName2 = doc.createElement("Attribute");
						eltAttributeName2.setTextContent("isOutput");
						eltInputSource.appendChild(eltAttributeName2);
						

						for(String valuetable:LocationList){
							String[] value=valuetable.split("/");
							String[] ID=memberObject.getConsumer().getGetFromId().get(a).split("-");
							if(value[0].equals(ID[0])){
								Element eltLocation = doc.createElement("Location");
						        eltLocation.setTextContent(value[1]);
						        eltInputSource.appendChild(eltLocation);
						       
						     }
						}
						
						eltsub16.appendChild(eltInputSource);

					}
				}
				
			}
			
			for(int k= 0; k< eltsub16.getChildNodes().getLength();k++){
				for(int j=0;j< eltsub16.getChildNodes().getLength();j++){
					if(eltsub16.getChildNodes().item(k).getAttributes().item(0).toString().trim().
							equals(eltsub16.getChildNodes().item(j).getAttributes().item(0).toString().trim())&&k!=j){
						eltsub16.removeChild(eltsub16.getChildNodes().item(j));
						j=j-1;
					}
					
				}
			}
			elt1.appendChild(eltsub16);
			Element eltsub20 = doc.createElement("OutputSources");
			for (MemberObject memberObject : newFormula.getMemberObjectList()) {
				if(memberObject.getProduceName().equals(member.Name)&&memberObject.getProducer().getSendToId().size()>0){
					for(int a=0;a<memberObject.getProducer().getSendToId().size();a++){
						Element eltOutputSource = doc.createElement("OutputSource");
						eltOutputSource.setAttribute("id",memberObject.getProducer().getSendToId().get(a));// 获取发布方的id
						Element eltAttributeName2 = doc.createElement("Attribute");
						eltAttributeName2.setTextContent("isOutput");
						eltOutputSource.appendChild(eltAttributeName2);
						
						eltsub20.appendChild(eltOutputSource);
					}

				}

			}
			
			for(int k= 0; k< eltsub20.getChildNodes().getLength();k++){
				for(int j=0;j< eltsub20.getChildNodes().getLength();j++){					
					if(eltsub20.getChildNodes().item(k).getAttributes().item(0).toString().trim().equals(eltsub20.getChildNodes().item(j).getAttributes().item(0).toString().trim())&&k!=j){						
						eltsub20.removeChild(eltsub20.getChildNodes().item(j));
						j=j-1;
					}
				}
			}
			elt1.appendChild(eltsub20);

			Element eltsub17 = doc.createElement("EventReceivers");
			for (MemberInteraction memberInteraction : newFormula
					.getMemberInteractionList()) {
				// 如果该成员为公布者且关系类型为交互类:发送方
				if (memberInteraction.getProduceName().equals(member.Name)) {
					Element eltEventReceiver = doc
							.createElement("EventReceiver");
					eltEventReceiver.setTextContent(String
							.valueOf(memberInteraction.getConsumerId()));
					eltsub17.appendChild(eltEventReceiver);
				}
			}
			elt1.appendChild(eltsub17);
			Element eltsub18 = doc.createElement("InitStates");
			Element eltsub19 = doc.createElement("InitParameters");
			if (member.Model.Model_para_infoList.size() > 0) {
				for (Model_para_info para : member.Model.Model_para_infoList) {
					
					switch (para.Para_type) {
					case init_para:
						Element eltsub181 = doc.createElement("Parameter");
						Element eltsub1812 = doc.createElement("Label");
						eltsub1812.setTextContent(para.Para_label);
						eltsub181.appendChild(eltsub1812);
						if (para.Para_length == 1) {
							Element eltsub18131 = doc.createElement("Type");
							eltsub18131.setTextContent(para.Basic_type);
							eltsub181.appendChild(eltsub18131);
							Element eltsub18132 = doc.createElement("Value");
							eltsub18132.setTextContent(para.Para_defa_value);
							eltsub181.appendChild(eltsub18132);
						} else {
							Element eltsub18131 = doc.createElement("Type1");
							eltsub18131.setTextContent("array");
							eltsub181.appendChild(eltsub18131);
							Element eltsub181322 = doc.createElement("Value");
							eltsub181322.setTextContent(para.Para_defa_value);
							eltsub181.appendChild(eltsub181322);
						}
						eltsub18.appendChild(eltsub181);
						elt1.appendChild(eltsub18);
						break;
					case model_para:
						Element eltsub191 = doc.createElement("Parameter");
						Element eltsub1911 = doc.createElement("Name");
						eltsub1911.setTextContent(para.Para_physicsunit);
						eltsub191.appendChild(eltsub1911);
						Element eltsub1912 = doc.createElement("Label");
						eltsub1912.setTextContent(para.Para_label);
						eltsub191.appendChild(eltsub1912);
						Element eltsub1913 = doc.createElement("Value");
						if (para.Para_length == 1) {
							Element eltsub19131 = doc.createElement("Type");
							eltsub19131.setTextContent(para.Basic_type);
							eltsub1913.appendChild(eltsub19131);
							Element eltsub19132 = doc.createElement("Value");
							eltsub19132.setTextContent(para.Para_defa_value);
							eltsub1913.appendChild(eltsub19132);
						} else {
							Element eltsub19131 = doc.createElement("Type2");
							eltsub19131.setTextContent("array");
							eltsub1913.appendChild(eltsub19131);
							Element eltsub191322 = doc.createElement("Value");
							eltsub191322.setTextContent(para.Para_defa_value);
							eltsub1913.appendChild(eltsub191322);
						}
						eltsub191.appendChild(eltsub1913);
						eltsub19.appendChild(eltsub191);
						elt1.appendChild(eltsub19);
						break;
					case input_para:
						Element eltsub155 = doc.createElement("InputPara");
						Element eltsub156 = doc.createElement("Key");
						eltsub156.setTextContent(para.Para_physicsunit);
						eltsub155.appendChild(eltsub156);
						Element eltsub1517 = doc.createElement("Length");
						eltsub1517.setTextContent(String.valueOf(para.Para_length));
						eltsub155.appendChild(eltsub1517);
						Element eltsub1518 = doc.createElement("Value");
						eltsub1518.setTextContent(para.Para_defa_value);
						eltsub155.appendChild(eltsub1518);
						Element eltsub1519 = doc.createElement("Label");
						eltsub1519.setTextContent(para.Para_label);
						eltsub155.appendChild(eltsub1519);
						eltsub15.appendChild(eltsub155);
						elt1.appendChild(eltsub15);
						break;
					case output_para:
						Element eltsub151 = doc.createElement("OutputPara");
						Element eltsub1513 = doc.createElement("Value");
						eltsub1513.setTextContent(para.Para_defa_value);
						eltsub151.appendChild(eltsub1513);
						Element eltsub1514 = doc.createElement("Label");
						eltsub1514.setTextContent(para.Para_label);
						eltsub151.appendChild(eltsub1514);
						Element eltsub1511 = doc.createElement("Key");
						eltsub1511.setTextContent(para.Para_physicsunit);
						eltsub151.appendChild(eltsub1511);
						Element eltsub1512 = doc.createElement("Length");
						eltsub1512.setTextContent(String.valueOf(para.Para_length));
						eltsub151.appendChild(eltsub1512);						
						eltsub15.appendChild(eltsub151);
						elt1.appendChild(eltsub15);
						break;					
					}
				}
			}
			nlModelInfo.item(0).appendChild(elt1);

		}
		// file表示指定路径下的文件
		File file = new File(SavePath + newFormula.Name + "Som.xml");
		if (file.exists()) {
			int result = JOptionPane.showOptionDialog(new JPanel(),
					"存在同名文件，确认覆盖？", "提示", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if (result == 0) {
				file.delete();
				try {
					transformerXml(newFormula.Name, doc, SavePath, "Som.xml");
					
					
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				}
				try {
				frmUpModelManage.upSchemeModelInfo(SavePath + newFormula.Name + "Som.xml");
				frmUpModelManage.upSchemeDescInfo(SavePath + newFormula.Name + "Som.xml");
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				JOptionPane.showMessageDialog(new JPanel(), "Som文件保存成功",
						"提示", JOptionPane.YES_NO_CANCEL_OPTION);
			}
		} 
		else {
			try {
				transformerXml(newFormula.Name, doc, SavePath, "Som.xml");
				

			} catch (TransformerConfigurationException e) {
				
				e.printStackTrace();
			}
			try {
			frmUpModelManage.upSchemeModelInfo(SavePath + newFormula.Name + "Som.xml");
			frmUpModelManage.upSchemeDescInfo(SavePath + newFormula.Name + "Som.xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			JOptionPane.showMessageDialog(new JPanel(), "Som文件保存成功", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
		}
	}

	/**
	 * 创建组合成员的的XML文件
	 * @param combineMember 要生成XML的组合成员
	 */
	public void CreateCombineMembersXml(CombineMember combineMember){
		String xmlPath = "src\\psm\\Image\\CombineMember_Template.xml";
		String SavePath = "src\\psm\\CombineMembers\\"; //设置保存路径
		DocumentBuilder db = null;
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// 创建DocumentBuilder对象
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		File fileXml = new File(xmlPath);
		try {
			// 解析FOM_Template.xml
			doc = db.parse(fileXml);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		NodeList nlCombineMemberInfo=doc.getElementsByTagName("CombineMemberInfo");
		
		NodeList nlCombineMemberId = doc.getElementsByTagName("CombineMemberID");// 返回带有指定标签名的对象集合
		nlCombineMemberId.item(0).setTextContent(combineMember.Id);// 设置组合成员的id
		NodeList nlCombineMemberName = doc.getElementsByTagName("CombineMemberName");// 返回带有指定标签名的对象集合
		nlCombineMemberName.item(0).setTextContent(combineMember.Name);// 设置组合成员的名字
		NodeList nlCombineMemberOutLine = doc.getElementsByTagName("CombineMemberOutLine");// 返回带有指定标签名的对象集合
		nlCombineMemberOutLine.item(0).setTextContent(combineMember.outLine);// 设置组合成员的描述
		
		
		Element eltpublishtime=doc.createElement("Public_time");
		eltpublishtime.setTextContent("2014-11-19");		
		nlCombineMemberInfo.item(0).appendChild(eltpublishtime);
		
		Element eltModlfiedtime=doc.createElement("Modlfied_time");
		eltModlfiedtime.setTextContent("2014-11-19");
		nlCombineMemberInfo.item(0).appendChild(eltModlfiedtime);
		
		Element eltcmmuid=doc.createElement("Muid");
		eltcmmuid.setTextContent("0");
		nlCombineMemberInfo.item(0).appendChild(eltcmmuid);
		
		Element eltcmsrc=doc.createElement("Hava_src");
		eltcmsrc.setTextContent("fales");
		nlCombineMemberInfo.item(0).appendChild(eltcmsrc);
		
		Element eltcmdoc=doc.createElement("Hava_doc");
		eltcmdoc.setTextContent("false");
		nlCombineMemberInfo.item(0).appendChild(eltcmdoc);
		
		Element eltcmverified=doc.createElement("Is_verified");
		eltcmverified.setTextContent("false");
		nlCombineMemberInfo.item(0).appendChild(eltcmverified);
		
		Element eltcmparainfolist=doc.createElement("Model_para_infoList");

		for(Member inputMember:combineMember.getInputRelationList()){
			if(inputMember.Model.Model_para_infoList.size()>0){
				for (Model_para_info inputpara : inputMember.Model.Model_para_infoList) {
					switch (inputpara.Para_type) {
					case input_para:

						Element eltcmparainfo=doc.createElement("Model_para_info");
						
						Element eltcmparamuid=doc.createElement("Muid");
						eltcmparamuid.setTextContent(String.valueOf(inputpara.Muid));
						eltcmparainfo.appendChild(eltcmparamuid);
						
						Element eltcmparatype=doc.createElement("Para_type");
						eltcmparatype.setTextContent(String.valueOf(inputpara.Para_type));
						eltcmparainfo.appendChild(eltcmparatype);
						
						
						Element eltcmparaUniqueId=doc.createElement("UniqueId");
						eltcmparaUniqueId.setTextContent(String.valueOf(inputMember.UniqueId));
						eltcmparainfo.appendChild(eltcmparaUniqueId);
						
						
						Element eltcmparadefa=doc.createElement("Para_defa_value");
						eltcmparadefa.setTextContent(inputpara.Para_defa_value);
						eltcmparainfo.appendChild(eltcmparadefa);
						
						Element eltcmparalable=doc.createElement("Para_lable");
						eltcmparalable.setTextContent(inputpara.Para_label);
						eltcmparainfo.appendChild(eltcmparalable);
						
						Element eltcmparainfo_=doc.createElement("Para_info_");
						
						Element eltcmpid=doc.createElement("Pid");
						eltcmpid.setTextContent(String.valueOf(inputpara.Mpid));
						eltcmparainfo_.appendChild(eltcmpid);
						
						
						Element eltcmparamuid_=doc.createElement("Muid");
						eltcmparamuid_.setTextContent(String.valueOf(inputpara.Muid));
						eltcmparainfo_.appendChild(eltcmparamuid_);
						
						Element eltcmparaphysics=doc.createElement("Para_physicsunit");
						eltcmparaphysics.setTextContent(String.valueOf(inputpara.Para_physicsunit));
						eltcmparainfo_.appendChild(eltcmparaphysics);
						
						Element eltcmparabasictype=doc.createElement("Basic_type");
						eltcmparabasictype.setTextContent(String.valueOf(inputpara.Basic_type));
						eltcmparainfo_.appendChild(eltcmparabasictype);
						
						Element eltcmparalength=doc.createElement("Para_length");
						eltcmparalength.setTextContent(String.valueOf(inputpara.Para_length));
						eltcmparainfo_.appendChild(eltcmparalength);
						
						Element eltcmparadesc=doc.createElement("Para_desc");
						eltcmparadesc.setTextContent(String.valueOf(inputpara.Para_desc));
						eltcmparainfo_.appendChild(eltcmparadesc);
						
						eltcmparainfo.appendChild(eltcmparainfo_);
						eltcmparainfolist.appendChild(eltcmparainfo);
					}
					
				}
				
			}
		}
		
		for(Member outputMember:combineMember.getOutputRelationList()){
			if(outputMember.Model.Model_para_infoList.size()>0){
				for (Model_para_info outputpara : outputMember.Model.Model_para_infoList) {
					switch (outputpara.Para_type) {
					case output_para:
						Element eltcmparainfo=doc.createElement("Model_para_info");
						Element eltcmparamuid=doc.createElement("Muid");
						eltcmparamuid.setTextContent(String.valueOf(outputpara.Muid));
						eltcmparainfo.appendChild(eltcmparamuid);
						
						
						Element eltcmparatype=doc.createElement("Para_type");
						eltcmparatype.setTextContent(String.valueOf(outputpara.Para_type));
						eltcmparainfo.appendChild(eltcmparatype);

						Element eltcmparaUniqueId=doc.createElement("UniqueId");
						eltcmparaUniqueId.setTextContent(String.valueOf(outputMember.UniqueId));
						eltcmparainfo.appendChild(eltcmparaUniqueId);
						
						Element eltcmparadefa=doc.createElement("Para_defa_value");
						eltcmparadefa.setTextContent(outputpara.Para_defa_value);
						eltcmparainfo.appendChild(eltcmparadefa);
						
						Element eltcmparalable=doc.createElement("Para_lable");
						eltcmparalable.setTextContent(outputpara.Para_label);
						eltcmparainfo.appendChild(eltcmparalable);
						
						Element eltcmparainfo_=doc.createElement("Para_info_");
						
						Element eltcmpid=doc.createElement("Pid");
						eltcmpid.setTextContent(String.valueOf(outputpara.Mpid));
						eltcmparainfo_.appendChild(eltcmpid);
						
						
						Element eltcmparamuid_=doc.createElement("Muid");
						eltcmparamuid_.setTextContent(String.valueOf(outputpara.Muid));
						eltcmparainfo_.appendChild(eltcmparamuid_);
						
						Element eltcmparaphysics=doc.createElement("Para_physicsunit");
						eltcmparaphysics.setTextContent(String.valueOf(outputpara.Para_physicsunit));
						eltcmparainfo_.appendChild(eltcmparaphysics);
						
						Element eltcmparabasictype=doc.createElement("Basic_type");
						eltcmparabasictype.setTextContent(String.valueOf(outputpara.Basic_type));
						eltcmparainfo_.appendChild(eltcmparabasictype);
						
						Element eltcmparalength=doc.createElement("Para_length");
						eltcmparalength.setTextContent(String.valueOf(outputpara.Para_length));
						eltcmparainfo_.appendChild(eltcmparalength);
						
						Element eltcmparadesc=doc.createElement("Para_desc");
						eltcmparadesc.setTextContent(String.valueOf(outputpara.Para_desc));
						eltcmparainfo_.appendChild(eltcmparadesc);
						
						eltcmparainfo.appendChild(eltcmparainfo_);
						eltcmparainfolist.appendChild(eltcmparainfo);
					
					}
					
				}
				
			}
		}
		nlCombineMemberInfo.item(0).appendChild(eltcmparainfolist);
		
		for (Member member : combineMember.memberList) {

			NodeList nlModelInfo = doc.getElementsByTagName("ModelInfo");			
			
			Element elt1 = doc.createElement("ModelExeConfig");
			Element eltsub1 = doc.createElement("FederateName");
			eltsub1.setTextContent(member.Name);
			elt1.appendChild(eltsub1);
			Element eltsub3 = doc.createElement("MemberID");
			eltsub3.setTextContent(member.Id);
			elt1.appendChild(eltsub3);
			
			Element eltsub13 = doc.createElement("MemberUniqueId");
			eltsub13.setTextContent(member.UniqueId);
			elt1.appendChild(eltsub13);
			
			Element eltsub4 = doc.createElement("MemberDBID");
			eltsub4.setTextContent(String.valueOf(member.Model.Muid));
			elt1.appendChild(eltsub4);
			
			Element eltsub7 = doc.createElement("IsTimeRegulating");
			eltsub7.setTextContent(member.IsTimeRegulating ? "1" : "0");
			elt1.appendChild(eltsub7);
			Element eltsub8 = doc.createElement("IsTimeConstrained");
			eltsub8.setTextContent(member.IsTimeConstrained ? "1" : "0");
			elt1.appendChild(eltsub8);
			Element eltsub9 = doc.createElement("Lookahead");
			eltsub9.setTextContent(member.Step);
			elt1.appendChild(eltsub9);
			
			Element eltsub15 = doc.createElement("Display");
			Element eltsub16 = doc.createElement("InputSources");

			Element eltsub20 = doc.createElement("OutputSources");

			for (MemberObject memberObject : combineMember.getMemberObjectList()){
				if(memberObject.getConsumerName().equals(member.Name)&&memberObject.getConsumer().getGetFromId().size()>0){
					for(int a=0;a<memberObject.getConsumer().getGetFromId().size();a++){
						Element eltInputSource = doc.createElement("InputSource");
						eltInputSource.setAttribute("id",memberObject.getConsumer().getGetFromId().get(a));// 获取发布方的id
						eltsub16.appendChild(eltInputSource);						
					}
					
				}
				for(int k= 0; k< eltsub16.getChildNodes().getLength();k++){
					for(int j=0;j< eltsub16.getChildNodes().getLength();j++){
						if(eltsub16.getChildNodes().item(k).getAttributes().item(0).equals(eltsub16.getChildNodes().item(j).getAttributes().item(0))&&k!=j){
							eltsub16.removeChild(eltsub16.getChildNodes().item(j));
						}
					}
				}

//				for (int i = 0; i < 4; i++) {
//					// 如果该成员为订购者且关系类型为对象类:接收方
//					if (memberObject.getConsumerName().equals(member.Name)&& rects[i].contains(memberObject.logicalLine.EndPoint.getX()+2,memberObject.logicalLine.EndPoint.getY()+2)) {
//						Element eltInputSource = doc
//								.createElement("InputSource");
//						eltInputSource.setAttribute("id",
//								String.valueOf(memberObject.getProducerId()));// 获取发布方的id
//
//						if (memberObject.logicalLine.lineAttribute.isInput) {
//
//							Element eltAttributeName1 = doc
//									.createElement("Attribute");
//							eltAttributeName1.setTextContent("isInput");
//							eltInputSource.appendChild(eltAttributeName1);
//						}
//						if (memberObject.logicalLine.lineAttribute.isOutput) {
//							Element eltAttributeName2 = doc
//									.createElement("Attribute");
//							eltAttributeName2.setTextContent("isOutput");
//							eltInputSource.appendChild(eltAttributeName2);
//						}
//						if (memberObject.logicalLine.lineAttribute.isInit) {
//							Element eltAttributeName3 = doc
//									.createElement("Attribute");
//							eltAttributeName3.setTextContent("isInit");
//							eltInputSource.appendChild(eltAttributeName3);
//						}
//						if (memberObject.logicalLine.lineAttribute.isModel) {
//							Element eltAttributeName4 = doc
//									.createElement("Attribute");
//							eltAttributeName4.setTextContent("isModel");
//							eltInputSource.appendChild(eltAttributeName4);
//						}
//
//						eltsub16.appendChild(eltInputSource);
//					}
//				}
			}
			
			elt1.appendChild(eltsub16);
//			Element eltOutputSource = doc.createElement("OutputSource");
			
			for (MemberObject memberObject : combineMember.getMemberObjectList()){
				if(memberObject.getProducerName().equals(member.Name)&&memberObject.getProducer().getSendToId().size()>0){
					for(int a=0;a<memberObject.getProducer().getSendToId().size();a++){
						Element eltOutputSource = doc.createElement("OutputSource");
						eltOutputSource.setAttribute("id",memberObject.getProducer().getSendToId().get(a));// 获取订购方的id
						eltsub20.appendChild(eltOutputSource);
						
					}					
				}
			}

//				for (int i = 0; i < 4; i++) {
//					// 如果该成员为订购者且关系类型为对象类:发布方
//					if (memberObject.getProduceName().equals(member.Name)&& points[i].equals(memberObject.logicalLine.StartPoint)) {
//						
//						if (memberObject.logicalLine.lineAttribute.isInput) {
//							Element eltAttribute1 = doc
//									.createElement("Attribute");
//							eltAttribute1.setTextContent("isInput");
//							eltOutputSource.appendChild(eltAttribute1);
//						}
//						if (memberObject.logicalLine.lineAttribute.isOutput) {
//							Element eltAttribute2 = doc
//									.createElement("Attribute");
//							eltAttribute2.setTextContent("isOutput");
//							eltOutputSource.appendChild(eltAttribute2);
//						}
//						if (memberObject.logicalLine.lineAttribute.isInit) {
//							Element eltAttribute3 = doc
//									.createElement("Attribute");
//							eltAttribute3.setTextContent("isInit");
//							eltOutputSource.appendChild(eltAttribute3);
//						}
//						if (memberObject.logicalLine.lineAttribute.isModel) {
//							Element eltAttribute4 = doc
//									.createElement("Attribute");
//							eltAttribute4.setTextContent("isModel");
//							eltOutputSource.appendChild(eltAttribute4);
//						}
//						
//						for(int k = 0;  k< eltOutputSource.getChildNodes().getLength(); k++){
//							for(int j=0;j< eltOutputSource.getChildNodes().getLength()-1;j++){
//								if(eltOutputSource.getChildNodes().item(k).getTextContent().equals(eltOutputSource.getChildNodes().item(j).getTextContent())&&k!=j){
//									eltOutputSource.removeChild(eltOutputSource.getChildNodes().item(j));
//								}
//							}
//						}
//						
//						eltsub20.appendChild(eltOutputSource);
//					}
//
//				}
//
//			}
			elt1.appendChild(eltsub20);

			Element eltmodel=doc.createElement("Model");			
			eltmodel.setAttribute("Model_name", member.Model.Model_name);
			
			
			Element eltversion=doc.createElement("Model_version");
			eltversion.setTextContent(member.Model.Model_version);
			eltmodel.appendChild(eltversion);

			Element eltptime=doc.createElement("Publish_time");
			eltptime.setTextContent(String.valueOf(member.Model.Publish_time));
			eltmodel.appendChild(eltptime);

			Element eltmtime=doc.createElement("Modified_time");
			eltmtime.setTextContent(String.valueOf(member.Model.Modified_time));
			eltmodel.appendChild(eltmtime);

			Element eltmuid=doc.createElement("Muid");
			eltmuid.setTextContent(String.valueOf(member.Model.Muid));
			eltmodel.appendChild(eltmuid);

			Element eltlanguages=doc.createElement("Development_languages");
			eltlanguages.setTextContent(member.Model.Development_languages);
			eltmodel.appendChild(eltlanguages);

			Element elttools=doc.createElement("Development_tools");
			elttools.setTextContent(member.Model.Development_tools);
			eltmodel.appendChild(elttools);

			Element eltsrc=doc.createElement("Hava_src");
			eltsrc.setTextContent(String.valueOf(member.Model.Hava_src));
			eltmodel.appendChild(eltsrc);
			
			Element eltdoc=doc.createElement("Hava_doc");
			eltdoc.setTextContent(String.valueOf(member.Model.Hava_doc));
			eltmodel.appendChild(eltdoc);
			
			Element eltverified=doc.createElement("Is_verified");
			eltverified.setTextContent(String.valueOf(member.Model.Is_verified));
			eltmodel.appendChild(eltverified);
			
			Element eltsrcpath=doc.createElement("Src_path");
			eltsrcpath.setTextContent(member.Model.Src_path);
			eltmodel.appendChild(eltsrcpath);
			
			Element eltdocpath=doc.createElement("Doc_path");
			eltdocpath.setTextContent(member.Model.Doc_path);
			eltmodel.appendChild(eltdocpath);
			
			Element eltcategory=doc.createElement("Model_category");
			eltcategory.setTextContent(String.valueOf(member.Model.Model_category));
			eltmodel.appendChild(eltcategory);
			
			Element eltparainfolist=doc.createElement("Model_para_infoList");
			if (member.Model.Model_para_infoList.size() > 0) {
				for (Model_para_info para : member.Model.Model_para_infoList) {
					Element eltparainfo=doc.createElement("Model_para_info");
					
					Element eltparamuid=doc.createElement("Muid");
					eltparamuid.setTextContent(String.valueOf(para.Muid));
					eltparainfo.appendChild(eltparamuid);
					
					Element eltparatype=doc.createElement("Para_type");
					eltparatype.setTextContent(String.valueOf(para.Para_type));
					eltparainfo.appendChild(eltparatype);
					
					Element eltparadefa=doc.createElement("Para_defa_value");
					eltparadefa.setTextContent(para.Para_defa_value);
					eltparainfo.appendChild(eltparadefa);
					
					Element eltparalable=doc.createElement("Para_lable");
					eltparalable.setTextContent(para.Para_label);
					eltparainfo.appendChild(eltparalable);
					
					Element eltparainfo_=doc.createElement("Para_info_");
					
					Element eltpid=doc.createElement("Pid");
					eltpid.setTextContent(String.valueOf(para.Mpid));
					eltparainfo_.appendChild(eltpid);
					
					
					Element eltparamuid_=doc.createElement("Muid");
					eltparamuid_.setTextContent(String.valueOf(para.Muid));
					eltparainfo_.appendChild(eltparamuid_);
					
					Element eltparaphysics=doc.createElement("Para_physicsunit");
					eltparaphysics.setTextContent(String.valueOf(para.Para_physicsunit));
					eltparainfo_.appendChild(eltparaphysics);
					
					Element eltparabasictype=doc.createElement("Basic_type");
					eltparabasictype.setTextContent(String.valueOf(para.Basic_type));
					eltparainfo_.appendChild(eltparabasictype);
					
					Element eltparalength=doc.createElement("Para_length");
					eltparalength.setTextContent(String.valueOf(para.Para_length));
					eltparainfo_.appendChild(eltparalength);
					
					Element eltparadesc=doc.createElement("Para_desc");
					eltparadesc.setTextContent(String.valueOf(para.Para_desc));
					eltparainfo_.appendChild(eltparadesc);
					
					eltparainfo.appendChild(eltparainfo_);
					eltparainfolist.appendChild(eltparainfo);
										
				}
			}
			
			eltmodel.appendChild(eltparainfolist);
			elt1.appendChild(eltmodel); 
			
			nlModelInfo.item(0).appendChild(elt1);
		}
	
		
		try {							
				transformerXml(combineMember.Name, doc, SavePath, ".xml");
			} 
		catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(new JPanel(), "文件保存成功","提示", JOptionPane.YES_NO_CANCEL_OPTION);					
			
	}
	
	/**
	 * 读取组合成员生成的xml，将组合成员的信息绑定到对应的组很成员中
	 */
	public CombineMember GetCombineMemberXML(String XMLPath) {
		File file = new File(XMLPath);
		CombineMember combineMember = new CombineMember();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		List<String> inputMemberUnId=new ArrayList<String>();
		List<String> outputMemberUnId=new ArrayList<String>();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			// 获取根节点
			Element rootElement = doc.getDocumentElement();
			// 获取子节点
			NodeList rootChild = rootElement.getChildNodes();
			for (int i = 1; i < rootChild.getLength(); i++) {
			
				Node nCombineMemberInfo = rootChild.item(i);
				if (nCombineMemberInfo.getNodeName().equals("CombineMemberInfo")) {
					NodeList nlCombineMemberInfoChild = nCombineMemberInfo.getChildNodes();
					for (int j = 1; j < nlCombineMemberInfoChild.getLength(); j++) {
						Node nCombineMemberInfoChild = nlCombineMemberInfoChild.item(j);
						if (nCombineMemberInfoChild.getNodeName().equals("CombineMemberID")) {
							combineMember.Id = nCombineMemberInfoChild.getFirstChild().getNodeValue();
						}
						if (nCombineMemberInfoChild.getNodeName().equals("CombineMemberName")) {
							combineMember.Name = nCombineMemberInfoChild.getFirstChild().getNodeValue();
						}
						if (nCombineMemberInfoChild.getNodeName().equals("CombineMemberOutLine")) {
							if (!(nCombineMemberInfoChild instanceof Element)) {
								combineMember.outLine = nCombineMemberInfoChild.getFirstChild().getNodeValue();
							}
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Public_time")) {
							combineMember.Model.Publish_time = Date.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Modlfied_time")) {
							combineMember.Model.Modified_time = Date.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Muid")) {
							combineMember.Model.Muid =Integer.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Hava_src")) {
							combineMember.Model.Hava_src = Boolean.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Hava_doc")) {
							combineMember.Model.Hava_doc = Boolean.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Is_verified")) {
							combineMember.Model.Is_verified = Boolean.valueOf(nCombineMemberInfoChild.getFirstChild().getNodeValue());
						}
						if (nCombineMemberInfoChild.getNodeName().equals("Model_para_infoList")) {
							
							NodeList nlcmModelParaInfoList=nCombineMemberInfoChild.getChildNodes();
							for(int a=0;a<nlcmModelParaInfoList.getLength();a++){
								
								Model_para_info cmpara=new Model_para_info();
								Node nModelParaInfoList=nlcmModelParaInfoList.item(a);
								if(nModelParaInfoList.getNodeName().equals("Model_para_info")){
									NodeList nlModelParaInfo=nModelParaInfoList.getChildNodes();
									for(int b=0;b<nlModelParaInfo.getLength();b++){
										Node nModelParaInfo=nlModelParaInfo.item(b);
										if(nModelParaInfo.getNodeName().equals("Muid")){
											
											cmpara.Muid=Integer.valueOf(nModelParaInfo.getFirstChild().getNodeValue());
										}
										
										if(nModelParaInfo.getNodeName().equals("Para_type")){
											cmpara.Para_type=ParaType.valueOf(nModelParaInfo.getFirstChild().getNodeValue());											
										}
										
										if(nModelParaInfo.getNodeName().equals("UniqueId")){
											if(String.valueOf(cmpara.Para_type).equals("input_para")){
												inputMemberUnId.add(nModelParaInfo.getFirstChild().getNodeValue());
											}
											
											else if(String.valueOf(cmpara.Para_type).equals("output_para")){
												outputMemberUnId.add(nModelParaInfo.getFirstChild().getNodeValue());
											}
											
										}
										if(nModelParaInfo.getNodeName().equals("Para_defa_value")){
											if(!(nModelParaInfo instanceof Element)){
											cmpara.Para_defa_value=nModelParaInfo.getFirstChild().getNodeValue();
											}
										}
										if(nModelParaInfo.getNodeName().equals("Para_lable")){
											cmpara.Para_label=nModelParaInfo.getFirstChild().getNodeValue();
										}
										if(nModelParaInfo.getNodeName().equals("Para_info_")){
											NodeList nlparainfo_=nModelParaInfo.getChildNodes();
											for(int c=0;c<nlparainfo_.getLength();c++){
												Node nparainfo_=nlparainfo_.item(c);
												if(nparainfo_.getNodeName().equals("Pid")){
													cmpara.Mpid=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
												}
												if(nparainfo_.getNodeName().equals("Muid")){
													cmpara.Muid=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
												}
												if(nparainfo_.getNodeName().equals("Para_physicsunit")){
													cmpara.Para_physicsunit=nparainfo_.getFirstChild().getNodeValue();
												}
												if(nparainfo_.getNodeName().equals("Basic_type")){
													cmpara.Basic_type=nparainfo_.getFirstChild().getNodeValue();
												}
												if(nparainfo_.getNodeName().equals("Para_length")){
													cmpara.Para_length=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
												}
												if(nparainfo_.getNodeName().equals("Para_desc")){
													cmpara.Para_desc=nparainfo_.getFirstChild().getNodeValue();
												}
											}
									
										}
						}
									combineMember.Model.Model_para_infoList.add(cmpara);
									
								}
							}
							
							for(int in=0;in<inputMemberUnId.size();in++){
								for(int ip=inputMemberUnId.size()-1;ip>=0;ip--){
									if((inputMemberUnId.get(in).equals(inputMemberUnId.get(ip))&&in!=ip)){
										inputMemberUnId.remove(ip);
									}
								}
							}
							for(int out=0;out<outputMemberUnId.size();out++){
								for(int op=outputMemberUnId.size()-1;op>=0;op--){
									if((outputMemberUnId.get(out).equals(outputMemberUnId.get(op))&&out!=op)){
										outputMemberUnId.remove(op);
									}
								}
							}
						}
					}
				}
				if (nCombineMemberInfo.getNodeName().equals("ModelInfo")) {
					NodeList nlModelInfo = nCombineMemberInfo.getChildNodes();
					for (int k = 1; k < nlModelInfo.getLength(); k++) {

						Node nModelInfo = nlModelInfo.item(k);
						if (nModelInfo.getNodeName().equals("ModelExeConfig")) {

							Member member = new Member();
							NodeList nlModelExeConfig = nModelInfo.getChildNodes();
							for (int m = 0; m < nlModelExeConfig.getLength(); m++) {
								Element nModelExeConfig =(Element) nlModelExeConfig.item(m);
								if (nModelExeConfig.getNodeName().equals("FederateName")) {
									member.Name = nModelExeConfig.getFirstChild().getNodeValue().toString();
								}
								else if (nModelExeConfig.getNodeName().equals("MemberID")) {
									member.Id = nModelExeConfig.getFirstChild().getNodeValue();
								}
								else if (nModelExeConfig.getNodeName().equals("MemberUniqueId")) {
									member.UniqueId = nModelExeConfig.getFirstChild().getNodeValue();
								}
								else if (nModelExeConfig.getNodeName().equals("MemberDBID")) {
									member.Model.Muid = Integer.valueOf(nModelExeConfig.getFirstChild().getNodeValue());
								}
								else if (nModelExeConfig.getNodeName().equals("IsTimeRegulating")) {
									if (nModelExeConfig.getFirstChild().getNodeValue().equals("1")) {
										member.IsTimeRegulating = true;
									} else {
										member.IsTimeRegulating = false;
									}
								} 
								else if (nModelExeConfig.getNodeName().equals("IsTimeConstrained")) {
									if (nModelExeConfig.getFirstChild().getNodeValue().equals("1")) {
										member.IsTimeConstrained = true;
									} else {
										member.IsTimeConstrained = false;
									}
								} 
								else if (nModelExeConfig.getNodeName().equals("Lookahead")) {
									member.Step = nModelExeConfig.getFirstChild().getNodeValue();
								} 
								else if (nModelExeConfig.getNodeName().equals("InputSources")) {
									NodeList nlInputSources = nModelExeConfig.getChildNodes();

									for (int n = 0; n < nlInputSources.getLength(); n++) {
										Element nInputSources = (Element)nlInputSources.item(n);

										if (nInputSources.getNodeName().equals("InputSource")) {
											member.getGetFromId().add(nInputSources.getAttribute("id"));
											
											NodeList nlInputSource = nInputSources.getChildNodes();

											for (int q = 0; q < nlInputSource.getLength(); q++) {
												Node nInputSource = nlInputSource.item(q);

												if (nInputSource.getNodeName().equals("Attribute")) {
													// combineMember.memberList.get(count).attribute=Attribute.valueOf(nInputSource.getFirstChild().getNodeValue());
												}
											}
										}
										

									}
								}
								else if (nModelExeConfig.getNodeName().equals("OutputSources")) {

									NodeList nlOutputSources = nModelExeConfig.getChildNodes();

									for (int l = 0; l < nlOutputSources.getLength(); l++) {
										Element nOutputSources =(Element) nlOutputSources.item(l);

										if (nOutputSources.getNodeName().equals("OutputSource")) {

											member.getSendToId().add(nOutputSources.getAttribute("id"));
											
											
											NodeList nlOutputSource = nOutputSources.getChildNodes();

											for (int q = 0; q < nlOutputSource.getLength(); q++) {

												System.out.println("20");
												Node nOutputSource = nlOutputSource.item(q);

												if (nOutputSource.getNodeName().equals("Attribute")) {

													System.out.println("21");
													// combineMember.memberList.get(count).attribute=Attribute.valueOf(nInputSource.getFirstChild().getNodeValue());
												}
											}
										}

									}
								}
								else if(nModelExeConfig.getNodeName().equals("Model")){
									member.Model.Model_name=nModelExeConfig.getAttribute("Model_name");
									NodeList nlModel=nModelExeConfig.getChildNodes();
									for(int r=0;r<nlModel.getLength();r++){
										Node nModel=nlModel.item(r);
										if(nModel.getNodeName().equals("Model_version")){
											member.Model.Model_version=nModel.getFirstChild().getNodeValue();
										}
										if(nModel.getNodeName().equals("Publish_time")){
											member.Model.Publish_time=Date.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Modified_time")){
											member.Model.Modified_time=Date.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Muid")){
											member.Model.Muid=Integer.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Development_languages")){
											member.Model.Development_languages=nModel.getFirstChild().getNodeValue();
										}
										if(nModel.getNodeName().equals("Development_tools")){
											member.Model.Development_tools=nModel.getFirstChild().getNodeValue();
										}
										if(nModel.getNodeName().equals("Hava_src")){
											member.Model.Hava_src=Boolean.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Hava_doc")){
											member.Model.Hava_doc=Boolean.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Is_verified")){
											member.Model.Is_verified=Boolean.valueOf(nModel.getFirstChild().getNodeValue());											
										}
										if(nModel.getNodeName().equals("Src_path")){
											if(!(nModel instanceof Element)){
											member.Model.Src_path=nModel.getFirstChild().getNodeValue();
											}
										}
										if(nModel.getNodeName().equals("Doc_path")){
											if(!(nModel instanceof Element)){
											member.Model.Doc_path=nModel.getFirstChild().getNodeValue();
											}
										}
										if(nModel.getNodeName().equals("Model_category")){
											member.Model.Model_category=Integer.valueOf(nModel.getFirstChild().getNodeValue());
										}
										if(nModel.getNodeName().equals("Model_para_infoList")){
											
											NodeList nlModelParaInfoList=nModel.getChildNodes();
											for(int a=0;a<nlModelParaInfoList.getLength();a++){
												Model_para_info para=new Model_para_info();
												Node nModelParaInfoList=nlModelParaInfoList.item(a);
												if(nModelParaInfoList.getNodeName().equals("Model_para_info")){
													NodeList nlModelParaInfo=nModelParaInfoList.getChildNodes();
													for(int b=0;b<nlModelParaInfo.getLength();b++){
														Node nModelParaInfo=nlModelParaInfo.item(b);
														if(nModelParaInfo.getNodeName().equals("Muid")){
															para.Muid=Integer.valueOf(nModelParaInfo.getFirstChild().getNodeValue());
														}
														
														if(nModelParaInfo.getNodeName().equals("Para_type")){
															para.Para_type=ParaType.valueOf(nModelParaInfo.getFirstChild().getNodeValue());
														}
														if(nModelParaInfo.getNodeName().equals("Para_defa_value")){
															if(!(nModelParaInfo instanceof Element)){
															para.Para_defa_value=nModelParaInfo.getFirstChild().getNodeValue();
															}
														}
														if(nModelParaInfo.getNodeName().equals("Para_lable")){
															para.Para_label=nModelParaInfo.getFirstChild().getNodeValue();
														}
														if(nModelParaInfo.getNodeName().equals("Para_info_")){
															NodeList nlparainfo_=nModelParaInfo.getChildNodes();
															for(int c=0;c<nlparainfo_.getLength();c++){
																Node nparainfo_=nlparainfo_.item(c);
																if(nparainfo_.getNodeName().equals("Pid")){
																	para.Mpid=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
																}
																if(nparainfo_.getNodeName().equals("Muid")){
																	para.Muid=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
																}
																if(nparainfo_.getNodeName().equals("Para_physicsunit")){
																	para.Para_physicsunit=nparainfo_.getFirstChild().getNodeValue();
																}
																if(nparainfo_.getNodeName().equals("Basic_type")){
																	para.Basic_type=nparainfo_.getFirstChild().getNodeValue();
																}
																if(nparainfo_.getNodeName().equals("Para_length")){
																	para.Para_length=Integer.valueOf(nparainfo_.getFirstChild().getNodeValue());
																}
																if(nparainfo_.getNodeName().equals("Para_desc")){
																	para.Para_desc=nparainfo_.getFirstChild().getNodeValue();
																}
															}
													
														}
													}
												}												
												member.Model.Model_para_infoList.add(para);
											}
																																	
										}
										
									}
									
								}
							
							}
							combineMember.memberList.add(member);
						}
					}
					for(int z=0;z<combineMember.getMemberList().size();z++){
						for(int y=0;y<inputMemberUnId.size();y++){
							if(combineMember.getMemberList().get(z).UniqueId.equals(inputMemberUnId.get(y))){
								combineMember.getInputRelationList().add(combineMember.getMemberList().get(z));
							}
						}
						for(int w=0;w<outputMemberUnId.size();w++){
							if(combineMember.getMemberList().get(z).UniqueId.equals(outputMemberUnId.get(w))){
								combineMember.getOutputRelationList().add(combineMember.getMemberList().get(z));
							}
						}
					}
					
					//遍历组合成员中的所有成员
					for(int a=0;a<combineMember.getMemberList().size();a++){
						//第二次遍历组合成员中的所有成员
						for(int b=0;b<combineMember.getMemberList().size();b++){
							//判断第一次遍历的成员中是否为公布者
							if(combineMember.getMemberList().get(a).getSendToId().size()>0){
								//遍历该公布者公布的所有成员
								for(int c=0;c<combineMember.getMemberList().get(a).getSendToId().size();c++){
									String unId=null;
									String[] tmp=combineMember.getMemberList().get(a).getSendToId().get(c).split("-"); 
									for(int j=0;j<tmp.length;j++){
										unId=tmp[1];
									}
									MemberObject memberObject=new MemberObject();
									//订购公布者的成员是否匹配第二次循环的成员
									if(unId.equals(combineMember.getMemberList().get(b).UniqueId)){
										//获取关系的公布方信息
										memberObject.producer=combineMember.getMemberList().get(a);
										memberObject.producerName=combineMember.getMemberList().get(a).Name;
										memberObject.producerId=Integer.valueOf(combineMember.getMemberList().get(a).Id);
									    //获取订购方的信息
										memberObject.consumer=combineMember.getMemberList().get(b);
										memberObject.consumerName=combineMember.getMemberList().get(b).Name;
										memberObject.consumerId=Integer.valueOf(combineMember.getMemberList().get(b).Id);
										
										combineMember.getMemberObjectList().add(memberObject);
								}
							}
							
						}
							
						}
					}
				}			
			}
		} catch (Exception e) {
		}
		return combineMember;
	}
	/**
	 * 将xml对象封装到StreamResult并输出文件
	 * 
	 * @param 方案
	 * @param 文件文档
	 * @param 保存路径
	 * @throws TransformerConfigurationException
	 */
	public void transformerXml(String name, Document doc, String savePath,
			String fileName) throws TransformerConfigurationException {
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		DOMSource DomS = new DOMSource(doc);// 将被变换的Document对象封装到一个DOMSource对象中
		StreamResult SmR = null;
		if (fileName.equals("Som.xml")) {
			SmR = new StreamResult(
					new File(savePath + name + "Som.xml"));
		}
		if (fileName.equals("Fom.xml")) {
			SmR = new StreamResult(
					new File(savePath + name + "Fom.xml"));
		}
		if (fileName.equals(".xml")) {
			SmR = new StreamResult(
					new File(savePath + name + ".xml"));
		}
		
		try {
			tf.transform(DomS, SmR);
		} catch (TransformerException e) {
			e.printStackTrace();
		} // 文件转换器
		
	}

	/**
	 * 保存预案信息 序列化当前Formula对象到指定路径
	 * 
	 * @param 需要保存的方案
	 * @param 指定的对话框操作方法
	 * @param 文件保存路径
	 * @throws IOException
	 */
	public void DoSaveAction(Formula formula, String path) throws IOException {
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(formula);

		oos.close();
		fos.close();
	}

	/**
	 * 文件打开
	 */
	public Formula DoOpenAction(String path) throws IOException,
			ClassNotFoundException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Formula formula = (Formula) ois.readObject();
		ois.close();
		fis.close();
		return formula;
	}

	/**
	 *  递归算法解开组合成员
	 * @param combineMember
	 */
	public void OpenCombineMember(CombineMember combineMember,Formula formula){
		 int combineMemberNum=0;
		 //如果组合成员中还包含组合成员就一直递归解开
		 for(Member member:combineMember.memberList){
			 if(member.IsCombineMember){
				 combineMemberNum++;
				 OpenCombineMember((CombineMember)member,formula);
			 }
		 }
		 //组很成员中没有组合成员的时候
		 if(combineMemberNum==0){
			 //遍历方案中所有的关系
			 for(MemberObject memberObject:formula.getMemberObjectList()){
				 //如果关系的订购者是组合成员
				 if(String.valueOf(memberObject.consumerId).equals(combineMember.Id)){
					 //遍历方案中的所有成员，找到此关系的发布者
					 for(Member memberPro:formula.getMemberList()){
						 //确定成员中的发布者
						if(String.valueOf(memberObject.producerId).equals(memberPro.Id)){
							//遍历组合成员中的所有成员
							for(Member memberCon:combineMember.memberList){
								memberObject.consumer=memberCon;
								memberObject.consumerName=memberCon.Name;
								memberObject.consumerId=Integer.valueOf(memberCon.Id);
							}
						}
					 }
				 }
			 
				 else if(String.valueOf(memberObject.producerId).equals(combineMember.Id)){
					 for(Member memberCon :formula.getMemberList() ){
						 if(String.valueOf(memberObject.consumerId).equals(memberCon.Id)){
							 for(Member memberPro:combineMember.memberList){
								 memberObject.producer=memberPro;
								 memberObject.producerName=memberPro.Name;
								 memberObject.producerId=Integer.valueOf(memberPro.Id);
							 }
						 }						 
					 }
				 }				 
			 }
			 for(Member member :combineMember.getMemberList()){
				 try {
					 formula.getMemberList().add(MemberManage.Clone(member));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 for(MemberObject object:combineMember.getMemberObjectList()){
				 formula.getMemberObjectList().add(object);
			 }
		 }
	 }
	
	/**
	 *将含有组合成员的扩展方案转换成只有普通成员的标准方案 	 
	 */
	private  void TurnToStandardFormula(Formula formula)
        {
            for (int i =formula.getMemberList().size() - 1; i > -1; i--)
            {
                if (formula.getMemberList().get(i).IsCombineMember)
                {
                    OpenCombineMember((CombineMember)formula.getMemberList().get(i),formula);
                    formula.getMemberList().remove(i);
                }
            }
        }
	 
	
	public void WriteFED(String fedName,String SavePath) throws IOException{
		  File file = new File(SavePath);
		  if(file.exists()){
				int result = JOptionPane.showOptionDialog(new JPanel(),
						"存在同名文件，确认覆盖？", "提示", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if(result==0){
					  BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					  BufferWrite(bw,"src\\psm\\Image\\FED-1.txt");
					    for(int i=0;i<objParameters.size();i++){
					    	String tem="\t(class "+objParameters.get(i).getName()+"\r\n\t(attribute "+objParameters.get(i).getValue()+" reliable receive))";
					    	bw.write(tem+"\r\n");
					    	
				    	  }
					  BufferWrite(bw,"src\\psm\\Image\\FED-2.txt");  
					  bw.close();
				}
				JOptionPane.showMessageDialog(new JPanel(), "FED文件保存成功",
						"提示", JOptionPane.YES_NO_CANCEL_OPTION);
		  }else{
			  BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			  BufferWrite(bw,"src\\psm\\Image\\FED-1.txt");
			    for(int i=0;i<objParameters.size();i++){
			    	String tem="\t(class "+objParameters.get(i).getName()+"\r\n\t(attribute "+objParameters.get(i).getValue()+" reliable receive))";
			    	bw.write(tem+"\r\n");
		    	  }
			  BufferWrite(bw,"src\\psm\\Image\\FED-2.txt");  
			  bw.close();
			  JOptionPane.showMessageDialog(new JPanel(), "FED文件保存成功",
						"提示", JOptionPane.YES_NO_CANCEL_OPTION);
		  }


		  
	}

	public  void BufferWrite(BufferedWriter bw,String path) throws IOException{
		File read = new File(path);
		  try {
			BufferedReader br = new BufferedReader(new FileReader(read));
			String temp = null;
			temp = br.readLine();
			while(temp != null){
			//写文件
			bw.write(temp + "\r\n"); 				           
			temp = br.readLine();
			
	     }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void UploadScheme(String FileName) {
		// TODO Auto-generated method stub
		File file = new File("src\\psm\\FomOrScheme\\"+FileName);
		  if(!file.exists()){
				 JOptionPane.showMessageDialog(new JPanel(),
						 "文件不存在!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
		  }else{			  
			  FTPManage.uploadFileByApacheByBinary(FileName);
			  JOptionPane.showMessageDialog(new JPanel(), FileName+"上传成功",
						"提示", JOptionPane.YES_NO_CANCEL_OPTION);
		  }
		  
	}


}

class objectClass{
	 public String name=null;
	 public String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 


