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
	        listView=(ListView)this.findViewById(R.id.list);    //��listView�벼�ֶ������  
	          
	        setInfo();  //����Ϣ��ֵ��������������  
	          
	        listView.setAdapter(new ListViewAdapter(mlistInfo));  
	          
	        //����Item�ĵ���¼�  
	        listView.setOnItemClickListener(new OnItemClickListener(){  
	            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {  
	                info getObject = mlistInfo.get(position);   //ͨ��position��ȡ������Ķ���  
	                int infoId = getObject.getId(); //��ȡ��Ϣid  
	                String infoTitle = getObject.getTitle();    //��ȡ��Ϣ����  
	                String infoDetails = getObject.getDetails();    //��ȡ��Ϣ����  
	                  
	                //Toast��ʾ����  
	                Toast.makeText(ListTestActivity.this, "��ϢID:"+infoId,Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	          
	        //�����˵���ʾ  
	        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
	            public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {  
	                conMenu.setHeaderTitle("�˵�");  
	                conMenu.add(0, 0, 0, "��Ŀһ");  
	                conMenu.add(0, 1, 1, "��Ŀ��");  
	                conMenu.add(0, 2, 2, "��Ŀ��");  
	            }  
	        });  
	          
	    }  
	    //�����˵�������  
	    public boolean onContextItemSelected(MenuItem aItem) {  
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();  
	        switch (aItem.getItemId()) {  
	             case 0:  
	                 Toast.makeText(ListTestActivity.this, "��������Ŀһ",Toast.LENGTH_SHORT).show();  
	                 return true;  
	             case 1:  
	                 Toast.makeText(ListTestActivity.this, "��������Ŀ��",Toast.LENGTH_SHORT).show();  
	               
	                 return true;  
	             case 2:  
	                 Toast.makeText(ListTestActivity.this, "��������Ŀ��",Toast.LENGTH_SHORT).show();  
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
	                info getInfo=(info)mlistInfo.get(i);    //��ȡ��i������  
	                //����makeItemView��ʵ����һ��Item  
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
	          
	        //����Item�ĺ���  
	        private View makeItemView(String strTitle, String strText, int resId) {    
	            LayoutInflater inflater = (LayoutInflater) ListTestActivity.this    
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
	    
	            // ʹ��View�Ķ���itemView��R.layout.item����  
	            View itemView = inflater.inflate(R.layout.item, null);  
	              
	            // ͨ��findViewById()����ʵ��R.layout.item�ڸ����  
	            TextView title = (TextView) itemView.findViewById(R.id.title);    
	            title.setText(strTitle);    //������Ӧ��ֵ  
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
	            information.setTitle("����"+i);  
	            information.setDetails("��ϸ��Ϣ"+i);  
	            information.setAvatar(R.drawable.i2);  
	              
	            mlistInfo.add(information); //���µ�info������뵽��Ϣ�б���  
	            i++;  
	        }  
	    }  
	  
	}  
	     
        
        
    }  
      
    //�����˵���Ӧ����  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
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
