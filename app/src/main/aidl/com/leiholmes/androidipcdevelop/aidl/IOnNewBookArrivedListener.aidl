package com.leiholmes.androiddevelopproject.aidl;

import com.leiholmes.androiddevelopproject.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
