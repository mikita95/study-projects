package messages;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public class CachedRecord extends Record {
    public CachedRecord(String name, Type type, RRClass recordClass, int ttl) {
        super(name, type, recordClass, ttl);
    }
}