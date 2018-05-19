package edu.jhun.singleton;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *设计模式测试：单例模式采购管理类
 */
public class Purchase {
	
	//通过引用中介者对外部管理类进行调用
	private AbstractMediator mediator;
	//私有的构造方法，不允许外界直接创建对象
	private Purchase(AbstractMediator mediator){
		this.mediator = mediator;
	}
	
	//创建类的唯一实例
	private static Purchase INSTANCE = null;
	
	//公有静态方法给外部提供类的唯一实例
	public static Purchase getInstance(AbstractMediator mediator){
		if (INSTANCE==null) {
			INSTANCE = new Purchase(mediator);
		}
		return INSTANCE;
	}
	//通知中介者，获取存货量并考虑采购
	public void buyProducts(int number) {
		System.out.println("Pur: CALL {Sale} FOR [Status]");
		mediator.dispatchCenter("pur.buy", number);
	}
	//不再采购产品
	public void refuseBuy() {
		System.out.println("Pur: [Refuse Buy]");
	}
}
