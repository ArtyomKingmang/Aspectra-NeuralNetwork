package com.kingmang.aspectra.utils;

import com.kingmang.aspectra.Markov;

import java.util.ArrayList;

public class Response {
    public static String response(String input, Markov markov) throws Exception {
        Keywords kf = new Keywords();
        ArrayList<String> keys = kf.findKeys(input);
        Log.append( "KEYS: " + keys );
        String[][] responses = new String[keys.size()][10];
        for( int j = 0; j < keys.size(); j++ ) {
            for( int i = 0; i < 10; i++ ) {
                String newSentence = markov.generateSentence(keys.get(j));
                if( newSentence != null ) {
                    responses[j][i] = newSentence;
                }
                else {
                    responses[j][i] = "";
                }
            }
        }
        responses = new String[keys.size()][10];
        for( int j = 0; j < keys.size(); j++ ) {
            for( int i = 0; i < 10; i++ ) {
                String newSentence = markov.generateSentence(keys.get(j));
                if( newSentence != null ) {
                    responses[j][i] = newSentence;
                }
                else {
                    responses[j][i] = "";
                }
            }
        }

        for( int j = 0; j < keys.size(); j++ ) {
            for( int i = 0; i < 10; i++ ) {
                Log.append(responses[j][i]);
            }
        }

        int maxKeyMatches = 0;
        String bestSentence = "";
        for( int j = 0; j < keys.size(); j++ ) {
            for( int i = 0; i < 10; i++ ) {
                int tempKeyMatches = 0;
                for( String key: keys ) {
                    if( responses[j][i].contains(key) ) {
                        tempKeyMatches++;
                    }
                }
                if( tempKeyMatches > maxKeyMatches ) {
                    maxKeyMatches = tempKeyMatches;
                    bestSentence = responses[j][i];
                }
            }
        }
        return bestSentence.equals("") ? "..." : bestSentence;
    }
}
