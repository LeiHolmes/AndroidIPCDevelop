package com.leiholmes.androidipcdevelop.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Description:
 * author         xulei
 * Date           2018/6/6
 */
public class PetManagerImpl extends Binder implements IPetManager {
    public PetManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    /**
     * Cast an IBinder object into an com.leiholmes.androiddevelopproject.aidl.IBookManager interface,
     * generating a proxy if needed.
     */
    public static IPetManager asInterface(IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof com.leiholmes.androidipcdevelop.aidl.IBookManager))) {
            return ((IPetManager) iin);
        }
        return new PetManagerImpl.Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getPetList: {
                data.enforceInterface(DESCRIPTOR);
                List<Pet> _result = this.getPetList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addPet: {
                data.enforceInterface(DESCRIPTOR);
                Pet _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Pet.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addPet(_arg0);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<Pet> getPetList() throws RemoteException {
        return null;
    }

    @Override
    public void addPet(Pet pet) throws RemoteException {

    }

    private static class Proxy implements IPetManager {
        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<Pet> getPetList() throws RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<Pet> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getPetList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Pet.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addPet(Pet pet) throws RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((pet != null)) {
                    _data.writeInt(1);
                    pet.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addPet, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }
}
