package com.qiufeng.compat.proxy;
import java.lang.reflect.*;
import android.util.*;
import java.util.concurrent.*;
import com.qiufeng.compat.listener.*;
import com.qiufeng.compat.*;
//import android.icu.util.*;

public class DebugProxy implements InvocationHandler
{
	public Object obj;
	public Object proxyInstance;
	//public OnLogListener log=OnLogListener.DEFAULT;

	public DebugProxy(Object obj){
		this.obj = obj;
		proxyInstance=Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
	}
	@Override
	public Object invoke(Object p1, Method p2, Object[] p3) throws Throwable{
		// TODO: Implement this method
		Const.currentLogListener.log("Method "+p2.getName()+" from class "+p2.getDeclaringClass().getName()+" started to invoke.");
		long startTime=System.nanoTime();
		try{
			p2.invoke(obj,p3);
		}catch(Throwable e){
			Const.currentLogListener.err("Method "+p2.getName()+" from class "+p2.getDeclaringClass().getName()+" occurred an error.",e);
		}
		long dif=TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-startTime);
		Const.currentLogListener.log("Method "+p2.getName()+" from class "+p2.getDeclaringClass().getName()+" successfully invoked using "+dif+"ms.");
		return null;
	}
	
}
