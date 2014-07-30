package com.wqqgt.tanghistory;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HistoryListAdapter extends BaseAdapter {
  
  public static class HistoryItem {
    public String mInfo;
    public int mType;
    public HistoryItem(String info, int type) {
      mInfo = info;
      mType = type;
    }
  }
  
  private ArrayList<HistoryItem> mData = new ArrayList<HistoryItem>();
  private LayoutInflater mInflater;
  private Context mAppContext;
  private int mCurrentDate;

  private class ViewHolder {
    public TextView info;
    public Button detail;
  }
  
  public HistoryListAdapter(Context context) {
    super();
    mInflater = LayoutInflater.from(context);
    mAppContext = context;
  }
  
  @Override
  public int getCount() {
    return mData.size();
  }

  @Override
  public Object getItem(int position) {
    return mData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    final int pos = position;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = mInflater.inflate(R.layout.history_list_item, parent, false);
      holder.info = (TextView)convertView.findViewById(R.id.db_info);
      holder.detail = (Button)convertView.findViewById(R.id.btn_detail);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder)convertView.getTag();
    }
    holder.info.setText(mData.get(position).mInfo);
    holder.detail.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        DetailActivity.startSelf(mAppContext, mData.get(pos).mType, mCurrentDate);
      }
    });
    return convertView;
  }
  
  public void setData(ArrayList<HistoryItem> data, int date) {
    mData = data;
    mCurrentDate = date;
    notifyDataSetChanged();
  }
}
