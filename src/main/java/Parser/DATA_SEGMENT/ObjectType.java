package Parser.DATA_SEGMENT;

import Parser.Type.Type;

public interface ObjectType {

    Type asPrimative();     /** ПРИМИТИВНЫЙ ТИП ДАННЫХ **/
    Type[] asAggregate();  /** АГРЕГИРУЮЩИЙ ТИП ДАННЫХ**/
    Type[][] asMultiAggregate();   /** ДВУХ МЕРНЫЙ МАССИВ ТИПА ДАННЫХ**/

    String getName(); /**** this is test fiend ****/
}
