package cz.holsoft.single4j;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * HOLMAN_O
 */
public class SingleInstanceSerializer extends StdSerializer<SingleInstance> {

    public SingleInstanceSerializer() {
        this(null);
    }

    public SingleInstanceSerializer(Class<SingleInstance> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(SingleInstance singleInstance, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("id", singleInstance.getId());
        jsonGenerator.writeEndObject();
    }
}
