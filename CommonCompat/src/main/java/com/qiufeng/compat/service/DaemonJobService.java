package com.qiufeng.compat.service;


import android.annotation.*;
import android.app.job.*;
import android.content.*;
import android.os.*;
import android.util.*;
import com.qiufeng.compat.*;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DaemonJobService extends JobService {
    private int kJobId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("JOBSERVICE", "onStartJob  +++");
        String sName = DaemonService.class.getName();
        boolean isLocalServiceWork = Const.isServiceRunning(sName, this);//此处填入服务的全名
        if (!isLocalServiceWork) {
            this.startService(new Intent(this, DaemonService.class));
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("JOBSERVICE", "onStopJob  +++");
        scheduleJob(getJobInfo());
        return true;
    }

    //将任务作业发送到作业调度中去
    public void scheduleJob(JobInfo t) {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, DaemonService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //   builder.setPersisted(true);//手机重启
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        //间隔1000毫秒
        builder.setPeriodic(1000);
        return builder.build();
    }
}
