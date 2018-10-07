package com.qiufeng.compat.bmob;

import android.util.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import java.util.*;

public class BmobUtils
{
	//public static List<BmobUser> users;
	public static void getUsers(OnBmobCompleteListener cl){
		final OnBmobCompleteListener clgu=cl;
		BmobQuery<BmobUser> query=new BmobQuery<BmobUser>();
		query.findObjects(new FindListener<BmobUser>(){
			public void onStart(){
				Log.i("usr","started.");
			}
			public void done(List<BmobUser> usrs,BmobException e){
				if(clgu!=null)
					clgu.onComplete(usrs,e==null,e);
			}
		});
	}
	public static void isUserExist(String usr,OnBmobCompleteListener cl){
		final OnBmobCompleteListener clue=cl;
		BmobQuery<BmobUser> query=new BmobQuery<BmobUser>("_User");
		query.addWhereEqualTo("username",usr);
		query.findObjects(new FindListener<BmobUser>(){
			public void done(List<BmobUser> u,BmobException e){
				if(clue!=null)
					clue.onComplete(u,e==null,e);
			}
		});
	}
	public static void registerUser(String username,String password,String email,OnBmobCompleteListener cl){
		final OnBmobCompleteListener clru=cl;
		BmobUser usr=new BmobUser();
		usr.setUsername(username);
		usr.setPassword(password);
		usr.setEmail(email);
		usr.signUp(new SaveListener<BmobUser>(){
			public void done(BmobUser obj,BmobException e){
				if(clru!=null)
					clru.onComplete(obj,e==null,e);
			}
		});
	}
	public static BmobUser loginUser(String username,String password,String email,OnBmobCompleteListener cl){
		final OnBmobCompleteListener cllu=cl;
		BmobUser usr=new BmobUser();
		usr.setUsername(username);
		usr.setPassword(password);
		usr.setEmail(email);
		usr.login(new SaveListener<BmobUser>(){
			public void done(BmobUser obj,BmobException e){
				if(cllu!=null)
					cllu.onComplete(obj,e==null,e);
			}
		});
		return usr;
	}
	public static void save(BmobObject obj,OnBmobCompleteListener cl){
		final OnBmobCompleteListener cls=cl;
		obj.save(new SaveListener<String>(){
			public void done(String str,BmobException e){
				if(cls!=null){
					cls.onComplete(str,e==null,e);
				}
			}
		});
	}
}
