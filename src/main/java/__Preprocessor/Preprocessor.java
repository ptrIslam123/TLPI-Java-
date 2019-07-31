package __Preprocessor;

import java.io.IOException;

public class Preprocessor extends BaseClass {
    private StringBuilder Buffer;
    private String input;
    private int pos, length;

    public Preprocessor(String input) {
        this.input = input;
        this.length = input.length();
    }

    public String preprocess() throws IOException {
        Buffer = new StringBuilder();
        while (pos < length) {
            if (peek(0) == '\"') {
                pos++;
                string_type();
            }

            if (peek(0) == '/' && peek(1) == '/') comments('\n');
            if (peek(0) == '/' && peek(1) == '*') comments();

            if (peek(0) == '#' && peek(1) == 'i' && peek(2) == 'm' && peek(3) == 'p'
                    && peek(4) == 'o' && peek(5) == 'r' && peek(6) == 't') {}

            if(peek(0) == '#' && peek(1) == 'c' && peek(2) == 'o' && peek(3)
                    == 'n' && peek(4) == 's' && peek(5) == 't'){
                pos+=6;
                directive_const();
            }

            Buffer.append(peek(0));
            pos++;
        }

        //System.out.println(Buffer.toString());
        return null;
    }

    private void directive_const() {
        StringBuilder buffer_const_name = new StringBuilder();
        skip_blank_characters();

        while(pos < length){
            if(peek(0) == '='){ pos++; break; }
            buffer_const_name.append(peek(0));
            pos++;
        }
        System.out.println(buffer_const_name.toString());
    }

    /// update method
    private StringBuilder getImport_FileName() {
        StringBuilder buffer_import_filename = new StringBuilder();
        skip_blank_characters();
        while (pos < length) {
            if (Character.isWhitespace(peek(0)) == true || peek(0) == '\n') {
                break;
            }
            buffer_import_filename.append(peek(0));
            pos++;
        }
        return buffer_import_filename;
    }
    private void comments(final char current) {
        while (pos < length) {
            if (peek(0) == current) {
                pos++;
                break;
            }
            pos++;
        }
    }
    private void comments() {
        while (pos < length) {
            if (peek(0) == '*' && peek(1) == '/') {
                pos += 2;
                break;
            }
            pos++;
        }
    }
    private void string_type() {
        while (true) {
            if (peek(0) == '\"') {
                pos++;
                break;
            }
            pos++;
        }
    }
    private void skip_blank_characters() {
        while (pos < length) {
            if (Character.isWhitespace(peek(0)) == false) break;
            pos++;
        }
    }
    private char peek(final int position_relative) {
        int position = pos + position_relative;
        if (position >= length) return '\0';
        return input.charAt(position);
    }
}
