package com.lowware.fractal;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class TestFractal extends Fractal
{
     /**
     * Constructs the image of the fractal, pixel at a time.
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
      
        
            
        
        
        
        
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                double xco = getCurrentMinX() + x * (getCurrentMaxX() - getCurrentMinX()) / SCREEN_WIDTH;
                double yco = getCurrentMinY() + y * (getCurrentMaxY() - getCurrentMinY()) / SCREEN_HEIGHT;
                double z = 0;
                double c = 0;
                int iterations = 0;

                
                //calculate escape orbit
                while ((z + c <= 4) && (iterations < getMaxIterations())) {     
                    
                    double znext = z * z - c * c + Math.abs(xco);
                    c =  2 * z * c + Math.abs(yco);
                    z = znext;
                    iterations++;
                    
                    
                }
                
                if (iterations >= getMaxIterations()) {  
                    getPanel().setPixel(x, y, 0, 0, 0); //black
               } else {    
                   //Exterior color
                    
                    
                    double magx = Math.sqrt(x*x)+(y*y);
                    double log2magx = Math.log(Math.log(magx)/Math.log(2));
                    double smoothing = iterations + 1 - log2magx;
                    
                    int green = (int) (getGreen() + smoothing * 2) % 255;
                    int red = (int) (getRed() + smoothing * 4) % 255;
                    int blue = (int) (getBlue() + smoothing * 8) % 255;
                    
                    getPanel().setPixel(x, y, red, green, blue);
                    
            }
        }
    }
       
    }
        
    

    
        public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("Test fractal: ");
        b.append("  ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public TestFractal(FractalPanel panel) {
        super(panel);
    }
}
