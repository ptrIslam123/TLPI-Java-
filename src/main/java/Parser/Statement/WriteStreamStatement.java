package Parser.Statement;

import Parser.Type.*;

public class WriteStreamStatement implements Statement {
    private Type expression;

    public void stream(Type expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        if(expression instanceof IntegerType){
            System.out.println(expression.asInt());
            return;
        }
        if(expression instanceof DoubleType){
            System.out.println(expression.asDouble());
            return;
        }
        if(expression instanceof StringType){
            System.out.println(expression.asString());
            return;
        }
        if(expression instanceof CharType){
            System.out.println(expression.asChar());
            return;
        }
        if(expression instanceof BoolType){
            System.out.println(expression.asBoll());
            return;
        }
        throw new RuntimeException("Unknown token type: " + expression);
    }
}
