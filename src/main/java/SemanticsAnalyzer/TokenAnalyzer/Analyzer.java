package SemanticsAnalyzer.TokenAnalyzer;

import Lexer.*;
import SemanticsAnalyzer.Functions.DefFunction;
import SemanticsAnalyzer.Functions.FunctionTable;
import java.util.ArrayList;
import java.util.List;

public class Analyzer extends BaseInterface {
    public Analyzer(final List<Token> tokens){
       setTokens(tokens);
   }

   public List<Token> run(){
        parseDefineOrCallFunction();
        setPos(0);

        parseTokenBlock();
        setPos(0);

        addFunctionDefineFunctionTable();

        return getTokens();
   }



    /*************************************************************
     * Методы по парсингу объявлений или вызовов функций
     ************************************************************/
    public void parseDefineOrCallFunction(){
        while(!evenToken(TypeToken.EOF)){
            if(getType(0) == TypeToken.L_SQUareParen){
                removeToken();
                insertToken(getPos(), parseIndexToken());
                continue;
            }

            if(getType(0) == TypeToken.Word && getType(1) == TypeToken.Lparen){
                defineOrCallCurrentFunction();
                parsePushInputData();
                continue;
            }
            next(1);
        }
    }

    private Token parseIndexToken() {
        TokenIndex index = new TokenIndex();
        while(getType(0) != TypeToken.R_SQUareParen){
            index.add(getType(0), getValue(0));
            removeToken();
        }
        removeToken();
        return index;
    }

    private void parsePushInputData() {
        addPushIfNextNoEqTokenRparen();
        while(getType(0) != TypeToken.Rparen){
            /////
            if(getType(0) == TypeToken.L_SQUareParen){
                removeToken();
                insertToken(getPos(), parseIndexToken());
                continue;
            }
            ////
            if(getType(0) == TypeToken.Comma){
                setTokenType(getPos(), new BaseToken(TypeToken.Push));
            }
            next(1);
        }
        removeToken();
    }

    private void addPushIfNextNoEqTokenRparen() {
        if(getType(0) != TypeToken.Rparen){
            insertToken(getPos(), new BaseToken(TypeToken.Push));
        }
    }


    private void defineOrCallCurrentFunction() {
        if(getPos() == 0){
            addTokenPush();
        }
        else if(getType(-1) != TypeToken.Define_func){
            addTokenPush();
        }
        next(1);
        removeToken();
    }

    private void addTokenPush(){
        insertToken(getPos(), new BaseToken(TypeToken.Call));
        next(1);
    }


    /****************************************************************************************
     * Метод  по парсингу определений функции и загрузки этих определений в таблицу функции
     ***************************************************************************************/
    private void addFunctionDefineFunctionTable() {
     while(!evenToken(TypeToken.EOF)){
         if(getType(0) == TypeToken.Define_func){
             removeToken();
             initializedFunctionTable();
             continue;
         }
         next(1);
     }
    }

    private void initializedFunctionTable() {
        /**  Получаем имя определяемой функций  **/
        String name = parseFunctionName();
        /**
         * Получаем вектор из имен входных аргументов, которые в будущем (при вызове функции)
         * будут инициализированны данными. Метод работает в циикле: пока нам встречаеться токен
         * Push мы считываем символьную информацию, а затем по окончанию цикла возвращаем вектор.
         */
        List<String> localParams = parseLocalParamsCurrentFunction();
        /**  Здесь мы получаем тело функции (TokenBlock)  **/
        TokenBlock body = (TokenBlock) get(0);
        removeToken();
        /** Вданном метсте мы инстанцируем объект Function который содержит в себе все атрибуты одной функции  **/
        DefFunction function = new DefFunction(name, localParams, body);
        /** Данной строчкой кода мы загружаем в таблицу функции: FunctionTable созданный нами ранне объект function  **/
        FunctionTable.add(function);
    }

    private String parseFunctionName() {
        String nameFunction = getValue(0);
        removeToken();
        return nameFunction;
    }

    private ArrayList<String> parseLocalParamsCurrentFunction() {
        final int BEGIN_SIZE_LOCAL_PARAMS = 6;
        ArrayList<String> localParams = new ArrayList<>(BEGIN_SIZE_LOCAL_PARAMS);
        while(!evenToken(TypeToken.EOF)){
            if(getType(0) == TypeToken.Push){
                removeToken();
                localParams.add(getValue(0));
                removeToken();
                continue;
            }
            break;
        }
        return localParams;
    }

    /***************************************************************************************
     * Метод по парсингу блочных токенов (токенов содержащих в себе
     * другие токены). Если встречаем токен ShapeLparen то удаляем этот токен
     * и вызываем метод по парсингу блочных токенов.
     **************************************************************************************/
    private void parseTokenBlock() {
        while(!evenToken(TypeToken.EOF)){
            if(getType(0) == TypeToken.ShapeLparen){
                removeToken();
                getTokenBlock();
                continue;
            }
            next(1);
        }
    }

    /**
     * Создаем специальный объект TokenBlock который просто хранит в себе вектор
     * таких же примитивных токенов.
     * Если встречаем токен ShapeRparen то прекращаем считывать токены, и заменяем текущий
     * токен на TokenBlock котрый содержит в себе все считанные токены. Если в процессе считавания токенов
     * мы встретли лексему ShapeLparen то удаляем эту лексему в рекурсии вызываем сам метод парсинга TokenBlock типа.
     */
    private void getTokenBlock() {
        TokenBlock block = new TokenBlock();
        while(!evenToken(TypeToken.EOF)){
            if(getType(0) == TypeToken.ShapeRparen){
                removeToken();
                break;
            }
            if(getType(0) == TypeToken.ShapeLparen){
                removeToken();
                getTokenBlock();
                continue;
            }
            if(getType(0) == TypeToken.Block){
                block.put((TokenBlock) get(0));
                removeToken();
                continue;
            }
            //block.put(getType(0), getValue(0));
            block.put(get(0));
            removeToken();
        }
        insertToken(getPos(), block);
    }
}
