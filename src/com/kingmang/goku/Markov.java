package com.kingmang.goku;

import com.kingmang.goku.exceptions.WordNotFoundException;
import com.kingmang.goku.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;


public class Markov {
    public Hashtable<String, ArrayList> forwardMarkov = new Hashtable();
    public Hashtable<String, ArrayList> backwardMarkov = new Hashtable();
    public Random rnd = new Random();

    private Hashtable trainForward( File file ) {
        ArrayList<ArrayList<String>> startArrayList = new ArrayList<>();
        startArrayList.add(new ArrayList<>());
        startArrayList.add(new ArrayList<>());
        startArrayList.add(new ArrayList<>());
        forwardMarkov.put("_start", startArrayList);
        forwardMarkov.put("_end", new ArrayList<ArrayList<String>>());

        String thisLine;

        try
        {
            BufferedReader br = new BufferedReader( new FileReader( file ) );
            while ( ( thisLine = br.readLine() ) != null ) {
                String[] originalWordList = thisLine.split( " " );
                ArrayList<String> words = new ArrayList<>();
                //удаляем все пустые строки в массиве
                for( int i = 0; i < originalWordList.length; i++ ) {
                    if( !originalWordList[i].equals("") ) {
                        words.add( originalWordList[i] );
                    }
                }

                for ( int i = 0; i < words.size(); i++ ) {
                    //если первое слово
                    if (i == 0) {
                        ArrayList<ArrayList<String>> startWords = forwardMarkov.get( "_start" );
                        ArrayList<String> firstStartWords = startWords.get( 0 );
                        firstStartWords.add( words.get(i) );

                        if ( words.size() > 1 ) {
                            startWords.get( 1 ).add(words.get(i + 1));
                            if ( words.size() > 2 ) {
                                startWords.get( 2 ).add( words.get(i + 2) );
                            }
                        }
                    }
                    // если последнее слово
                    else if ( i ==words.size() - 1 ) {
                        ArrayList<String> endWords = forwardMarkov.get( "_end" );
                        endWords.add( words.get(i) );
                    }
                    else {
                        ArrayList<ArrayList<String>> followingWords = forwardMarkov.get( words.get(i) );
                        String currentWord = words.get(i);
                        String firstNextWord = words.get(i+1);
                        String secondNextWord = "";
                        String thirdNextWord = "";
                        String fourthNextWord = "";
                        String fifthNextWord = "";
                        if (i < words.size() - 2)
                        {
                            secondNextWord = words.get(i + 2);
                            if (i < words.size() - 3)
                            {
                                thirdNextWord = words.get(i+3);
                                if (i < words.size() - 4)
                                {
                                    fourthNextWord = words.get(i+4);
                                    if (i < words.size() - 5)
                                    {
                                        fifthNextWord = words.get(i+5);
                                    }
                                }
                            }
                        }

                        if (followingWords == null)
                        {
                            ArrayList<String> firstWords = new ArrayList<>();
                            ArrayList<String> secondWords = new ArrayList<>();
                            ArrayList<String> thirdWords = new ArrayList<>();
                            ArrayList<String> fourthWords = new ArrayList<>();
                            ArrayList<String> fifthWords = new ArrayList<>();
                            firstWords.add(firstNextWord);
                            secondWords.add(secondNextWord);
                            thirdWords.add(thirdNextWord);
                            fourthWords.add(fourthNextWord);
                            fifthWords.add(fifthNextWord);
                            ArrayList<ArrayList<String>> wordsToAdd = new ArrayList<>();
                            wordsToAdd.add(firstWords);
                            wordsToAdd.add(secondWords);
                            wordsToAdd.add(thirdWords);
                            wordsToAdd.add(fourthWords);
                            wordsToAdd.add(fifthWords);
                            forwardMarkov.put(currentWord, wordsToAdd);
                        }
                        else
                        {
                            followingWords.get( 0 ).add(firstNextWord);
                            followingWords.get ( 1 ).add(secondNextWord);
                            followingWords.get( 2 ).add(thirdNextWord);
                            followingWords.get ( 3 ).add(fourthNextWord);
                            followingWords.get( 4 ).add(fifthNextWord);
                        }
                    }
                }
            }
        }
        catch(Exception ignored) {}
        return forwardMarkov;
    }

    private Hashtable trainBackward( File file )
    {
        ArrayList<ArrayList<String>> endArrayList = new ArrayList<>();
        endArrayList.add(new ArrayList<>());
        endArrayList.add(new ArrayList<>());
        endArrayList.add(new ArrayList<>());
        backwardMarkov.put("_end", endArrayList);
        backwardMarkov.put("_start", new ArrayList<ArrayList<String>>());

        String thisLine;

        try
        {
            BufferedReader br = new BufferedReader( new FileReader( file ) );
            while ( ( thisLine = br.readLine() ) != null ) {
                String[] originalWordList = thisLine.split( " " );

                ArrayList<String> words = new ArrayList<>();
                for( int i = 0; i < originalWordList.length; i++ ) {
                    if( !originalWordList[i].equals("") ) {
                        words.add( originalWordList[i] );
                    }
                }

                for ( int i = 0; i < words.size(); i++ ) {
                    if ( i == 0 ) {
                        ArrayList<String> startWords = backwardMarkov.get( "_start" );
                        startWords.add( words.get(i) );
                    }

                    else if ( i == words.size() - 1 )
                    {
                        ArrayList<ArrayList<String>> endWords = backwardMarkov.get( "_end" );

                        endWords.get( 0 ).add( words.get(i) );

                        if (words.size() > 1) {
                            endWords.get( 1 ).add( words.get(i-2) );
                            if ( words.size() > 2 ) {
                                endWords.get( 2 ).add( words.get(i-3) );
                            }
                        }
                    }

                    else {
                        ArrayList<ArrayList<String>> followingWords = backwardMarkov.get( words.get(i) );

                        String currentWord = words.get(i);
                        String firstPreviousWord = words.get(i - 1);
                        String secondPreviousWord = "";
                        String thirdPreviousWord = "";
                        String fourthPreviousWord = "";
                        String fifthPreviousWord = "";
                        if (i > 1)
                        {
                            secondPreviousWord = words.get(i-2);
                            if (i > 2)
                            {
                                thirdPreviousWord = words.get(i-3);
                                if (i > 3)
                                {
                                    fourthPreviousWord = words.get(i-4);
                                    if (i > 4)
                                    {
                                        fifthPreviousWord = words.get(i-5);
                                    }
                                }
                            }
                        }

                        // если текущее слово еще не добавлено
                        if (followingWords == null)
                        {
                            ArrayList<String> firstWords = new ArrayList<>();
                            ArrayList<String> secondWords = new ArrayList<>();
                            ArrayList<String> thirdWords = new ArrayList<>();
                            ArrayList<String> fourthWords = new ArrayList<>();
                            ArrayList<String> fifthWords = new ArrayList<>();
                            firstWords.add(firstPreviousWord);
                            secondWords.add(secondPreviousWord);
                            thirdWords.add(thirdPreviousWord);
                            fourthWords.add(fourthPreviousWord);
                            fifthWords.add(fifthPreviousWord);
                            ArrayList<ArrayList<String>> wordsToAdd = new ArrayList<>();
                            wordsToAdd.add(firstWords);
                            wordsToAdd.add(secondWords);
                            wordsToAdd.add(thirdWords);
                            wordsToAdd.add(fourthWords);
                            wordsToAdd.add(fifthWords);
                            backwardMarkov.put(currentWord, wordsToAdd);
                        }
                        else
                        {
                            followingWords.get( 0 ).add(firstPreviousWord);
                            followingWords.get ( 1 ).add(secondPreviousWord);
                            followingWords.get( 2 ).add(thirdPreviousWord);
                            followingWords.get( 3 ).add(fourthPreviousWord);
                            followingWords.get( 4 ).add(fifthPreviousWord);
                        }
                    }
                }
            }
        }
        catch(Exception ignored) {}
        return backwardMarkov;
    }

    private String generateBackward(String keyword) throws WordNotFoundException
    {
        String firstLastWord;
        String secondLastWord;
        String thirdLastWord;
        String fourthLastWord;
        String fifthLastWord;
        String output = " ";
        while ((keyword.charAt(keyword.length() - 1) != '.') || (keyword.charAt(keyword.length() - 1) != '?')
                || (keyword.charAt(keyword.length() - 1) != '!'))
        {
            ArrayList<ArrayList<String>> wordsList = backwardMarkov.get(keyword);
            if ( wordsList == null )
            {
                throw new WordNotFoundException("Word not found.");
            }
            ArrayList<String> firstLastWords = wordsList.get( 0 );
            ArrayList<String> secondLastWords = wordsList.get( 1 );
            ArrayList<String> thirdLastWords = wordsList.get( 2 );
            ArrayList<String> fourthLastWords = wordsList.get( 3 );
            ArrayList<String> fifthLastWords = wordsList.get( 4 );
            int location = rnd.nextInt(firstLastWords.size());

            firstLastWord = firstLastWords.get(location);
            if ((firstLastWord.charAt(firstLastWord.length() - 1) == '.') ||
                    (firstLastWord.charAt(firstLastWord.length() - 1) == '?') ||
                    (firstLastWord.charAt(firstLastWord.length() - 1) == '!')) {
                break;
            }

            secondLastWord = secondLastWords.get(location);
            if ((secondLastWord.charAt(secondLastWord.length() - 1) == '.') ||
                    (secondLastWord.charAt(secondLastWord.length() - 1) == '?') ||
                    (secondLastWord.charAt(secondLastWord.length() - 1) == '!')) {
                output = firstLastWord + " " + output;
                break;
            }

            thirdLastWord = thirdLastWords.get(location);
            if ((thirdLastWord.charAt(thirdLastWord.length() - 1) == '.') ||
                    (thirdLastWord.charAt(thirdLastWord.length() - 1) == '?') ||
                    (thirdLastWord.charAt(thirdLastWord.length() - 1) == '!')) {
                output = secondLastWord + " " + firstLastWord + " " + output;
                break;
            }


            fourthLastWord = fourthLastWords.get(location);
            if ((fourthLastWord.charAt(fourthLastWord.length() - 1) == '.') ||
                    (fourthLastWord.charAt(fourthLastWord.length() - 1) == '?') ||
                    (fourthLastWord.charAt(fourthLastWord.length() - 1) == '!')) {
                output = thirdLastWord + " " + secondLastWord + " " + firstLastWord + " " + output;
                break;
            }

            fifthLastWord = fifthLastWords.get(location);
            if ((fifthLastWord.charAt(fifthLastWord.length() - 1) == '.') ||
                    (fifthLastWord.charAt(fifthLastWord.length() - 1) == '?') ||
                    (fifthLastWord.charAt(fifthLastWord.length() - 1) == '!')) {
                output = fourthLastWord + " " + thirdLastWord + " " + secondLastWord + " " + firstLastWord + " " + output;
                break;
            }
            output = fifthLastWord + " " + fourthLastWord + " " + thirdLastWord + " " + secondLastWord + " " + firstLastWord + " " + output;
            keyword = fifthLastWord;
        }

        return output;
    }

    private String generateForward(String keyword) throws WordNotFoundException
    {
        String firstWord;
        String secondWord;
        String thirdWord;
        String fourthWord;
        String fifthWord;
        String output = keyword + " ";
        while ((keyword.charAt(keyword.length() - 1) != '.') || (keyword.charAt(keyword.length() - 1) != '?')
                || (keyword.charAt(keyword.length() - 1) != '!'))
        {
            ArrayList<ArrayList<String>> wordsList = forwardMarkov.get(keyword);
            if ( wordsList == null )
            {
                try {
                    Log.append(keyword);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                WordNotFoundException exception = new WordNotFoundException("Word not found.");
                throw exception;
            }
            ArrayList<String> firstWords = wordsList.get( 0 );
            ArrayList<String> secondWords = wordsList.get( 1 );
            ArrayList<String> thirdWords = wordsList.get( 2 );
            ArrayList<String> fourthWords = wordsList.get( 3 );
            ArrayList<String> fifthWords = wordsList.get( 4 );
            int location = rnd.nextInt(firstWords.size());
            firstWord = firstWords.get(location);
            if ((firstWord.charAt(firstWord.length() - 1) == '.') ||
                    (firstWord.charAt(firstWord.length() - 1) == '?') ||
                    (firstWord.charAt(firstWord.length() - 1) == '!'))
            {
                output += firstWord + " ";
                //output += firstWord + " ";
                break;
            }
            secondWord = secondWords.get(location);
            if ((secondWord.charAt(secondWord.length() - 1) == '.') ||
                    (secondWord.charAt(secondWord.length() - 1) == '?') ||
                    (secondWord.charAt(secondWord.length() - 1) == '!')) {
                output += firstWord + " " + secondWord + " ";
                //output += secondWord + " ";
                break;
            }
            thirdWord = thirdWords.get(location);
            if ((thirdWord.charAt(thirdWord.length() - 1) == '.') ||
                    (thirdWord.charAt(thirdWord.length() - 1) == '?') ||
                    (thirdWord.charAt(thirdWord.length() - 1) == '!')) {
                output += firstWord + " " + secondWord + " " + thirdWord + " ";
                //output += thirdWord + " ";
                break;
            }
            fourthWord = fourthWords.get(location);
            if ((fourthWord.charAt(fourthWord.length() - 1) == '.') ||
                    (fourthWord.charAt(fourthWord.length() - 1) == '?') ||
                    (fourthWord.charAt(fourthWord.length() - 1) == '!')) {
                output += firstWord + " " + secondWord + " " + thirdWord + " " + fourthWord + " ";
                //output += fourthWord + " ";
                break;
            }
            fifthWord = fifthWords.get(location);
            if ((fifthWord.charAt(fifthWord.length() - 1) == '.') ||
                    (fifthWord.charAt(fifthWord.length() - 1) == '?') ||
                    (fifthWord.charAt(fifthWord.length() - 1) == '!')) {
                output += firstWord + " " + secondWord + " " + thirdWord + " " + fourthWord + " " + fifthWord + " ";
                break;
            }
            output += firstWord + " " + secondWord + " " + thirdWord + " " + fourthWord + " " + fifthWord + " ";
            keyword = fifthWord;
        }
        return output;
    }

    public String generateSentence(String keyword) {
        try {
            String first = generateBackward(keyword).trim();
            String second = generateForward(keyword).trim();
            return first + " " + second;
        }
        catch(WordNotFoundException ex) {
            try {
                Log.append("Word not found exception: " + keyword);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "-1";
        }
        catch(Exception ex) {
            try {
                Log.append("exception: " + keyword);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "-1";
        }
    }

    public void train(File file) {
        trainForward(file);
        trainBackward(file);
    }

    public void train(String filename) {
        trainForward(new File(filename));
        trainBackward(new File(filename));
    }


}
