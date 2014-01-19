package org.griffins1884.javavisiontrackingframework.parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

import org.griffins1884.javavisiontrackingframework.App;
import org.griffins1884.javavisiontrackingframework.ui.ImageDisplay;
import org.griffins1884.javavisiontrackingframework.ui.SettingsDisplay;

import com.google.gson.Gson;

public class ParameterStore {
    private static HashMap<String, Parameter> parameters;
    static {
        parameters = new HashMap<String, Parameter>();
        App.__static_load__();
        ImageDisplay.__static_load__();
    }
    
    public static final class ParameterType {
        public static final String NUMBER = "number",
                                   STRING = "string";
    }
    
    public static Parameter getParameter(String key) {
        return parameters.get(key);
    }
    
    protected static NumberParameter createNumberParameter(String key, double initialValue) {
        return createNumberParameter(key, initialValue, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    public static NumberParameter getNumberParameter(String key) {
        Parameter result = parameters.get(key);
        if(result != null) {
            return (NumberParameter) result;
        }
        return createNumberParameter(key, 0.0);
    }
    
    protected static NumberParameter createNumberParameter(String key, double initialValue, double min, double max) {
        NumberParameter result = new NumberParameter(key, initialValue, min, max);
        parameters.put(result.getKey(), result);
        result.setSlider(SettingsDisplay.addNumberParameter(result));
        return result;
    }
    
    protected static StringParameter createStringParameter(String key, String initialValue) {
        StringParameter result = new StringParameter(key, initialValue);
        parameters.put(result.getKey(), result);
        result.setEditor(SettingsDisplay.addStringParameter(result));
        return result;
    }
    
    public static StringParameter getStringParameter(String key) {
        Parameter result = parameters.get(key);
        if(result != null) {
            return (StringParameter) result;
        }
        return createStringParameter(key, "");
    }
    
    @SuppressWarnings("unused")
    private static class TemporaryParameterClass {
        private String key;
        private Object value;
        private Double min, max;
        
        public String getKey() {
            return this.key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public void setValue(Object value) {
            this.value = value;
        }
        
        public Double getMin() {
            return this.min;
        }
        
        public void setMin(Double min) {
            this.min = min;
        }
        
        public Double getMax() {
            return this.max;
        }
        
        public void setMax(Double max) {
            this.max = max;
        }
    }
    
    public static void readFile(String filepath, String classpathPath) throws IOException {
        InputStream input = openFileIfExistsClasspathIfNot(filepath, classpathPath);
        byte[] fileData = new byte[10000];
        int length = input.read(fileData);
        fileData = Arrays.copyOf(fileData, length); // TODO: fix, ugly
        String inputString = new String(fileData);
        
        TemporaryParameterClass[] tempList = new Gson().fromJson(inputString, TemporaryParameterClass[].class);
        for(TemporaryParameterClass temp : tempList) {
            Parameter parameter = parameters.get(temp.key);
            if(parameter == null) {
                if(temp.value instanceof Double) {
                    if(temp.min != null && temp.max != null) {
                        createNumberParameter(temp.key, (Double) temp.value, temp.min, temp.max);
                    } else {
                        createNumberParameter(temp.key, (Double) temp.value);
                    }
                } else if(temp.value instanceof String) {
                    createStringParameter(temp.key, (String) temp.value);
                } else {
                    System.err.println("Unexpected type of parameter with key \"" + temp.key + "\"");
                }
            } else {
                if(temp.value instanceof Double) {
                    if(temp.min != null && temp.max != null) {
                        ((NumberParameter) parameter).setMin((Double) temp.min);
                        ((NumberParameter) parameter).setMax((Double) temp.max);
                    }
                    ((NumberParameter) parameter).setValue((Double) temp.value);
                } else if(temp.value instanceof String) {
                    ((StringParameter) parameter).setValue((String) temp.value);
                } else {
                    System.err.println("Unexpected type of parameter with key \"" + temp.key + "\"");
                }
            }
        }
    }
    
    private static InputStream openFileIfExistsClasspathIfNot(String filepath, String classpathPath) throws IOException {
        try {
            return new FileInputStream(filepath);
        } catch(FileNotFoundException ex) {
            return ParameterStore.class.getResourceAsStream(classpathPath);
        }
    }
}