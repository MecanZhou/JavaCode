package edu.jhun.singleton;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *���ģʽ���ԣ�����ģʽ�ɹ�������
 */
public class Purchase {
	
	//ͨ�������н��߶��ⲿ��������е���
	private AbstractMediator mediator;
	//˽�еĹ��췽�������������ֱ�Ӵ�������
	private Purchase(AbstractMediator mediator){
		this.mediator = mediator;
	}
	
	//�������Ψһʵ��
	private static Purchase INSTANCE = null;
	
	//���о�̬�������ⲿ�ṩ���Ψһʵ��
	public static Purchase getInstance(AbstractMediator mediator){
		if (INSTANCE==null) {
			INSTANCE = new Purchase(mediator);
		}
		return INSTANCE;
	}
	//֪ͨ�н��ߣ���ȡ����������ǲɹ�
	public void buyProducts(int number) {
		System.out.println("Pur: CALL {Sale} FOR [Status]");
		mediator.dispatchCenter("pur.buy", number);
	}
	//���ٲɹ���Ʒ
	public void refuseBuy() {
		System.out.println("Pur: [Refuse Buy]");
	}
}
