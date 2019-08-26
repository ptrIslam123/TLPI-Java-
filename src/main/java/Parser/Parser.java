package Parser;



import Lexer.TypeToken;
import Lexer.*;
import Parser.DATA_SEGMENT.AggregateType;
import Parser.DATA_SEGMENT.ObjArray;
import Parser.Expression.BinaryConditionExpression;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.Statement.Statement;
import Parser.Statement.WriteStreamStatement;
import Parser.Type.*;
import Parser.Expression.*;
import com.sun.corba.se.impl.ior.ObjectAdapterIdArray;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private  Parser parser;
    private final Token EOF = new BaseToken(TypeToken.EOF);
    private List<Token> tokens;
    private int pos,length;

    private boolean flage_block = false;

    private BinaryExpression binaryExpr;
    private BinaryConditionExpression binaryCondExpr;
    private WriteStreamStatement write;
    private ObjectExpression objectExpr;

    public Parser() {
        this.objectExpr = new ObjectExpression();
        this.binaryExpr = new BinaryExpression();
        this.binaryCondExpr = new BinaryConditionExpression();
        this.write = new WriteStreamStatement();
    }

    public void init_parser(List<Token> tokens, boolean flage_block){
        this.tokens = tokens;
        this.length = tokens.size();
        this.pos = 0;
        this.flage_block = flage_block;
    }



    public void run(){
       for(Token it : tokens) System.out.println(it.getType()+" : "+it.getValue());
       System.out.println("================================\n");


         while(cond())statement();

        /*System.out.println("size: "+SegmentData.getTable().size());
         for(Map.Entry<String ,Type> it : SegmentData.getTable().entrySet()){
             System.out.println(it.getKey()+" = "+it.getValue().asInt());
         }*/

    }


   private Statement statement(){
        if(get(0).getType() == TypeToken.sys_write){
            next(1);
            write.stream(expression());  // load expr
            write.execute(); // print expr
            return write;
        }
        if(get(0).getType() == TypeToken.Word) {  /** ДЕКЛАРИРОВАНИЕ ДАННЫХ(ПЕРЕМЕННЫЕ, МАССИВВЫ И Т.Д.) **/
            String name_word = get(0).getValue(); // name_word
            next(1);

            if(get(0).getType() == TypeToken.Equals) {   /** парсим переменные **/
                next(1);
                return declare_variable(name_word);
            }

            if(get(0).getType() == TypeToken.L_SQUareParen) {   /** парсим массивы **/
               next(1);
               return declare_array(name_word);
            }
        }
        throw new RuntimeException("Unknown operator type: " + get(0).getType());
   }

    private Statement declare_variable(String name_var) {
        if (flage_block == true) {  /** ЕСЛИ ЭТО БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ НА СТЕКЕ **/


        }
        /** ЕСЛИ ЭТО НЕ БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ В СЕГИЕНТЕ ДАННЫХ (ГЛОБАЛЬНЫЕ ОБЪЕКТЫ) **/
        SegmentData.newObject(name_var, expression());
        return null;
    }


    private Statement declare_array(final String name_array) {
        Type size1 = null, size2 = null;
        List<Type> init_data = null;

        if(get(0).getType() == TypeToken.NumInt32){
            size1 = new IntegerType(get(0).getValue());
            next(1);
        }

        if(get(0).getType() == TypeToken.L_SQUareParen){ /** если это двухмерный массив **/
            next(1);
            if(get(0).getType() == TypeToken.NumInt32){
                size2 = new IntegerType(get(0).getValue());
                next(1);
            }
            declare_multi_araay(name_array, size1, size2);
            return null;
        }
        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(2);
            init_data = parse_init_data_array();
        }

        if(flage_block == true){        /** ЕСЛИ ЭТО БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ НА СТЕКЕ **/

        }
        /** ЕСЛИ ЭТО НЕ БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ В СЕГИЕНТЕ ДАННЫХ (ГЛОБАЛЬНЫЕ ОБЪЕКТЫ) **/
        SegmentData.newObject(name_array, size1, init_data);
        return null;
    }

    private void declare_multi_araay(String name_array, Type size1, Type size2) {
        List<ObjArray> multiArray = null;
        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(2);
            multiArray = parse_init_data_multi_array();
        }
        if(flage_block == true){        /** ЕСЛИ ЭТО БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ НА СТЕКЕ **/

        }
        /** ЕСЛИ ЭТО НЕ БЛОК-ТЕЛО ТО ВСЕ ОБЪЕКТЫ ВЫДЕЛЯЕМ В СЕГИЕНТЕ ДАННЫХ (ГЛОБАЛЬНЫЕ ОБЪЕКТЫ) **/
        SegmentData.newObject(name_array, size1, size2, multiArray);
    }

    private List<ObjArray> parse_init_data_multi_array() {
       List<ObjArray> array = new ArrayList<ObjArray>();
       while(get(0).getType() != TypeToken.Rparen){
           if(get(0).getType() == TypeToken.Lparen){
               next(1);
               ObjArray objArray = new ObjArray();
               while(get(0).getType() != TypeToken.Rparen){
                   if(get(0).getType() == TypeToken.Comma)next(1);
                   objArray.add(expression());
               }
               array.add(objArray);
               next(1);
           }
           if(get(0).getType() == TypeToken.Comma)next(1);
       }
        next(1);
       return array;
    }

    private List<Type> parse_init_data_array() {
        List<Type> array = new ArrayList<Type>();
        while(get(0).getType() != TypeToken.Rparen){
            if(get(0).getType() == TypeToken.Comma)next(1);
            array.add(expression());
        }
        next(1);
        return array;
    }


    private Type expression(){ return conditionExpression(); }
    private Type conditionExpression(){
        Type result = additive();
        while(true){
            if(get(0).getType() == TypeToken.Less){
                next(1);
                result = eval(TypeToken.Less, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.More){
                next(1);
                result = eval(TypeToken.More, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.LessEq){
                next(1);
                result = eval(TypeToken.LessEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.MoreEq){
                next(1);
                result = eval(TypeToken.MoreEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.Eq){
                next(1);
                result = eval(TypeToken.Eq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.NoEq){
                next(1);
                result = eval(TypeToken.NoEq, result, additive());
                continue;
            }
            break;
        }
        return result;
    }
    private Type additive(){
        Type result = multiplivative();
        while(true){
            if(get(0).getType() == TypeToken.Add){
                next(1);
                result = eval('+',result, multiplivative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                next(1);
                result = eval('-',result, multiplivative());
                continue;
            }
            break;
        }
        return result;
    }
    private Type multiplivative(){
        Type result = primary();
        while(true){
            if(get(0).getType() == TypeToken.Mult){
                next(1);
                result = eval('*',result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                next(1);
                result = eval('-',result, primary());
                continue;
            }
            break;
        }
        return result;
    }



    private Type primary(){
        Type temp = null;
        if(get(0).getType() == TypeToken.Lparen) {
            next(1);
            temp = expression();
            consume(TypeToken.Rparen);
            return temp;
        }

        if(get(0).getType() == TypeToken.Word){ /** ЕСЛИ ИДЕТ ОБРАЩЕНИЕ К ОБЪЕКТАМ **/
            String name = get(0).getValue();
            next(1);


            if(get(0).getType() == TypeToken.L_SQUareParen){    /** обращение к массиву **/
                next(1);
                Type index_1, index_2;
                index_1 = expression();
                if(get(0).getType() == TypeToken.L_SQUareParen){    /** обращение к двумерному массиву **/
                    next(1);
                    index_2 = expression();
                    objectExpr.setObject(name, index_1.asInt(), index_2.asInt());
                    return objectExpr.eval();
                }
                objectExpr.setObject(name, index_1.asInt());
                return objectExpr.eval();
            }

            objectExpr.setObject(name);     /** обращение к переменной **/
            return objectExpr.eval();
        }



        /** примитивные типы данных **/
        if(get(0).getType() == TypeToken.NumInt32){
            temp = new IntegerType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Str){
            temp = new StringType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.NumDouble64){
            temp = new DoubleType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Bool){
            temp = new BoolType(get(0).getValue());
            next(1);
            return temp;
        }
        if(get(0).getType() == TypeToken.Char){
            temp = new CharType(get(0).getValue());
            next(1);
            return temp;
        }
        throw new RuntimeException("Unknown token type");
    }

    private void next(final int shift_pos){ pos+=shift_pos; }
    private Token get(final int position_relative){
        int position = pos + position_relative;
        if(position >= length)return EOF;
        return tokens.get(position);
    }
    private Type eval(char operation, Type expr1, Type expr2) {
       binaryExpr.init(operation, expr1, expr2);
       return binaryExpr.eval();
    }
    private Type eval(TypeToken operation, Type expr1, Type expr2) {
        binaryCondExpr.init(operation, expr1, expr2);
        return binaryCondExpr.eval();
    }
    private boolean cond() {
        if(get(0).getType() == TypeToken.EOF)return false;
        else return true;
    }
    private boolean consume(final TypeToken type){
        if(get(0).getType() == type){
            next(1);
            return true;
        }else throw new RuntimeException("Unknown token type");
    }
}
