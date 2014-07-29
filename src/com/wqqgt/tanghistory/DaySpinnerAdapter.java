package com.wqqgt.tanghistory;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DaySpinnerAdapter extends BaseAdapter {
  private ArrayList<String> mAdapterData = new ArrayList<String>();
  private LayoutInflater mInflater;

  private class ViewHolder {
    public TextView dayView;
  }
  public DaySpinnerAdapter(Context context) {
    super();
    buildDateString();
    mInflater = LayoutInflater.from(context);
  }
  
  @Override
  public int getCount() {
    return mAdapterData.size();
  }

  @Override
  public Object getItem(int position) {
    return mAdapterData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = mInflater.inflate(R.layout.spinner_list_item, parent, false);
      holder.dayView = (TextView)convertView.findViewById(R.id.day_item);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder)convertView.getTag();
    }
    holder.dayView.setText(mAdapterData.get(position));
    return convertView;
  }
  
  public void buildDateString() {
    mAdapterData.add("today");
    mAdapterData.add("yesterday");
    mAdapterData.add("yesterday-1");
    mAdapterData.add("yesterday-2");
    mAdapterData.add("yesterday-3");
  }

}
