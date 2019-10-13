
import Lexer.Lexer;
import Lexer.Token;
import Parser.Type.ParserClasses.Parser;
import Preprocessor.Preprocessor;
import SemanticsAnalyzer.TokenAnalyzer.TokenAnalyzer;
import SerializableClass.ByteClass;
import com.sun.corba.se.impl.encoding.CodeSetConversion;
import com.sun.corba.se.impl.util.PackagePrefixChecker;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.scene.Parent;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StringBuilder file = readFile(createFile("main.txt"));
        Preprocessor preprocessor = new Preprocessor(file);
        StringBuilder run = preprocessor.run();
        Lexer lexer = new Lexer(run.toString());
        List<Token> resLexer = lexer.run();

        TokenAnalyzer tokenAnalyzer = new TokenAnalyzer(resLexer);
        tokenAnalyzer.run();
        ArrayList<Token> resAnalyzer = tokenAnalyzer.getArrayTokens();

        Parser parser = new Parser();
        parser.init_parser(resAnalyzer, false);
        parser.run();

    }


    private static void test(final String fileName) throws IOException {
        StringBuilder fileData = readFile(createFile(fileName));

        Preprocessor preprocessor = new Preprocessor(fileData);
        StringBuilder res = preprocessor.run();

        Lexer lexer = new Lexer(res.toString());
        List<Token> resLexer = lexer.run();

        TokenAnalyzer tokenAnalyzer = new TokenAnalyzer(resLexer);
        tokenAnalyzer.run();
        ArrayList<Token> resTokenAnalyzer = tokenAnalyzer.getArrayTokens();

        Parser parser = new Parser();
        parser.init_parser(resTokenAnalyzer, false);
        parser.run();

    }
    private static void executeProcess(final String flage, final String fileInput, final String fileOutput) throws IOException, ClassNotFoundException {
        /**  -Е ЗАПУСК ПРЕПРОЦЕССОРА  **/
        if(flage.equals("-e")){
            /**  для начало читаем даные из файла и передаем препроцессору **/
            StringBuilder data = readFile(getFile(fileInput));
            /** запускаем препроцессор с переданными данными **/
            String output = callPreprocessor(data);
            /** Output препроцессора и имя выходного файла (который должен быть созан)
             *  передаем на вход методу. Он создаст новый файл куда запишеться
             *  результат работы препроцессора **/
            createFileWithOutputData(fileOutput, output);
        }
        /**  -С ЗАПУСК ПРОЦЕССА КОМПИЛЯЦИЙ И СОЗДАНИЯ ОЪЕКТНОГ ФАЙЛА СОДЕРЖАЩЕГО БАЙТ КОД  **/
        else if(flage.equals("-c")){
            /**  для начало читаем даные из файла и передаем препроцессору **/
            StringBuilder data = readFile(getFile(fileInput));
            /** запускаем препроцессор с переданными данными **/
            String outPreprocessor = callPreprocessor(data);
            /** ЗАПУСКАЕМ ЛЕКСЕР С ОБРАБОТАННЫМ ТЕКСТОМ ПРЕПРОЦЕССОРА. Лексер должен разбить
             *  текс на лексемы(токены) и передать дальше по конвенций **/
            List<Token> outputLexer = callLexer(outPreprocessor);
            /** Далее в дело вступает аналлизатор лексем(токенов) который проводит
             * анализ и преобразование а такде стадий оптимизацию **/
            List<Token> outputAnalyzer = callTokenAnalyzer(outputLexer);
            /** Последним мы сеарилизуем(преобразуем в массив байт и записываем
             *  в файл) обрапотанный список(вектор) токенов **/
            serialize(fileOutput, outputAnalyzer);
        }
        /**  -О ЗАПУСК ПРОЦЕССА ЛИНКОВКИ(СКЛЕИВАНИЕ ОБЪЕКТНИКОВ) И СОЗДАНИЕ ИСПОЛНЯЕМОГО ПРОЦЕССА(ФАЙЛА)  **/
        else if(flage.equals("-o")){
            /** Десериализуем содержимое переданного файла, в следствие чего возвращаем вектор токенов
             *  и затемподаем на вход парсеру который выстройт синтаксическое дерево и распарсит и исполнит его **/
            List<Token> output = deserialize(fileInput);
            callParser(output);
        }
        else {
            throw new RuntimeException("Error!");
        }
    }
    private static void callParser(final List<Token> output) throws IOException {
        /** раздел "компиляций" в будущем будем сериализовывать и десиарилизовывать
         * промежуточное представление в объектный файл**/
        Parser parser = new Parser();
        parser.init_parser((ArrayList<Token>) output, false);
        parser.run();
    }

    private static List<Token> deserialize(String fileInput) throws IOException, ClassNotFoundException {
        File file = getFile(fileInput);
        return ByteClass.deserialize(file);
    }

    private static void serialize(final String fileOutput, final List<Token> output) throws IOException {
        File file = createFile(fileOutput);
        ByteClass.serialize(file, (ArrayList<Token>) output);
    }

    private static List<Token> callTokenAnalyzer(List<Token> output) {
        /**
         * Данный раздел отвечает за анализ токенов(лексем), а так же
         * вторичную их обработку и предкомпиляционные вычисления (оптимизация кода).
         * На вход Парсеру он подает обработанный вектор {ArrayList<Token> tokens} (массив) токенов
         */
        TokenAnalyzer tokenAnalyzer = new TokenAnalyzer(output);
        tokenAnalyzer.run();
        return tokenAnalyzer.getArrayTokens();
    }

    private static List<Token> callLexer(final String data) {
        /** ДЕКСИЧЕСКИЙ АНАЛИЗ - РАЗБИВАЕМ СИМВОЛЬНУЮ ИНФОРМАЦИЮ
         * НА ЛЕКСЕМЫ (ТОКЕНЫ).
         * Данный класс возвращает {список(LinkedList)} токенов (лексем)
         **/
        Lexer lexer = new Lexer(data);
        List<Token> tokens = lexer.run();
        return tokens;
    }

    private static String callPreprocessor(final StringBuilder data) throws IOException {
        /** ПРЕПРОЦЕССОРНАЯ ЦАСТЬ **/
         Preprocessor preprocessor = new Preprocessor(data);
         StringBuilder output = preprocessor.run();
         return output.toString();
    }


    private static void createFileWithOutputData(String fileOutput, String output) throws IOException {
        File file = createFile(fileOutput);
        writeDataWithFile(file, output);
    }

    private static File createFile(String fileOutput) throws IOException {
        File file = new File(fileOutput);
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    private static File getFile(String fileName) {
        File file = new File(fileName);
        if(file.exists()){
            return file;
        }
        throw new RuntimeException("this file already exists: "+fileName);
    }

    private static void writeDataWithFile(final File file, final String output) throws FileNotFoundException {
        PrintWriter out  = new PrintWriter(file.getAbsoluteFile());
        out.print(output);
        out.close();
    }
    private static StringBuilder readFile(final File file) throws IOException {
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

