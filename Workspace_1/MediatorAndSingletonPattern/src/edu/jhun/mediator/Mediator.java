/**
 * 
 */
package edu.jhun.mediator;

import edu.jhun.singleton.Purchase;
import edu.jhun.singleton.Sale;
import edu.jhun.singleton.Stock;

/**
 * @author Administrator
 * @time 2018��1��6������6:29:55
 * 
 */
public class Mediator extends AbstractMediator{
	
	//���ո���������ʵ����ʵ���н��ߵ��Ȳ���
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
		if (event.equals("pur.buy")) {//�յ��ɹ��������󣬵���purchase.buy
			this.buyProducts((Integer)objects[0]);
		}else if (event.equals("sal.sell")) {//�յ����۵������󣬵���sale.sell
			this.sellProducts((Integer)objects[0]);
		}else if (event.equals("sal.offsell")) {//�յ��ۼ��������󣬵���sale.offsell
			this.offsell();
		}else if (event.equals("sto.clear")) {//�յ���ִ������󣬵���sale.offsell
			this.clearStock();
		}
	}
	
	//ʵ�ֵ��Ⱦ�������������Ʒ�����۲�Ʒ���ۼ����ۡ���ִ����
	//�����Ʒ
	private  void buyProducts(int number) {
		int saleStatus = sale.getSaleStatus();
		if (saleStatus<80&&stock.getStockNumber()>number) {
			int buyNumber = number/2;
			stock.increase(buyNumber);
		} else {
			stock.increase(number);
		}
	}
	//���۲�Ʒ
	private void sellProducts(int number) {
		if (stock.getStockNumber() < number) {
			purchase.buyProducts(number);
		}
		stock.decrease(number);
	}
	//�ۼ����۲���տ��
	private void offsell() {
		stock.decrease(stock.getStockNumber());
	}
	//��ִ���
	private void clearStock() {
		sale.offsale();
		purchase.refuseBuy();
	}
}
