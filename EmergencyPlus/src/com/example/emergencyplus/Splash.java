package com.example.emergencyplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Splash extends Activity {
	
	TextView textHeader;
	TextView instructions;
	
	// splash screen delay time
	private static final int SPLASH_DISPLAY_TIME = 5000;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		textHeader = (TextView) findViewById(R.id.textView1);
		textHeader.setText("Welcome to EmergencyPlus!");
		
		instructions = (TextView) findViewById(R.id.textView2);
		StringBuilder strInstr = new StringBuilder();
		strInstr.append("Instructions for use!");
		strInstr.append("\n");
		strInstr.append("Step 1: Create your profile.");
		strInstr.append("\n");
		strInstr.append("Step 2: Emergency call - Double tap Volume Up button");
		strInstr.append("\n");
		strInstr.append("Step 3: Emergency SMS - Single tap Volume Down button");
		strInstr.append("\n");
		strInstr.append("Step 4: Go on! Use the app and let us know how you like it..");
		strInstr.append("\n");
		strInstr.append("");
		
		instructions.setText(strInstr.toString());
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent.setClass(Splash.this, MainActivity.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}
		}, SPLASH_DISPLAY_TIME);
	}

}
