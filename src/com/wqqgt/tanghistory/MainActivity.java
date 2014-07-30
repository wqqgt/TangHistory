package com.wqqgt.tanghistory;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
  private ListView mShowHistoryList;
  private HistoryListAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn_shit).setOnClickListener(this);
    findViewById(R.id.btn_pee).setOnClickListener(this);
    findViewById(R.id.btn_sleep).setOnClickListener(this);
    findViewById(R.id.btn_drink).setOnClickListener(this);

    SpinnerAdapter mSpinnerAdapter = new DaySpinnerAdapter(getApplicationContext());
    getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    getActionBar().setListNavigationCallbacks(mSpinnerAdapter, new OnNavigationListener() {

      @Override
      public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        changeDateHistory(itemPosition);
        return false;
      }
    });

    mShowHistoryList = (ListView) findViewById(R.id.show_today);
    mAdapter = new HistoryListAdapter(this);
    mShowHistoryList.setAdapter(mAdapter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @Override
  public void onClick(View v) {
    int type = 0;
    if (v.getId() == R.id.btn_shit) {
      type = Utils.TYPE_SHIT;
    } else if (v.getId() == R.id.btn_pee) {
      type = Utils.TYPE_PEE;
    } else if (v.getId() == R.id.btn_sleep) {
      type = Utils.TYPE_SLEEP;
    } else if (v.getId() == R.id.btn_drink) {
      type = Utils.TYPE_DRINK;
    }
    ContentValues value = new ContentValues();
    value.put(DBHelper.CLOUMN_TYPE, type);
    getContentResolver().insert(TangContentProvider.TANG_CONTENT_URI, value);
    showAddMsg(type);
  }

  public void showAddMsg(int type) {
    if (type < Utils.TYPE_BEGIN || type > Utils.TYPE_END) {
      Toast.makeText(getApplicationContext(), "type error", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(getApplicationContext(), "+1", Toast.LENGTH_SHORT).show();
    }
  }

  public void changeDateHistory(int pos) {
    ArrayList<HistoryListAdapter.HistoryItem> adapterData = 
        new ArrayList<HistoryListAdapter.HistoryItem>();
    for (int i = 0; i < Utils.typeArray.length; i++) {
      int curType = Utils.typeArray[i];
      Cursor c;
      if (pos == 0) {
        c =
            getContentResolver().query(
                TangContentProvider.TANG_CONTENT_URI,
                null,
                DBHelper.CLOUMN_TYPE + "=?" + " AND " + DBHelper.CLOUMN_TIME
                    + " > datetime('now','localtime','start of day')",
                new String[] {Integer.toString(curType)}, null);

      } else {
        c =
            getContentResolver().query(
                TangContentProvider.TANG_CONTENT_URI,
                null,
                DBHelper.CLOUMN_TYPE + "=?" + " AND " + DBHelper.CLOUMN_TIME
                    + " < datetime('now','localtime','start of day', '-"
                    + Integer.toString(pos - 1) + " day')" + " AND " + DBHelper.CLOUMN_TIME
                    + " > datetime('now','localtime','start of day', '-" + Integer.toString(pos)
                    + " day')", new String[] {Integer.toString(curType)}, null);
      }
      if (c != null && c.moveToNext()) {
        adapterData.add(new HistoryListAdapter.HistoryItem(
            Utils.getTypeName(this,curType) + " (" + c.getCount() + ") ",curType));
        c.close();
      }
    }
    mAdapter.setData(adapterData, pos);
  }

}
