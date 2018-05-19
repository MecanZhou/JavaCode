package psm.Models.BusinessModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import psm.Models.DB.ModelService;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Models.DataModel.ModelClass.ParaType;

public class ModelManage implements InterfaceModelManage 
{
    //[start] Fields
	static List<Model> ModelList=new ArrayList<Model>();
	//[end]
	
	//[start] Property
	public static void setModelList(List<Model> ModelList_)
	{
		ModelList=ModelList_;
	}
	public static List<Model> getModelList()
	{
		return ModelList;
	}

	//[end]
	 /**
	  * 获取模型库中所有模型的基本信息
	  * @return
	  * @throws SQLException
	  */
	public List<Model> GetModelsBasicInfo() throws SQLException {
		List<Model> modelList = new ArrayList<Model>();
		ResultSet rs = ModelService.GetModels(); // 获取model_info中所有数据的缓存
		if (rs != null) {
			while (rs.next()) // 遍历model_info的所有行
			{     
				Model model = new Model();
				model.Muid=rs.getInt("MUID");
				model.Model_name = rs.getString("MODEL_NAME");
				model.Development_languages = rs.getString("Development_languages");
				model.Development_tools = rs.getString("Development_tools");
				model.Hava_doc = rs.getBoolean("Hava_doc");
				model.Hava_src = rs.getBoolean("Hava_src");
				model.Is_verified = rs.getBoolean("Is_verified");
				model.Cat_id = rs.getString("Cat_id");
				model.Model_version = rs.getString("Model_version");
				if (rs.getDate("Modified_time") != null) {
					model.Modified_time = rs.getDate("Modified_time");
				}
				model.Muid = rs.getInt("Muid");
				model.Publish_time = rs.getDate("Publish_time");
				model.Src_path = rs.getString("Src_path");
				model.Model_target=rs.getString("Target_path");
				modelList.add(model);
			}
		}
		return modelList;
	}
     
	 
	public List<Model> GetModelsAllInfo() throws SQLException {
		List<Model> modelList = new ArrayList<Model>();
		modelList = GetModelsBasicInfo();
		for (Model model : modelList) {
			// 查询model_cat
			ResultSet dt_cat = Model.GetModelVerfiById(model);
			
		    int dt_catcount = 0;// 获取rs_para的行数
			while (dt_cat.next()) {
				dt_catcount += 1;
			}

			if (dt_catcount > 0) {
				dt_cat.beforeFirst();
				while (dt_cat.next()) {
					model.Model_category = dt_cat.getInt("model_category");
				}
			}		

			List<String> tableNames = new ArrayList<String>();
			tableNames.add("model_para_info");
			
			ResultSet rs_para = ModelService.GetModelInfo(model.Muid, tableNames);
						

			int rs_paracount = 1;// 获取rs_paracount的行数

			while (rs_para.next()) {
				rs_paracount += 1;
			}
			if (rs_paracount > 0) {
				rs_para.beforeFirst();
				while (rs_para.next()) {
					Model_para_info model_para_info = new Model_para_info();
					model.Model_para_infoList.add(model_para_info);

					model_para_info.Muid = rs_para.getInt("Muid");
					model_para_info.Mpid = rs_para.getInt("Mpid");
					model_para_info.Para_label = rs_para.getString("Para_label");
					model_para_info.Para_type = ParaType.valueOf(rs_para.getString("Para_type"));
					model_para_info.Para_defa_value = rs_para.getString("Para_defa_value");
					model_para_info.Basic_type = rs_para.getString("Basic_type");
					model_para_info.Para_desc = rs_para.getString("Para_desc");
					model_para_info.Para_physicsunit = rs_para.getString("Para_physicsunit");
					model_para_info.Para_length = rs_para.getInt("Para_length");
				}
			}
		}
		
		return modelList;
	}

	/**
	 *  提供一个克隆模型的方法
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
     public static Model Clone(Model model) throws IOException, ClassNotFoundException
     {
		Model modelNew = new Model();
		// 将对象写到流里 序列化
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(model);
		// 从流里读出来 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		modelNew = (Model) ois.readObject();
		oos.close();
		ois.close();
		return modelNew;

     }


     }
