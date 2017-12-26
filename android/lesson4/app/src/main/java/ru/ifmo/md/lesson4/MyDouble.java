package ru.ifmo.md.lesson4;
public class MyDouble implements MyType<MyDouble> {
    private Double a;

    public MyDouble stringToMyType(String s) {
        return new MyDouble(Double.parseDouble(s));
    }

    public String myTypeToString() {
        return String.valueOf(getDouble());
    }


    public MyDouble getValue(int a) {
        return new MyDouble(a);
    }

    MyDouble(Double a) {
        this.a = a;
    }

    Double getDouble() {
        return this.a;
    }

    MyDouble(double a) {
        this.a = a;
    }

    MyDouble() {
        this.a = 0.;
    }
    public MyDouble add(MyDouble a) {

        return new MyDouble(getDouble() + a.getDouble());
    }

    public Double getType() {
        return this.a;

    }


    public MyDouble subtract(MyDouble a) {
        return new MyDouble(getDouble() - a.getDouble());
    }

    public MyDouble divide(MyDouble a) {
        return new MyDouble(getDouble() / a.getDouble());
    }

    public MyDouble mul(MyDouble a) {
        return new MyDouble(getDouble() * a.getDouble());
    }

    public MyDouble abs() {
        return new MyDouble(Math.abs(getDouble()));
    }

    public MyDouble neg() {
        return new MyDouble(-getDouble());
    }

    public MyDouble plus() {
        return new MyDouble(getDouble());
    }

    public MyDouble not() {

        return new MyDouble(getDouble());

    }

    public MyDouble mod(MyDouble a) {
        return new MyDouble(getDouble() % a.getDouble());
    }

}