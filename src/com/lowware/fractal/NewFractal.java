package com.lowware.fractal;

public class NewFractal extends Fractal
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
        
                double phi = 1.6180339887;
                double pi = Math.PI;
                
                //calculate escape orbit
                while ((z + c <= 4) && (iterations < getMaxIterations())) {
                    
                    double znext = phi * (z * z) - (c * c) / 2 + xco;
                    c = pi * (2 * z * c) / 2+ yco;
                    z = znext;
                    iterations++;
                    
                    /**
                     * double znext = Math.abs(z * z - c * c) + xco;
                    c =  Math.abs(2 * z * c) + yco;
                    z = znext;
                    iterations++;
                     * 
                     */
                    
                    /** Absolute mandebrot
                     * double znext = z * z - c * c + Math.abs(xco);
                    c =  2 * z * c + Math.abs(yco);
                    z = znext;
                    iterations++;
                     */
                    
                    /**
                     * SpiderWeb
                     *  double znext = Math.abs(z * z - c * c + xco);
                        c =  Math.abs(2 * z * c + yco);
                        z = znext;
                        iterations++;
                     */
                    
                    
                    /**
                     * double znext = (z*z*z*z) - (6*z*z*c*c) + (c*c*c*c) + xco;
                     * c = (2*z*c*c*c) + (4*z*z*z*c) + yco;
                     * z = znext;
                     * iterations++;
                    */
                    
                    /**
                    double znext = ((z * z) - (c * c)) * (z + c) + xco;
                    c = (2 * z * c) * (z + c) + yco;
                    z = znext;
                    iterations++;
                    */
                    
                    /**
                    double znext = z * z - c * c + xco;
                    c = z * z * c * c + yco;
                    z = znext;
                    iterations++;
                    */
                    
                    /**
                    double znext = (z * z) - (c * c) + xco;
                    c = 0.5 * z * z* c * c + yco;
                    z = znext;
                    iterations++;
                    */
                    
                    /**
                    double znext = (z * z * z * z) - (c * c * c * c) + xco;
                    c = c = (c*c*c*c) - (4*z*z*z*c) + yco;
                    z = znext;
                    iterations++;
                    */
                    
                    /**
                    double znext = (z * z * z) - (c * c * c) + xco;
                    c = (c * c * c) - (3 * z * z * c) + yco;
                    z = znext;
                    iterations++;
                    */
                    
                    /**
                    double znext2 = (z * z) - (c * c);
                    double cnext = (2 * z * c);
                    double znext = (znext2 * znext2) - (cnext * cnext);
                    z = znext + xco;
                    c = (2 * znext * cnext) + yco;
                    iterations++;
                    */
                   
                    /**
                    double znext2 = (z * z) - (c * c) + xco;
                    double cnext = (2 * z * c) + yco;
                    double znext = (znext2 * znext2) - (cnext * cnext) + xco;
                    z = znext;
                    c = cnext;
                    iterations++;
                    */
                    
                    /**
                    double zlast = (z*z*z - 3*z*c*c) * (z + c) + xco;
                    c = (3*z*z*c - c*c*c) * (z + c) + yco;
                    z = zlast;                               
                    iterations++;
                    */
                    
                    /**
                     * double znext = (z*z*z*z) - (c*c*c*c) + xco;
                     * c = (c*c*c*c) - (4*z*z*z*c) - (3*z*c*c) - (z*c*c*c) + yco;
                     * z = znext;
                     * iterations++;
                    */
                    
                    
                    /**
                     * double znext = (zre*zre*zre*zre) - (2*zre*zre*zim*zim) + (zim*zim*zim*zim) + xco;
                     * zim = (4*zre*zre*zim) - (4*zre*zim*zim) + yco;
                     * zre = znext;
                     * iterations++;
                     */
                }
               
                //color the fractal
                if (iterations >= getMaxIterations()) {  
                    getPanel().setPixel(x, y, 0, 0, 0); //black
               } else {    
                   //Exterior color
                    double magx = Math.sqrt(x*x)+(y*y);
                    double log2magx = Math.log(Math.log(magx)/Math.log(2));
                    double smoothing = iterations + 1 - log2magx;
                    
                    //int lastIter = iterations;
                    //iterations = iterations * (int) Math.pow(log2magx, 2);
                    
                    
                    int green = (int) (getGreen() + smoothing * iterations) % 255;
                    int red = (int) (getRed() + smoothing * iterations) % 255;
                    int blue = (int) (getBlue() + smoothing * iterations) % 255;
                    
                    getPanel().setPixel(x, y, red, green, blue);
                }
            }
        }
    }

    
    public boolean isStrongPrime(int p) {
        //11, 17, 29, 37, 41, 59, 67, 71, 79, 97, 101, 107, 127, 137, 149, 163, 179, 191, 197, 223
        //|| (p == 29) || (p == 37) || (p == 41) || (p == 59) || (p == 67) || (p == 71) || (p == 79) || (p == 97) || (p == 101) || (p == 107) || (p == 127) || (p == 137) ||(p == 149) || (p == 163) || (p == 179) || (p == 191) || (p == 197) || (p == 223)) {
            
        if ((p == 11) || (p == 17)) {
            return true;
        } else {
            return false;
        }
    }
    
        public String getFractalName() {
        StringBuffer b = new StringBuffer();
        b.append("NewFractal");
        b.append("  ");
        b.append("(" + getCurrentMinX() + ", " + getCurrentMinY() + ")");
        b.append(" .. ");
        b.append("(" + getCurrentMaxX() + ", " + getCurrentMinY() + ")");
        return b.toString();
    }
    
    public NewFractal(FractalPanel panel) {
        super(panel);
    }
}
