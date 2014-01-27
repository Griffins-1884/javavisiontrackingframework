package org.griffins1884.javavisiontrackingframework.parameters;

import org.griffins1884.javavisiontrackingframework.ui.widgets.NumberParameterSlider;

public class NumberParameter extends Parameter {
    private double value;
    private double min, max;
    private NumberParameterSlider slider = null;
    
    protected NumberParameter(String key, double value, double min, double max) {
        super(key);
        this.min = min;
        this.max = max;
        this.setValue(value);
    }
    public double getValue() {
        return value;
    }
    public double getDoubleValue() {
        return value;
    }
    public int getIntValue() {
        return (int) value;
    }
    public float getFloatValue() {
        return (float) value;
    }
    public void setValue(double value) {
        if(value < this.min || value > this.max) {
            throw new IllegalArgumentException("The value was outside the allowable range");
        }
        this.value = value;
        if(slider != null) {
            slider.setParameterValue(value);
        }
    }
    public double getMin() {
        return this.min;
    }
    protected void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return this.max;
    }
    protected void setMax(double max) {
        this.max = max;
    }
    protected void setSlider(NumberParameterSlider slider) {
        this.slider = slider;
    }
}