

import Preprocessor.Preprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        StringBuilder file_code = readFile("file.txt");

        Preprocessor preprocessor = new Preprocessor(file_code);
        preprocessor.run();
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
