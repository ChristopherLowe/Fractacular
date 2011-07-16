package com.lowware.fractal;
 
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

/**
 * A panel for displaying fractals
 *
 */
public class FractalPanel extends JPanel
{
    /**
     * image of the fractal
     */
    private BufferedImage image;
    
    /**
     * width of the image (and the array)
     */
    private int width;
    /**
     * height of the image (and the array)
     */
    private int height; 
   

    /**
     * Construct the panel
     *
     * @param width of the image (and array)
     * @param height of the image (and array)
     */
    public FractalPanel(int width, int height)
    {
        this.width = width;
        this.height = height;
        
        // using BufferedImage of type INT_ARGB
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // make image BLUE to start with
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.BLUE); //My favourite colour
        g2.fillRect(0, 0, width, height);
    }

    
    /**
     * Set one pixel in the image using RGB value
     *
     * @param x the column number
     * @param y the row number
     * @param red 0-255
     * @param green 0-255
     * @param blue 0-255
     */
    public void setPixel(int x, int y, int red, int green, int blue)
    {
      //Range fix
      if (red > 255) { red = 255; }
      if (green > 255) { green = 255; }
      if (blue > 255) { blue = 255; }
      if (red < 0) { red = 0; }
      if (green < 0) { green = 0; }
      if (blue < 0) { blue = 0; }  
      
      int argb = (0xFF<<24) + (red<<16) + (green<<8) + blue;
      image.setRGB(x, y, argb);
    }

    /**
     * Set one pixel in the image using RGB value plus a transparency byte
     *
     * @param x the column number
     * @param y the row number
     * @param alpha 0-255
     * @param red 0-255
     * @param green 0-255
     * @param blue 0-255
     */
    public void setPixel(int x, int y, int alpha, int red, int green, int blue)
    {
      //Range fix
      if (red > 255) { red = 255; }
      if (green > 255) { green = 255; }
      if (blue > 255) { blue = 255; }
      if (red < 0) { red = 0; }
      if (green < 0) { green = 0; }
      if (blue < 0) { blue = 0; }  
      
      int argb = (0xFF<<alpha) + (red<<16) + (green<<8) + blue;
      image.setRGB(x, y, argb);
    }
    
    
    /**
     * Overriden paintComponent - the fractal gets drawn here
     *
     * @param g a graphics object to draw the component with
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    
     public void saveToFile(String filename) {
        try {
            // Save as JPEG
            String fn = filename + ".png";
            File file = new File(fn);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {}
    }

    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
}
