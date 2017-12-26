public class Pair<T> {
    String expressionType;
    T additionalInfo;

    public Pair(String expressionType, T additionalInfo) {
        this.expressionType = expressionType;
        this.additionalInfo = additionalInfo;
    }
}
