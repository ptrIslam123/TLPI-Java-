import Lexer.Lexer;
import Lexer.Token;
import Parser.Type.ParserClasses.Parser;
import Preprocessor.Preprocessor;
import SemanticsAnalyzer.TokenAnalyzer.TokenAnalyzer;
import SerializableClass.ByteClass;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        verifyCountInputArgs(args.length < 3);
        String flage = args[0];
        String fileInput = args[1];
        String fileOutput = args[2];
        executeProcess(flage, fileInput, fileOutput, args.length);
    }

    private static void executeProcess(final String flage, final String fileInput, final String fileOutput, int length) throws IOException, ClassNotFoundException {
        /**  -Е ЗАПУСК ПРЕПРОЦЕССОРА  **/
        if(flage.equals("-p")){
            verifyCountInputArgs(length == 3);
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
            verifyCountInputArgs(length == 3);
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
            verifyCountInputArgs(length == 3);
            /** Десериализуем содержимое переданного файла, в следствие чего возвращаем вектор токенов
             *  и затемподаем на вход парсеру который выстройт синтаксическое дерево и распарсит и исполнит его **/
            List<Token> output = deserialize(fileInput);
            callParser(output);
        }
        else if(flage.equals("-v")){
            verifyCountInputArgs(length == 1);
            System.out.println("version: 2.0");
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

    private static void verifyCountInputArgs(final boolean res){
        if(!res)throw new RuntimeException("error: unrecognized command line option");
    }
}

