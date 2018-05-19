package psm.Models.DataModel.ModelClass;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Model_para_info implements Serializable
{
    //public Member OwnerMember { set; get; }//该参数所属的模型（这个是解决组合模型中出现同级paryKey相同的关键字段）

    public int Mpid; //模型参数信息序列id
    public int Muid;//参数归属的模型的id
    public ParaType Para_type;//参数类型  input output init
    public String Para_defa_value;//参数默认值
    public String Para_label;//参数的标签
    public String Para_physicsunit;//参数的key
    public String Basic_type;//参数的基本信息
    public int Para_length;//参数的长度
    public String Para_desc;//参数的描述信息
}
