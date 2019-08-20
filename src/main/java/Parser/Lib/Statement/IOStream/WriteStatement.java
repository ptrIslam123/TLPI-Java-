package Parser.Lib.Statement.IOStream;

import Parser.Lib.Statement.Statement;
import Parser.Type.*;

public final class WriteStatement implements Statement {
    private Type expression;

    public void stream(Type expression) {
        this.expression = expression;
        eval();
    }

    @Override
    public void eval() {
        if(expression instanceof IntegerType){
            System.out.println(this.expression.asInt());
            return;
        }
        if(expression instanceof StringType){
            System.out.println(this.expression.asString());
            return;
        }
        if(expression instanceof BoolType){
            System.out.println(this.expression.asBoll());
            return;
        }
        if(expression instanceof DoubleType){
            System.out.println(this.expression.asDouble());
            return;
        }
        throw new RuntimeException("Unknown Token Type");
    }
}
