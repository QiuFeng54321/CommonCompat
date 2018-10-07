package com.qiufeng.compat.listener;

import android.util.*;

public interface OnLogListener{
	public static OnLogListener DEFAULT=new OnLogListener(){
		@Override
		public void log(String str){
			// TODO: Implement this method
			Log.i("DebugProxy",str);
		}

		@Override
		public void err(String str, Throwable e){
			// TODO: Implement this method
			Log.wtf("DebugProxy",str,e);
		}

		@Override
		public void warn(String str){
			// TODO: Implement this method
			Log.w("DebugProxy",str);
		}

		
	};
	public static OnLogListener NONE=new OnLogListener(){

		@Override
		public void log(String str){
			// TODO: Implement this method
		}

		@Override
		public void warn(String str){
			// TODO: Implement this method
		}

		@Override
		public void err(String str, Throwable e){
			// TODO: Implement this method
		}



		
	};
	
	public void log(String str);
	public void err(String str,Throwable e);
	public void warn(String str);
}
