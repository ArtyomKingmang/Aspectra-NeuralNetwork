package com.kingmang.goku.utils;

import java.util.HashMap;

public class Preprocessor {
    public static String preprocess(String test, HashMap<String, String> changeList) {
        String[] words = test.toLowerCase().split(" ");
        for( int i = 0; i < words.length; i++ ) {
            if( words[i].lastIndexOf("?") == words[i].length() - 1 || words[i].lastIndexOf("!") == words[i].length() - 1 || words[i].lastIndexOf(".") == words[i].length() - 1  ) {
                words[i] = words[i].substring(0, words[i].length() - 1);
            }
        }

        for( int i = 0; i < words.length; i++ ) {
            if(changeList.get(words[i]) != null ) {
                words[i] = changeList.get(words[i]);
            }
        }

        String ret = "";
        for( int i = 0; i < words.length; i++ ) {
            if( words[i].equals("the") || words[i].equals("a") || words[i].equals("an") ) {
                continue;
            }
            ret += (words[i] + " ");
        }
        ret = ret.trim();
        return ret;
    }
}
