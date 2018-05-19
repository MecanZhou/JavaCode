package psm.Models.DataModel;
import java.awt.Image;
import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import psm.Models.DB.ModelService;
import psm.Models.DataModel.ModelClass.Model_cat;
import psm.Models.DataModel.ModelClass.Model_desc_para;
import psm.Models.DataModel.ModelClass.Model_interaction_info;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Models.DataModel.ModelClass.ParaType;

public class Model implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2439830455424558214L;
	/*��Ӧmodeldb��model_program_info*/
    public String Src_path;//Դ�ļ���url
    public String Doc_path;//�ĵ���url
    public int Muid;//ģ����modeldb���id
    public String Model_name;//ģ����
    public String Model_version;//ģ�Ͱ汾
    public void setModel_version(String model_version){
    	Model_version=model_version;
    }
    
    public String getModel_version(){
    	return Model_version;
    }
    public Date Publish_time;//ģ�ͷ���ʱ��
    public Date Modified_time;//ģ������޸�ʱ��
    public String Cat_id;//ģ������
    public String Development_languages;//ģ�Ϳ���������
    public String Development_tools;//������ģ�͵Ĺ��ߣ������ƣ�
    //public String Extens_from;//��ģ�ͼ̳����ĸ�ģ�ͣ�������Ǽ̳�������ģ������������
    public String Model_intro;//����ģ�͵������Ϣ
    public String Model_sponsor;//����ģ�͵���Ա
    public Boolean Hava_src;//�Ƿ���Դ�ļ�
    public Boolean Hava_doc;//�Ƿ����ĵ�
    public Boolean Is_verified;//�Ƿ��������֤
    public String Target_access;//Ŀ��Ȩ��
    public int Model_category;//ģ�ͳ����еȼ�֮�֣����о���ķ���.��Ӧ��model_verfication
    public int Model_vid;
    public String Model_target;//ģ��dll����
    public Image Model_picture;//ģ��ͼƬ

    public List<Model_cat> Model_catList;//ģ�����ͷ���
    public List<Model_desc_para> Model_desc_paraList;
    public List<Model_para_info> Model_para_infoList;//ģ�Ͳ�����Ϣ
    public List<Model_interaction_info> Model_interaction_infoList;//ģ�ͽ�������Ϣ     

    public Model()
    {
        Model_para_infoList = new ArrayList<Model_para_info>();
        Model_interaction_infoList = new ArrayList<Model_interaction_info>();
        Model_catList = new ArrayList<Model_cat>();
        Model_desc_paraList = new ArrayList<Model_desc_para>();
    }
    
    public static ResultSet GetModelVerfiById(Model model) throws SQLException{
    	ResultSet rs=ModelService.GetModelVerfi(model.Muid);
    	return rs;
    }
}


