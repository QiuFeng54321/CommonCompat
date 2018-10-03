package com.qiufeng.compat.activity;
import android.app.*;
import android.app.ActionBar.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.annotation.*;

public class AboutActivity extends TemplateActivity
{
	@ViewID(R.id.aboutTitle)
	TextView title;
	@ViewID(R.id.aboutMessage)
	TextView message;
	@ViewID(R.id.aboutMesLay)
	LinearLayout lay;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ViewGetter.initViews(this,this);
		setQuitWhenDoubleBackPressEnabled(false);
	}
	public void setAboutTitle(String s){
		title.setText(s);
	}
	public void setAboutMessage(String s){
		message.setText(s);
	}
	
}
