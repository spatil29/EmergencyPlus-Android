package com.example.emergencyplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends Activity implements OnClickListener,
OnItemSelectedListener {

	private Button submit;
	private Button cancel;
	private EditText firstName;
	private EditText lastName;
	private EditText phoneNo;
	private EditText emergencyPhoneNo;

	//DB class
	SQLHelper db;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		//Instantiate DB Helper class
		db = new SQLHelper(this);

		firstName = (EditText) findViewById(R.id.efirstname);
		lastName = (EditText) findViewById(R.id.elastname);
		phoneNo = (EditText) findViewById(R.id.ephoneno);
		emergencyPhoneNo= (EditText) findViewById(R.id.eemergencyphoneno);

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		// Cancel Button
		case R.id.cancel:
			Intent i = new Intent(getBaseContext(), MainActivity.class);
			startActivity(i);
			break;

			// Submit Button
		case R.id.submit:
			System.out.println("*******REGISTER BUTTON CLICK*************");
			String fname = firstName.getText().toString();
			String lname = lastName.getText().toString();
			String phnno = phoneNo.getText().toString();
			String ephnno = emergencyPhoneNo.getText().toString();

			boolean invalid = false;

			if (fname.equals("")) {
				invalid = true;
				Toast.makeText(getApplicationContext(), "Enter your firstname",
						Toast.LENGTH_SHORT).show();
			} else

				if (lname.equals("")) {
					invalid = true;
					Toast.makeText(getApplicationContext(),
							"Please enter your lastname", Toast.LENGTH_SHORT)
							.show();
				} else

					if (phnno.equals("")) {
						invalid = true;
						Toast.makeText(getApplicationContext(),
								"Please enter your  Phone Number", Toast.LENGTH_SHORT)
								.show();
					} else

						if (ephnno.equals("")) {
							invalid = true;
							Toast.makeText(getApplicationContext(),
									"Please enter your Emergency Phone Number", Toast.LENGTH_SHORT)
									.show();

						} else
							if (phnno.length()<10) {
								invalid = true;
								Toast.makeText(getApplicationContext(),
										"Please enter Valid Phone Number", Toast.LENGTH_SHORT)
										.show();

							} else
								if (ephnno.length()<10) {
									invalid = true;
									Toast.makeText(getApplicationContext(),
											"Please enter Valid  Emergency Phone Number", Toast.LENGTH_SHORT)
											.show();

								}


								else if (invalid == false) 
								{
									db.createUserAndPersist(fname, lname, phnno, ephnno);
									Toast.makeText(getApplicationContext(),
											"Profile created", Toast.LENGTH_SHORT)
											.show();
									Intent i1 = new Intent(ProfileActivity.this, MainActivity.class);
									startActivity(i1);

								}

			break;
		}}

}
