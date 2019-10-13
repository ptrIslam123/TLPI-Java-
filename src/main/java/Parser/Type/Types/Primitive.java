package Parser.Type.Types;

/**
 *  ЗДЕСЬ БУДУТ НАХОДИТЬСЯ ПРИМИТИВНЫЕ ТИПЫ ДАННЫХ:
 *          INT, STRING, DOUBLE, BOOLEAN, CHARECTER
 */
public interface Primitive {

    int asInt();
    String asString();
    boolean asBool();
    char asChar();
    double asDouble();
    void asVoid();

}
