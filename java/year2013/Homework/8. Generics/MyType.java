public interface MyType<E> {
    E add(E a);
    E mul(E a);
    E subtract(E a);
    E divide(E a);
    E abs();
    E mod(E a);
    E neg();
    E not();
    E stringToMyType(String s);
    String myTypeToString();
    E getValue(int a);
    Number getType();
}
