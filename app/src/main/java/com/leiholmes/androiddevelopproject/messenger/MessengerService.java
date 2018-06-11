package com.leiholmes.androiddevelopproject.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.leiholmes.androiddevelopproject.Constants;

/**
 * Description:   服务端
 * author         xulei
 * Date           2018/6/8 11:28
 */
public class MessengerService extends Service {
    private static final String TAG = "TestMessengerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_CLIENT:
                    //收到客户端来的消息
                    Log.i(TAG, "Receive msg from client：" + msg.getData().getString("msg"));
                    //给客户端回复消息
                    Messenger clientMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain(null, Constants.MSG_FROM_SERVICE);
                    Bundle replyData = new Bundle();
                    replyData.putString("reply", "OK! I'v got your message!");
                    replyMessage.setData(replyData);
                    try {
                        clientMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
