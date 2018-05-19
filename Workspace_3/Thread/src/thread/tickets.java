package thread;

public class tickets implements Runnable{
private int count=100;

	
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			synchronized ("") {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(count>0){	
				System.out.println(Thread.currentThread().getName()+"卖了1张票，还剩票"+--count+"张");
			}else{
				break;
			}
		}
		}
	}
	public static void main(String[] args) {
		
		tickets t=new tickets();
		
		Thread t1= new Thread(t,"窗口1");
		Thread t2= new Thread(t,"窗口2");
		Thread t3= new Thread(t,"窗口3");
		
		t1.start();
		t2.start();
		t3.start();
	}
}
