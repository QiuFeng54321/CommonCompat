package com.qiufeng.compat.view;
import android.content.*;
import android.view.*;
import android.widget.*;
import javax.xml.datatype.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.annotation.*;
import android.app.*;

public class SnackBar extends Toast{
	@ViewID(R.id.SnackText)
	TextView snackText;
	@ViewID(R.id.SnackPositive)
	Button snackPositive;
	@ViewID(R.id.SnackNegative)
	Button snackNegative;
	@ViewID(R.id.SnackNeutral)
	Button snackNeutral;
	public SnackBar(Activity cx){
		super(cx);
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
	public static SnackBar makeSnack(Activity context, CharSequence text, int duration){
		// 根据传进来的上下文，创建一个吐司对象
		SnackBar result = new SnackBar(context);
		result.setDuration(duration);
		// 布局填充器
		LayoutInflater inflate = (LayoutInflater)
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 填充系统布局文件，转换为View对
		View v = inflate.inflate(R.layout.snack, null);
		ViewGetter.initViews(result, context);
		result.snackText.setText(text);
		result.setView(v);

		try{
			Object mTN ;
			mTN = result.getClass().getField("mTN").get(result);
			if(mTN != null){
				Object mParams = mTN.getClass().getField("mParams").get(mTN);
				if(mParams != null && mParams instanceof WindowManager.LayoutParams){
					WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
					params.windowAnimations = R.style.SnackAnimation;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		// 返回处理过的吐司对象
		return result;
	}

	@Override
	public void setText(CharSequence s){
		// TODO: Implement this method
		snackText.setText(s);
	}
}
