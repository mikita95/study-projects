import java.math.BigInteger;

public class MyBigInteger implements MyType<MyBigInteger> {
    private BigInteger a;

    public MyBigInteger stringToMyType(String s) {
        return new MyBigInteger(new BigInteger(s));
    }

    public String myTypeToString() {
        return String.valueOf(getBigInteger());
    }


    public MyBigInteger getValue(int a) {
        return new MyBigInteger(a);
    }

    MyBigInteger(BigInteger a) {
        this.a = a;
    }

    MyBigInteger(int a) {
        this.a = BigInteger.valueOf(a);
    }

    BigInteger getBigInteger() {
        return this.a;
    }


    MyBigInteger() {
        this.a = BigInteger.ZERO;
    }

    public MyBigInteger add(MyBigInteger a) {

        return new MyBigInteger(getBigInteger().add(a.getBigInteger()));
    }

    public BigInteger getType() {
        return this.a;

    }

    public MyBigInteger subtract(MyBigInteger a) {
        return new MyBigInteger((getBigInteger().subtract(a.getBigInteger())));
    }

    public MyBigInteger divide(MyBigInteger a) {
        return new MyBigInteger((getBigInteger().divide(a.getBigInteger())));
    }

    public MyBigInteger mul(MyBigInteger a) {
        return new MyBigInteger((getBigInteger().multiply(a.getBigInteger())));
    }

    public MyBigInteger abs() {
        return new MyBigInteger(getBigInteger().abs());
    }

    public MyBigInteger neg() {
        return new MyBigInteger(BigInteger.valueOf(0).subtract((getBigInteger())));
    }

    public MyBigInteger not() {

        return new MyBigInteger(getBigInteger().not());

    }

    public MyBigInteger mod(MyBigInteger a) {
        return new MyBigInteger(getBigInteger().mod(a.getBigInteger()));
    }

}