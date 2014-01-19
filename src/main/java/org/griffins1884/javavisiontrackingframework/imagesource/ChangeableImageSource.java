package org.griffins1884.javavisiontrackingframework.imagesource;

import org.griffins1884.javavisiontrackingframework.parameters.ParameterStore;
import org.griffins1884.javavisiontrackingframework.parameters.StringParameter;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class ChangeableImageSource implements ImageSource {
    private ImageSource source;
    private StringParameter sourceDescriptor;
    private String lastSourceDescriptor;
    public ChangeableImageSource(ImageSource source) {
        sourceDescriptor = ParameterStore.getStringParameter("image source");
        this.source = source;
    }
    
    public void checkForNewSource() {
        String sourceDescriptor = this.sourceDescriptor.getValue();
        if(!sourceDescriptor.equals(lastSourceDescriptor) && followsConvention(sourceDescriptor)) {
            this.setSource(sourceDescriptor);
            lastSourceDescriptor = sourceDescriptor;
        }
    }
    
    public boolean isReady() {
        return source != null;
    }
    
    private boolean followsConvention(String s) {
        int indexOfAt = s.indexOf('@');
        return indexOfAt > 0 && indexOfAt < s.length() - 1;
    }
    
    private void setSource(String s) {
        int indexOfAt = s.indexOf('@');
        String major = s.substring(0, indexOfAt), minor = s.substring(indexOfAt + 1);
        if(major.equals("webcam")) {
            source = WebcamImageSource.getWebcam(minor);
        } else if(major.equals("still image")) {
            source = new StillImageSource("minor");
        }
    }
    
    public void setSource(ImageSource source) {
        this.source = source;
    }
    
    public Mat getImage() {
        return source.getImage();
    }

    public Size getImageSize() {
        return source.getImageSize();
    }
}