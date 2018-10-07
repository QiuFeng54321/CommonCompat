package com.qiufeng.compat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 用于正式运行处理事件的服务
 */
public class DaemonService extends Service {
    long i = 0L;

    public void onCreate() {
        super.onCreate();
        i = 0;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    Log.e("JOBSERVICE", "WorkService 服务没有死" + (i++));
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
