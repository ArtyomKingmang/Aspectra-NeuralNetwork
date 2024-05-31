package com.kingmang.aspectra.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Keywords {
  
  private File nounFile;
  private File verbFile;
  @Getter
  @Setter
  private ArrayList<String> nouns;
  @Getter
  @Setter
  private ArrayList<String> verbs;
  
  public Keywords() {
    nounFile = new File("nouns.txt");
    verbFile = new File("verbs.txt");
    nouns = new ArrayList<>();
    verbs = new ArrayList<>();
    uploadText();
  }
  
  private void uploadText() {
    String thisLine = "";
    try
        {
            BufferedReader br = new BufferedReader( new FileReader( nounFile ) );
      while( (thisLine = br.readLine()) != null ) {
        nouns.add( thisLine );
      }
      br = new BufferedReader( new FileReader( verbFile ) );
      while( (thisLine = br.readLine()) != null ) {
        verbs.add( thisLine );
      }
      br.close();
    }
    catch( IOException ex ) {
      ex.printStackTrace();
    }
  }
  
  public ArrayList<String> findKeys2( String sentence ) throws Exception {
    Log.append( "Finding keys to sentence: [" + sentence + "]" );
    String[] words = sentence.split(" ");

    ArrayList<String> nv = new ArrayList<>();
    for( int i = 0; i < words.length; i++ ) {
      String wordCopy = words[i];
      wordCopy = wordCopy.replaceAll( "[^A-Za-z]", "" );
      boolean noun = isNoun( wordCopy, i == 0 );
      boolean verb = isVerb( wordCopy );
      if( noun && verb ) {
        nv.add( wordCopy );
      }
      else {
        if( noun || verb ) {
          nv.add( wordCopy );
        }
        else {
          continue;
        }
      }
    }
    return nv;
  }
  
  private boolean isNoun( String test, boolean isFirstWord ) {
    for( String noun: nouns ) {
      if( test.toLowerCase().equals(noun) ) {
        return true;
      }
    }
    if( Character.isUpperCase( test.charAt(0) ) && !isFirstWord ) {
      return true;
    }
    return false;
  }

  private boolean isVerb( String test ) {
    for( String verb: verbs ) {
      if( test.toLowerCase().equals(verb) ) {
        return true;
      }
    }
    return false;
  }

  
  public ArrayList<String> findKeys( String sentence ) throws Exception {
    Log.append( "Finding keys to sentence: [" + sentence + "]" );
    String[] words = sentence.split(" ");
    ArrayList<String> nounList = new ArrayList<>();
    for (String s : words) {
      String wordCopy = s;
      wordCopy = wordCopy.replaceAll("[^A-Za-z]", "");
      for (String noun : nouns) {
        if (wordCopy.equals(noun)) {
          String word = s.replaceAll("[^A-Za-z']", "").toLowerCase();
          nounList.add(word);
          nounList.add(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        }
      }
    }
    return nounList;
  }

}