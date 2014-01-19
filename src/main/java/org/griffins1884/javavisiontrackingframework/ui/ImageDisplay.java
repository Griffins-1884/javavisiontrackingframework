package org.griffins1884.javavisiontrackingframework.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.griffins1884.javavisiontrackingframework.parameters.ParameterStore;
import org.griffins1884.javavisiontrackingframework.parameters.StringParameter;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageDisplay {
    private static StringParameter imageOption;
    private static ImagePanel imagePanel;
    public static void __static_load__() {}
    
    static {
        imageOption = ParameterStore.getStringParameter("image option");
        imagePanel = new ImagePanel();
    }
    
    public static void showImage(String optionName, Mat image) {
        if(optionName.equals(imageOption.getValue())) {
            imagePanel.setImage(image);
        }
    }
    
    public static class ImagePanel extends JFrame {
        private BufferedImage img;
        public ImagePanel() {
            this.setVisible(true);
        }
        
        public void setImage(Mat img) {
            this.setImage(matToBufferedImage(img));
        }
        
        public void setImage(BufferedImage img) {
            this.img = img;
            this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight()));
            this.repaint();
        }
        
        @Override
        public void paint(Graphics g) {
            if(img != null) {
                this.setSize(img.getWidth(), img.getHeight());
                g.drawImage(img, 0, 0, null);
            }
        }
        
        public static BufferedImage matToBufferedImage(Mat m) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if(m.channels() > 1) {
//                Imgproc.cvtColor(m, m, Imgproc.COLOR_BGR2RGB);
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            byte[] b = new byte[m.channels() * m.cols() * m.rows()];
            m.get(0, 0, b);
            BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
            image.getRaster().setDataElements(0, 0, m.cols(), m.rows(), b);
            return image;
        }
    }
}