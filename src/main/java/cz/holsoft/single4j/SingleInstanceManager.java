package cz.holsoft.single4j;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * HOLMAN_O
 */
public abstract class SingleInstanceManager {


    public <B, W> SingleInstance<B, W> getOrCreate(B object, W id) {
         SingleInstance<B, W> targetInstance = retrieve(object.getClass(), id);
        if(targetInstance.getObject() == null) {
            targetInstance.object = object;
            save(targetInstance);
        }

        return targetInstance;
    }

    public <B,W> SingleInstance<B,W> create(B object, W id) {
        SingleInstance<B, W> targetInstance = new SingleInstance<>();
        targetInstance.id = id;
        targetInstance.object = object;
        save(targetInstance);
        return targetInstance;
    }

    public <B,W> SingleInstance<B,W> get(Class<B> type, W id) {
        return retrieve(type, id);
    }

    public static SimpleModule getSerializationModule(SingleInstanceManager sim) {
        SimpleModule singleInstanceModule = new SimpleModule();
        singleInstanceModule.addSerializer(SingleInstance.class, new SingleInstanceSerializer());
        singleInstanceModule.addDeserializer(SingleInstance.class, new SingleInstanceDeserializer(sim));
        return singleInstanceModule;
    }

    protected abstract <B, W> void save(SingleInstance<B, W> targetInstance);
    protected abstract <B, W> SingleInstance<B,W> retrieve(Class<?> type, W id);
}
