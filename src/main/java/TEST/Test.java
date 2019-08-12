package TEST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    private List<String> loc_param = new ArrayList<String>();
    private List<String> in_param = new ArrayList<String>();
    private Map<String, String> defMap = new HashMap<String, String>();
    private String body = "num1+num2";

    public void run(){
        StringBuilder buffer = new StringBuilder();
        init();
        int[] index = null;
        int count = 0, result_equals = 0;
        for(int i=0; true; i++){

            index =  searchP(loc_param, body.charAt(i));
            if(cond_sum(index) > 0) {// if even true
                count = ret_index(count, index);
                result_equals = equals_str(body, loc_param, count, i);
                if(result_equals != 0) {

                    //System.out.println("p: "+i+"\t\tv:"+body.charAt(i));
                    buffer.append(getValMap(loc_param.get(count)));
                    i+=result_equals;
                }
                count++;
                if(i>=body.length())break;
            }
            buffer.append(body.charAt(i));
        }
        System.out.println(buffer.toString());
    }

    private int equals_str(String body, List<String> loc_param, int index_p, int beg){
        int j=0;
        int len = loc_param.get(index_p).length();
        for(int i=beg; i<len; i++, j++){
            if(body.charAt(i) != loc_param.get(index_p).charAt(j))return 0; // false
        }
        //System.out.println("shift = " + loc_param.get(index_p).length());
        return loc_param.get(index_p).length(); // true
    }

    private int ret_index(int beg, int[] index){
        for(int i=beg; i<index.length; i++){
            if(index[i] != -1)return i;
        }
        return -1;
    }
    private int cond_sum(int[] arr){
        int sum = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i] >= 0){
                sum++;
            }
        }
        return sum;
    }

    private int[] searchP(List<String> loc_param, char current){
        int len = loc_param.size();
        int[] index_p = new int[len];
        for(int i=0; i<len; i++){
            if(current == loc_param.get(i).charAt(0)){
                index_p[i] = i;
            }else index_p[i] = -1;
        }
        return index_p;
    }
    private String getValMap(String def_name){
        return defMap.get(def_name);
    }

    private void init(){
        loc_param.add("num1");
        loc_param.add("num2");
        in_param.add("10");
        in_param.add("20");

        defMap.put("num1","10");
        defMap.put("num2","20");
    }
}
