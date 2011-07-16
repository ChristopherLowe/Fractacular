package com.lowware.fractal;

import javax.swing.JPanel;
import java.awt.Cursor;


public class Fractal04 extends Fractal
{
     /**
     * Constructs the image of the Mandlebrot, pixel at a time.
     * 
     * @parm    minx
     * @parm    maxx
     * @parm    miny    
     * @parm    maxy
     */
    public void generateFractal(double minx, double maxx, double miny, double maxy) {
         
        //save min/max as instance variable for zooming and scaling
        setCurrentMinX(minx);
        setCurrentMaxX(maxx); 
        setCurrentMaxY(maxy);
        setCurrentMinY(miny);
      
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
        Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        getPanel().setCursor(hourglassCursor);
        
           
        //for each pixel in the screen
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                double xco = getCurrentMinX() + x * (getCurrentMaxX() - getCurrentMinX()) / SCREEN_WIDTH;
                double yco = getCurrentMinY() + y * (getCurrentMaxY() - getCurrentMinY()) / SCREEN_HEIGHT;
                double z = 0;
                double c = 0;
                int iterations = 0;
        
                //calculate escape orbit
                while ((z <= 2) && (c <= 2) && (iterations < getMaxIterations())) {
                    double znext = ((z * z) - (c * c)) * (z + c) + xco;
                    c = (2 * z * c) * (z + c) + yco;
                    z = znext;
                    iterations++;
                }
               
                //color the fractal
                if (iterations >= getMaxIterations()) {  
                    getPanel().setPixel(x, y, 0, 0, 0); //black
               } else {    
                   //Exterior color
                    double magx = Math.sqrt((x*x)+(y*y));
                    double log2magx = Math.log(Math.log(magx)/Math.log(2));
                    double smoothing = iterations + 1 - log2magx;
                    int green = getGreen() + ((int) smoothing * 2 % 255);
                    int red = getRed() + ((int) smoothing * 3 % 255);
                    int blue = getBlue() + ((int) smoothing * 4 % 255);
                    getPanel().setPixel(x, y, red, green, blue);
                }
            }
        }
        
        getPanel().setCursor(normalCursor);
    }
    
    public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("Fractal04 ");
        b.append("Z(n+1) = Z(n)^(2+Z(n-1)) + c, over " + getMaxIterations() + " iterations at");
        b.append(" ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public Fractal04(FractalPanel panel) {
        super(panel);
    }
}
