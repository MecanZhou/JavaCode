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
	 * ��ȡ��Ӧ�ļ�������ϳ�Ա��Ϣ
	 * 
	 * @param combineMemberList ��¼��Щ��ϳ�Ա������
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
//			JOptionPane.showMessageDialog(new JPanel(), "�ڴ���ϳ�Աxml�ļ�ʱ�������⣡",
//					"��ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
//		}
	}

	/**
	 * ���չ̶��ĸ�ʽ������ϳ�Ա����Ϣ
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
	 * ��ϳ�Ա�Ŀ�¡����
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
		// ����������� �����л�
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newCombineMember = (CombineMember) ois.readObject();
		oos.close();
		ois.close();

		return newCombineMember;
	}

}
