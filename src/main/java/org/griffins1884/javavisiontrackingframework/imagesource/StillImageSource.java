package org.griffins1884.javavisiontrackingframework.imagesource;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

public class StillImageSource implements ImageSource {
    private String filename;
    private Mat image;
    public StillImageSource(String filename) {
        this.filename = filename;
        
        image = Highgui.imread(this.filename);
    }
    
    public Mat getImage() {
        return image;
    }

    public Size getImageSize() {
        return new Size(image.cols(), image.rows());
    }
}