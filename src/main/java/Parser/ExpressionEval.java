package Parser;

import Lexer.Token;
import Lexer.TypeToken;
import Parser.DATA_SEGMENT.ObjectData;
import Parser.Type.Type;
import com.sun.org.apache.regexp.internal.RE;

import java.util.List;

public class ExpressionEval extends BaseParser {
    public void init_expression_eval(List<Token> tokens){
        setTokens(tokens);
        setLength(tokens.size());
        setPos(0);
    }

    protected Type expression(){ return conditionExpression(); }
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
    protected Type primary(){
        Type temp = null;
        if(get(0).getType() == TypeToken.Lparen) {
            next(1);
            temp = expression();
            consume(TypeToken.Rparen);
            return temp;
        }

        /** в начале будет проведено на соответствие обращения к пользовательским объектам **/
        if(get(0).getType() == TypeToken.Word){
            String name_obj = get(0).getValue();
            next(1);

            if(get(0).getType() == TypeToken.Equals){  /** VAR = NEW_EXPRESSION **/
                next(1);
                return setValueVariable(name_obj, expression());
            }

            if(get(0).getType() == TypeToken.L_SQUareParen){  /**  WORD [?] **/
                next(1);
                return setValueArray(name_obj);
            }

            return objectData.getObject(name_obj);
        }

        return primitive(); /** парсинг примитивных типов данных **/
    }

    private Type setValueVariable(String name_obj, Type expression) {
        objectData.setValueObject(name_obj, expression);
        return voidV;
    }

    private Type setValueArray(String name_obj) {
        Type index_1, index_2, newValue = null;
        index_1 = expression();

        if(get(0).getType() == TypeToken.L_SQUareParen){
            next(1);
            index_2 = expression();

                if(get(0).getType() == TypeToken.Equals){
                    next(1);
                    newValue = expression();
                    objectData.setValueObject(name_obj, index_1.asInt(), index_2.asInt(), newValue);
                    return voidV;
                }

                return objectData.getObject(name_obj, index_1.asInt(), index_2.asInt());
        }

        if(get(0).getType() == TypeToken.Equals){  /** присвоение значения массиву по его индексу **/
            next(1);
            newValue = expression();
            objectData.setValueObject(name_obj, index_1.asInt(), newValue);
            return voidV;
        }

        return objectData.getObject(name_obj,index_1.asInt());
    }
}
