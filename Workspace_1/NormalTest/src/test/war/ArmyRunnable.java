package test.war;

//���ӹ����̣߳�ģ��˫����ս����Ϊ
public class ArmyRunnable implements Runnable {

	//volatile��֤���߳̿�����ȷ��ȡ�����̵߳�ֵ
	volatile boolean AttackCommand = true;
	
	public void run() {
		while(AttackCommand){
			for(int i=1;i<=5;i++){
				System.out.println(Thread.currentThread().getName()+" Attacked the Anemy ["+i+"] time(s)");
				Thread.yield();//�߳��ò���䣬�����ö���߳��������
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" Stoped Attack!");
	}

}

