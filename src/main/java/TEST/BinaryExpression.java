package TEST;

public class BinaryExpression {
    private int expr1, expr2;
    private char operation;

    public BinaryExpression(char operation, int expr1, int expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    public int eval(){
        switch (operation){
            case '*':return expr1*expr1;
            case '/':return expr1/expr1;
            case '-':return expr1-expr1;
            default:return expr1 + expr2;
        }
    }
}
