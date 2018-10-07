package com.qiufeng.compat.view;
import android.content.*;
import android.view.*;
import android.widget.*;
import javax.xml.datatype.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.annotation.*;
import android.app.*;
import java.lang.reflect.*;
import android.util.*;

public class SnackBar{
	@ViewID(R.id.SnackText)
	TextView snackText;
	@ViewID(R.id.SnackPositive)
	Button snackPositive;
	@ViewID(R.id.SnackNegative)
	Button snackNegative;
	@ViewID(R.id.SnackNeutral)
	Button snackNeutral;
	Context cx;
	public Toast snack;
	public SnackBar(Context cx){
		//super(cx);
		this.cx=cx;
		//ViewGetter.initViews(this,cx);
	}

	public void setSnackText(TextView snackText){
		this.snackText = snackText;
	}

	public TextView getSnackText(){
		return snackText;
	}

	public void setSnackPositive(Button snackPositive){
		this.snackPositive = snackPositive;
	}

	public Button getSnackPositive(){
		return snackPositive;
	}

	public void setSnackNegative(Button snackNegative){
		this.snackNegative = snackNegative;
	}

	public Button getSnackNegative(){
		return snackNegative;
	}

	public void setSnackNeutral(Button snackNeutral){
		this.snackNeutral = snackNeutral;
	}

	public Button getSnackNeutral(){
		return snackNeutral;
	}
	public static SnackBar makeSnack(Context context, CharSequence text, int duration){
		// 根据传进来的上下文，创建一个吐司对象
		SnackBar s=new SnackBar(context);
		s.snack = new Toast(context);
		s.snack.setDuration(duration);
		// 布局填充器
		LayoutInflater inflate = (LayoutInflater)
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 填充系统布局文件，转换为View对
		View v = inflate.inflate(R.layout.snack, null);
		ViewGetter.initDeclaredViews(s, v);
		//s.snack.setView(v);
		s.snackText.setText(text);
		s.snack.setView(v);
		s.snack.setGravity(Gravity.BOTTOM,0,0);

		try{
			Object mTN ;
			Field f=s.snack.getClass().getDeclaredField("mTN");
			f.setAccessible(true);
			mTN=f.get(s.snack);
			
			Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
			tnNextViewField.setAccessible(true);
			tnNextViewField.set(mTN, s.snack.getView());
			if(mTN != null){
				Object mParams;
				Field ff= mTN.getClass().getDeclaredField("mParams");
				ff.setAccessible(true);
				mParams=ff.get(mTN);
				if(mParams != null && mParams instanceof WindowManager.LayoutParams){
					Log.w("SnackBar","found mParams");
					WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
					params.windowAnimations = R.style.SnackAnimation;
					ff.set(mTN,params);
					Log.w("SnackBar","set the Animation to "+params.windowAnimations);
				}else
					Log.w("SnackBar","no params");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// 返回处理过的吐司对象
		return s;
	}
	public void setText(CharSequence s){
		// TODO: Implement this method
		snackText.setText(s);
	}
}
