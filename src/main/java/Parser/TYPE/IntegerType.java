package Parser.TYPE;
/** Кдасс тип Integer32 **/
public class IntegerType implements Type {
    private String value;

    public IntegerType(final String value){
        this.value = value;
    }
    public IntegerType(final int value){
        this.value = String.valueOf(value);
    }
    @Override
    public int asInteger32() {
        return Integer.parseInt(value);
    }

    @Override
    public double asDouble64() {
        return 0;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public boolean asBool() {
        return false;
    }
}
