package com.example.tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FriendActivity extends Activity {

	ListView list = (ListView) findViewById(R.id.list);
	private List<info> mlistInfo = new ArrayList<info>();
	private ListView listView;
	 @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_friend);
	        listView=(ListView)this.findViewById(R.id.list);    //将listView与布局对象关联  
	          
	        setInfo();  //给信息赋值函数，用来测试  
	          
	        listView.setAdapter(new ListViewAdapter(mlistInfo));  
	          
	        //处理Item的点击事件  
	        listView.setOnItemClickListener(new OnItemClickListener(){  
	            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {  
	                info getObject = mlistInfo.get(position);   //通过position获取所点击的对象  
	                int infoId = getObject.getId(); //获取信息id  
	                String infoTitle = getObject.getTitle();    //获取信息标题  
	                String infoDetails = getObject.getDetails();    //获取信息详情  
	                  
	                //Toast显示测试  
	                Toast.makeText(ListTestActivity.this, "信息ID:"+infoId,Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	          
	        //长按菜单显示  
	        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
	            public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {  
	                conMenu.setHeaderTitle("菜单");  
	                conMenu.add(0, 0, 0, "条目一");  
	                conMenu.add(0, 1, 1, "条目二");  
	                conMenu.add(0, 2, 2, "条目三");  
	            }  
	        });  
	          
	    }  
	    //长按菜单处理函数  
	    public boolean onContextItemSelected(MenuItem aItem) {  
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();  
	        switch (aItem.getItemId()) {  
	             case 0:  
	                 Toast.makeText(ListTestActivity.this, "你点击了条目一",Toast.LENGTH_SHORT).show();  
	                 return true;  
	             case 1:  
	                 Toast.makeText(ListTestActivity.this, "你点击了条目二",Toast.LENGTH_SHORT).show();  
	               
	                 return true;  
	             case 2:  
	                 Toast.makeText(ListTestActivity.this, "你点击了条目三",Toast.LENGTH_SHORT).show();  
	                 return true;  
	        }  
	        return false;  
	   }  
	      
	      
	    public class ListViewAdapter extends BaseAdapter {    
	        View[] itemViews;  
	    
	        public ListViewAdapter(List<info> mlistInfo) {  
	            // TODO Auto-generated constructor stub  
	            itemViews = new View[mlistInfo.size()];              
	            for(int i=0;i<mlistInfo.size();i++){  
	                info getInfo=(info)mlistInfo.get(i);    //获取第i个对象  
	                //调用makeItemView，实例化一个Item  
	                itemViews[i]=makeItemView(  
	                        getInfo.getTitle(), getInfo.getDetails(),getInfo.getAvatar()  
	                        );  
	            }  
	        }  
	  
	        public int getCount() {  
	            return itemViews.length;    
	        }  
	    
	        public View getItem(int position) {    
	            return itemViews[position];    
	        }    
	    
	        public long getItemId(int position) {    
	            return position;    
	        }  
	          
	        //绘制Item的函数  
	        private View makeItemView(String strTitle, String strText, int resId) {    
	            LayoutInflater inflater = (LayoutInflater) ListTestActivity.this    
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
	    
	            // 使用View的对象itemView与R.layout.item关联  
	            View itemView = inflater.inflate(R.layout.item, null);  
	              
	            // 通过findViewById()方法实例R.layout.item内各组件  
	            TextView title = (TextView) itemView.findViewById(R.id.title);    
	            title.setText(strTitle);    //填入相应的值  
	            TextView text = (TextView) itemView.findViewById(R.id.info);    
	            text.setText(strText);    
	            ImageView image = (ImageView) itemView.findViewById(R.id.img);    
	            image.setImageResource(resId);  
	              
	            return itemView;    
	        }  
	    
	          
	        public View getView(int position, View convertView, ViewGroup parent) {    
	            if (convertView == null)    
	                return itemViews[position];    
	            return convertView;  
	        }    
	    }     
	      
	    public void setInfo(){  
	        mlistInfo.clear();  
	        int i=0;  
	        while(i<10){  
	            info information = new info();  
	            information.setId(1000+i);  
	            information.setTitle("标题"+i);  
	            information.setDetails("详细信息"+i);  
	            information.setAvatar(R.drawable.i2);  
	              
	            mlistInfo.add(information); //将新的info对象加入到信息列表中  
	            i++;  
	        }  
	    }  
	  
	}  
	     
        
        
    }  
      
    //长按菜单响应函数  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");   
        return super.onContextItemSelected(item);  
    } 
 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend, menu);
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
