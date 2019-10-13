package Parser.Type.Types;


import Parser.Type.StructType.Struct.Struct;

public interface Type {

    Primitive asPrimitive(); /**    примитивный тип данных      **/
    Aggregate asAggregate(); /**    агрегирующий тип данных (массивы)     **/
    Struct asStruct();       /**    структурный тип данных      **/

}
