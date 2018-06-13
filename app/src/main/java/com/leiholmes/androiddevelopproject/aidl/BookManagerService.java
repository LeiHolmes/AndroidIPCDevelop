package com.leiholmes.androiddevelopproject.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:   服务端
 * author         xulei
 * Date           2018/6/11 15:43
 */
public class BookManagerService extends Service {
    private static final String TAG = "TestAIDLService";
    private AtomicBoolean mIsServiceDestory = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList(); //支持并发读写
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.d(TAG, "Already exists!");
            }
            Log.d(TAG, "RegisterListener size" + mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.d(TAG, "UnregisterListener succeed!");
            } else {
                Log.d(TAG, "Not found, can not unregister!");
            }
            Log.d(TAG, "UnregisterListener size:" + mListenerList.size());
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
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestory.set(true);
        super.onDestroy();
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            //后台运行，每5秒加入一本新书
            while (!mIsServiceDestory.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book NO." + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        mBookList.add(newBook);
        Log.d(TAG, "onNewBookArrived, notify listeners：" + mListenerList.size());
        for (int i = 0; i < mListenerList.size(); i++) {
            IOnNewBookArrivedListener listener = mListenerList.get(i);
            Log.d(TAG, "onNewBookArrived, notify listener：" + listener);
            listener.onNewBookArrived(newBook);
        }
    }
}
