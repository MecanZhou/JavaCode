package psm.Models.BusinessModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import psm.Models.DB.DBHelper;
import psm.Models.DB.ModelService;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Models.DataModel.ModelClass.ParaType;

public class FrmDownModelManage {
	
	public List<Model> getModelName() throws SQLException{
		List<Model> modelNameList = new ArrayList<Model>();
		ResultSet rs = ModelService.GetModels(); // 获取model_info中所有数据的缓存
		if (rs != null) {
			while (rs.next()) // 遍历model_info的所有行
			{
				Model model = new Model();
				model.Muid=rs.getInt("MUID");
				model.Model_name = rs.getString("MODEL_NAME");
	            model.Model_version =rs.getString("MODEL_VERSION");
	            model.Publish_time = rs.getDate("Publish_time");
	            if (rs.getDate("Modified_time") != null) {
					model.Modified_time = rs.getDate("Modified_time");
				}
	            model.Development_languages = rs.getString("DEVELOPMENT_LANGUAGES");
	            model.Development_tools =rs.getString("DEVELOPMENT_TOOLS");
	            model.Hava_src =rs.getBoolean("HAVA_SRC");
	            model.Hava_doc =rs.getBoolean("HAVA_DOC");
	            model.Is_verified = rs.getBoolean("IS_VERIFIED");
	            model.Model_intro =rs.getString("MODEL_INTRO");
	            model.Model_sponsor =rs.getString("Sponsor");
	            model.Target_access =rs.getString("Target_access");
	            model.Model_target=rs.getString("Target_path");
				modelNameList.add(model);
				
			}
		}
		return modelNameList;
	}
	
	public List<Model_para_info> getModelParaInfo() throws SQLException{
		List<Model_para_info> modelParaList = new ArrayList<Model_para_info>();
		ResultSet rs = ModelService.GetModelParaInfo(); // 获取model_info中所有数据的缓存
		if (rs != null) {
			while (rs.next()) // 遍历model_info的所有行
			{
				Model_para_info modelParaInfo = new Model_para_info();
				modelParaInfo.Muid=rs.getInt("MUID");
				modelParaInfo.Para_type = ParaType.valueOf(rs.getString("Para_type"));
                modelParaInfo.Para_defa_value = rs.getString("Para_defa_value");
                modelParaInfo.Para_label =rs.getString("Para_label");
                modelParaInfo.Para_physicsunit = rs.getString("Para_physicsunit");
                modelParaInfo.Basic_type = rs.getString("Basic_type");
                modelParaInfo.Para_length =rs.getInt("Para_length");
                modelParaInfo.Para_desc =rs.getString("Para_desc");
                modelParaList.add(modelParaInfo);
			}
		}
		return modelParaList;
		
	}

}
