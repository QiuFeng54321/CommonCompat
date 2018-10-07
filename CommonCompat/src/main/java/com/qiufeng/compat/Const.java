package com.qiufeng.compat;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import com.qiufeng.compat.activity.*;
import com.qiufeng.compat.listener.*;
import com.qiufeng.compat.service.*;
import java.util.*;

public class Const
{
	public static TemplateActivity currentActivity;
	public static boolean isLogOn=BuildConfig.DEBUG;
	public static OnLogListener currentLogListener=OnLogListener.DEFAULT;
	public static void startDaemonService(Context mAct) {
        if (Build.VERSION.SDK_INT > 20 && Build.VERSION.SDK_INT < 24) {//JobService只支持21,22,23 也就是5.0  5.1  6.0的系统
            if (!isServiceRunning("" + DaemonService.class.getName(), mAct)) {
                Intent intent1 = new Intent(mAct, DaemonJobService.class);
                intent1.setAction("myaction");
                mAct.startService(intent1);
            } else {
                currentLogListener.warn( "Daemon service already Running on Android version 5.1(+)");
            }
        } else {
            if (!isServiceRunning(DaemonService.class.getName(), mAct)) {
                Intent intent1 = new Intent(mAct, DaemonService.class);
                mAct.startService(intent1);
            } else {
                currentLogListener.warn( "Daemon service already Running on Android version 5.1(-)");
            }
        }
    }

    /**
     * 判断服务是否处于运行状态.
     *
     * @param servicename
     * @param context
     * @return
     */
    public static boolean isServiceRunning(String servicename, Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : infos) {
            if (servicename.equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
	
}
