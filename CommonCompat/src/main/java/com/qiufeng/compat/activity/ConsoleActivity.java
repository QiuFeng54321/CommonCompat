package com.qiufeng.compat.activity;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.annotation.*;
import com.qiufeng.compat.listener.*;
import java.io.*;
import android.util.*;

public class ConsoleActivity extends TemplateActivity
{
	@ViewID(R.id.scr)
	public ScrollView scroll;
	@ViewID(R.id.outs)
	public LinearLayout output;
	@ViewID(R.id.consoleInput)
	public EditText input;
	@ViewID(R.id.exec)
	public Button exec;
	public Console console;
	public OnLogListener LOG_LISTENER_CONSOLE=new OnLogListener(){
		public void log(String str){
			console.log(str);
			Log.i("Console",str);
		}
		public void err(String str,Throwable t){
			console.error(str);
			ByteArrayOutputStream sw=new ByteArrayOutputStream();
			PrintWriter w=new PrintWriter(sw);
			t.printStackTrace(w);
			console.error(new String(sw.toByteArray()));
			t.printStackTrace();
		}
		public void warn(String str){
			console.warn(str);
			Log.w("Console",str);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setQuitWhenDoubleBackPressEnabled(false);
		setContentView(R.layout.console);
		ViewGetter.initPublicViews(this,this);
		console=new Console(this);
	}
	public void scrollToBottom(){
		Handler h=new Handler();
		h.post(new Runnable(){
			public void run(){
				scroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}
	public void exec(View v){
		String str=input.getText().toString();
		if(str!=null){
			console.log(str);
		}
	}
	public class Console{
		public ConsoleActivity cx;

		public Console(ConsoleActivity cx){
			this.cx = cx;
		}
		public void custom(String str,int color,int size){
			TextView tv=new TextView(cx);
			tv.setText(str);
			tv.setTextColor(color);
			tv.setTextSize(size);
			tv.setTypeface(Typeface.MONOSPACE);
			cx.output.addView(tv);
			scrollToBottom();
		}
		public void custom(String str,int color){
			custom(str,color,20);
		}
		public void log(String str){
			custom(str,Color.WHITE);
		}
		public void warn(String str){
			custom(str,Color.YELLOW);
		}
		public void error(String str){
			custom(str,Color.RED);
		}
		public void info(String str){
			custom(str,Color.BLUE);
		}
	}
}
