package com.example.emergencyplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private ImageView iv;
	private ImageView iv2;
	private ImageView iv3;
	private TextView tv1;
	
	
	//SQL Helper object
	SQLHelper db;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        //DB Helper object 
        db = new SQLHelper(this);
        
        tv1 = (TextView) findViewById(R.id.textView1);
        iv = (ImageView) findViewById(R.id.imageView1);
        User user = db.getUser();
        if(user!= null){
        	iv.setImageResource(R.drawable.profile_edit);
        	tv1.setText("Edit Profile");
        }
        else {
        	iv.setImageResource(R.drawable.profile_create);
        }
        
        iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				User profileUser = db.getUser();
				//if profile is not set up right.. Move to Profile Creation Intent
				if(profileUser == null){
					Intent i = new Intent(MainActivity.this, ProfileActivity.class);
					startActivity(i);
				}
				//Call EditProfile intent
				else{
					Intent i = new Intent(MainActivity.this, EditProfile.class);
					startActivity(i);
				}
		}
        });
        
        iv2 = (ImageView) findViewById(R.id.imageView2);
        iv2.setImageResource(R.drawable.emer_call);
        iv2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// get selected radio button from radioGroup
				Intent i = new Intent(MainActivity.this, CallActivity.class);
				startActivity(i);
		}
        });
        
        iv3 = (ImageView) findViewById(R.id.imageView3);
        iv3.setImageResource(R.drawable.emer_sms);
        iv3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//Fetch the profile of the user from the database.
				User profileUser = db.getUser();
				//if profile is not set up right.. prompt user to correct it
				if(profileUser == null){
					Toast.makeText(getApplicationContext(),"User set-up not complete. Please fill your PROFILE to proceed!",  
					         Toast.LENGTH_LONG).show();
				}
				//else send it to GPS Track intent
				else{
					Intent i = new Intent(MainActivity.this, GPSTracker.class);
					startActivity(i);
				}
		}
        });
        
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
    
    //Shortcuts for call and SMS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     switch(keyCode){
       case KeyEvent.KEYCODE_VOLUME_UP:
    	   //Toast.makeText(this,"Volumen Up pressed", Toast.LENGTH_SHORT).show();
         event.startTracking();
         return true;
       case KeyEvent.KEYCODE_VOLUME_DOWN:
         //Toast.makeText(this,"Volumen Down pressed", Toast.LENGTH_SHORT).show();
         return false;
       case KeyEvent.KEYCODE_BACK:
    	     onBackPressed();
    	     return true;
       case KeyEvent.KEYCODE_MENU: 
  	     //Toast.makeText(this, "Menu key pressed", Toast.LENGTH_SHORT).show();
  	    return true; 
  	   case KeyEvent.KEYCODE_SEARCH:
  	     //Toast.makeText(this, "Search key pressed", Toast.LENGTH_SHORT).show();
  	     return true;
  	   case KeyEvent.KEYCODE_POWER:
  	  	     //Toast.makeText(this, "Power pressed", Toast.LENGTH_SHORT).show();
  	  	     return true;       	     
     }
     return super.onKeyDown(keyCode, event);
    }

    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
     switch(keyCode){
       case KeyEvent.KEYCODE_VOLUME_UP:
         Toast.makeText(this, "Trying to place emergency call", Toast.LENGTH_SHORT).show();
         Intent i = new Intent(MainActivity.this, CallActivity.class);
			startActivity(i);
         return true;
       case KeyEvent.KEYCODE_VOLUME_DOWN:
         Toast.makeText(this, "Trying to send emergency SMS", Toast.LENGTH_SHORT).show();
         //Launch GPS Tracker activity
         //Fetch the profile of the user from the database.
			User profileUser = db.getUser();
			//if profile is not set up right.. prompt user to correct it
			if(profileUser == null){
				Toast.makeText(getApplicationContext(),"User set-up not complete. Please fill your PROFILE to proceed!",  
				         Toast.LENGTH_LONG).show();
			}
			//else send it to GPS Track intent
			else{
				Intent i1 = new Intent(MainActivity.this, GPSTracker.class);
				startActivity(i1);
			}
         return true;
       case KeyEvent.KEYCODE_MENU:
  	     //Toast.makeText(this, "Menu key released", Toast.LENGTH_SHORT).show();
  	     return true;
     case KeyEvent.KEYCODE_SEARCH:
  	     //Toast.makeText(this, "Search key released", Toast.LENGTH_SHORT).show();
  	     return true;
     case KeyEvent.KEYCODE_POWER:
	     //Toast.makeText(this, "Volumen Down released", Toast.LENGTH_SHORT).show();
	     return true;         
       }
       return super.onKeyDown(keyCode, event);
    }
    
}
