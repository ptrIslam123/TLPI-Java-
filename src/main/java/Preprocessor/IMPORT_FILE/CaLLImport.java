package Preprocessor.IMPORT_FILE;

import Preprocessor.BasePr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CaLLImport extends BasePr {

    private StringBuilder input;
    public CaLLImport(StringBuilder input, int pos) {
        this.input = input;
        setText(input);
        setPos(pos);
    }

    public StringBuilder callImport() throws IOException {
        StringBuilder Import_fileName = directive_import_file(); /** получаем имя импортируемого файла **/
        return Import_fileName = readFile(Import_fileName); /** получение содержимого импортируемого файла**/
    }

    private StringBuilder directive_import_file() {
        StringBuilder buffer_fileName = new StringBuilder();
        skip_blank_characters();
        while(cond(peek(0))){
            if(peek(0) == ';'){
                next();
                break;
            }
            buffer_fileName.append(peek(0));
            next();
        }
        return buffer_fileName;
    }

    private StringBuilder readFile(final StringBuilder filename) throws IOException {
        File file = new File(filename.toString());
        if(!file.exists()){
           throw new RuntimeException("FILE: "+filename+" : NOT FOUND");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        int len = fileInputStream.available();
        byte data[] = new byte[len];
        fileInputStream.read(data);
        String text = new String(data);
        StringBuilder str = new StringBuilder(len);
        str.append(text);
        return str;
    }
    public int getPosEnd(){ return getPos(); }
    private char peek(final int position){
        return getCh(position);
    }

}
