package com.leiholmes.androidipcdevelop.binderpool;

import android.os.RemoteException;

import com.leiholmes.androidipcdevelop.aidl.ICompute;

/**
 * Description:   提供计算加法的功能
 * author         xulei
 * Date           2018/7/5
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
