package com.wqqgt.tanghistory;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.view.MenuItem;
import android.widget.ListView;

public class DetailActivity extends Activity {
  public static final String DETAIL_TYPE = "detail-type";
  public static final String DETAIL_DATE = "detail-date";
  private int mType;
  private int mDate;
  private ListView mDetailList;
  
  public static void startSelf(Context context, int type, int day) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(DETAIL_TYPE, type);
    intent.putExtra(DETAIL_DATE, day);
    context.startActivity(intent);
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    
    //get type
    mType = getIntent().getIntExtra(DETAIL_TYPE, Utils.TYPE_SHIT);
    mDate = getIntent().getIntExtra(DETAIL_DATE, 0);
    
    //action bar setting
    getActionBar().setDisplayHomeAsUpEnabled(true);
    
    //list view setting
    mDetailList = (ListView)findViewById(R.id.show_detail);
    initDetailListAdapter();
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case android.R.id.home: {
        finish();
        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }
  
  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
  
  private void initDetailListAdapter() {
    Cursor c;
    if (mDate == 0) {
      c =
          getContentResolver().query(
              TangContentProvider.TANG_CONTENT_URI,
              null,
              DBHelper.CLOUMN_TYPE + "=?" + " AND " + DBHelper.CLOUMN_TIME
                  + " > datetime('now','localtime','start of day')",
              new String[] {Integer.toString(mType)}, null);

    } else {
      c =
          getContentResolver().query(
              TangContentProvider.TANG_CONTENT_URI,
              null,
              DBHelper.CLOUMN_TYPE + "=?" + " AND " + DBHelper.CLOUMN_TIME
                  + " < datetime('now','localtime','start of day', '-"
                  + Integer.toString(mDate - 1) + " day')" + " AND " + DBHelper.CLOUMN_TIME
                  + " > datetime('now','localtime','start of day', '-" + Integer.toString(mDate)
                  + " day')", new String[] {Integer.toString(mType)}, null);
    }
    if (c != null && c.moveToNext()) {
      SimpleCursorAdapter adapter =
          new SimpleCursorAdapter(getApplicationContext(), R.layout.detail_list_item, c,
              new String[] {DBHelper.CLOUMN_ID, DBHelper.CLOUMN_TIME}, new int[] {
                  R.id.db_detail_id, R.id.db_detail_time});
      mDetailList.setAdapter(adapter);
    }
  }
}
