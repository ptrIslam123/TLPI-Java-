package __Preprocessor;

import __Preprocessor.MACROSS.Define;
import java.io.*;
import java.util.List;

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
   protected  void skip_char(){
        while(getCh(0) != '\0'){
            if(Character.isWhitespace(getCh(0)) == false)break;
            pos++;
        }
   } /** пропуск пустых символов **/
    protected void skip_char(final char current){
        while(getCh(0) != '\0'){
            if(getCh(0) == current)break;
            pos++;
        }
    }
    protected boolean equalsExpr(StringBuilder expr1, StringBuilder expr2){
        String ex1, ex2;
        ex1 = expr1.toString();
        ex2 = expr2.toString();
        if(ex1.equalsIgnoreCase(ex2))return true;
        else return false;
    }
    protected void skip_char(final char current, final int shift){
        while(getCh(0) != '\0'){
            if(getCh(0) == current){ pos+=shift; break;}
            pos++;
        }
    }
    protected boolean mismatch(final char current){
        if(Character.isDigit(current))return true; // если число
        if(Character.isLetter(current))return true; // если буква
        if(current == '_')return true;
        if(current == '@')return true;
        return false;
    }

    protected Define searchDefine(final List<Define> defines, final String def_name){
        Define temp = null;
        for(int i=0; i<defines.size(); i++){
            temp = defines.get(i);
            if(def_name.equalsIgnoreCase(temp.getName())){ // если равен(тоесть нашли нужный макрос), то
                return temp;
            }
        }
        throw new RuntimeException("an undefined macro");
    } /** пойск неопходимо макроса по имени **/
}
