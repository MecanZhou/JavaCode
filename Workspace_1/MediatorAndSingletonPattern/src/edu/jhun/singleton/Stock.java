package edu.jhun.singleton;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *设计模式测试：单例模式库存管理类
 */
public class Stock {
	
	//通过引用中介者对外部管理类进行调用
	private AbstractMediator mediator;
	//库存100件产品
	private static int PRODUCTS_NUMBER=100;
	//私有的构造方法，不允许外界直接创建对象
	private Stock(AbstractMediator mediator){
		this.mediator = mediator;
	}
	//创建类的唯一实例
	private static Stock INSTANCE = null;
	//公有静态方法给外部提供类的唯一实例
	public static Stock getInstance(AbstractMediator mediator) {
		if (INSTANCE==null) {
			INSTANCE = new Stock(mediator);
		}
		return INSTANCE;
	}
	
	//增加库存
	public void increase(int number) {
		PRODUCTS_NUMBER+=number;
		System.out.println("Stock: [Increase] to "+PRODUCTS_NUMBER);
	}
	//库存减少
	public void decrease(int number) {
		PRODUCTS_NUMBER-=number;
		System.out.println("Stock: [Decrease] to "+PRODUCTS_NUMBER);
	}
	//库存量
	public int getStockNumber() {
		System.out.println("Stock: [Number] :"+ PRODUCTS_NUMBER);
		return PRODUCTS_NUMBER;
	}
	//通知中介者根据库存折价销售
	public void clearStock() {
		System.out.println("Stock:CALL {Sale} FOR [Offsale] :"+PRODUCTS_NUMBER);
		System.out.println("Stock:CALL {Pur} FOR [RefuseBuy]");
		mediator.dispatchCenter("sto.clear");
	}
}
