package Parser.Library.VARIABLE;

import Parser.TYPE.Type;

public class Variable {
    private String variable_name;
    private Type variable_value;
    private int area_of_visibility; /** обдасти видимости переменных **/

    public Variable(final String variable_name, final Type variable_value) {
        this.variable_name = variable_name;
        this.variable_value = variable_value;
    }
    public Variable(final String variable_name, final Type variable_value, final int area_of_visibility) {
        this.variable_name = variable_name;
        this.variable_value = variable_value;
        this.area_of_visibility = area_of_visibility;
    }
    public void setVisibility(final int area_of_visibility){ this.area_of_visibility = area_of_visibility; }
    public int getVisibility(){ return this.area_of_visibility; }
    public String getVariableName(){ return variable_name; }
    public Type getVariableValue(){ return variable_value; }
    public void setVariableName(final String variable_name){ this.variable_name = variable_name; }
    public void setVariableValue(final Type value){ this.variable_value = value; }
}
