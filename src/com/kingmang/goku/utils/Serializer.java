package com.kingmang.goku.utils;

import com.kingmang.goku.Goku;

import java.io.*;

public class Serializer {
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void save(Goku obj, String filename){
      try {
          oos = new ObjectOutputStream(new FileOutputStream(filename));
          oos.writeObject(obj);
          oos.close();
      }catch (IOException e){
          throw new RuntimeException(e);
      }

    }

    public static Object open(String filename) {
        Goku obj;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            obj = (Goku) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}


