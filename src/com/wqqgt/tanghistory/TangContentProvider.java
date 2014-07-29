package com.wqqgt.tanghistory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

public class TangContentProvider extends ContentProvider {
  
  public static final Uri TANG_CONTENT_URI = Uri.parse(
  		"content://com.wqqgt.providers.tangcontentprovider/"+DBHelper.TABLE_NAME);
  
  private DBHelper mDBHelper;

  @Override
  public int delete(Uri arg0, String arg1, String[] arg2) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getType(Uri arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Uri insert(Uri _uri, ContentValues values) {
    SQLiteDatabase database = mDBHelper.getWritableDatabase();
    long rowID = database.insert(DBHelper.TABLE_NAME, null, values);
    if (rowID > 0) {
      Uri uri = ContentUris.withAppendedId(TANG_CONTENT_URI, rowID);
      getContext().getContentResolver().notifyChange(uri, null);
      return uri;
    }
    
    throw new SQLException("Failed to insert row into " + _uri);
  }

  @Override
  public boolean onCreate() {
    mDBHelper = new DBHelper(getContext());
    return false;
  }

  @Override
  public Cursor query(Uri arg0, String[] projection, String selection, String[] selectionArgs, String sortBy) {
    SQLiteDatabase database = mDBHelper.getReadableDatabase();
    Cursor c = database.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, 
        null, null, DBHelper.CLOUMN_ID);
    return c;
  }

  @Override
  public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
    // TODO Auto-generated method stub
    return 0;
  }

}
