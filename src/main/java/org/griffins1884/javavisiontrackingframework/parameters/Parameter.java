package org.griffins1884.javavisiontrackingframework.parameters;

public abstract class Parameter {
    private final String key;
    
    protected Parameter(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}