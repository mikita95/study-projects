package dividers;

import data.Data;

/**
 * Created by nikita on 11.10.16.
 */
public abstract class Divider implements Iterable<Division> {
    protected Data data;

    public Divider(Data data) {
        this.data = data;
    }
}
