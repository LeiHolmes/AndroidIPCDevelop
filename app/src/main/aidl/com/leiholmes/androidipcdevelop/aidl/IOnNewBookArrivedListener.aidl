package com.leiholmes.androidipcdevelop.aidl;

import com.leiholmes.androidipcdevelop.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
