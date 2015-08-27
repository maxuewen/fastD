package cn.zh.Utils;

import cn.zh.fastD.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ActionBarUtils {

	public static View setAtionBar(Context context , int layout){
		android.app.ActionBar actionBar =((Activity)context).getActionBar();

		actionBar.setDisplayShowHomeEnabled( false );
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		    
		    LayoutInflater inflater =((Activity)context).getLayoutInflater();
		    View view = inflater.inflate(layout, null);
		    
		    ActionBar.LayoutParams l = new ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		    actionBar.setCustomView(view,l);
		    
		    return view;
	}
	
}
