package Preprocessor.CONST;

public class Const {
    private StringBuilder constExprName, constExprValue;
    public Const(final StringBuilder constExprName, final StringBuilder constExprValue){
        this.constExprName = constExprName;
        this.constExprValue = constExprValue;
    }
    public StringBuilder getConstName(){ return constExprName; }
    public StringBuilder getConstValue(){ return constExprValue; }
}
