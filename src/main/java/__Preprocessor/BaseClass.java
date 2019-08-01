package __Preprocessor;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

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
    protected boolean verificat_compliance_const_expr(final char current){ // метод проверки на соответствие констаннтного выражения
       // char charaster[] = {'_'};
        if(Character.isLetter(current) == true)return true;
        if(Character.isDigit(current) == true)return true;
        if(current == '_')return true;
        if(current == '@')return true;
        return false;
    }

    /*
    private List<String> str;
    private Map<String, String> map = new HashMap<String, String>();
    private ArrayList<String> list;
    Pattern pattern;
    Matcher matcher;
    StringBuilder buffer;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    Character character;
    */
}
