package Parser.Library.VARIABLE;

import java.util.ArrayList;
import java.util.List;

public class VariableTable {
    private Variable variable;
    private static List<Variable> variable_table;

    public VariableTable(final int init_size_variable_table){
        this.variable_table = new ArrayList<Variable>(init_size_variable_table);
    }/** инициализация таблицы переменных с задание изначального размера под таблицу**/

    public VariableTable(){
        this.variable_table = new ArrayList<Variable>(); /** инициализация таблицы переменных**/
    }

    public void pustVariable(final Variable variable){
        this.variable_table.add(variable);
    } /** добавить переменную в таблицу переменных **/
    public Variable getVariable(final String name){
        Variable temp = null;
        for(int i=0; i<variable_table.size(); i++){
            temp = variable_table.get(i);
            if(temp.getVariableName().equalsIgnoreCase(name))return temp;
        }
        throw new RuntimeException("Errorrr!");
    }

    public List<Variable> getVariableTable(){ return this.variable_table; } /** получить ссылку на табдицу переменных **/
}
