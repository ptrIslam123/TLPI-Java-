package Parser.Variable;

import Parser.Lib.Statement.Statement;
import Parser.Type.Type;

import java.util.List;

public class ArrayDeclare implements Statement {
    private String name;
    private Type capasity;
    private List<Type> initialize_data;

    public void init(String name, Type capasity, List<Type> initialize_data) {
        this.name = name;
        this.capasity = capasity;
        this.initialize_data = initialize_data;
        check_array(name, capasity, initialize_data);
        eval();
    }

    private void check_array(String name, Type capasity, List<Type> initialize_data) {
        if(capasity == null && (initialize_data == null || initialize_data.size() == 0))throw new RuntimeException("Uninitialized block of memory: "+name);
        return;
    }

    @Override
    public void eval() {
        if(capasity == null){
            ArrayTable.addArray(this.name, this.initialize_data);
            return;
        }
        if(initialize_data == null){
            ArrayTable.addArray(this.name, this.capasity);
            return;
        }
        ArrayTable.addArray(this.name, this.capasity, this.initialize_data);
    }
}
