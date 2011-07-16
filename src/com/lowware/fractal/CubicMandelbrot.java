package com.lowware.fractal; 


/**
 * A cubic mandelbrot given by the forumula
 *
 *   Z(n+1) = Z^3 + c
 *
 * 
 */
public class CubicMandelbrot extends Fractal
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
        
                //calculate escape iterations
                while ((z <= 2) && (c <= 2) && (iterations < getMaxIterations())) {                
                    double zlast = (z*z*z - 3*z*c*c) + xco;
                    c = (3*z*z*c - c*c*c) + yco;
                    z = zlast;                               
                    iterations++;
                }
               
                //color the fractal
                if (iterations >= getMaxIterations()) {  
                    //Interior color
                    getPanel().setPixel(x, y, 0, 0, 0); //black
               } else {    
                   //Exterior color
                    double magx = Math.sqrt((x*x) + (y*y));
                    double logmagxlog3 = Math.log(Math.log(magx)/Math.log(3));
                    double smoothing = iterations + 1 - logmagxlog3;
                    int green = getGreen() + (int) smoothing % 255;
                    int red = getRed() + (green / 2);
                    int blue = getBlue() + iterations + (green * 4) % 255;
                    getPanel().setPixel(x, y, red, green, blue);
                }
            }
        }
    }
    
    public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("Cubic Mandelbrot ");
        b.append("Z(n+1) = Z(n)^3 + c");
        b.append("   ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public CubicMandelbrot(FractalPanel panel) {
        super(panel);
    }
}
