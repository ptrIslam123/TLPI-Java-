package Parser.Type.StructType.Struct;

import Lexer.TypeToken;
import Parser.Type.Types.Type;

public class Struct_t {
    private String name;
    private String field;
    private Type value;
    TypeToken type;
    Type indexFirst, indexSecond;

    public Struct_t() {
        this.name = null;
        this.field = null;
        this.value = null;
        this.indexFirst = null;
        this.indexSecond = null;
    }

    public void setNameAndField(String name){
        this.name = name;
        this.field = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public void setType(TypeToken type) {
        this.type = type;
    }

    public void setIndexFirst(Type indexFirst) {
        this.indexFirst = indexFirst;
    }

    public void setIndexSecond(Type indexSecond) {
        this.indexSecond = indexSecond;
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public Type getValue() {
        return value;
    }

    public TypeToken getType() {
        return type;
    }

    public Type getIndexFirst() {
        return indexFirst;
    }

    public Type getIndexSecond() {
        return indexSecond;
    }
}
