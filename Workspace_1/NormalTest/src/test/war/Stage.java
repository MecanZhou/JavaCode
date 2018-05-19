package test.war;

public class Stage extends Thread {
	
	public void run(){
		System.out.println("Welcom to Mainland tile lorraine!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.out.println("All of Army Attacking!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		System.out.println("Bianconeri started in on both sides!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		ArmyRunnable ArmyTaskOfNorthTexas = new ArmyRunnable();
		ArmyRunnable ArmyTaskOfDEMarcia = new ArmyRunnable();
		
		//使用Runnable接口创建线程
		Thread ArmyOfNorthTexas = new Thread(ArmyTaskOfNorthTexas,"theNorthTexasArmy");
		Thread ArmyOfDEMarcia = new Thread(ArmyTaskOfDEMarcia,"theDEMarciaArmy");
		
		//启动线程让军队开始作战
		ArmyOfNorthTexas.start();
		ArmyOfDEMarcia.start();
		
		//Stage的current线程暂时休眠
		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
		System.out.println("The Hero Jarvan IV was commming!");
		Thread hero = new KeyPersonThread();
		hero.setName("Javan IV");
		System.out.println("DE Marcia,Forever!");
		
		ArmyTaskOfNorthTexas.AttackCommand = false;
		ArmyTaskOfDEMarcia.AttackCommand = false;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		hero.start();
		try {//stage线程和hero线程是同时进行的，此处join方法让stage线程等待hero线程结束后再执行
			hero.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Victory!!!");
	}

	public static void main(String[] args) {
		new Stage().start();

	}

}

