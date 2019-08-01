package __Preprocessor;

import __Preprocessor.CONSTANT_DIRECTIVE.Constant;
import java.io.IOException;
import java.util.Map;

public class Preprocessor extends BaseClass {
    private StringBuilder Buffer;
    private String input;
    private int pos, length;
    private Constant constant;

    public Preprocessor(String input) {
        this.input = input;
        this.length = input.length();
        this.constant = new Constant();
    }

    public String preprocess() throws IOException {
        Buffer = new StringBuilder();
        while (pos < length) {
            if (peek(0) == '\"') { pos++; string_type(); }

            if (peek(0) == '/' && peek(1) == '/') comments('\n');
            if (peek(0) == '/' && peek(1) == '*') comments();

            if (peek(0) == '#' && peek(1) == 'i' && peek(2) == 'm' && peek(3) == 'p'
                    && peek(4) == 'o' && peek(5) == 'r' && peek(6) == 't') {}

            if(peek(0) == '#' && peek(1) == 'c' && peek(2) == 'o' && peek(3)
                    == 'n' && peek(4) == 's' && peek(5) == 't'){
                pos+=6;
                directive_const();
            }
            if(peek(0) == '@'){
               String const_name = replace_const_expr();
               String const_value = constant.getConst_Value(const_name);

               Buffer.append(const_value);
               continue;
            }
            System.out.println("p: "+peek(0));
            Buffer.append(peek(0));
            pos++;
        }

        System.out.println("\n"+Buffer.toString());
        return null;
    }

    private String replace_const_expr() {
        StringBuilder buffer_const_expr = new StringBuilder();
        while(pos < length){
            if(verificat_compliance_const_expr(peek(0)) == false)break;
            buffer_const_expr.append(peek(0));
            pos++;
        }
       // System.out.println(buffer_const_expr.toString()+"\t"+buffer_const_expr.toString().length());
        return buffer_const_expr.toString();
    }

    private void directive_const() {
        StringBuilder buffer_const_name = new StringBuilder();
        StringBuilder buffer_const_value = null;
        skip_blank_characters();
        while(pos < length){
            if(peek(0) == '='){ pos++; break; }
            buffer_const_name.append(peek(0));
            pos++;
        }
        buffer_const_value = directive_const_value();
        constant.putConst_value(buffer_const_name.toString(), buffer_const_value.toString());
    }

    private StringBuilder directive_const_value() {
        StringBuilder buffer_const_value = new StringBuilder();
        while(pos < length){
            if(peek(0) == '\n')break;
            buffer_const_value.append(peek(0));
            pos++;
        }
        return buffer_const_value;
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
