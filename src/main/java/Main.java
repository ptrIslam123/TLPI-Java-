
import Lexer.Lexer;
import Lexer.Token;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        StringBuilder file_code = readFile("main.txt");

        /** ПРЕПРОЦЕССОРНАЯ ЦАСТЬ
        Preprocessor preprocessor = new Preprocessor(file_code);
        StringBuilder output = preprocessor.run();
        **/

        /** ДЕКСИЧЕСКИЙ АНАЛИЗ - РАЗБИВАЕМ СИМВОЛЬНУЮ ИНФОРМАЦИЮ
         * НА ЛЕКСЕМЫ (ТОКЕНЫ).
         **/
        Lexer lexer = new Lexer(file_code.toString());
        List<Token> tokens = lexer.run();

        Token  temp = null;
        for(int i=0; i<tokens.size(); i++){
            temp = tokens.get(i);
            System.out.println(temp.getType()+" : "+temp.getValue()); /** В ЦИКЛЕ ВЫВОДИМ СПИОК ТОКЕНОВ ПОЛУЧИВШИХСЯ В РЕЗУЛЬТАТЕ ЛЕКСИЧЕСКОГО АНАЛИЗА**/
        }
    }

    private static StringBuilder readFile(final String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("File: " + filename + " not found!");
            file.createNewFile();
            return null;
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
}
