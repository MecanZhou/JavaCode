package psm.Models.BusinessModel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.PictureOfMember;

public class CombineMemberManage {
	
	static InterfaceFileManage faceFileManage;
	
	private static ArrayList<CombineMember> combineMemberList = new ArrayList<CombineMember>();

	public static ArrayList<CombineMember> getCombineMemberList() {
		return combineMemberList;
	}

	public static void setCombineMemberList(
			ArrayList<CombineMember> combineMemberList) {
		CombineMemberManage.combineMemberList = combineMemberList;
	}

	/**
	 * 获取对应文件夹中组合成员信息
	 * 
	 * @param combineMemberList 记录这些组合成员的链表
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void GetCombineMemberInfo(
			ArrayList<CombineMember> combineMemberList) throws IOException,
			ClassNotFoundException {
		//try {
			if (combineMemberList != null) {
				combineMemberList.clear();
			}
			String[] ss = new File("src\\psm\\CombineMembers").list();
			for (int i = 0; i < ss.length; i++) {
				CombineMember newCombineMember = new CombineMember();

				faceFileManage=new FileManage();
				newCombineMember = faceFileManage.GetCombineMemberXML("src\\psm\\CombineMembers" + "\\"+ ss[i]);
				combineMemberList.add(newCombineMember);
			}
			System.out.println("~~~~~~~~~~~~~~~~~~");
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(new JPanel(), "在打开组合成员xml文件时出现问题！",
//					"提示", JOptionPane.YES_NO_CANCEL_OPTION);
//		}
	}

	/**
	 * 按照固定的格式保存组合成员的信息
	 * 
	 * @param combineMember
	 * @throws IOException
	 */
	public static void SaveCombineMember(CombineMember combineMember)
			throws IOException {
		if (combineMember == null) {
			return;
		} else {
			faceFileManage=new FileManage();
			faceFileManage.CreateCombineMembersXml(combineMember);
		}
	}

	/**
	 * 组合成员的克隆复用
	 * 
	 * @param combineMember
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static CombineMember Clone(CombineMember combineMember)
			throws IOException, ClassNotFoundException {
		CombineMember newCombineMember = new CombineMember();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(combineMember);
		// 从流里读出来 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newCombineMember = (CombineMember) ois.readObject();
		oos.close();
		ois.close();

		return newCombineMember;
	}

}
