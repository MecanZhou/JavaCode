package test.war;

public class KeyPersonThread extends Thread {

	public void run(){
		
		System.out.println(Thread.currentThread().getName()+" Started Fighting!");
		for(int i=1;i<=10;i++){
			System.out.println(Thread.currentThread().getName()+" Attacked the Enemy ["+i+"] time(s)");
		}
		System.out.println(Thread.currentThread().getName()+" Ended the WAR!");
	}
	
}

