package Parser.Type.StructType.Struct;

import Parser.Type.Types.Type;

public class Struct {
    private String name;
    private StructBody structBody;

    public Struct(String name, StructBody structBody) {
        this.name = name;
        this.structBody = structBody;
    }

    public String getName() {
        return name;
    }
    public Type getValueField(final String name_field){
       return structBody.getValue(name_field).getValue();
    }
    public StructBody getBody() {
        return structBody;
    }
}
