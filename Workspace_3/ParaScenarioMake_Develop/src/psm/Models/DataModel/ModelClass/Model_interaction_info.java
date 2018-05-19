package psm.Models.DataModel.ModelClass;

import java.io.Serializable;

public class Model_interaction_info  implements Serializable
{
    public int Inid;//交互类的id
    public int Muid;//交互类归属模型的id
    public String Sent_interactions;//交互类发送的消息
    public int Accepted_interactions;//交互类接受的消息
    public String Interaction_key;//交互类的key
    public String Interaction_paras;//交互类的参数
    public String Interaction_desc;//交互类的描述信息
}
