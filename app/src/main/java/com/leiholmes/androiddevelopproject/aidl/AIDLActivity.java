package com.leiholmes.androiddevelopproject.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leiholmes.androiddevelopproject.R;

import java.util.List;

/**
 * Description:   客户端
 * author         xulei
 * Date           2018/6/11 15:42
 */
public class AIDLActivity extends AppCompatActivity {
    private static final String TAG = "TestAIDLClient";
    //与服务端Service连接监听
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.i(TAG, "Get book list：" + bookList.toString());
                Book newBook = new Book(3, "Android你想不想来");
                bookManager.addBook(newBook);
                List<Book> newBookList = bookManager.getBookList();
                Log.i(TAG, "Get new book list：" + newBookList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

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
        unbindService(mConnection);
        super.onDestroy();
    }
}
