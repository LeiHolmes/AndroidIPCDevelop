package com.leiholmes.androidipcdevelop.binderpool;

import android.os.RemoteException;

import com.leiholmes.androidipcdevelop.aidl.ISecurityCenter;

/**
 * Description:   提供加解密功能
 * author         xulei
 * Date           2018/7/5
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypy(String password) throws RemoteException {
        return encrypt(password);
    }
}
