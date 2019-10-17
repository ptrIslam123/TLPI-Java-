package Parser.Type.Types;

/**
 *  ДАННЫЙ ИНТЕРФЕЙС РЕАЛИЗУЕТ АГРЕГИРУЮЩИЙ (СОСТАВНОЙ) ТИП ДАННЫХ
 */
public interface Aggregate {

    Primitive[] asArray();
    Primitive[][] asMultiArray();
    Type getLengthArray();
}
