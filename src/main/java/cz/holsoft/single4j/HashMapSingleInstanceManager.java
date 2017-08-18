package cz.holsoft.single4j;

import java.util.HashMap;
import java.util.Map;

/**
 * HOLMAN_O
 */
public class HashMapSingleInstanceManager extends SingleInstanceManager {
    private Map<Class, Map<Object, SingleInstance<?,?>>> instances = new HashMap<>();

    @Override
    protected <B, W> void save(SingleInstance<B, W> targetInstance) {
        Map<Object, SingleInstance<?, ?>> map = instances.get(targetInstance.getObject().getClass());
        if(map == null) {
            instances.put(targetInstance.getObject().getClass(), new HashMap<>());
            map = instances.get(targetInstance.getObject().getClass()); //TODO fix this bullcrap
        }
        map.put(targetInstance.getId(), targetInstance);
    }

    @Override
    protected <B, W> SingleInstance<B, W> retrieve(Class<?> type, W id) {
        Map<?, ? extends SingleInstance> map = instances.get(type);
        SingleInstance<B,W> instance = null;
        if(map == null) {
            instance = new SingleInstance<>();
            instance.id = id;
            return instance;
        }
        instance = map.get(id);
        if(instance == null) {
            instance = new SingleInstance<>();
            instance.id = id;
        }
        return instance;
    }
}
