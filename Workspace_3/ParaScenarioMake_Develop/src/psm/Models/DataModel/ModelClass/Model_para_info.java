package psm.Models.DataModel.ModelClass;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Model_para_info implements Serializable
{
    //public Member OwnerMember { set; get; }//�ò���������ģ�ͣ�����ǽ�����ģ���г���ͬ��paryKey��ͬ�Ĺؼ��ֶΣ�

    public int Mpid; //ģ�Ͳ�����Ϣ����id
    public int Muid;//����������ģ�͵�id
    public ParaType Para_type;//��������  input output init
    public String Para_defa_value;//����Ĭ��ֵ
    public String Para_label;//�����ı�ǩ
    public String Para_physicsunit;//������key
    public String Basic_type;//�����Ļ�����Ϣ
    public int Para_length;//�����ĳ���
    public String Para_desc;//������������Ϣ
}
