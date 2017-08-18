package cz.holsoft.single4j.testmodel;

import cz.holsoft.single4j.SingleInstance;

/**
 * HOLMAN_O
 */
public class TestObject {
    private String name;
    private Long id;
    private SingleInstance<TestObject, Long> nested;

    public TestObject() {}

    public TestObject(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SingleInstance<TestObject, Long> getNested() {
        return nested;
    }

    public void setNested(SingleInstance<TestObject, Long> nested) {
        this.nested = nested;
    }
}
