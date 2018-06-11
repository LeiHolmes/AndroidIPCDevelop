package com.leiholmes.androiddevelopproject.binder;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Description:   手动实现Binder跨进程通信
 * author         xulei
 * Date           2018/6/6
 */
public interface IPetManager extends IInterface {
    static final java.lang.String DESCRIPTOR = "com.leiholmes.androiddevelopproject.binder.IPetManager";

    static final int TRANSACTION_getPetList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addPet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public List<Pet> getPetList() throws RemoteException;

    public void addPet(Pet pet) throws RemoteException;
}
