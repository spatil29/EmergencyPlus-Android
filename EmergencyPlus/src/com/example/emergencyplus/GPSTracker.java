package com.example.emergencyplus;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GPSTracker extends Activity implements LocationListener{

	private LocationManager locationManager;
	private Location location;

	private Button btn;

	//Text Message components
	private String latitude;
	private String longitude;
	private String mapsURL;

	//DB HElper object
	SQLHelper db;

	//Constructor to access methods of this class
	public GPSTracker() {
		super();

	}

	//Getters and Setters
	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Instantiate the db helper object
		db = new SQLHelper(this);

		//Get Profile user
		User profileUser = db.getUser();
		if (profileUser != null) {
			//Get location and send SMS
			getLocation();
			//Text String
			StringBuilder textMsgBldr = new StringBuilder();
			textMsgBldr.append("ALERT! From EMERGENCYPLUS!" + "\n");
			textMsgBldr.append("From "+profileUser.getFirstName() +" "+ profileUser.getLastName() + "\n");
			textMsgBldr.append("Location: " + "\n");
			textMsgBldr.append("Latitude: " + this.latitude  + "\n");
			textMsgBldr.append("Longitide: " + this.longitude + "\n");
			textMsgBldr.append("Maps-URL: " + this.mapsURL  + "\n");
			textMsgBldr.append("HURRY!");

			String textMsg = textMsgBldr.toString();
			//Send SMS Using in-built Intent
			sendSMS(profileUser, textMsg);

			if(latitude != null && longitude != null && mapsURL != null){
				Log.d("Location current: ", this.latitude +" " + this.longitude + " " + this.mapsURL);
				setContentView(R.layout.activity_gpstracker);

				btn = (Button) findViewById(R.id.button1);
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(GPSTracker.this, MainActivity.class);
						startActivity(i);
					}
				});
			}
		}
	}
	//Method to recieve the location of the device.
	public void getLocation(){
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 if(location != null) {
			System.out.println("in location != null loop");
			double latitude = location.getLatitude();
			this.latitude = Double.toString(latitude);
			double longitude = location.getLongitude();
			this.longitude = Double.toString(longitude);
			this.mapsURL ="http://www.google.com/maps/place/"+this.latitude+","+this.longitude +"]";
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
		else {
			System.out.println("location not present");
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}

	} 

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
			//locationManager.removeUpdates(this);
			double latitude = location.getLatitude();
			this.latitude = Double.toString(latitude);
			double longitude = location.getLongitude();
			this.longitude = Double.toString(longitude);


			this.mapsURL ="http://www.google.com/maps/place/"+this.latitude+","+this.longitude +"]";
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	protected void sendSMS(User profileUser, String textMsg) {
		Log.i("Send SMS", "");

		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("smsto:"));
		smsIntent.setType("vnd.android-dir/mms-sms");

		smsIntent.putExtra("address"  , profileUser.getEmergencyNumber());
		smsIntent.putExtra("sms_body"  , textMsg); 
		try {
			startActivity(smsIntent);
			finish();
			Log.i("Finished sending SMS...", "");
		} catch (android.content.ActivityNotFoundException ex) {
			//Re-direct to Main activity incase of errors and display toast message
			Intent i = new Intent(GPSTracker.this, MainActivity.class);
			startActivity(i);
			Toast.makeText(getApplicationContext(), 
					"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
		}
	}

}
