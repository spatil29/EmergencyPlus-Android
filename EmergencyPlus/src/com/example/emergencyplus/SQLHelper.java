package com.example.emergencyplus;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	private static final String DATABASE = "EMERGENCYPLUS";
	private static final int DB_VERSION = 1;

	//Columns of t_user table
	private static final String KEY_ID = "id";
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_PHONE = "phone_number";
	private static final String KEY_EMERGENCY = "emergency_number";	
	// EmergencyPlus user table name
	private static final String TABLE_USER = "t_user";

	public SQLHelper(Context context){
		super(context, DATABASE, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_USER_TABLE = "CREATE TABLE t_user ( " +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"first_name TEXT, "+
				"last_name TEXT, "+
				"phone_number TEXT, "+
				"emergency_number TEXT )";
		// create user table
		db.execSQL(CREATE_USER_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//DROP TABLE t_user on upgrade and recreate it - Dev Testing
		//To be Commented the code for production usage. No tables are dropped in PROD env
		String DROP_USER_TABLE = "DROP TABLE t_user";
		// Drop books table
		db.execSQL(DROP_USER_TABLE);
		//Create the CREATE method
		this.onCreate(db);

	}

	public void addEmergencyProfile(User user) {
		// TODO Auto-generated method stub
		Log.d("addEmergencyProfile", user.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, user.getFirstName()); // get firstName
		values.put(KEY_LAST_NAME, user.getLastName()); // get lastName
		values.put(KEY_PHONE, user.getPhoneNumber()); // get phoneNumber
		values.put(KEY_EMERGENCY, user.getEmergencyNumber()); // get emergencyNumber
		// 3. insert
		db.insert(TABLE_USER, // table
				null, //nullColumnHack
				values); // key/value -> keys = column names/values
		// 4. Close dbase
		db.close();
	}

	//Creates an object of type User and inserts it into the Database
	public void createUserAndPersist(String firstName, String lastName, String phoneNumber, String emergencyNumber){

		//Creates a user object
		User user = new User(firstName,lastName,phoneNumber,emergencyNumber);
		addEmergencyProfile(user);
	}


	// Get All Books
	public User getUser() {
		List<User> users = new LinkedList<User>();
		// 1. build the query
		String query = "SELECT * FROM " + TABLE_USER;
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// 3. go over each row, build book and add it to list
		User user = null;
		if (cursor.moveToFirst()) {
			do {
				user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setFirstName(cursor.getString(1));
				user.setLastName(cursor.getString(2));
				user.setPhoneNumber(cursor.getString(3));
				user.setEmergencyNumber(cursor.getString(4));
				// Add book to books
				users.add(user);
			} while (cursor.moveToNext());
		}
		Log.d("getAllBooks()", users.toString());
		//Take only the first user - Ideally only once the profile can be created in the db
		if(users.isEmpty())
			return null;
		else
			return users.get(0); // return user
	}

	public int UpdateUser(User profileUser, String fname, String lname,
			String phnno, String ephnno) {
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, fname); // get firstName
		values.put(KEY_LAST_NAME, lname); // get lastName
		values.put(KEY_PHONE, phnno); // get phoneNumber
		values.put(KEY_EMERGENCY, ephnno); // get emergencyNumber
		// 3. updating row
		int i = db.update(TABLE_USER, //table
				values, // column/value
				KEY_ID+" = ?", // selections
				new String[] { String.valueOf(profileUser.getId()) }); //selection args
		// 4. close dbase
		db.close();
		Log.d("UpdateBook", String.valueOf(profileUser.getId()) );

		return i;
	}


}
