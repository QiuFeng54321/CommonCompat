package com.qiufeng.compat.view;
import android.app.*;
import android.os.*;
import com.qiufeng.compat.*;
import android.util.*;
import android.content.*;
import android.view.*;

public abstract class AdvancedTabListener implements ActionBar.TabListener
{
	public abstract Fragment getFragment();
	public Activity getContext(){
		return Const.currentActivity;
	}
	public abstract int getView()
	@Override
	public void onTabSelected(ActionBar.Tab p1, FragmentTransaction p2){
		// TODO: Implement this method
		Log.i("TabListener",p1.getText()+" is selected.");
		Fragment frag = getFragment();

		int index = p1.getPosition() + 1;

		Bundle bundle = new Bundle();

		bundle.putInt("key", index);

		frag.setArguments(bundle);

		FragmentTransaction action = getContext().getFragmentManager()
			.beginTransaction();

		action.replace(getView(), frag);

		action.commit();
	}

	@Override
	public void onTabReselected(ActionBar.Tab p1, FragmentTransaction p2){
		// TODO: Implement this method
		Log.i("TabListener",p1.getText()+" is reselected.");
	}

	@Override
	public void onTabUnselected(ActionBar.Tab p1, FragmentTransaction p2){
		// TODO: Implement this method
		Log.i("TabListener",p1.getText()+" is unselected.");
	}
}
