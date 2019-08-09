package Preprocessor.DEFINE;

import java.util.ArrayList;
import java.util.List;

public class Defines {
    private StringBuilder defExprName, defExprBody;
    private List<StringBuilder> loc_param, in_param;

    public Defines(final StringBuilder defExprName, final List<StringBuilder> loc_param, final StringBuilder defExprBody){
        this.defExprName = defExprName;
        this.defExprBody = defExprBody;
        initLoc_param(loc_param);
    }

    public StringBuilder getName(){ return defExprName; }
    public StringBuilder getBody(){ return defExprBody; }
    public List<StringBuilder> getLoc_param(){ return loc_param; }
    public List<StringBuilder> getIn_param(){ return in_param; }
    public void setIn_param(final List<StringBuilder> in_param){
        int size = in_param.size();
        this.in_param = new ArrayList<StringBuilder>(size);
        for(int i=0; i<size; i++){
            this.in_param.add(in_param.get(i));
        }
    }

    public StringBuilder getValueParam(final StringBuilder defParamName){
        int sizeof = loc_param.size(), setIndex = 0;
        for(int i=0; i<sizeof; i++){
            if(equalsStr(defParamName, loc_param.get(i)) == true)setIndex = i;
        }
        return in_param.get(setIndex);
    } /** метод по получению значения по имени локального параметра**/

    private boolean equalsStr(final StringBuilder expr1, final StringBuilder expr2){
        String ex1 = expr1.toString();
        String ex2 = expr2.toString();
        if(ex1.equalsIgnoreCase(ex2))return true;
        else return false;
    }
    private void initLoc_param(List<StringBuilder> param) {
        int size = param.size();
        this.loc_param = new ArrayList<StringBuilder>(size);
        for(int i=0; i<size; i++){
            this.loc_param.add(param.get(i));
        }
    }
}
