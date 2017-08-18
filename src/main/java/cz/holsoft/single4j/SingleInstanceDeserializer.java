package cz.holsoft.single4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

/**
 * HOLMAN_O
 */
public class SingleInstanceDeserializer extends JsonDeserializer<SingleInstance<?,?>> implements ContextualDeserializer {

    SingleInstanceManager manager;
    private JavaType keyType;
    private JavaType objectType;

    public SingleInstanceDeserializer(SingleInstanceManager manager) {
        this.manager = manager;
    }

    @Override
    public SingleInstance<?,?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if(keyType == null) return null;
        jsonParser.nextToken();
        jsonParser.nextToken();
        Object id = deserializationContext.readValue(jsonParser, keyType);
        return manager.get(objectType.getRawClass(), id);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType jKeyType;
        JavaType jObjType;
        if (property != null) {
            JavaType propertyType = property.getType();
            jKeyType = propertyType.containedType(1);
            jObjType = propertyType.containedType(0);
        }
        else {
            jKeyType = ctxt.getContextualType().containedType(1);
            jObjType = ctxt.getContextualType().containedType(0);
        }
        SingleInstanceDeserializer deserializer = new SingleInstanceDeserializer(manager);
        deserializer.keyType = jKeyType;
        deserializer.objectType = jObjType;
        return deserializer;
    }
}
