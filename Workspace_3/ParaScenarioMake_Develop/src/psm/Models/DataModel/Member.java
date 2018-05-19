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
	public String memberCode;//成员代码
	//[end]
	
    //[start] Properties
    public String Id;
    public String  UniqueId; //成员的唯一标示ID
    public String Name;//成员名
    public String DefaultTime;//默认时间
    public String Step;//步长
    public String StepTime;//步长时间
    public String ArithmeticPath;//算法路径
    public boolean IsCombineMember;//是成员组合
    public boolean IsTimeRegulating;//是否时间调节
    public boolean IsTimeConstrained;//是否时间受限
    public String ConfigPath;//配置文件存放路径
    public int Count;//对象计算器
    public PictureOfMember pictureOfMember;//成员图片
    public Model Model;//引用模型
    public boolean IsDeploied;//是否已经配置
    public List<Port> PortList;//端口链表
    public Attribute attribute;//成员属性
    public boolean IsPublish;//在成员组合中是否被调用
    public String memberTimer;
    public Image picture;//成员图片
   
    
    private List<String>sendToId=new ArrayList<String>();//公布方将交互发给哪个订购
    private List<String> getFromId=new ArrayList<String>();//订购方从那个公布方接受信息
    
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
