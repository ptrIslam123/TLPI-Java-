package Parser.Statement.IOstream;

import Lexer.TypeToken;
import Parser.Statement.Statement;
import Parser.Type.AggregateType;
import Parser.Type.Integral.*;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.ArrayType;
import Parser.Type.StructType.MultiArrayType;
import Parser.Type.StructType.Struct.StructType;
import Parser.Type.Types.Aggregate;
import Parser.Type.Types.Primitive;
import Parser.Type.Types.Type;

public class WriteStreamStatement implements Statement {
    private Type expression;
    private boolean nextLine = false;

    public void streamInitialize(final Type expression) {
        this.expression = expression;
    }
    public void streamInitialize(final Type expression, final boolean nextLine) {
        this.expression = expression;
        this.nextLine = nextLine;
    }

    @Override
    public void execute() {
        if(expression instanceof PrimitiveType){
            if(nextLine){
                primitiveWrite(expression.asPrimitive(),nextLine);
            }else
                primitiveWrite(expression.asPrimitive());
        }
        else if(expression instanceof AggregateType){
            aggregateWrite(expression.asAggregate());
        }
        else if(expression instanceof StructType){
            structWrite(expression);
        }
        else showErrorMessage();
    }

    private void primitiveWrite(Primitive expr, boolean nextLine) {
        defTypeln(expr);
    }
    private void primitiveWrite(Primitive expr) {
        defType(expr);
    }

    private void structWrite(Type expression) {
        StructType structType = (StructType) expression;
        System.out.println(structType.getClass());
    }

    private void aggregateWrite(Aggregate expr) {
        if(expr instanceof ArrayType){
            showArrayType(expr);
        }
        else if(expr instanceof MultiArrayType){
            showMultiArray(expr);
        }
        else showErrorMessage();
    }

    private void showMultiArray(Aggregate array) {
        Primitive[][] primitives = array.asMultiArray();
        for(int i=0; i<primitives.length; i++){
            if(primitives[i] == null)break;
            for(int j=0; j<primitives[i].length; j++){
                if(primitives[i][j] == null)break;
                System.out.print("["+i+"]["+j+"] ");
                defType(primitives[i][j]);
                System.out.println("\t");
            }
            System.out.println("");
        }
    }

    private void showArrayType(Aggregate array) {
        Primitive[] primitives = array.asArray();
        for(int i=0; i<primitives.length; i++){
            if(primitives[i] == null)break;
            System.out.print("["+i+"] ");
            defType(primitives[i]);
            System.out.println("");
        }
    }

    private void defType(Primitive expr){
        if(expr instanceof IntegerType){
           writeConsole(TypeToken.integer_t, expr);
        }
        else if(expr instanceof DoubleType){
            writeConsole(TypeToken.double_t, expr);
        }
        else if(expr instanceof StringType){
            writeConsole(TypeToken.string_t, expr);
        }
        else if(expr instanceof BoolType){
            writeConsole(TypeToken.bool_t, expr);
        }
        else if(expr instanceof CharType){
            writeConsole(TypeToken.char_t, expr);
        }
        else showErrorMessage();
    }
    private void defTypeln(Primitive expr){
        if(expr instanceof IntegerType){
            writeConsoleln(TypeToken.integer_t, expr);
        }
        else if(expr instanceof DoubleType){
            writeConsoleln(TypeToken.double_t, expr);
        }
        else if(expr instanceof StringType){
            writeConsoleln(TypeToken.string_t, expr);
        }
        else if(expr instanceof BoolType){
            writeConsoleln(TypeToken.bool_t, expr);
        }
        else if(expr instanceof CharType){
            writeConsoleln(TypeToken.char_t, expr);
        }
        else showErrorMessage();
    }
    private void writeConsoleln(TypeToken type, final Primitive expr){
        switch (type){
            case integer_t:
                System.out.println(expr.asInt());
                break;

            case double_t:
                System.out.println(expr.asDouble());
                break;

            case string_t:
                System.out.println(expr.asString());
                break;

            case bool_t:
                System.out.println(expr.asBool());
                break;

            case char_t:
                System.out.println(expr.asChar());
                break;

            default: showErrorMessage();
        }
    }
    private void writeConsole(TypeToken type, final Primitive expr){
        switch (type){
            case integer_t:
                System.out.print(expr.asInt());
                break;

            case double_t:
                System.out.print(expr.asDouble());
                break;

            case string_t:
                System.out.print(expr.asString());
                break;

            case bool_t:
                System.out.print(expr.asBool());
                break;

            case char_t:
                System.out.print(expr.asChar());
                break;

            default: showErrorMessage();
        }
    }

    private void showErrorMessage(){
        throw new RuntimeException("Unknown token type: " + expression);
    }
}
