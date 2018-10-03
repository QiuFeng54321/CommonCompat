package com.qiufeng.compat.annotation;
import android.app.*;
import android.util.*;
import android.view.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import android.content.*;

public class ViewGetter
{
	public static void initViews(Object a,Activity ctx){
		Field[] fs=a.getClass().getDeclaredFields();
		for(Field f : fs){
			try{
				Log.i("ViewGetter","viewing "+f.getName());
				f.setAccessible(true);
				if(View.class.isAssignableFrom(f.getType())){
					if(!(f.isAnnotationPresent(ViewID.class))){
						f.setAccessible(false);
						Log.i("ViewGetter","missed "+f.getName());
						continue;
					}
					Annotation anno=f.getAnnotation(ViewID.class);
					int id=((ViewID)anno).value();
					View v=ctx.findViewById(id);
					f.set(a,v );
					Log.i("ViewGetter","set view "+f.getName()+" to ID "+id+",Object="+ctx.findViewById(id));
				}else{
					Log.i("ViewGetter",f.getName()+" is not a view.");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void initViews(Object a,View v){
		Field[] fs=a.getClass().getDeclaredFields();
		for(Field f : fs){
			try{
				Log.i("ViewGetter","viewing "+f.getName());
				f.setAccessible(true);
				if(View.class.isAssignableFrom(f.getType())){
					if(!(f.isAnnotationPresent(ViewID.class))){
						f.setAccessible(false);
						Log.i("ViewGetter","missed "+f.getName());
						continue;
					}
					Annotation anno=f.getAnnotation(ViewID.class);
					int id=((ViewID)anno).value();
					View view= v.findViewById(id);
					f.set(a,view);
					Log.i("ViewGetter","set view "+f.getName()+" to ID "+id+",Object="+view);
				}else{
					Log.i("ViewGetter",f.getName()+" is not a view.");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
