package com.example.test;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	private static final String a1="a1"+s
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App a=new App();
        (App)getApplicationContext().set
        InputStream ist=getResources().getAssets().open("fileName.txt");
        
        File file =new File("a.txt");
        file
        file.mkdirs();
        File sdfile=Environment.getExternalStorageDirectory();
        File sdfile2 =new File(sdfile, "my sdfile");
        if(!sdfile.exists()){
        	sdfile2.createNewFile();
        	return;
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
