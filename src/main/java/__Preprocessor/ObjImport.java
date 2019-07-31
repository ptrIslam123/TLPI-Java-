package __Preprocessor;

import java.io.IOException;

public class ObjImport extends BaseClass {

    private StringBuilder input_file;
    private boolean flage = false;
    private int pos;

    public ObjImport(StringBuilder input_file){ this.input_file = input_file; }

    public String retFileText() throws IOException {

        return import_file(input_file);
    }

    private String import_file(StringBuilder fileName) {
       return null;
    }



    private char getCh(final int position_relative, final StringBuilder file){
        final int length = file.length();
        int position = pos + position_relative;
        if(position >= length)return '\0';
        return file.charAt(position);
    }

}
