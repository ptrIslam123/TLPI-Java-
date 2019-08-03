package __Preprocessor.CONSTANT;

public class Const {
    private StringBuilder const_expr_name, const_expr_value;

    public Const(final StringBuilder const_expr_name, final StringBuilder const_expr_value){
        this.const_expr_name = const_expr_name;
        this.const_expr_value = const_expr_value;
    }
    public StringBuilder getConstName(){ return const_expr_name; }
    public StringBuilder getConstValue(){ return const_expr_value; }
}
