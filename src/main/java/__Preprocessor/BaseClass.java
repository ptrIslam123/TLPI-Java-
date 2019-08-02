package __Preprocessor;

import java.io.*;
/*
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
*/

public class BaseClass {

    private int pos, len;
    private String text;

    protected String readFile(final String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("File: " + filename + " not found!");
            file.createNewFile();
            return "";
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        int len = fileInputStream.available();
        byte data[] = new byte[len];
        fileInputStream.read(data);
        String text = new String(data);
        return text;
    }

   protected void setText(final String data){
        text = data;
        len = data.length();
    }
   protected char getCh(final int position_relative){
        int position = pos + position_relative;
        if(position >= len)return '\0';
        return text.charAt(position);
   }
   protected int getLen(){ return len;}
   protected int getPos(){ return pos; }
   protected void setPos(final int _position){ pos = _position; }
   protected void next(final int next_pos){ pos += next_pos; }
   protected void next(){pos++;}


}
