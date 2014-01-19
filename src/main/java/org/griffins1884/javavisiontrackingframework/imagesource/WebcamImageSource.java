package org.griffins1884.javavisiontrackingframework.imagesource;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import com.github.sarxos.webcam.Webcam;

// Credit where it is due: this was adapted from https://github.com/sarxos/webcam-capture/tree/master/webcam-capture-drivers/webcam-capture-driver-javacv
public class WebcamImageSource implements ImageSource {
    
    private static ArrayList<WebcamImageSource> webcams;
    
    public static WebcamImageSource getWebcam(String contains) {
        List<Webcam> list = Webcam.getWebcams();
        int numberOfDevices = list.size();
        for(int i = 0; i < numberOfDevices; i++) {
            String name = list.get(i).getName();
            if(name.contains(contains)) {
                return new WebcamImageSource(list.get(i));
            }
        }
        return null;
    }
    
    private int address;
    private String name;
    private Webcam webcam;
    
    private volatile boolean open = false;
    private volatile boolean disposed = false;
    
    private WebcamImageSource(Webcam webcam) {
        this.webcam = webcam;
        this.webcam.setViewSize(new Dimension(640, 480));
        this.webcam.open();
    }
    
    public Mat getImage() {
        BufferedImage bufferedImage = webcam.getImage();
        byte[] imageData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        Mat matImage = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        matImage.put(0, 0, imageData);
        return matImage;
    }
    
    public Size getImageSize() {
        Dimension size = webcam.getViewSize();
        return new Size((int) size.getWidth(), (int) size.getHeight());
    }
}