package database;

public class ProductsDaoException extends RuntimeException {
    public ProductsDaoException() {
        super();
    }

    public ProductsDaoException(String message) {
        super(message);
    }

    public ProductsDaoException(Throwable throwable) {
        super(throwable);
    }

    public ProductsDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}