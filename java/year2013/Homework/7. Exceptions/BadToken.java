public class BadToken extends Exception {
    public BadToken(String message) {
        super(message);
    }

    public BadToken(String message, Throwable cause) {
        super(message, cause);
    }

}
