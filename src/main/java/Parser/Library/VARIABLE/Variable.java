package Parser.Library.VARIABLE;

import Parser.TYPE.Type;

public class Variable {
    private String name;
    private Type value;

    public Variable(String name, Type value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Type getValue() {
        return value;
    }
}
