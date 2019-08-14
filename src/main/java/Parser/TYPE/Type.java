package Parser.TYPE;

public interface Type {

     int asInteger32(); /** привести к типу int32**/
     double asDouble64(); /** привести к типу double64 **/
     String asString();   /** привести к типу String **/
     boolean asBool();    /** привести к типу Bool **/

}
