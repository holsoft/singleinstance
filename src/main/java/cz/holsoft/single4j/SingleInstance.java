package cz.holsoft.single4j;

/**
 * HOLMAN_O
 */
public class SingleInstance<OBJ_TYPE, ID_TYPE> {
    SingleInstance() {}

    OBJ_TYPE object;
    ID_TYPE id;

    public OBJ_TYPE getObject() {
        return object;
    }

    public ID_TYPE getId() {
        return id;
    }
}
