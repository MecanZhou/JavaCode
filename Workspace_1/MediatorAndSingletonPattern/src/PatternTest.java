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
 * @time 2018��1��6������8:19:14
 * �н���ģʽ�͵���ģʽ������
 */
public class PatternTest {
	//�����н���ģʽ
	@Test
	public void testMediator() {
		//ʵ�����н���
		AbstractMediator mediator = new Mediator();
		//�ɹ�������Ա�ɹ���Ʒ
		System.out.println("-----{Pur} BUY-----");
		Purchase purchase = Purchase.getInstance(mediator);
		purchase.buyProducts(100);
		//���۹�����Ա���۲�Ʒ
		System.out.println("\n-----{Sale} SALE-----");
		Sale sale = Sale.getInstance(mediator);
		sale.sellProducts(400);
		//��������Ա������
		System.out.println("\n-----{Stock} CLEAR-----");
		Stock stock = Stock.getInstance(mediator);
		stock.clearStock();
	}
}
