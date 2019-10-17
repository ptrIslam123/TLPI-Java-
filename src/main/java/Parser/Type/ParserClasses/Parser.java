package Parser.Type.ParserClasses;

import Lexer.TypeToken;
import Lexer.*;
import Parser.DATA_SEGMENT.ObjectArray;
import Parser.DATA_SEGMENT.SegmentData;
import Parser.DATA_SEGMENT.SteckData;
import Parser.Statement.ControlConstruction.BreakStatement;
import Parser.Statement.ControlConstruction.ContinueStatement;
import Parser.Statement.Statement;
import Parser.Type.Integral.IntegerType;
import Parser.Type.PrimitiveType;
import Parser.Type.StructType.Struct.Struct;
import Parser.Type.StructType.Struct.StructBody;
import Parser.Type.StructType.Struct.StructVector;
import Parser.Type.Types.Type;
import java.io.IOException;
import java.util.ArrayList;

public class Parser extends ExpressionEval {
    private static StructBody structBody;

    public void init_parser(ArrayList<Token> tokens, boolean flage_block){
      init_expression_eval(tokens);
      setPos(0);
      this.flage_block = flage_block;
    }

    public void run() throws IOException {
        //for(Token it : getTokens()) System.out.println(it.getType() + " : " + it.getValue());
        //System.out.println("================");

        while(cond()){
           statement();
        }
    }

    private Statement executeBlockStatmentOrStatment() throws IOException {
        if(get(0).getType() == TypeToken.Block){
            blockStatement();
            next(1);
            return null;
        }
        return statement();
    }
    private Statement blockStatement() throws IOException {
        paser = new Parser();
        TokenBlock block = (TokenBlock) get(0);
        paser.init_parser(block.getTokens(), true);

        visibility++;

        paser.run();

        SteckData.deleteObject(visibility);  /** ЧИСТИМ СТЕК ПОСЛЕ ВЫХОДА ИЗ ОБЛАСТИ ВИДИМОСТИ **/
        setFlage_block(false);       /** УСТАГАВЛИВАЕМ ФЛАГ ЧТО ВЫХОДИМ ИЗ БЛОКА STATEMENT`ОВ **/

        visibility--;
        return null;
    }

    private Statement statement() throws IOException {
        if(get(0).getType() == TypeToken.If){
            next(1);
            return ifStatement();
        }
        if(get(0).getType() == TypeToken.While){
            next(1);
            return whileStatement();
        }
        if(get(0).getType() == TypeToken.Block){
            blockStatement();
            next(1);
            return null;
        }
        if(get(0).getType() == TypeToken.sys_writeln){
            next(1);
            writeStream.streamInitialize(expression(), true);
            writeStream.execute();
            return null;
        }
        if(get(0).getType() == TypeToken.sys_write){
            next(1);
            writeStream.streamInitialize(expression());
            writeStream.execute();
            return null;
        }
        if(get(0).getType() == TypeToken.Alloc){
           next(1);

            init_static_object();

           String name_obj = get(0).getValue();
           next(1);
           declareObject(name_obj);
           return null;
        }
        if(get(0).getType() == TypeToken.Struct){
            String name_struct = get(1).getValue();
            next(2);
            declareStruct(name_struct);
            return null;
        }
        primary();
        return null;
    }

    private Statement whileStatement() throws IOException {
        int beginLoopPosition = getPos();
        while(expression().asPrimitive().asBool()){
            try {
                executeBlockStatmentOrStatment();
                setPos(beginLoopPosition);
            }catch (BreakStatement breakStatement){
                break;
            }catch (ContinueStatement continueStatement){
                continue;
            }
        }
        next(1);
        return null;
    }

    private Statement ifStatement() throws IOException {
        boolean conditionResult = expression().asPrimitive().asBool();
        if(conditionResult) {
           executeBlockStatmentOrStatment();
        }else next(1);

        if(get(0).getType() == TypeToken.Els){
            next(1);
            if(conditionResult == false){
               executeBlockStatmentOrStatment();
            }
            next(1);
        }
        return null;
    }


    /***********************************************************
     *
     * @param name_struct
     **********************************************************/
    private void declareStruct(String name_struct) throws IOException {
        structBody = new StructBody();
        setFlage_struct(true);
        executeBlockStatmentOrStatment();
        setFlage_struct(false);
        /** генерация структуры и ее полей **/
        StructVector.declareStruct(name_struct, structBody);
    }

    /** декларирование объектов **/
    private Statement declareObject(final String name_obj) throws IOException {
       if(get(0).getType() == TypeToken.Index){
           return declareArray(name_obj);
       }

       if(get(0).getType() == TypeToken.Equals){
           next(1);
           return declareVariable(name_obj, expression());
       }

       if(get(0).getType() == TypeToken.Word){
           String name_struct_dec = get(0).getValue();
           next(1);
           return memory_Allocation_the_structure(name_obj, name_struct_dec);
       }
       return declareVariable(name_obj, new PrimitiveType(new IntegerType("0")));
    }

    private Statement memory_Allocation_the_structure(String name_obj, String name_struct_dec) {
        Struct struct = StructVector.getStruct(name_obj);
        createObject(name_obj, name_struct_dec, struct);
        return null;
    }

    /** ДЕКЛОРИРОВАНИЕ МАССИВА **/
    private Statement declareArray(String name_obj) throws IOException {
        Type capasity = evalIndexToken((TokenIndex)get(0));
        ArrayList<Type> init_data = null;

        if(get(0).getType() == TypeToken.Index){
            return declareMultiArray(name_obj, capasity);
        }

        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(2);
            init_data = parse_init_data_array();
        }
        createObject(name_obj, capasity, init_data);
        return null;
    }

    private ArrayList<Type> parse_init_data_array() throws IOException {
        ArrayList<Type> array_data = new ArrayList<>();
        while(cond(TypeToken.Rparen)){
            if(get(0).getType() == TypeToken.Comma)next(1);
            array_data.add(expression());
        }
        next(1);
        return array_data;
    }
    /** ДЕКЛАРИРОВАНИЕ ДВУМЕРНОГО МАССИВА **/
    private Statement declareMultiArray(String name_obj, Type capasityFirst) throws IOException {
        Type capasitySecond = evalIndexToken((TokenIndex) get(0));
        ArrayList<ObjectArray> init_data = null;

        if(get(0).getType() == TypeToken.Equals && get(1).getType() == TypeToken.Lparen){
            next(3);
            init_data = parse_init_data_multi_array();
        }
        createObject(name_obj, capasityFirst, capasitySecond, init_data);
        return null;
    }

    /** ПАРСИНГ НАЧАЛЬНЫЙ - ИНИЦИАЛИЗАЦИОННЫХ ДАННЫХ ДВУМЕРНОГО МАССИВА **/
    private ArrayList<ObjectArray> parse_init_data_multi_array() throws IOException {
        ArrayList<ObjectArray> array_data = new ArrayList<>();
        while(cond(TypeToken.Rparen)) {
            if(get(0).getType() == TypeToken.Lparen)next(1);
            ObjectArray array = new ObjectArray();
            while(cond(TypeToken.Rparen)){
                array.add(expression());
                if(get(0).getType() == TypeToken.Comma)next(1);
            }
            next(1);
            array_data.add(array);
            if(get(0).getType() == TypeToken.Comma)next(1);
        }
        next(1);
        return array_data;
    }
    /** ДЕКЛАРИРОВАНИЕ ПЕРЕМЕННЫХ  **/
    private Statement declareVariable(String name_obj, Type value) {
        createObject(name_obj, value); // createObject(name_obj, expression);
        return null;
    }
    private boolean isCapasity(){
        if(get(0).getType() == TypeToken.NumInt32 || get(0).getType() == TypeToken.Lparen || get(0).getType() == TypeToken.Sub){
            return true;
        }
        return false;
    }
    /** Декларирование структурного типа данных **/
    private void createObject(final String name_struct, final String name, final Struct struct){
        if(isFlage_struct() == true){
            structBody.put(name_struct, name, struct, visibility);
            return;
        }
        SegmentData.newObject(name_struct, name, struct, visibility);
    }
    /** Декларирование переменной **/
    private void createObject(final String name_obj, final Type value){
        if(isFlage_struct() == true){
            structBody.put(name_obj, value, -1);
            return;
        }
        if(isFlage_static() == true){
            SegmentData.newObject(name_obj, value, visibility);
            setFlage_static(false);
            return;
        }
        else if(isFlage_block() == true){
            SteckData.newObject(name_obj, value, visibility);
            return;
        }
        SegmentData.newObject(name_obj, value, visibility);
    }
    /** Декларирование объкта массива  **/
    private void createObject(final String name_obj, final Type capasity, final ArrayList<Type> init_data){
        if(isFlage_struct() == true){
            structBody.put(name_obj, capasity, init_data,  -1);
            return;
        }
        if(isFlage_static() == true){
            SegmentData.newObject(name_obj, capasity, init_data, visibility);
            setFlage_static(false);
            return;
        }
        else if(isFlage_block() == true){
            SteckData.newObject(name_obj, capasity, init_data, visibility);
            return;
        }
        SegmentData.newObject(name_obj, capasity, init_data, visibility);
    }
    /** Декларирование объекта двумерного массива  **/
    private void createObject(final String name_obj, final Type capasity_1, final Type capasity_2, final ArrayList<ObjectArray> init_data){
        if(isFlage_struct() == true){
            structBody.put(name_obj, capasity_1, capasity_2, init_data,  -1);
            return;
        }
        if(isFlage_static() == true){
            SegmentData.newObject(name_obj, capasity_1, capasity_2, init_data, visibility);
            setFlage_static(false);
            return;
        }
        else if(isFlage_block() == true){
            SteckData.newObject(name_obj, capasity_1, capasity_2, init_data, visibility);
            return;
        }
        SegmentData.newObject(name_obj, capasity_1, capasity_2, init_data, visibility);
    }
    private void init_static_object(){
        if(get(0).getType() == TypeToken.Static_o){
            next(1);
            setFlage_static(true);
        }
    } /** ИНИЦИАЛИЗАЦИЯ СТАТИЧЕСКИХ ОБЪЕКТОВ **/
}
