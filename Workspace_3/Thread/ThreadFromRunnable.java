public class ThreadFromRunnable implements Runnable {  
	private int count = 10;  
    public void run() 
    {  
        System.out.println(Thread.currentThread().getName()+" got count from " + count);  
        while(count > 0)  
        {  
            System.out.println(Thread.currentThread().getName()+" : "+ count--);  
        }  
        System.out.println(Thread.currentThread().getName()+" : exiting "+ count);  
 }  
    public static void main(String[] args)  
    {  
        ThreadFromRunnable tr = new ThreadFromRunnable();  
        Thread thread1 = new Thread(tr);  
        Thread thread2 = new Thread(tr);  
          
        thread1.start();  
        thread2.start();  
    }  
  
}  