package com.leiholmes.androidipcdevelop.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Description:   Serializable序列化
 * author         xulei
 * Date           2018/6/5
 */

public class PersonSerialize implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private boolean isMale;

    public PersonSerialize(String name, int age, boolean isMale) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;
    }

    public PersonSerialize() {
    }

    /**
     * 序列化
     */
    public void serialize() {
        try {
            PersonSerialize person = new PersonSerialize("baozi", 24, true);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            outputStream.writeObject(person);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     */
    public void antiSerialize() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("cache.txt"));
            PersonSerialize person = (PersonSerialize) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
