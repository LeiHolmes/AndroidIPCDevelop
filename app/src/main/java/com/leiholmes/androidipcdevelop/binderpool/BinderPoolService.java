package com.leiholmes.androidipcdevelop.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Description:   BinderPool服务端
 * author         xulei
 * Date           2018/7/5 15:24
 */
public class BinderPoolService extends Service {
    private static final String TAG = "TestBinderPoolService";
    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    public BinderPoolService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
