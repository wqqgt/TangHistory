package com.wqqgt.tanghistory;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private DBHelper mDBHelper;
	private ListView mShowHistoryList;
	private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_shit).setOnClickListener(this);
        findViewById(R.id.btn_pee).setOnClickListener(this);        
        findViewById(R.id.btn_sleep).setOnClickListener(this);
        findViewById(R.id.btn_drink).setOnClickListener(this); 
        
        mShowHistoryList = (ListView)findViewById(R.id.show_today);
        mDBHelper = new DBHelper(getApplicationContext());
        
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), 
        		R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				changeListMode(itemPosition+1);
				return false;
			}
		});
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
		mDBHelper.getWritableDatabase().insert(DBHelper.TABLE_NAME, null, value);
		showAddMsg(type);
	}
	
	public void showAddMsg(int type) {
		if (type < Utils.TYPE_BEGIN || type >Utils.TYPE_END) {
			Toast.makeText(getApplicationContext(), "type error", Toast.LENGTH_SHORT).show();
		} else  {
			Toast.makeText(getApplicationContext(), "+1", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void changeListMode(int type) {
		Cursor c = mDBHelper.getReadableDatabase().query(DBHelper.TABLE_NAME, null, DBHelper.CLOUMN_TYPE+"=?", 
				new String[] {Integer.toString(type)}, null, null, DBHelper.CLOUMN_TIME);
		if (c != null && c.moveToNext()) {
			mAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.history_list_item, c,
					new String[]{DBHelper.CLOUMN_TYPE, DBHelper.CLOUMN_TIME},
					new int[] {R.id.db_type, R.id.db_time});
			mShowHistoryList.setAdapter(mAdapter);
		}
	}
    
}
