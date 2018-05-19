package test.war;

//军队攻击线程，模拟双方作战的行为
public class ArmyRunnable implements Runnable {

	//volatile保证了线程可以正确读取其它线程的值
	volatile boolean AttackCommand = true;
	
	public void run() {
		while(AttackCommand){
			for(int i=1;i<=5;i++){
				System.out.println(Thread.currentThread().getName()+" Attacked the Anemy ["+i+"] time(s)");
				Thread.yield();//线程让步语句，可以让多个线程随机运行
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" Stoped Attack!");
	}

}

