package com.kingmang.aspectra.utils;

import com.kingmang.aspectra.Aspectra;

import java.io.*;

public class Serializer {
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void save(Aspectra obj, String filename){
      try {
          oos = new ObjectOutputStream(new FileOutputStream(filename));
          oos.writeObject(obj);
          oos.close();
      }catch (IOException e){
          throw new RuntimeException(e);
      }

    }

    public static Object open(String filename) {
        Aspectra obj;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            obj = (Aspectra) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}


