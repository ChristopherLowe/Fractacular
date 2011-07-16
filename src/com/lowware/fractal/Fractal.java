package com.lowware.fractal;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JPanel;


/**
 * Abstract  fractal
 * 
 */
public abstract class Fractal
{

    /**
     * Default range
     */
    public static final double DEFAULT_MINX = -2.5;
    public static final double DEFAULT_MAXX = 1;
    public static final double DEFAULT_MINY = -1.25;
    public static final double DEFAULT_MAXY = 1.25;
    
    /**
     *  Default escape time
     */
    public static final int DEFAULT_MAX_ITERATIONS = 64;
     
    /**
     * Current escape time
     */
    private int max_iterations;
    
    /**
     * Bounds of the most recently generated set
     */
    private double current_maxx;
    private double current_minx;
    private double current_maxy;
    private double current_miny;

     /**
     * image of the fractal
     */
    private FractalPanel panel;
    
     /**
     * Color modulations
     */
    private int red_mod = 1;
    private int green_mod = 1;
    private int blue_mod = 1;
    private Random r;
    
    
    public int SCREEN_WIDTH;
    public int SCREEN_HEIGHT;

    
    public abstract void generateFractal(double minx, double maxx, double miny, double maxy);
    
    public abstract String getFractalName();
    
    
    /**
     * Scales the x co-ordinate from the grid width to the current set
     * 
     * @ param x  the pixel coordinate to be scaled
     * @ returns the scaled x coordinate
     */
    public double getScaledXCoordinate(int x) {
        double xco = current_minx + x * (current_maxx - current_minx) / SCREEN_WIDTH;
        return xco;
    }
    
    /**
     * Scales the y co-ordinate from the grid height to the current set
     * 
     * @param y the pixel coordinate to be scaled
     * @returns double the scaled y coordinate
     */
    public double getScaledYCoordinate(int y) {
        double yco = current_miny + y * (current_maxy - current_miny) / SCREEN_HEIGHT;
        return yco;
    }
    
    public double getCurrentMinX() {
        return this.current_minx;
    }
    
    public double getCurrentMaxX() {
        return this.current_maxx;
    }
    
    public double getCurrentMinY() {
        return this.current_miny;
    }
    
    public double getCurrentMaxY() {
        return this.current_maxy;
    }
    
    public void setCurrentMinX(double minx) {
        this.current_minx = minx;
    }
    
    public void setCurrentMaxX(double maxx) {
        this.current_maxx = maxx;
    }
    
    public void setCurrentMinY(double miny) {
        this.current_miny = miny;
    }
    
    public void setCurrentMaxY(double maxy) {
        this.current_maxy = maxy;
    }
    
    public int getMaxIterations() {
        return this.max_iterations;
    }
    
    public void setMaxIterations(int maxIterations) {
        this.max_iterations = maxIterations;
    }
    
    public void setPanel(FractalPanel panel) {
        this.panel = panel;
    }
    
    public FractalPanel getPanel() {
        return this.panel;
    }
    
    public void setRandomRed() {
        red_mod = r.nextInt() % 255;
    }
    
    public void setRandomGreen() {
        green_mod = r.nextInt() % 255;
    }
     
    public void setRandomBlue() {
        blue_mod = r.nextInt() % 255;
    }
    
    public int getGreen() {		
        return this.green_mod;
    }
    
    public int getRed() {
        return this.red_mod;
    }
    
    public int getBlue() {
        return this.blue_mod;
    }
    
    public void setGreen(int green) {
		if (this.green_mod > 180) {
			this.green_mod = 0;
		} else {
			this.green_mod = green;
		}
	}
    
    public void setRed(int red) {
		if (this.red_mod > 180) {
			this.red_mod = 0;
		} else {
			this.red_mod = red;
		}
    }
    
    public void setBlue(int blue) {
		if (this.blue_mod > 180) {
			this.blue_mod = 0;
		} else {
			this.blue_mod = blue;
		}
	}
	
    
     /**
     * Constructor
     * 
     * Set's the default conditions and populates the image with a wide view of the fractal set
     */
    public Fractal(FractalPanel panel) {

        setPanel(panel);
        SCREEN_WIDTH = panel.getWidth();
        SCREEN_HEIGHT = panel.getHeight();
        this.current_minx = DEFAULT_MINX;
        this.current_maxx = DEFAULT_MAXX;
        this.current_miny = DEFAULT_MINY;
        this.current_maxy = DEFAULT_MAXY;
        this.max_iterations = DEFAULT_MAX_ITERATIONS;
        r = new Random();
        generateFractal(current_minx, current_maxx, current_miny, current_maxy);
    }
    
    
}
