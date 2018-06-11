package com.leiholmes.androiddevelopproject.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description:   服务端
 * author         xulei
 * Date           2018/6/11 15:43
 */
public class BookManagerService extends Service {
    private static final String TAG = "TestAIDLService";
    private CopyOnWriteArrayList mBookList = new CopyOnWriteArrayList(); //支持并发读写
    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android从入门到放弃"));
        mBookList.add(new Book(2, "Android从放弃到治疗"));
    }
}
