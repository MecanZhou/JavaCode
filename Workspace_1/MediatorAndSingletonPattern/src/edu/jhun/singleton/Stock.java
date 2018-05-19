package edu.jhun.singleton;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *���ģʽ���ԣ�����ģʽ��������
 */
public class Stock {
	
	//ͨ�������н��߶��ⲿ��������е���
	private AbstractMediator mediator;
	//���100����Ʒ
	private static int PRODUCTS_NUMBER=100;
	//˽�еĹ��췽�������������ֱ�Ӵ�������
	private Stock(AbstractMediator mediator){
		this.mediator = mediator;
	}
	//�������Ψһʵ��
	private static Stock INSTANCE = null;
	//���о�̬�������ⲿ�ṩ���Ψһʵ��
	public static Stock getInstance(AbstractMediator mediator) {
		if (INSTANCE==null) {
			INSTANCE = new Stock(mediator);
		}
		return INSTANCE;
	}
	
	//���ӿ��
	public void increase(int number) {
		PRODUCTS_NUMBER+=number;
		System.out.println("Stock: [Increase] to "+PRODUCTS_NUMBER);
	}
	//������
	public void decrease(int number) {
		PRODUCTS_NUMBER-=number;
		System.out.println("Stock: [Decrease] to "+PRODUCTS_NUMBER);
	}
	//�����
	public int getStockNumber() {
		System.out.println("Stock: [Number] :"+ PRODUCTS_NUMBER);
		return PRODUCTS_NUMBER;
	}
	//֪ͨ�н��߸��ݿ���ۼ�����
	public void clearStock() {
		System.out.println("Stock:CALL {Sale} FOR [Offsale] :"+PRODUCTS_NUMBER);
		System.out.println("Stock:CALL {Pur} FOR [RefuseBuy]");
		mediator.dispatchCenter("sto.clear");
	}
}
