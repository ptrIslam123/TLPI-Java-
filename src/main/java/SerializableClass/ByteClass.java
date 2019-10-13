package SerializableClass;

import Lexer.Token;

import java.io.*;
import java.util.ArrayList;

public class ByteClass implements Serializable {

    public static void serialize(final File fileName, final ArrayList<Token> tokens) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(tokens);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static ArrayList<Token> deserialize(final File fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<Token> tokens = (ArrayList<Token>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return tokens;
    }
}


