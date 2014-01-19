package org.griffins1884.javavisiontrackingframework.ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.griffins1884.javavisiontrackingframework.parameters.NumberParameter;
import org.griffins1884.javavisiontrackingframework.parameters.StringParameter;
import org.griffins1884.javavisiontrackingframework.ui.widgets.NumberParameterSlider;
import org.griffins1884.javavisiontrackingframework.ui.widgets.StringParameterEditor;

public class SettingsDisplay {
    private static JFrame settingsWindow;
    
    static {
        settingsWindow = new JFrame(": Settings");
        settingsWindow.setLayout(new BoxLayout(settingsWindow.getContentPane(), BoxLayout.PAGE_AXIS));
        settingsWindow.setVisible(true);
        settingsWindow.setLocation(640, 0);
    }
    public static void setTitle(String title) {
        settingsWindow.setTitle(title + ": Settings");
    }
    public static NumberParameterSlider addNumberParameter(NumberParameter parameter) {
        NumberParameterSlider result = new NumberParameterSlider(parameter);
        settingsWindow.add(result);
        settingsWindow.pack();
        return result;
    }
    public static StringParameterEditor addStringParameter(StringParameter parameter) {
        StringParameterEditor result = new StringParameterEditor(parameter);
        settingsWindow.add(result);
        settingsWindow.pack();
        return result;
    }
    public static void addWidget(JPanel widget) {
        settingsWindow.add(widget);
        settingsWindow.pack();
    }
}