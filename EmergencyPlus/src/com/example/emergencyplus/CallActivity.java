package com.example.emergencyplus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallActivity extends Activity {

	private Button makeCall; 
	private Button dialCall;
	private EditText number;
	
	//SQL Helper object
	SQLHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		makeCall = (Button) findViewById(R.id.call);
		dialCall = (Button) findViewById(R.id.dcall);
		number = (EditText) findViewById(R.id.phoneNo);

		//Instantiate DB Helper class
        db = new SQLHelper(this);

		// After button press make call
		makeCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					//Fetch the profile of the user from the database.
					User profileUser = db.getUser();
					//if profile is not set up right.. prompt user to correct it
					if(profileUser == null){
						Toast.makeText(getApplicationContext(),"User set-up not complete. Please fill your PROFILE to proceed!",  
						         Toast.LENGTH_LONG).show();
					}
					//else call the emergency number
					else{
						String emergencyNumber = profileUser.getEmergencyNumber();
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"+emergencyNumber));
						startActivity(callIntent);
					}
					

				} catch (Exception e) {

					Toast.makeText(getApplicationContext(),
							"Your call has failed...",

							Toast.LENGTH_LONG).show();

					e.printStackTrace();

				}

			}

		}); // End call
		
		//  Dial and Call the number ******** NEED TO TEST**************
		dialCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {

					// set the data
					/*String phoneNumber=number.getText().toString();
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
					startActivity(callIntent);*/
					String phoneNumber=number.getText().toString();
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+phoneNumber));
					startActivity(callIntent);

				} catch (Exception e) {

					Toast.makeText(getApplicationContext(),
							"Your call has failed...",

							Toast.LENGTH_LONG).show();

					e.printStackTrace();

				}

			}

		});// End Dial and call

	}
	
	//Shortcuts for call and SMS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     switch(keyCode){
       case KeyEvent.KEYCODE_VOLUME_UP:
    	   //Toast.makeText(this,"Volumen Up pressed", Toast.LENGTH_SHORT).show();
    	   event.startTracking();
         return true;
       case KeyEvent.KEYCODE_MENU: 
    	     //Toast.makeText(this, "Menu key pressed", Toast.LENGTH_SHORT).show();
    	    return true; 
    	   case KeyEvent.KEYCODE_SEARCH:
    	     //Toast.makeText(this, "Search key pressed", Toast.LENGTH_SHORT).show();
    	     return true;
    	   case KeyEvent.KEYCODE_BACK:
    	     onBackPressed();
    	     return true;
    	   case KeyEvent.KEYCODE_VOLUME_DOWN:
    		     //Toast.makeText(this,"Volumen Down pressed", Toast.LENGTH_SHORT).show();
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
    	   Toast.makeText(this, "Calling...", Toast.LENGTH_SHORT).show();
         //Fetch phone number and place call
         placeEmergencyCall();
         return true;
       case KeyEvent.KEYCODE_MENU:
    	     //Toast.makeText(this, "Menu key released", Toast.LENGTH_SHORT).show();
    	     return true;
       case KeyEvent.KEYCODE_SEARCH:
    	     //Toast.makeText(this, "Search key released", Toast.LENGTH_SHORT).show();
    	     return true;
       case KeyEvent.KEYCODE_VOLUME_DOWN:
    	     //Toast.makeText(this, "Volumen Down released", Toast.LENGTH_SHORT).show();
    	     return true;
       case KeyEvent.KEYCODE_POWER:
  	     //Toast.makeText(this, "Volumen Down released", Toast.LENGTH_SHORT).show();
  	     return true;
    	   }    	     
       return super.onKeyDown(keyCode, event);
    }

	private void placeEmergencyCall() {
		// TODO Auto-generated method stub
		try {
			//Fetch the profile of the user from the database.
			User profileUser = db.getUser();
			//if profile is not set up right.. prompt user to correct it
			if(profileUser == null){
				Toast.makeText(getApplicationContext(),"User set-up not complete. Please fill your PROFILE to proceed!",  
				         Toast.LENGTH_LONG).show();
			}
			//else call the emergency number
			else{
				String emergencyNumber = profileUser.getEmergencyNumber();
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+emergencyNumber));
				startActivity(callIntent);
			}
			

		} catch (Exception e) {

			Toast.makeText(getApplicationContext(),
					"Your call has failed...",

					Toast.LENGTH_LONG).show();

			e.printStackTrace();

		}
	}

}// End class
