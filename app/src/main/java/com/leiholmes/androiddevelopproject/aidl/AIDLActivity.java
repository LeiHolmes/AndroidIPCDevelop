package com.leiholmes.androiddevelopproject.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leiholmes.androiddevelopproject.Constants;
import com.leiholmes.androiddevelopproject.R;

import java.util.List;

/**
 * Description:   客户端
 * author         xulei
 * Date           2018/6/11 15:42
 */
public class AIDLActivity extends AppCompatActivity {
    private static final String TAG = "TestAIDLClient";

    private IBookManager mBookManager;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "Client：Receive new book added：" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    //新书被添加监听
    private IOnNewBookArrivedListener mListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(Constants.MSG_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    //与服务端Service连接监听
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            mBookManager = bookManager;
            try {
                //获取图书列表
                List<Book> bookList = bookManager.getBookList();
                Log.i(TAG, "Client：Get book list：" + bookList.toString());
                //添加新书
                Book newBook = new Book(3, "Android你想不想来");
                bookManager.addBook(newBook);
                List<Book> newBookList = bookManager.getBookList();
                Log.i(TAG, "Client：Get new book list：" + newBookList.toString());
                //注册新书监听
                Log.i(TAG, "Client：Register listener：" + mListener);
                bookManager.registerListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            Log.e(TAG, "Client：Binder died!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //取消注册新书监听
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, "Client：Unregister listener：" + mListener);
                mBookManager.unregisterListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        //解绑服务
        unbindService(mConnection);
        super.onDestroy();
    }
}
