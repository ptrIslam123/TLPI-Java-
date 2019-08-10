package Preprocessor;

import Preprocessor.CALL_EXPRESSION.ParseCall;
import Preprocessor.CONST.Const;
import Preprocessor.DEFINE.Defines;

import java.util.List;

public class IfDef extends BasePr{
    private StringBuilder input;
    private List<Defines> define_table;
    private List<Const> const_table;
    private ParseCall parseCall;

    public IfDef(final StringBuilder input, int pos, final List<Defines> define_table, final List<Const> const_table){
        this.input= input;
        this.define_table = define_table;
        this.const_table = const_table;
        setText(input);
        setPos(pos + 7);
    }

    public boolean getValueConditionExpression(){
        String value = retValueConditionExpression();
        if(value.equalsIgnoreCase("1"))return true;
        else if(value.equalsIgnoreCase("0"))return false;
        throw new RuntimeException("undefined conditional compilation type: "+value);
    }

    private String retValueConditionExpression(){
        skip_blank_characters();
        parseCall = new ParseCall(input, getPos(), define_table, const_table);
       return parseCall.callExpressiion().toString();
    }

    public int getPosEnd(){ return parseCall.getPosEnd(); }
}

