package com.leiholmes.androidipcdevelop.aidl;

interface IBinderPool {
    /*
    * 根据不同binderCode返回不同Binder对象
    */
    IBinder querBinder(int binderCode);
}
