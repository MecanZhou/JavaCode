package com.example.tour;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import thread.text2helper;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    private EditText username;
	private EditText userpassword;
	private Button login;
	PrintWriter output ;
	Text2Helper text2helper;
	int k=0;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.username);
        userpassword=(EditText)findViewById(R.id.userpassword);
        login=(Button)findViewById(R.id.login);
    }

    public void buttonsure(View v) {
    	new Thread(){
    			public void run(){
    				connect();
    				System.out.println("注册注册Text2ClientActivity的发送信息广播");
    			}
    	}.start();
    	System.out.println("注册Text2ClientActivity的发送信息广播");
    	OpenBroadcastReceiver();
	}
    public void OpenBroadcastReceiver() {
		// TODO Auto-generated method stub
		Text2ClientActivityReceiver receiver=new Text2ClientActivityReceiver(); 
		IntentFilter filter=new IntentFilter();
		filter.addAction("android.intent.action.VOICE_COMMAND");
		MainActivity.this.registerReceiver(receiver, filter);
	}
    public class Text2ClientActivityReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String yourself=intent.getStringExtra("SendYouself");
			String target=intent.getStringExtra("SendTarget");
			String message=intent.getStringExtra("SendMessage");
			if(k>0){
		    output.println(yourself);
		    output.flush();	
			output.println(target);
			output.flush();
			output.println(message);
			output.flush();
			System.out.println("接收器接收到Text2ClientActivity的广播，并发送到服务端");
			
			
		}
    	
    }
		};
    
    public void connect(){
    	try{
    		InetAddress addr=InetAddress.getByName("");
    		System.out.println("客户端发出请求");
    		Socket socket=new Socket("169.254.98.226",9616);
    		System.out.println("连接成功Socket="+socket);
    		output=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    		System.out.println("输出流获取成功");
    		output.println(username.getEditableText().toString());
    		output.flush();//强制讲输出流里面的数据发送出去
    		k++;
    	text2helper
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
