package com.wqqgt.tanghistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "_tang.db";
	public static final String TABLE_NAME = "_tanghistory";
	public static final String CLOUMN_TYPE = "_type";
	public static final String CLOUMN_TIME = "_createtime";
	public static final String CLOUMN_ID = "_id";
	public static final int DATABASE_VERSION = 1;

	public DBHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tableSql = "CREATE TABLE "+TABLE_NAME+" ("+CLOUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				CLOUMN_TYPE+" INTEGER,"+CLOUMN_TIME+" TimeStamp NOT NULL DEFAULT (datetime('now','localtime')));";
		db.execSQL(tableSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
