package org.griffins1884.javavisiontrackingframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import org.griffins1884.javavisiontrackingframework.imagesource.ChangeableImageSource;
import org.griffins1884.javavisiontrackingframework.ui.SettingsDisplay;
import org.griffins1884.javavisiontrackingframework.ui.widgets.StringParameterEditor;
import org.griffins1884.javavisiontrackingframework.ui.widgets.Widget;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class App {
    private static ArrayList<AppComponent> components;
    private static ChangeableImageSource source;
    public static void __static_load__() {}
    
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        SettingsDisplay.addWidget(new ReloadWidget());
        components = new ArrayList<AppComponent>();
        source = new ChangeableImageSource(null);
    }
    
    private static class ReloadWidget extends Widget implements ActionListener {
        public ReloadWidget() {
            JButton reloadButton = new JButton("Reload classes");
            reloadButton.addActionListener(this);
            this.add(reloadButton);
        }
        public void actionPerformed(ActionEvent ev) {
            App.reloadClassesSoon();
        }
    }
    
    public static void addComponent(AppComponent c) {
        if(components.size() > 0 && !c.inputType.equals(components.get(components.size() - 1))) {
            throw new IllegalArgumentException("Class does not input the previous return type");
        }
        components.add(c);
    }
    
    private static boolean shouldReloadClasses = false;
    
    public static void reloadClassesSoon() {
        shouldReloadClasses = true;
    }
    
    public static void runIndefinitely() {
        while(true) {
            try {
                if(shouldReloadClasses) {
                    shouldReloadClasses = false;
                    for(AppComponent c : components) {
                        c.reloadClass();
                    }
                }
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }
            source.checkForNewSource();
            if(source.isReady()) {
                runAllComponents(source.getImage());
            }
            try {
                Thread.sleep(50);
            } catch(InterruptedException e) {}
        }
    }
    
    private static void runAllComponents(Mat cameraInput) {
        Object input = cameraInput;
        for(AppComponent c : components) {
            Object returnValue = c.run(input);
            if(returnValue == null) {
                return;
            }
            if(input instanceof Mat) {
                ((Mat) input).release();
            }
            input = returnValue;
        }
    }
}