package edu.jhun.singleton;

import java.util.Random;

import javax.imageio.ImageTypeSpecifier;

import edu.jhun.mediator.AbstractMediator;

/**
 * @author Administrator
 *设计模式测试：单例模式销售管理类
 */
public class Sale {
	
	//通过引用中介者对外部管理类进行调用
	private AbstractMediator mediator;
	//私有的构造方法，不允许外界直接创建对象
	private Sale(AbstractMediator mediator){
		this.mediator = mediator;
	}
	//创建类的唯一实例
	private static Sale INSTANCE = null;
	//公有静态方法给外部提供类的唯一实例
	public static Sale getInstance(AbstractMediator mediator) {
		if (INSTANCE==null) {
			INSTANCE = new Sale(mediator);
		}
		return INSTANCE;
	}
	
	//获取销售状态
	public int getSaleStatus() {
		Random random = new Random(System.currentTimeMillis());
		int saleStatus = random.nextInt(100);
		System.out.println("Sale: [Status] " + saleStatus);
		return saleStatus;
	}
	
	//通知中介者根据库存量销售电脑
	public void sellProducts(int number) {
		System.out.println("Sale: CALL {Pur} FOR [Sell] "+number+" times");
		mediator.dispatchCenter("sal.sell", number);
	}
	//折价处理
	public void offsale() {
		System.out.println("Sale: [Offsale]");
		System.out.println("Sale: CALL {Stock} FOR [Number]");
		mediator.dispatchCenter("sal.offsell");
	}
}
