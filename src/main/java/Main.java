import __Preprocessor.Preprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String file_code = readFile("file.txt");

        Preprocessor preprocessor = new Preprocessor(file_code);
        preprocessor.preprocess();

    }

    private static String readFile(final String filename) throws IOException {
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
}
