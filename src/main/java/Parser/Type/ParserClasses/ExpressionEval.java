package Parser.Type.ParserClasses;

import Lexer.Token;
import Lexer.TypeToken;
import Parser.DATA_SEGMENT.SteckData;
import Parser.Statement.ControlConstruction.ReturnStatement;
import Parser.SystemFunction.SysFuncInterface.SysFunction;
import Parser.SystemFunction.SysFuncInterface.SysFunctionTable;
import Parser.Type.StructType.Struct.StructType;
import Parser.Type.StructType.Struct.Struct_t;
import Parser.Type.Types.Type;
import SemanticsAnalyzer.Functions.Function;
import SemanticsAnalyzer.Functions.FunctionTable;

import java.io.IOException;
import java.util.ArrayList;

public class ExpressionEval extends BaseParser {
    public void init_expression_eval(ArrayList<Token> tokens){
        setTokens(tokens);
        setLength(tokens.size());
        setPos(0);
    }

    protected Type expression() throws IOException {
        return evalLogicalFunction();
    }

    private Type evalLogicalFunction() throws IOException {
        Type result = conditionEval();
        while(true){
            if(get(0).getType() == TypeToken.And){
                next(1);
                result = evalLogicalFucntionExpression(TypeToken.And, result, conditionEval());
                continue;
            }
            if(get(0).getType() == TypeToken.Or){
                next(1);
                result = evalLogicalFucntionExpression(TypeToken.Or, result, conditionEval());
                continue;
            }
            break;
        }
        return result;
    }


    protected Type conditionEval() throws IOException {
        Type result = additive();

        while(true){
            if(get(0).getType() == TypeToken.Less){
                next(1);
                result = eval(TypeToken.Less, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.LessEq){
                next(1);
                result = eval(TypeToken.LessEq, result, additive());
                continue;
            }
            if(get(0).getType() == TypeToken.More){
                next(1);
                result = eval(TypeToken.More, result, additive());
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

    protected Type additive() throws IOException {
        Type result = multiplicative();

        while(true){
            if(get(0).getType() == TypeToken.Add){
                next(1);
                result = eval('+', result, multiplicative());
                continue;
            }
            if(get(0).getType() == TypeToken.Sub){
                next(1);
                result = eval('-', result, multiplicative());
                continue;
            }
            break;
        }
        return result;
    }

    protected Type multiplicative() throws IOException {
        Type result = primary();

        while(true){
            if(get(0).getType() == TypeToken.Mult){
                next(1);
                result = eval('*',result, primary());
                continue;
            }
            if(get(0).getType() == TypeToken.Div){
                next(1);
                result = eval('/',result, primary());
                continue;
            }
            break;
        }
        return result;
    }

    protected Type primary() throws IOException {
        if(get(0).getType() == TypeToken.Break){
            next(1);
            breakStatement.execute();
        }
        if(get(0).getType() == TypeToken.Continue){
            next(1);
            continueStatement.execute();
        }
        if(get(0).getType() == TypeToken.Call){
            next(1);
            return callFunctionStatement();
        }
        if(get(0).getType() == TypeToken.ret){
            next(1);
            returnStatement.setRetValue(expression());
            returnStatement.execute();
        }
        if(get(0).getType() == TypeToken.Lparen){
            next(1);
            Type result = expression();
            consume(TypeToken.Rparen);
            return result;
        }

        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.Point){
            return workStruct();
        }
        if(get(0).getType() == TypeToken.Word){
            String name_obj = get(0).getValue();
            next(1);
            return getObjectOrSetNewValue(name_obj);
        }
        return primitive();
    }

    /*********************************************************************
     * Работа с вызовами функций
     * @return
     * Метод по вызову функций и ее исполнения.
     * При встрече токена Call мы получаем имя конкретной вызываемой функций.
     * Далее парсим входные параметры вызываемой функций (получаем значения от передаваемый объкетов)
     * для передаче параметров по значению. После инициализаций локальных оюъектов вызываемой функций
     * начинаем исполнять тело функций до последней инструкции или же пока не встретим токен return, после чего
     * записываем в возвращаемую переменную результат вычислений функций и возвращаемся в то место откуда нас вызвали.
     * @return
     */
    private Type callFunctionStatement() throws IOException {
        String nameFunc = getNameCallFunc();
        ArrayList<Type> listInputParams = parseInputDataFunction();

        Function currentFunc = searchFunction(nameFunc, listInputParams.size());

        if(currentFunc != null){
            return executeUserFunc(currentFunc, listInputParams);
        }
        return executeSysFunc(nameFunc, listInputParams);
    }

    protected String getNameCallFunc() {
        String name = get(0).getValue();
        next(1);
        return name;
    }

    protected ArrayList<Type> parseInputDataFunction() throws IOException {
        final int BEGIN_SIZE_LOCAL_PARAMS = 6;
        ArrayList<Type> listInputDataParams = new ArrayList<>(BEGIN_SIZE_LOCAL_PARAMS);
        while(true){
            if(get(0).getType() == TypeToken.Push){
                next(1);
                listInputDataParams.add(expression());
                continue;
            }
            break;
        }
        return listInputDataParams;
    }

    protected Function searchFunction(final String nameFunction, final int lengtInputlParams) {
        return FunctionTable.getFunction(nameFunction, lengtInputlParams);
    }

    protected void initializationLocalParamCurrentFunction(final Function function, final ArrayList<Type> initData) {
        visibility++;
        for(int i=0; i<initData.size(); i++){
            String nameNewLocalParam = function.getLocalParams().get(i);
            Type valueNewLocalParam = initData.get(i);
            createLocalObject(nameNewLocalParam, valueNewLocalParam);
        }
    }

    protected void createLocalObject(final String name, final Type value) {
        SteckData.newObject(name, value, visibility);
    }

    protected Type executeBodyFunction(Function currentFunction) {
        try {
            paser = new Parser();
            paser.init_parser(currentFunction.getBody().getTokens(), true);
            paser.run();

            SteckData.deleteObject(visibility);  /** ЧИСТИМ СТЕК ПОСЛЕ ВЫХОДА ИЗ ОБЛАСТИ ВИДИМОСТИ **/
            setFlage_block(false);       /** УСТАГАВЛИВАЕМ ФЛАГ ЧТО ВЫХОДИМ ИЗ БЛОКА STATEMENT`ОВ **/

            visibility--;
        }catch (ReturnStatement returnStatement){
            SteckData.deleteObject(visibility);
            setFlage_block(false);
            visibility--;
            return returnStatement.getRetValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected Type executeSysFunc(final String nameFunc, final ArrayList<Type> listInputParams) throws IOException {
        SysFunction sysFunc = getCurrentSysFunc(nameFunc);
        sysFunc.setInputParams(listInputParams);
        return sysFunc.executeBody();
    }

    protected SysFunction getCurrentSysFunc(final String nameFunc) {
        SysFunction sysFunc = SysFunctionTable.getSysFunc(nameFunc);
        if(sysFunc == null)
            throw new RuntimeException("this function is not defined: "+nameFunc);
        return sysFunc;
    }

    protected Type executeUserFunc(final Function currentFunc, final ArrayList<Type> listInputParams) {
        initializationLocalParamCurrentFunction(currentFunc, listInputParams);
        return executeBodyFunction(currentFunc);
    }

    /*********************************************************************
     * Работа со структурными типами ланных
     * @return
     ********************************************************************/
    private Type workStruct() throws IOException {
        Struct_t result = getNextStructField();
        while(true){
            if(get(0).getType() == TypeToken.Point){
                next(1);
                result = getStruct_t(result, getNextStructField());
                continue;
            }
            break;
        }

        if(get(0).getType() == TypeToken.Equals){
            next(1);
            return writeNewValueStructField(result);
        }
        return readValueStructField(result);
    }


    /**
     * Данный метод создает новый абстрактный тип данных Struct_t
     * который включает в себя атрибуты полей двух принимаемых таких же
     * типов данных. Метод смотрит какой тип установлен у второго примаемого
     * аргумента и в зависимоти от того какой тип он создает требуюмую структуры
     * и так пока не будет закончен весь парсинг.
     * @param struct
     * @param field
     * @return
     */
    private Struct_t getStruct_t(Struct_t struct, Struct_t field) throws IOException {
        switch (field.getType()){
            case Word:
                return createStruct_tWord(struct, field);

            case Array:
                return createStruct_tArray(struct, field);

            case MultiArray:
                return createStruct_tMultiArray(struct, field);

            default: throw new RuntimeException("unknown struct type: "+field.getType());
        }
    }

    private Struct_t createStruct_tMultiArray(Struct_t struct, Struct_t field) throws IOException {
        if(struct.getValue() instanceof StructType){
            return createStructFieldMultiArrayStruct(struct, field);
        }
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = objectData.getStruct(nameStruct);
        Type valueField = structType.getValueField(nameField, field.getIndexFirst(), field.getIndexSecond());

        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setValue(valueField);
        struct_t.setIndexFirst(field.getIndexFirst());
        struct_t.setIndexSecond(field.getIndexSecond());
        struct_t.setType(TypeToken.MultiArray);
        return struct_t;
    }

    private Struct_t createStructFieldMultiArrayStruct(Struct_t struct, Struct_t field) throws IOException {
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = (StructType)struct.getValue();
        Type valueField = structType.getValueField(nameField, field.getIndexFirst(), field.getIndexSecond());

        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setIndexFirst(field.getIndexFirst());
        struct_t.setIndexSecond(field.getIndexSecond());
        struct_t.setValue(valueField);
        struct_t.setType(TypeToken.MultiArray);
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            updateValueMultiArrayStructField(struct_t, structType);
        }
        return struct_t;
    }

    private Struct_t createStruct_tArray(Struct_t struct, Struct_t field) throws IOException {
        if(struct.getValue() instanceof StructType){
            return createStructFieldArrayStruct(struct, field);
        }
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = objectData.getStruct(nameStruct);
        Type valueField = structType.getValueField(nameField, field.getIndexFirst());

        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setValue(valueField);
        struct_t.setIndexFirst(field.getIndexFirst());
        struct_t.setType(TypeToken.Array);
        return struct_t;
    }

    private Struct_t createStructFieldArrayStruct(Struct_t struct, Struct_t field) throws IOException {
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = (StructType)struct.getValue();
        Type valueField = structType.getValueField(nameField, field.getIndexFirst());

        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setIndexFirst(field.getIndexFirst());
        struct_t.setValue(valueField);
        struct_t.setType(TypeToken.Array);
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            updateValueArrayStructField(struct_t, structType);
        }
        return struct_t;
    }

    /**
     * Методы создающие абстрактный тип данных Struct_t
     * для конкретных полей структур (переменные, массивы и сами структуры).
     * @param struct
     * @param field
     * @return
     */
    private Struct_t createStruct_tWord(Struct_t struct, Struct_t field) throws IOException {
        if(struct.getValue() instanceof StructType){
            return createStructFieldWordStruct(struct, field);
        }
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = objectData.getStruct(nameStruct);
        Type valueFiled = structType.getValueField(nameField);

        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setValue(valueFiled);
        struct_t.setType(TypeToken.Word);
        return struct_t;
    }

    private Struct_t createStructFieldWordStruct(Struct_t struct, Struct_t field) throws IOException {
        String nameStruct = struct.getField();
        String nameField = field.getName();

        StructType structType = (StructType)struct.getValue();
        Type valueField = structType.getValueField(nameField);


        Struct_t struct_t = new Struct_t();
        struct_t.setName(nameStruct);
        struct_t.setField(nameField);
        struct_t.setValue(valueField);
        struct_t.setType(TypeToken.Word);
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            updateValueWordStructField(struct_t, structType);
        }
        return struct_t;
    }




    private void updateValueMultiArrayStructField(final Struct_t struct_t, final StructType structType) throws IOException {
        Type newValue = expression();
        structType.setValueField(struct_t.getField(), struct_t.getIndexFirst(),
                struct_t.getIndexSecond(), newValue);
    }

    private void updateValueArrayStructField(final Struct_t struct_t, final StructType structType) throws IOException {
        Type newValue = expression();
        structType.setValueField(struct_t.getField(), struct_t.getIndexFirst(), newValue);
    }


    private void updateValueWordStructField(final Struct_t struct_t, final StructType structType) throws IOException {
        Type newValue = expression();
        structType.setValueField(struct_t.getField(), newValue);
    }


    private Struct_t getNextStructField() throws IOException {
        if(get(0).getType() == TypeToken.Word && get(1).getType() == TypeToken.L_SQUareParen){
            return parseStructFieldArray();
        }
        return parseStructFieldWord();
    }

    /**
     * метод по парсингу типа данных массив поля структуры.
     * Получаем имя массива - поля структуры, индекс затем смотрим не двумерный ли он.
     * Если двумерный передаем имя и индекс в отдельный метод: parseStructFieldMultiArray
     * , если же это одномерный массив то просто создаем Struct_t и заполняем соответсвтующие поля
     * данные и дальше по конвенций.
     * @return
     */
    private Struct_t parseStructFieldArray() throws IOException {
        String name = get(0).getValue();
        next(2);
        Type index = expression();

            if(get(0).getType() == TypeToken.L_SQUareParen){
                next(1);
                return parseStructFieldMultiArray(name, index);
            }

        Struct_t struct_t = new Struct_t();
        struct_t.setNameAndField(name);
        struct_t.setIndexFirst(index);
        struct_t.setType(TypeToken.Array);
        return struct_t;
    }

    /**
     * Метод по парсингу поля структуры - двухмерный массив.
     * Получаем в качестве параметров имя поля массиваи первый индекс
     * далее обрабатываем (вычисляем expression) второй индекс.
     * Все полученные данные передаем в абстрактный тип данных Struct_t и возвращаем
     * @param name
     * @param indexFirst
     * @return
     */
    private Struct_t parseStructFieldMultiArray(String name, Type indexFirst) throws IOException {
        Type indexSecond = expression();

        Struct_t struct_t = new Struct_t();
        struct_t.setNameAndField(name);
        struct_t.setIndexFirst(indexFirst);
        struct_t.setIndexSecond(indexSecond);
        struct_t.setType(TypeToken.MultiArray);
        return struct_t;
    }

    /**
     * Мето дпо парсингу объектов полей структуры.
     * Мы получаем имя этого поля и заполняем соответствующими
     * значениями соответствующие поля типа данных Struct_t
     * @return
     */
    private Struct_t parseStructFieldWord() {
        String name = get(0).getValue();
        next(1);

        Struct_t struct_t = new Struct_t();
        struct_t.setNameAndField(name);
        struct_t.setType(TypeToken.Word);
        return struct_t;
    }

    /**
     * Данный метод получает в качестве параметра финальную структуру
     * содержащую весь пересень необходимых данных для получения
     * значения поля необходимой структуры.
     * @param result
     * @return
     */
    private Type readValueStructField(Struct_t result) {
        return result.getValue();
    }

    /**
     * Данный метод предназначен для обновления значения поля необходимой
     * структуры. Принимает как параметр законченую структуру содержащую все данные
     * для оперделения с какой структурой работаем, вычисляем новое(левостоящее выражение)
     * и обновляем (пишем) новое значение поверх старого.
     * @param result
     * @return
     */
    private Type writeNewValueStructField(Struct_t result) throws IOException {
        switch (result.getType()){
            case Word : return writeNewValueWordStrucrField(result);

            case Array : return writeNewValueArrayStructField(result);

            case MultiArray : return writeNewValueMultiArrayStructField(result);

            default: throw new RuntimeException("Unknown struct type: " + result.getType()+
                    " ["+result.getName()+"] : name struct ["+result.getField()+"] : name struct field");
        }
    }

    private Type writeNewValueMultiArrayStructField(Struct_t result) throws IOException {
        Type newValue = expression();
        StructType structType = objectData.getStruct(result.getName());
        structType.setValueField(result.getField(), result.getIndexFirst(), result.getIndexSecond(),  newValue);
        return voidType;
    }

    private Type writeNewValueArrayStructField(Struct_t result) throws IOException {
        Type newValue = expression();
        StructType structType = objectData.getStruct(result.getName());
        structType.setValueField(result.getField(), result.getIndexFirst(), newValue);
        return voidType;
    }

    private Type writeNewValueWordStrucrField(Struct_t result) throws IOException {
        Type newValue = expression();
        StructType structType = objectData.getStruct(result.getName());
        structType.setValueField(result.getField(), newValue);
        return voidType;
    }


    /**************************************************************************************
     * РАБОТА С ПРИМИТИВНЫМИ И АГРЕГИРУЮЩИМИ ТИПАМИ ДАННЫХ
     * (ПЕРЕМЕННЫЕ, МАССИВЫ)
     * @param name_obj
     * @return
     ******************************************************/
    private Type getObjectOrSetNewValue(String name_obj) throws IOException {
        if(get(0).getType() == TypeToken.L_SQUareParen){
            next(1);
            return getArrayOrSetNewValue(name_obj);
        }
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            return setNewValueVariable(name_obj, expression());
        }

        return getValueObject(name_obj);
    }


    private Type getArrayOrSetNewValue(String name_obj) throws IOException {
        Type index = expression();
        if(get(0).getType() == TypeToken.L_SQUareParen){
            next(1);
            return getMultiArrayOrSetNewValue(name_obj, index);
        }
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            return setNewValueArray(name_obj, index, expression());
        }

        return getValueArray(name_obj, index);
    }

    private Type getMultiArrayOrSetNewValue(String name_obj, Type indexFirst) throws IOException {
        Type indexSecond = expression();
        if(get(0).getType() == TypeToken.Equals){
            next(1);
            return setNewValueMultiArray(name_obj, indexFirst, indexSecond, expression());
        }
        return getValueMultiArray(name_obj, indexFirst, indexSecond);
    }

    /**     установка новых значений объектов   **/
    private Type setNewValueArray(String name_obj, Type index, Type newValue) {
        objectData.setNewValue(name_obj, index, newValue);
        return voidType;
    }

    private Type setNewValueVariable(String name_obj, Type expression) {
        objectData.setNewValue(name_obj, expression);
        return voidType;
    }

    private Type setNewValueMultiArray(String name_obj, Type indexFirst, Type indexSecond, Type newValue) {
        objectData.setNewValue(name_obj, indexFirst, indexSecond, newValue);
        return voidType;
    }

    /**     возвращение значений объектов   **/
    private Type getValueMultiArray(String name_obj, Type indexFirst, Type indexSecond) {
        return objectData.getObject(name_obj, indexFirst, indexSecond);
    }

    private Type getValueArray(String name_obj, Type index) {
        return objectData.getObject(name_obj, index);
    }

    private Type getValueObject(String name_obj) {
        return objectData.getObject(name_obj);
    }
}
