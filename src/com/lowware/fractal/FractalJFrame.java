package com.lowware.fractal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.ButtonGroup;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URI;



public class FractalJFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener
{
     /**
     * Default screen size
     */
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;
    private static final double DEFAULT_MINX = -2.5;
    private static final double DEFAULT_MAXX = 1;
    private static final double DEFAULT_MINY = -1.25;
    private static final double DEFAULT_MAXY = 1.25;
    private static final int COLOR_INCREASE = 20; //when R, G or B is pressed
    private static final int ZOOM_COUNT_ITERATION_INCREASE = 10; //how many times zoomed before iterations increase

    /**
     * Properties (see fractal.properties file)
     */
    private static final String TITLE = "Fractacular";
    private static final String PROPERTIES_FILE = "fractal.properties";
    
    /**
     * Instance variables
     */
    private int current_fractal = 0;    //index of current fractal
    private int fractal_count = 0;  
    private int zoom_count;             //number of times zoomed
    private String classi;              //classname of the fractal
    private String[] fractals;
    
    /**
     * The fractal
     */
    private FractalPanel panel;
    private Fractal m;
  
    
    /**
     * Constructor
     */
    public FractalJFrame() 
    {
        super(TITLE);
        
        panel = new FractalPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        //Load properties and set to the first fractal
        if (!(loadProperties() && loadFractal(0))) {
            m = new MandelbrotFractal(panel);
        }
        
        //Configure the JFrame
        getContentPane().add(panel);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
       
        //Center the JFrame
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = screensize.width;
        int height = screensize.height;
        int width_diff = width - SCREEN_WIDTH;
        int height_diff = height - SCREEN_HEIGHT;
        setLocation(width_diff / 2, height_diff / 2);
        
        setResizable(false);
        setVisible(true);
    }
    

    /**
     * Mouse clicked
     * 
     */
    public void mouseClicked(MouseEvent e)
    {       
            switch (e.getModifiers()) {
            case MouseEvent.BUTTON1_MASK:   //Left mouse button -- zoom in
                double newMinX = m.getScaledXCoordinate(e.getX() - 125);
                double newMaxX = m.getScaledXCoordinate(e.getX() + 125);
                double newMinY = m.getScaledYCoordinate(e.getY() - 125);
                double newMaxY = m.getScaledYCoordinate(e.getY() + 125);
                
                zoom_count++;
                if (zoom_count >= ZOOM_COUNT_ITERATION_INCREASE) {
                    zoom_count = 0;
                    m.setMaxIterations(m.getMaxIterations() * 2);
                }
                
                m.generateFractal(newMinX, newMaxX, newMinY, newMaxY);
                panel.repaint();
                break;
                
            case MouseEvent.BUTTON2_MASK:   //Middle mouse button -- does nothing
                break;    
            
            case MouseEvent.BUTTON3_MASK:   //Right mouse button -- zoom out
                double newMinX3 = m.getScaledXCoordinate(e.getX() - 500);
                double newMaxX3 = m.getScaledXCoordinate(e.getX() + 500);
                double newMinY3 = m.getScaledYCoordinate(e.getY() - 500);
                double newMaxY3 = m.getScaledYCoordinate(e.getY() + 500);
                m.generateFractal(newMinX3, newMaxX3, newMinY3, newMaxY3);
                panel.repaint();
                break;
                
            default:
                break;
        }
    }
    
    /**
     * Key pressed
     * 
     */
    public void keyPressed(KeyEvent e) 
    {
        double minx = m.getCurrentMinX();
        double maxx = m.getCurrentMaxX();
        double miny = m.getCurrentMinY();
        double maxy = m.getCurrentMaxY();
        double panSize = (maxx - minx) / 4;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:  //Return to default
                m.setMaxIterations(m.DEFAULT_MAX_ITERATIONS);
                m.setRed(0);
                m.setBlue(0);
                m.setGreen(0);
                m.generateFractal(m.DEFAULT_MINX, m.DEFAULT_MAXX, m.DEFAULT_MINY, m.DEFAULT_MAXY);
                repaint();
                break;
                
            case KeyEvent.VK_LEFT:    //Pan left
                m.generateFractal(minx - panSize, maxx - panSize, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_RIGHT:   //Pan right
                m.generateFractal(minx + panSize, maxx + panSize, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_UP:      //Pan up
                m.generateFractal(minx, maxx, miny - panSize, maxy - panSize);
                repaint();
                break;
                
            case KeyEvent.VK_DOWN:    //Pan down
                m.generateFractal(minx, maxx, miny + panSize, maxy + panSize);
                repaint();
                break;
                
            case KeyEvent.VK_A:    //Pan left
                m.generateFractal(minx - panSize, maxx - panSize, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_D:   //Pan right
                m.generateFractal(minx + panSize, maxx + panSize, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_W:      //Pan up
                m.generateFractal(minx, maxx, miny - panSize, maxy - panSize);
                repaint();
                break;
                
            case KeyEvent.VK_S:    //Pan down
                m.generateFractal(minx, maxx, miny + panSize, maxy + panSize);
                repaint();
                break;
                
            case KeyEvent.VK_P:       //Save to file
                panel.saveToFile(m.getFractalName());
                break;
                
            case KeyEvent.VK_ENTER:   //Cycle between fractals
                nextFractal();
                repaint();
                break;
                
            case KeyEvent.VK_R:       // RED
                m.setRed(m.getRed() + COLOR_INCREASE);
                m.generateFractal(minx, maxx, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_G:       // BLUE
                m.setGreen(m.getGreen() + COLOR_INCREASE);
                m.generateFractal(minx, maxx, miny, maxy);
                repaint();
                break;
            
            case KeyEvent.VK_B:       // GREEN
                m.setBlue(m.getBlue() + COLOR_INCREASE);
                m.generateFractal(minx, maxx, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_PLUS:    //Increase Iterations
                m.setMaxIterations(m.getMaxIterations() + 50);
                m.generateFractal(minx, maxx, miny, maxy);
                repaint();
                break;
                
            case KeyEvent.VK_MINUS:    //Decrease Iterations
                m.setMaxIterations(m.getMaxIterations() - 50);
                m.generateFractal(minx, maxx, miny, maxy);
                repaint();
                break;
            
            case KeyEvent.VK_F1:        //Open help dialog
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new HelpJFrame();
                    }
                });
            default:
                break;
            }
    }
    
    /**
     * Load fractal.properties and set the available fractals
     * 
     * @return true if succesful
     */
    private boolean loadProperties() {
        //Populate the list of available fractals
        Properties properties = new Properties();
        fractal_count = 0;
        fractals = null;
        try {
            properties = new Properties();
			InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            properties.load(propertiesStream);
			//properties.load(new FileInputStream(PROPERTIES_FILE));
            fractal_count = Integer.valueOf(properties.getProperty("count"));
            fractals = new String[fractal_count];
            
            for (int i = 0;  i < fractals.length; i++) {
                String propi = "fractal" + i;
                fractals[i] = properties.getProperty(propi);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not open " + PROPERTIES_FILE);
            return false;
        }
        
        if (fractals == null) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Load fractal
     * 
     * @parm i Number representing the property files 'fractal + i' property
     * @return true if succsessful
     */
    private boolean loadFractal(int i) {
        try {
            Class c = Class.forName(fractals[current_fractal]);
            Constructor ctor[] = c.getDeclaredConstructors();
            Object newfractal = ctor[0].newInstance(panel);
            m = (Fractal) newfractal;
            repaint(); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    /**
     * Next fractal
     */
    private void nextFractal() {
        //Increment to next available fractal
        current_fractal++;
        if (current_fractal >= fractal_count) {
            current_fractal = 0;
        }
        
		try {
			loadFractal(current_fractal);
		} catch (ArrayIndexOutOfBoundsException e) {
			nextFractal();
		}
    }

    /**
     * Not used
     */
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
            
}
    

