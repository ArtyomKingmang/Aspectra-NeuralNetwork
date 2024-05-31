package com.kingmang.aspectra;

import com.kingmang.aspectra.utils.Log;
import com.kingmang.aspectra.utils.Preprocessor;
import com.kingmang.aspectra.utils.Response;
import com.kingmang.aspectra.utils.StringFormator;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import static com.kingmang.aspectra.utils.ChangeList.getChanges;

public class Aspectra implements Serializable{
	@Getter
	@Setter
	private static Scanner input;

	private static boolean isDone;

	private static HashMap<String, String> changes;

	private static FileWriter fileWriter;

	private static Markov markov;

	private static File file;

	private static int addCount;



    public static void main( String[] args ) throws Exception {
        input = new Scanner( System.in );
        isDone = false;
        changes = getChanges();
		markov = new Markov();
		file = new File("src/data.txt");
		fileWriter = new FileWriter(file);
		markov.train(file);
		markov = new Markov();
		addCount = 0;
		file = new File("data.txt");
		markov.train(file);


        while( !isDone ) {
            System.out.print( "User: " );
            String raw = input.nextLine().toLowerCase();
			String[] leave = {
					"goodbye",
					"bye",
					"adios"
			};
			/*
			if(raw.contains("--clear")){
				fileWriter.write(Settings.INFO);
				fileWriter.close();
			}
			 */
            for(String exit: leave ) {
                if( raw.contains(exit) ) {
                    isDone = true;
                    break;
                }
            }
            if( isDone ) {
                break;
            }

			addToFile( raw );
			addCount++;
			if( addCount > 20 ) {
				markov.train(file);
				addCount = 0;
			}

            raw = Preprocessor.preprocess(raw, changes);
            String response = StringFormator.formatLongString(Response.response(raw, markov));
			System.out.println("Aspectra: " + response);

        }
    }

	public static void addToFile( String raw ) {
		try {
    		PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter("data.txt", true)));
			if( raw.lastIndexOf(".") != raw.length() - 1 || raw.lastIndexOf(".") != raw.length() - 1 || raw.lastIndexOf(".") != raw.length() - 1 ) {
				out.print(raw + ". ");
			}
			else {
    			out.print(raw + " ");
			}
    		out.close();
		} catch( IOException ex ) {

		}
	}

}
