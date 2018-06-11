package com.leiholmes.androiddevelopproject.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:
 * author         xulei
 * Date           2018/6/6
 */
public class Pet implements Parcelable {
    public int petAge;
    public String petName;

    protected Pet(Parcel in) {
        petAge = in.readInt();
        petName = in.readString();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(petAge);
        dest.writeString(petName);
    }
}
