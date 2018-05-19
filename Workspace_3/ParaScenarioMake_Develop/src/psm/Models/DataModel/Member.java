package psm.Models.DataModel;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Port;
import javax.swing.JCheckBox;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class Member implements Serializable 
{
	//[start] Fields
	public String memberCode;//��Ա����
	//[end]
	
    //[start] Properties
    public String Id;
    public String  UniqueId; //��Ա��Ψһ��ʾID
    public String Name;//��Ա��
    public String DefaultTime;//Ĭ��ʱ��
    public String Step;//����
    public String StepTime;//����ʱ��
    public String ArithmeticPath;//�㷨·��
    public boolean IsCombineMember;//�ǳ�Ա���
    public boolean IsTimeRegulating;//�Ƿ�ʱ�����
    public boolean IsTimeConstrained;//�Ƿ�ʱ������
    public String ConfigPath;//�����ļ����·��
    public int Count;//���������
    public PictureOfMember pictureOfMember;//��ԱͼƬ
    public Model Model;//����ģ��
    public boolean IsDeploied;//�Ƿ��Ѿ�����
    public List<Port> PortList;//�˿�����
    public Attribute attribute;//��Ա����
    public boolean IsPublish;//�ڳ�Ա������Ƿ񱻵���
    public String memberTimer;
    public Image picture;//��ԱͼƬ
   
    
    private List<String>sendToId=new ArrayList<String>();//�����������������ĸ�����
    private List<String> getFromId=new ArrayList<String>();//���������Ǹ�������������Ϣ
    
	 public List<String> getGetFromId() {
			return getFromId;
		}
	
	 public void setGetFromId(List<String> getFromId) {
			this.getFromId = getFromId;
		}
	

	public List<String> getSendToId() {
		return sendToId;
	}

	public void setSendToId(List<String> sendToId) {
		this.sendToId = sendToId;
	}

	public void setMemberCode(String memberCode_)
    {
    	memberCode=memberCode_;
    }
    public String getMemberCode()
    {
    	return memberCode;
    }
	//[end]
    
    //[start] Constructors
    public Member()
    { 	
        IsCombineMember = false;
        this.Step = "1";
        this.IsTimeConstrained = true;
        this.IsTimeRegulating = true;
        this.IsDeploied = false;
        this.Model = new Model();
        this.memberTimer="1000";
       
        attribute=new Attribute();
        pictureOfMember = new PictureOfMember();
        PortList = new ArrayList<Port>();
    }
    
    public Member(Model model)
    {
        IsCombineMember = false;
        this.Step = "1";
        this.IsTimeConstrained = true;
        this.IsTimeRegulating = true;
        this.IsDeploied = false;
        this.Model = model;
        attribute=new Attribute();
        pictureOfMember = new PictureOfMember();
    }
    //[end]
		   
}
