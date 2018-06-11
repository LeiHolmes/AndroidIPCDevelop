package com.leiholmes.androiddevelopproject.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.leiholmes.androiddevelopproject.Constants;
import com.leiholmes.androiddevelopproject.R;

import org.w3c.dom.Comment;

/**
 * Description:   客户端
 * author         xulei
 * Date           2018/6/8 12:05
 */
public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "TestMessengerClient";
    private final Messenger mGtReplyMessenger = new Messenger(new MessengerHandler());
    private Messenger mMessenger;

    //与服务端Service连接监听
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            //给服务端发送消息
            Message sendMessage = Message.obtain(null, Constants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "Hello world, this is client!");
            sendMessage.setData(data);
            sendMessage.replyTo = mGtReplyMessenger;
            try {
                mMessenger.send(sendMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_SERVICE:
                    //收到服务端来的消息
                    Log.i(TAG, "Get reply from service：" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        //绑定服务端Service
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
