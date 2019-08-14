package Parser.TYPE;

public class BoolType implements Type {
    private String value;

    public BoolType(String value) {
        this.value = value;
    }

    @Override
    public int asInteger32() {
        return 0;
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
        if(value.equalsIgnoreCase("1"))return true;
        else if(value.equalsIgnoreCase("0"))return false;
        else throw new RuntimeException("Error Type");
    }
}
