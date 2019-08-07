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

    private void initLoc_param(List<StringBuilder> param) {
        int size = param.size();
        this.loc_param = new ArrayList<StringBuilder>(size);
        for(int i=0; i<size; i++){
            this.loc_param.add(param.get(i));
        }
    }
}
