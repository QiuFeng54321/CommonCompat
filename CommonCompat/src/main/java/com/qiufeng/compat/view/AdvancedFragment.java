package com.qiufeng.compat.view;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;

public class AdvancedFragment extends Fragment
{
	public int getPosition(){
		return getArguments().getInt("key");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return container;
	}
}
