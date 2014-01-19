package org.griffins1884.javavisiontrackingframework.parameters;

import org.griffins1884.javavisiontrackingframework.ui.widgets.StringParameterEditor;

public class StringParameter extends Parameter {
    private String value;
    private StringParameterEditor editor;
    
    protected StringParameter(String key, String value) {
        super(key);
        this.value = value;
    }
    
    public void setValue(String value) {
        this.value = value;
        if(editor != null) {
            editor.setStringValue(value);
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public void setEditor(StringParameterEditor editor) {
        this.editor = editor;
    }
}