/**
 * 
 */
package edu.jhun.mediator;

import edu.jhun.singleton.Purchase;
import edu.jhun.singleton.Sale;
import edu.jhun.singleton.Stock;

/**
 * @author Administrator
 * @time 2018年1月6日下午6:29:55
 * 
 */
public class Mediator extends AbstractMediator{
	
	//接收各个单例的实例，实现中介者调度操作
	private Purchase purchase;
	private Stock stock;
	private Sale sale;
	public Mediator() {
		this.purchase = Purchase.getInstance(this);
		this.stock = Stock.getInstance(this);
		this.sale = Sale.getInstance(this);
	}

	/* (non-Javadoc)
	 * @see edu.jhun.mediator.AbstractMediator#dispatchCenter(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void dispatchCenter(String event, Object... objects) {
		if (event.equals("pur.buy")) {//收到采购电脑请求，调用purchase.buy
			this.buyProducts((Integer)objects[0]);
		}else if (event.equals("sal.sell")) {//收到销售电脑请求，调用sale.sell
			this.sellProducts((Integer)objects[0]);
		}else if (event.equals("sal.offsell")) {//收到折价销售请求，调用sale.offsell
			this.offsell();
		}else if (event.equals("sto.clear")) {//收到清仓处理请求，调用sale.offsell
			this.clearStock();
		}
	}
	
	//实现调度具体操作：购买产品、销售产品、折价销售、清仓处理等
	//购买产品
	private  void buyProducts(int number) {
		int saleStatus = sale.getSaleStatus();
		if (saleStatus<80&&stock.getStockNumber()>number) {
			int buyNumber = number/2;
			stock.increase(buyNumber);
		} else {
			stock.increase(number);
		}
	}
	//销售产品
	private void sellProducts(int number) {
		if (stock.getStockNumber() < number) {
			purchase.buyProducts(number);
		}
		stock.decrease(number);
	}
	//折价销售并清空库存
	private void offsell() {
		stock.decrease(stock.getStockNumber());
	}
	//清仓处理
	private void clearStock() {
		sale.offsale();
		purchase.refuseBuy();
	}
}
