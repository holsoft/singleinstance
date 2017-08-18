package cz.holsoft.single4j;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.holsoft.single4j.testmodel.TestObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * HOLMAN_O
 */

public class SingleInstanceTests {


    private ObjectMapper objectMapper;
    private SingleInstanceManager singleInstanceManager;
    private TestObject testObject1;
    private TestObject testObject2;

    @Before
    public void cleanObjects() {
        singleInstanceManager = new HashMapSingleInstanceManager();
        testObject1 = new TestObject("First object", 1L);
        testObject2 = new TestObject("Second object", 2L);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(SingleInstanceManager.getSerializationModule(singleInstanceManager));
    }

    @Test
    public void identityTest() {
        SingleInstance<TestObject, Long> singleInstance = singleInstanceManager.getOrCreate(testObject1, testObject1.getId());
        SingleInstance<TestObject, Long> singleInstance2 = singleInstanceManager.getOrCreate(testObject1, testObject1.getId());
        assertSame(singleInstance.getObject(), singleInstance2.getObject());
    }

    @Test
    public void simpleSerializationTest() throws IOException {
        SingleInstance<TestObject, Long> singleInstance = singleInstanceManager.getOrCreate(testObject1, testObject1.getId());
        String serializedInstance = objectMapper.writeValueAsString(singleInstance);
        SingleInstance<TestObject, Long> deserializedInstance = objectMapper.readValue(serializedInstance, new TypeReference<SingleInstance<TestObject, Long>>() {});
        assertSame(singleInstance.getObject(), deserializedInstance.getObject());
    }

    @Test
    public void nestedTest() throws IOException {
        SingleInstance singleInstance = singleInstanceManager.getOrCreate(testObject1, testObject1.getId());
        testObject2.setNested(singleInstance);
        String serializedInstance = objectMapper.writeValueAsString(testObject2);
        TestObject deserializedTestObject = objectMapper.readValue(serializedInstance, TestObject.class);
        assertSame(singleInstance.getObject(), deserializedTestObject.getNested().getObject());
    }

    @Test
    public void emptyTest() {
        SingleInstance singleInstance = singleInstanceManager.get(TestObject.class, testObject1.getId());
        assertNull(singleInstance.getObject());
        singleInstance = singleInstanceManager.getOrCreate(testObject1, testObject1.getId());
        assertNotNull(singleInstance.getObject());
    }
}
