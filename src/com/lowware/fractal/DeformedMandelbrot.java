package com.lowware.fractal;
 

public class DeformedMandelbrot extends Fractal
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
                    double znext = (z * z * z) - (c * c * 2) + xco;
                    c = (3 * z * z * c) - (2 * z * c) + yco;
                    z = znext;
                    iterations++;
                }
               
                //color the fractal
                if (iterations >= getMaxIterations()) {  
                    //Interior color
                    getPanel().setPixel(x, y, 171, 112, 79); //brown
                } else {    
                   //Exterior color
                    int red = (iterations * 8) % 255;
                    int green = (iterations * 2) % 255;
                    int blue = (255 - iterations * 4) % 255;
                    getPanel().setPixel(x, y, red, green, blue);
                }
			   
			   
            }
        }
    }
    
        public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("Deformed Mandelbrot");
        b.append("Z(n+1) = Z(n) + c");
        b.append("   ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public DeformedMandelbrot(FractalPanel panel) {
        super(panel);
    }
}
