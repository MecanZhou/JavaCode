package psm.Models.DataModel;

import java.util.ArrayList;
import java.util.List;

public class MemberRelation {
	 //[start] Fields
		public Line logicalLine;
	    public String consumerName;//订购方成员的名称
	    public String producerName;//公布成员的名称
		public Member producer;//公布成员
		public Member consumer;//订购成员
		public int producerId;//公布此交互成员的ID
		public int consumerId;//订购此交互成员的ID
		
		   
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
	     * 订购者
	     */
	    public void setConsumer(Member consumer_)
	    {
	    	consumer=consumer_;
	    }
	    /**
	     * 订购者ID
	     */
	    public void setConsumerId(int consumerId_)
	    {
	    	consumerId=consumerId_;
	    }
	    /**
	     * 订购此对象的成员名
	     */
	    public void setConsumerName(String consumerName_)
	    {
	    	consumerName=consumerName_;
	    }
	   
	    /**
	     * 关系对应的逻辑结构中的线
	     */
	    public void setLogicalLine(Line logicalLine_)
	    {
	    	logicalLine=logicalLine_;
	    }
	    /**
	     * 公布此对象的成员名
	     */
	    public void setProduceName(String produceName_)
	    {
	    	producerName=produceName_;
	    }
	    /**
	     * 公布者
	     */
	    public void setProducer(Member producer_)
	    {
	    	producer=producer_;
	    }
	    /**
	     * 公布者ID
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
