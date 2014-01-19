package org.griffins1884.javavisiontrackingframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AppComponent {
    protected Class<?> inputType, returnType;
    private String fullyQualifiedClassName;
    private Class<?> componentClass;
    public AppComponent(String fullyQualifiedClassName, Class<?> inputType, Class<?> returnType) throws ClassNotFoundException {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.inputType = inputType;
        this.returnType = returnType;
        this.reloadClass();
    }
    protected void reloadClass() throws ClassNotFoundException {
        File file = new File("bin/".concat(fullyQualifiedClassName.replaceAll("\\.", "/")).concat(".class"));
        byte[] data = new byte[(int) file.length()];
        try {
            new FileInputStream(file).read(data);
        } catch(FileNotFoundException e) {
            throw new ClassNotFoundException();
        } catch(IOException e) {
            throw new ClassNotFoundException();
        }
        Class<?> componentClass = (new ClassLoader() {
            public Class<?> load(String name, byte[] b, int off, int len) throws ClassNotFoundException {
                return defineClass(name, b, off, len);
            }
        }).load(this.fullyQualifiedClassName, data, 0, data.length);
        try {
            ((Class) componentClass.getMethod("run", inputType).getGenericReturnType()).equals(returnType);
        } catch(Exception ex) {
            throw new ClassNotFoundException();
        }
        this.componentClass = componentClass;
    }
    public Object run(Object input) {
        try {
            return this.componentClass.getMethod("run", inputType).invoke(null, input);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}