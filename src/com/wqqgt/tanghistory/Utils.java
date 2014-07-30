package com.wqqgt.tanghistory;

import android.content.Context;

public class Utils {
	
    public static final int TYPE_BEGIN = 1;
	public static final int TYPE_SHIT = 1;
	public static final int TYPE_PEE = 2;
	public static final int TYPE_SLEEP = 3;
	public static final int TYPE_DRINK = 4;
	public static final int TYPE_END = 4;
	
	public static final int[] typeArray = {
	  TYPE_SHIT, TYPE_PEE,TYPE_SLEEP,TYPE_DRINK
	};
	
    public static final String getTypeName(Context context, int type) {
      String name= new String();
      switch(type) {
        case Utils.TYPE_SHIT:{
          name = context.getResources().getString(R.string.title_shit);
          break;
        }
        case Utils.TYPE_PEE:{
          name = context.getResources().getString(R.string.title_pee);
          break;
        }
        case Utils.TYPE_SLEEP:{
          name = context.getResources().getString(R.string.title_sleep);
          break;
        }
        case Utils.TYPE_DRINK:{
          name = context.getResources().getString(R.string.title_drink);
          break;
        }
        default:
          break;
      }
      return name;
    }
}
