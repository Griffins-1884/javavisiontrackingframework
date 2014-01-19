package org.griffins1884.javavisiontrackingframework.imagesource;

import org.opencv.core.Mat;
import org.opencv.core.Size;

public interface ImageSource {
    public Mat getImage();
    public abstract Size getImageSize();
}