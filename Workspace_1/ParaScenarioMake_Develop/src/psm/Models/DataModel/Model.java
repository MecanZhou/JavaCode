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
	/*对应modeldb的model_program_info*/
    public String Src_path;//源文件的url
    public String Doc_path;//文档的url
    public int Muid;//模型在modeldb里的id
    public String Model_name;//模型名
    public String Model_version;//模型版本
    public void setModel_version(String model_version){
    	Model_version=model_version;
    }
    
    public String getModel_version(){
    	return Model_version;
    }
    public Date Publish_time;//模型发布时间
    public Date Modified_time;//模型最后修改时间
    public String Cat_id;//模型类型
    public String Development_languages;//模型开发的语言
    public String Development_tools;//开发此模型的工具（的名称）
    //public String Extens_from;//此模型继承于哪个模型，如果不是继承于其它模型则是自连接
    public String Model_intro;//介绍模型的相关信息
    public String Model_sponsor;//建立模型的人员
    public Boolean Hava_src;//是否有源文件
    public Boolean Hava_doc;//是否有文档
    public Boolean Is_verified;//是否进行了验证
    public String Target_access;//目标权限
    public int Model_category;//模型除了有等级之分，还有具体的分类.对应表model_verfication
    public int Model_vid;
    public String Model_target;//模型dll名称
    public Image Model_picture;//模型图片

    public List<Model_cat> Model_catList;//模型类型分类
    public List<Model_desc_para> Model_desc_paraList;
    public List<Model_para_info> Model_para_infoList;//模型参数信息
    public List<Model_interaction_info> Model_interaction_infoList;//模型交互类信息     

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


