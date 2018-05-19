package edu.jhun.singleton;

import java.util.Random;

import javax.imageio.ImageTypeSpecifier;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *���ģʽ���ԣ�����ģʽ���۹�����
 */
public class Sale {
	
	//ͨ�������н��߶��ⲿ��������е���
	private AbstractMediator mediator;
	//˽�еĹ��췽�������������ֱ�Ӵ�������
	private Sale(AbstractMediator mediator){
		this.mediator = mediator;
	}
	//�������Ψһʵ��
	private static Sale INSTANCE = null;
	//���о�̬�������ⲿ�ṩ���Ψһʵ��
	public static Sale getInstance(AbstractMediator mediator) {
		if (INSTANCE==null) {
			INSTANCE = new Sale(mediator);
		}
		return INSTANCE;
	}
	
	//��ȡ����״̬
	public int getSaleStatus() {
		Random random = new Random(System.currentTimeMillis());
		int saleStatus = random.nextInt(100);
		System.out.println("Sale: [Status] " + saleStatus);
		return saleStatus;
	}
	
	//֪ͨ�н��߸��ݿ�������۵���
	public void sellProducts(int number) {
		System.out.println("Sale: CALL {Pur} FOR [Sell] "+number+" times");
		mediator.dispatchCenter("sal.sell", number);
	}
	//�ۼ۴���
	public void offsale() {
		System.out.println("Sale: [Offsale]");
		System.out.println("Sale: CALL {Stock} FOR [Number]");
		mediator.dispatchCenter("sal.offsell");
	}
}
