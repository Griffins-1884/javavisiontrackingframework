package org.griffins1884.javavisiontrackingframework.imagesource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class StillImageSource implements ImageSource {
    private String filename;
    private Mat image;
    public StillImageSource(String filename) {
        this.filename = filename;
        
//        image = Highgui.imread(this.filename);
        BufferedImage bufferedImage;
        try {
            System.out.println(new File(filename).getAbsolutePath());
            bufferedImage = ImageIO.read(new File(filename));
            byte[] imageData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            image = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
            image.put(0, 0, imageData);
            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    
    public Mat getImage() {
        return image.clone();
    }

    public Size getImageSize() {
        return new Size(image.cols(), image.rows());
    }
}