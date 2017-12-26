public class MyInteger implements MyType<MyInteger> {
    private Integer a;

    public MyInteger stringToMyType(String s) {
        return new MyInteger(Integer.parseInt(s));
    }

    public String myTypeToString() {
        return String.valueOf(getInteger());
    }

    public Integer getType() {
        return this.a;

    }

    MyInteger() {
        this.a = 0;
    }

    MyInteger(Integer a) {
        this.a = a;
    }

    public Integer getInteger() {
        return this.a;
    }

    public MyInteger getValue(int a) {
        return new MyInteger(a);
    }


    public MyInteger add(MyInteger a) {

        return new MyInteger(getInteger() + a.getInteger());
    }


    public MyInteger subtract(MyInteger a) {
        return new MyInteger(getInteger() - a.getInteger());
    }

    public MyInteger divide(MyInteger a) {
        return new MyInteger(getInteger() / a.getInteger());
    }

    public MyInteger mul(MyInteger a) {
        return new MyInteger(getInteger() * a.getInteger());
    }

    public MyInteger abs() {
        return new MyInteger(Math.abs(getInteger()));
    }

    public MyInteger neg() {
        return new MyInteger(-getInteger());
    }

    public MyInteger not() {

        return new MyInteger(~getInteger());

    }

    public MyInteger mod(MyInteger a) {
        return new MyInteger(getInteger() % a.getInteger());
    }

}
