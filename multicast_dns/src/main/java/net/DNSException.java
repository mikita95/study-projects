package net;

/**
 * Created by Nikita_Markovnikov on 2/27/2017.
 */
public class DNSException extends Exception {
    public DNSException() {
        super();
    }

    public DNSException(Throwable throwable) {
        super(throwable);
    }

    public DNSException(String message) {
        super(message);
    }
}
