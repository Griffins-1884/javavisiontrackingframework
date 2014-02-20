package org.griffins1884.javavisiontrackingframework.ui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.griffins1884.javavisiontrackingframework.imagesource.ChangeableImageSource;
import org.griffins1884.javavisiontrackingframework.imagesource.StillImageSource;
import org.griffins1884.javavisiontrackingframework.imagesource.WebcamImageSource;
import org.griffins1884.javavisiontrackingframework.parameters.StringParameter;

public class StringParameterEditor extends Widget implements ActionListener {
    private StringParameter parameter;
    private JTextField textField;
    
    public StringParameterEditor(StringParameter parameter) {
        this.parameter = parameter;
        this.init();
    }
    
    public void init() {
        this.textField = new JTextField(60);
        this.textField.addActionListener(this);
        this.add(textField);
    }
    
    public void actionPerformed(ActionEvent ev) {
        parameter.setValue(textField.getText());
    }
    
    public void setStringValue(String s) {
        this.textField.setText(s);
    }
}