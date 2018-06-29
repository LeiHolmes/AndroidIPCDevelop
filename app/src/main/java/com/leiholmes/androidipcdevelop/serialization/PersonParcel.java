package com.leiholmes.androidipcdevelop.serialization;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Description:   Parcelable序列化
 * author         xulei
 * Date           2018/6/6
 */

public class PersonParcel implements Parcelable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private boolean isMale;

    public PersonParcel(String name, int age, boolean isMale) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;
    }

    public PersonParcel() {
    }

    protected PersonParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
        isMale = in.readByte() != 0;
    }

    /**
     * 序列化
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeByte((byte) (isMale ? 1 : 0));
    }

    /**
     * 内容描述
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 反序列化
     */
    public static final Creator<PersonParcel> CREATOR = new Creator<PersonParcel>() {
        @Override
        public PersonParcel createFromParcel(Parcel in) {
            return new PersonParcel(in);
        }

        @Override
        public PersonParcel[] newArray(int size) {
            return new PersonParcel[size];
        }
    };
}
