public class ThreadExtendsThread extends Thread {  
    private int count =10;  
    public void run()  
    {    
        System.out.println(this.getName()+" got count from " + count);  
        while(count > 0)  
        {  
            System.out.println(this.getName()+" : "+count--);  
        }  
        System.out.println(this.getName()+" : existing count=" + count);  
    }  
      
    public static void main(String[] args)  
    {  
        ThreadExtendsThread thread1 = new ThreadExtendsThread();  
        ThreadExtendsThread thread2 = new ThreadExtendsThread();  
        thread1.start();  
        thread2.start(); 
    }  
}  