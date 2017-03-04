package com.gsv.etijori.database;

import java.util.ArrayList;

import com.gsv.etijori.AccountCredentials;
import com.gsv.etijori.util.ETijoriUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AccountCredentialsDatabase {
	
	private static Context mContext;
	
	private static AccountCredentialsDatabase INSTANCE;
	private ETijoriDatabase mETijoriDatabase;
	
	private AccountCredentialsDatabase() {
		mETijoriDatabase = new ETijoriDatabase(mContext);
	}
	
	public static AccountCredentialsDatabase getInstance(Context context) {
		mContext = context;
		if (null == INSTANCE) {
			INSTANCE = new AccountCredentialsDatabase();
		}
		
		return INSTANCE;
	}

	public boolean add(AccountCredentials accCred) {
		if (null == accCred) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Invalid account credentials to insert.");
			return false;
		}
		
		SQLiteDatabase db = mETijoriDatabase.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME, accCred.getUserName());
		values.put(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_PASSWORD, accCred.getPassword());
		values.put(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_ACCOUNT_TYPE, accCred.getAccountName());
		long ret = db.insert(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, null, values);
		db.close();
		if (ret <= 0) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Adding account credentials record in table failed.");
			return false;
		}
		return true;
	}
	
	public boolean update(AccountCredentials accCred) {
		if (null == accCred) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Invalid account credentials.");
			return false;
		}
		
		SQLiteDatabase db = mETijoriDatabase.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME, accCred.getUserName());
		values.put(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_PASSWORD, accCred.getPassword());
		String whereClause = ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME + "=?";
		String[] whereArgs = new String[]{accCred.getUserName()};
		long ret = db.update(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, values, whereClause, whereArgs);
		db.close();
		if (ret <= 0) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Updating account credentials record in table failed.");
			return false;
		}
		return true;
	}
	
	public boolean delete(AccountCredentials accCred) {
		if (null == accCred) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Invalid account credentials.");
			return false;
		}
		
		SQLiteDatabase db = mETijoriDatabase.getWritableDatabase();
		String whereClause = ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME + "=? and " + ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_ACCOUNT_TYPE + "=?";
		String[] whereArgs = new String[]{accCred.getUserName(), accCred.getAccountName()};
		long ret = db.delete(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, whereClause, whereArgs);
		db.close();
		if (ret <= 0) {
			Log.e(ETijoriUtil.ET_APPLICATION_TAG, "Deleting account credentials record from table failed.");
			return false;
		}
		return true;
	}
	
	public AccountCredentials getCredentials(String userName) {
		SQLiteDatabase db = mETijoriDatabase.getReadableDatabase();
		String selection = ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME + "=?";
		String[] selectionArgs = new String[]{userName};
		Cursor cursor = db.query(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (null == cursor) {
			return null;
		}
		
		AccountCredentials accCred = null;
		if (cursor.moveToFirst()) {
			accCred = new AccountCredentials(cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME)),
					cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_PASSWORD)));
		}
		
		cursor.close();
		return accCred;
	}

	public AccountCredentials getCredentials(String userName, String accountType) {
		SQLiteDatabase db = mETijoriDatabase.getReadableDatabase();
		String selection = ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME + "=? and " + ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_ACCOUNT_TYPE + "=?";
		String[] selectionArgs = new String[]{userName, accountType};
		Cursor cursor = db.query(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		if (null == cursor) {
			return null;
		}

		AccountCredentials accCred = null;
		if (cursor.moveToFirst()) {
			accCred = new AccountCredentials(cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME)),
					cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_PASSWORD)), accountType);
		}

		cursor.close();
		return accCred;
	}
	
	public ArrayList<AccountCredentials> getAllCredentials() {
		ArrayList<AccountCredentials> accCredList = new ArrayList<AccountCredentials>();
		SQLiteDatabase db = mETijoriDatabase.getReadableDatabase();
		Cursor cursor = db.query(ETijoriDatabase.AccountCredentilsTable.TABLE_NAME, null, null, null, null, null, null);
		if (null == cursor) {
			db.close();
			return accCredList;
		}
		
		if (cursor.moveToFirst()) {

			do {
				String userName = cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_USERNAME));
				String password = cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_PASSWORD));
				String accountType = cursor.getString(cursor.getColumnIndex(ETijoriDatabase.AccountCredentilsTable.COLUMN_NAME_ACCOUNT_TYPE));

				if (ETijoriUtil.ET_ACCOUNT_TYPE.equals(accountType)) {
					continue;
				}
				AccountCredentials accCred = new AccountCredentials(userName,
					password,
						accountType);
				accCredList.add(accCred);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		db.close();
		return accCredList;
	}
}
