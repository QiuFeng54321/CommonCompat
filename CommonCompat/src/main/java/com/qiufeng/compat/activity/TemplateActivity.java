package com.qiufeng.compat.activity;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.widget.*;
import cn.bmob.v3.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.verification.*;
import android.content.pm.PackageManager.*;
import android.content.pm.*;

public class TemplateActivity extends Activity{
	public TemplateActivity cx;
	public SharedPreferences preferences;
	public SharedPreferences.Editor prefEditor;
	public long lastBackPress=-1;
	public boolean quitWhenDoubleBackPressEnabled=true;
	public long doubleBackPressDuration=2000;
	public void setDoubleBackPressDuration(long doubleBackPressDuration){
		this.doubleBackPressDuration = doubleBackPressDuration;
	}

	public long getDoubleBackPressDuration(){
		return doubleBackPressDuration;
	}

	public void setQuitWhenDoubleBackPressEnabled(boolean quitWhenDoubleBackPressEnabled){
		this.quitWhenDoubleBackPressEnabled = quitWhenDoubleBackPressEnabled;
	}

	public boolean isQuitWhenDoubleBackPressEnabled(){
		return quitWhenDoubleBackPressEnabled;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		Const.currentActivity=this;
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences("pref", MODE_PRIVATE);
		prefEditor = preferences.edit();
	}
	protected void initBmob(String key){
		Bmob.initialize(this, key);
	}
	public boolean isNetworkOn(){
		ConnectivityManager s=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info=s.getActiveNetworkInfo();
		return info != null && info.isConnected() && info.isAvailable();
	}
	@Override
	public void onBackPressed(){
		// TODO: Implement this method
		if(!quitWhenDoubleBackPressEnabled){
			super.onBackPressed();
			return;
		}
		if(lastBackPress == -1 || System.currentTimeMillis() - lastBackPress >= doubleBackPressDuration){
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			lastBackPress = System.currentTimeMillis();
		}else{
			finish();
		}
	}
	public boolean checkCertificate(String expected){
		try{
			CertificateChecker cc=CertificateChecker.createFromActivity(this);
			return cc.isExpectedCertification(expected);
		}catch(PackageManager.NameNotFoundException e){
			return false;
		}
	}
	public void toast(String str){
		final String strbuf=str;
		final Context ctxbuf=this;
		runOnUiThread(new Runnable(){
			public void run(){
				Toast.makeText(ctxbuf, strbuf, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
