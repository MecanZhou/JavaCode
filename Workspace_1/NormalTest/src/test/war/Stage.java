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
		
		//ʹ��Runnable�ӿڴ����߳�
		Thread ArmyOfNorthTexas = new Thread(ArmyTaskOfNorthTexas,"theNorthTexasArmy");
		Thread ArmyOfDEMarcia = new Thread(ArmyTaskOfDEMarcia,"theDEMarciaArmy");
		
		//�����߳��þ��ӿ�ʼ��ս
		ArmyOfNorthTexas.start();
		ArmyOfDEMarcia.start();
		
		//Stage��current�߳���ʱ����
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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		
		hero.start();
		try {//stage�̺߳�hero�߳���ͬʱ���еģ��˴�join������stage�̵߳ȴ�hero�߳̽�������ִ��
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

