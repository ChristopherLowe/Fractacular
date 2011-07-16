package com.lowware.fractal;
 

public class InversePiMandelbrot extends Fractal
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
      
        
        //for each pixel in the screen
        for (int x = 0; x < SCREEN_WIDTH; x++) {
            for (int y = 0; y < SCREEN_HEIGHT; y++) {
                double xco = getCurrentMinX() + x * (getCurrentMaxX() - getCurrentMinX()) / SCREEN_WIDTH;
                double yco = getCurrentMinY() + y * (getCurrentMaxY() - getCurrentMinY()) / SCREEN_HEIGHT;
                double z = 0;
                double c = 0;
                int iterations = 0;
                double invpi = 0.318309886;
                //calculate escape iterations
                while ((z <= 4) && (c <= 4) && (iterations < getMaxIterations())) {
                    double temp = (z * z) - (c * c)  + xco;
                    double temp2 = (2 * z * c) + yco;
                    
                    
                    c = temp2;
                    z = temp * invpi;
                    
                   
                    iterations++;
                }
               
                //color the fractal
                if (iterations >= getMaxIterations()) {  
                    //Interior color
                    getPanel().setPixel(x, y, 0, 0, 0); 
               } else {    
                   //Exterior color
                   
                    double magx = Math.sqrt(x*x)+(y*y);
                    double log2magx = Math.log(Math.log(magx)/Math.log(2));
                    double smoothing = iterations + 1 - log2magx;   
                    int green = getGreen() + (int) smoothing % 255;
                    int red = getRed() + 6 * green % 255;
                    int blue = getBlue() + (green / 2) % 255;
                    getPanel().setPixel(x, y, red, green, blue);
                }
            }
        }
    }
    
        public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("InversePi Mandelbrot");
        b.append("Z(n+1) = Z(n) + c where c=(1/Pi)i");
        b.append("   ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public InversePiMandelbrot(FractalPanel panel) {
        super(panel);
    }
}
