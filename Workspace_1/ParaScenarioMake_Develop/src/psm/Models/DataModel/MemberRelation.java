package psm.Models.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MemberRelation {
	 //[start] Fields
		public Line logicalLine;
	    public String consumerName;//��������Ա������
	    public String producerName;//������Ա������
		public Member producer;//������Ա
		public Member consumer;//������Ա
		public int producerId;//�����˽�����Ա��ID
		public int consumerId;//�����˽�����Ա��ID
		
		   
	//[end]
	    public Member getConsumer()
	    {
	    	return consumer;
	    }
	    public int getConsumerId()
	    {
	    	return consumerId;
	    }
	    public String getConsumerName()
	    {
	    	return consumerName;
	    }
	   
	    public Line getLogicalLine()
	    {
	    	return logicalLine;
	    }
	    public String getProduceName()
	    {
	    	return producerName;
	    }	    
	    public Member getProducer()
	    {
	    	return producer;
	    }
	    public int getProducerId()
	    {
	    	return producerId;
	    }
	    public String getProducerName() {
			return producerName;
		}
	   
	    /**
	     * ������
	     */
	    public void setConsumer(Member consumer_)
	    {
	    	consumer=consumer_;
	    }
	    /**
	     * ������ID
	     */
	    public void setConsumerId(int consumerId_)
	    {
	    	consumerId=consumerId_;
	    }
	    /**
	     * �����˶���ĳ�Ա��
	     */
	    public void setConsumerName(String consumerName_)
	    {
	    	consumerName=consumerName_;
	    }
	   
	    /**
	     * ��ϵ��Ӧ���߼��ṹ�е���
	     */
	    public void setLogicalLine(Line logicalLine_)
	    {
	    	logicalLine=logicalLine_;
	    }
	    /**
	     * �����˶���ĳ�Ա��
	     */
	    public void setProduceName(String produceName_)
	    {
	    	producerName=produceName_;
	    }
	    /**
	     * ������
	     */
	    public void setProducer(Member producer_)
	    {
	    	producer=producer_;
	    }
	    /**
	     * ������ID
	     */
	    public void setProducerId(int producerId_)
	    {
	    	producerId=producerId_;
	    }
	    public void setProducerName(String producerName) {
			this.producerName = producerName;
		}
	   
	    public MemberRelation(){
	    	producer=new Member();
	    	consumer=new Member();
	    }

}
