package com.leiholmes.androidipcdevelop.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.leiholmes.androidipcdevelop.R;
import com.leiholmes.androidipcdevelop.aidl.ICompute;
import com.leiholmes.androidipcdevelop.aidl.ISecurityCenter;

/**
 * Description:
 * author         xulei
 * Date           2018/7/5 15:59
 */
public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "TestBinderPoolClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstanse(BinderPoolActivity.this);
 
                Log.e(TAG, "binderPoolClient: " + "-----SecurityCenter-----");
                IBinder securityCenterImpl = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
                ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityCenterImpl);
                String msg1 = "你好，Binder池，我要SecurityCenter";
                Log.e(TAG, "binderPoolClient: " + msg1);
                try {
                    String password = securityCenter.encrypt(msg1);
                    Log.e(TAG, "binderPoolClient: encrypt: " + password);
                    Log.e(TAG, "binderPoolClient: decrypy: " + securityCenter.decrypy(password));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "binderPoolClient: " + "-----Compute-----");
                IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
                ICompute compute = ComputeImpl.asInterface(computeBinder);
                String msg2 = "你好，Binder池，我要Compute";
                Log.e(TAG, "binderPoolClient: " + msg2);
                try {
                    Log.e(TAG, "binderPoolClient: add: " + "2+6=" + compute.add(2, 6));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
