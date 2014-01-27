package org.griffins1884.javavisiontrackingframework.ui.widgets;

import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.griffins1884.javavisiontrackingframework.parameters.NumberParameter;

public class NumberParameterSlider extends Widget implements ChangeListener {
    private NumberParameter parameter;
    private JSlider slider;
    public NumberParameterSlider(NumberParameter parameter) {
        super();
        this.parameter = parameter;
        
        init();
    }
    private final int SLIDER_MIN = 0, SLIDER_MAX = 400, LABEL_SPACING = 100;
    private void init() {
        JLabel label = new JLabel(parameter.getKey());
        this.add(label);
        
        slider = new JSlider(SLIDER_MIN, SLIDER_MAX, computeSliderValue(parameter.getValue()));
        slider.setMajorTickSpacing(LABEL_SPACING);
        slider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        for(int labelLocation = SLIDER_MIN; labelLocation <= SLIDER_MAX; labelLocation += LABEL_SPACING) {
            labelTable.put(labelLocation, new JLabel(Double.toString(computeParameterValue(labelLocation))));
        }
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        slider.setPreferredSize(new Dimension(600, 40));
        this.add(slider);
    }
    public void setParameterValue(double parameterValue) {
        slider.setValue(computeSliderValue(parameterValue));
    }
    private int computeSliderValue(double parameterValue) {
        double min = parameter.getMin(), max = parameter.getMax();
        if(min == Double.NEGATIVE_INFINITY) {
            min = -1000;
        }
        if(max == Double.POSITIVE_INFINITY) {
            max = 1000;
        }
        return (int) ((SLIDER_MAX - SLIDER_MIN) * (parameterValue - min) / (max - min) + SLIDER_MIN);
    }
    private double computeParameterValue(int sliderValue) {
        double min = parameter.getMin(), max = parameter.getMax();
        if(min == Double.NEGATIVE_INFINITY) {
            min = -1000;
        }
        if(max == Double.POSITIVE_INFINITY) {
            max = 1000;
        }
        return (max - min) * (sliderValue - SLIDER_MIN) / (SLIDER_MAX - SLIDER_MIN) + min;
    }
    public void stateChanged(ChangeEvent ev) {
        int sliderValue = ((JSlider) ev.getSource()).getValue();
        this.parameter.setValue(computeParameterValue(sliderValue));
    }
}