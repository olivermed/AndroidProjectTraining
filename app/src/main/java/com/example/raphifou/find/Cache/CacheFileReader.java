package com.example.raphifou.find.Cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by oliviermedec on 13/05/2017.
 */

public class CacheFileReader {
    private String FILENAME;
    private  String sCurrentLine;

    public String read(String filename) {

        FILENAME = filename;
        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            br = new BufferedReader(new FileReader(FILENAME));

            String str = "";
            while ((str = br.readLine()) != null) {
                sCurrentLine = str;
            }
        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {

                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
        return sCurrentLine;
    }
}
