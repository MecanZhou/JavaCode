import org.junit.Test;

import edu.jhun.mediator.AbstractMediator;
import edu.jhun.mediator.Mediator;
import edu.jhun.singleton.Purchase;
import edu.jhun.singleton.Sale;
import edu.jhun.singleton.Stock;

/**
 * 
 */

/**
 * @author Administrator
 * @time 2018年1月6日下午8:19:14
 * 中介者模式和单例模式测试类
 */
public class PatternTest {
	//测试中介者模式
	@Test
	public void testMediator() {
		//实例化中介者
		AbstractMediator mediator = new Mediator();
		//采购管理人员采购产品
		System.out.println("-----{Pur} BUY-----");
		Purchase purchase = Purchase.getInstance(mediator);
		purchase.buyProducts(100);
		//销售管理人员销售产品
		System.out.println("\n-----{Sale} SALE-----");
		Sale sale = Sale.getInstance(mediator);
		sale.sellProducts(400);
		//库存管理人员管理库存
		System.out.println("\n-----{Stock} CLEAR-----");
		Stock stock = Stock.getInstance(mediator);
		stock.clearStock();
	}
}
