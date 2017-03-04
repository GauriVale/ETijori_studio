package com.gsv.etijori.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ETijoriDatabase extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "etijori.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_ACCOUNT_CREDENTIALS = "CREATE TABLE IF NOT EXISTS " + AccountCredentilsTable.TABLE_NAME + " ("
            + AccountCredentilsTable._ID + " INTEGER,"
            + AccountCredentilsTable.COLUMN_NAME_USERNAME + " TEXT PRIMARY KEY,"
            + AccountCredentilsTable.COLUMN_NAME_PASSWORD + " TEXT,"
            + AccountCredentilsTable.COLUMN_NAME_ACCOUNT_TYPE + " TEXT"
            + ");";
    
    public static final class AccountCredentilsTable implements BaseColumns {

        public static final String TABLE_NAME = "accountcredentials";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_ACCOUNT_TYPE = "accountType";
    }


	public ETijoriDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ACCOUNT_CREDENTIALS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
