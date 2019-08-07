public class ConditionC {

    private String input;
    private int pos, length;
    private static boolean flage = false;

    public ConditionC(String input){
        this.input= input;
        this.length = input.length();
    }
    public void run(){
        StringBuilder buffer =new StringBuilder();
        while(pos < length){
           if(consyme("#if_def")){
              String answer = directive_parse_comst_expr();
               condition_block(answer);
               continue;
           }
           if(consyme("#els_def") && flage == false){
               skip("#end_def");
               continue;
           }

           if(consyme("#end_def")){pos++;}
            buffer.append(peek(0));
            pos++;
        }

        System.out.println(buffer.toString());
    }

    private void skip(String s) {
        while(pos < length){
            if(consyme(s))break;
            pos++;
        }
    }

    private void condition_block(String answer) {
        if(answer.equalsIgnoreCase("1")) {// если if_def true
            flage = false;
        }else {
            flage = true;
            while(pos < length){
                if(consyme("#els_def"))break;
                if(consyme("#end_def"))break;
                pos++;
            }
        }
    }


    private String directive_parse_comst_expr() {
        pos++;
        StringBuilder buffer = new StringBuilder();
        buffer.append(peek(0));
        pos++;
        return buffer.toString();
    }
    private boolean consyme(final String str){
        int temp_pos = pos;
        for(int i=0; i<str.length(); i++, pos++){
            if(peek(0) != str.charAt(i)){ pos = temp_pos; return false;}
        }
        return true;
    }
    private char peek(int position_r){
        int position = pos + position_r;
        if(position >= length)return '\0';
        return input.charAt(position);
    }
}
