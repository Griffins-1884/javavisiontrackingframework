package org.griffins1884.javavisiontrackingframework.imagesource;

public class ImageSourceException extends RuntimeException {
    private static final long serialVersionUID = -1317019122850145084L;
    
    public ImageSourceException(String message) {
        super(message);
    }
    public ImageSourceException(Exception e) {
        super(e);
    }
}