package com.qiufeng.compat.annotation;
import android.app.*;
import android.util.*;
import android.view.*;
import com.qiufeng.compat.*;
import com.qiufeng.compat.listener.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

public class ViewGetter
{
	//public static ArrayBlockingQueue queue=new ArrayBlockingQueue(Integer.MAX_VALUE);
	public static Field[] getDeclaredFields(Object obj){
		return obj.getClass().getDeclaredFields();
	}
	public static Field[] getPublicFields(Object obj){
		return obj.getClass().getFields();
	}
	public static void initialise(Field[] fs,Object obj,Object v,int t){
		for(Field f : fs){
			try{
				if(Const.isLogOn)Const.currentLogListener.log("viewing "+f.getName());
				f.setAccessible(true);
				if(View.class.isAssignableFrom(f.getType())){
					if(!(f.isAnnotationPresent(ViewID.class))){
						f.setAccessible(false);
						if(Const.isLogOn)Const.currentLogListener.log("missed "+f.getName());
						continue;
					}
					Annotation anno=f.getAnnotation(ViewID.class);
					int id=((ViewID)anno).value();
					VAHandler.handle(f,obj,v,t,id);
					if(Const.isLogOn)Const.currentLogListener.log("set view "+f.getName()+" to ID "+id+",Object="+v);
				}else{
					if(Const.isLogOn)Const.currentLogListener.log(f.getName()+" is not a view.");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static final int HANDLE_TYPE_VIEW=0;
	public static final int HANDLE_TYPE_ACTIVITY=1;
	public static class VAHandler{
		public static void handle(Field f,Object obj,Object v,int t,int id){
			switch(t){
				case HANDLE_TYPE_VIEW:
					View view= ((View)v).findViewById(id);
					try{
						f.set(obj, view);
					}catch(Exception e){
						e.printStackTrace();
					}
					break;
				case HANDLE_TYPE_ACTIVITY:
					View a=((Activity)v).findViewById(id);
					try{
						f.set(obj, a);
					}catch(Exception e){
						e.printStackTrace();
					}
					break;
			}
		}
	}
	public static void initDeclaredViews(Object a,Activity ctx){
		Log.w("ViewGetter","initialising objects Of "+a.getClass());
		
		//Field[] fs;
		/*for(Field f : getDeclaredFields(a)){
			try{
				if(Const.isLogOn)Const.currentLogListener.log("viewing "+f.getName());
				f.setAccessible(true);
				if(View.class.isAssignableFrom(f.getType())){
					if(!(f.isAnnotationPresent(ViewID.class))){
						f.setAccessible(false);
						if(Const.isLogOn)Const.currentLogListener.log("missed "+f.getName());
						continue;
					}
					Annotation anno=f.getAnnotation(ViewID.class);
					int id=((ViewID)anno).value();
					
					if(Const.isLogOn)Const.currentLogListener.log("set view "+f.getName()+" to ID "+id+",Object="+ctx.findViewById(id));
				}else{
					if(Const.isLogOn)Const.currentLogListener.log(f.getName()+" is not a view.");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
		initialise(getDeclaredFields(a),a,ctx,HANDLE_TYPE_ACTIVITY);
		Log.w("ViewGetter","View initialised.");
	}
	public static void initDeclaredViews(Object a,View v){
		Log.w("ViewGetter","initialising objects Of "+a.getClass());
		//Field[] fs;
		initialise(getDeclaredFields(a),a,v,HANDLE_TYPE_VIEW);
		Log.w("ViewGetter","Object initialised.");
	}
	public static void initPublicViews(Object a,View v){
		
		Log.w("ViewGetter","initialising objects Of "+a.getClass());
		initialise(getPublicFields(a),a,v,HANDLE_TYPE_VIEW);
		Log.w("ViewGetter","Object initialised.");
		
	}
	public static void initPublicViews(Object a,Activity v){

		Log.w("ViewGetter","initialising objects Of "+a.getClass());
		initialise(getPublicFields(a),a,v,HANDLE_TYPE_ACTIVITY);
		Log.w("ViewGetter","Object initialised.");

	}
	/*public static enum TaskType{
		ACTIVITY,
		VIEW
	}
	private static class Task{
		public TaskType type;
		public Object obj;
		public Object from;
	}
	public static class ActivityTask extends Task{
		public ActivityTask(Object obj,Activity from){
			type=TaskType.ACTIVITY;
			this.obj=obj;
			this.from=from;
		}
		public Activity from;
	}
	public static class ViewTask extends Task{
		public ViewTask(Object obj,View v){
			type=TaskType.VIEW;
			this.obj=obj;
			this.from=v;
		}
		public View from;
	}*/
}
