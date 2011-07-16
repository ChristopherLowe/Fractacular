package com.lowware.fractal;

/**
 * Entry point
 * 
 */
public class Main
{
  	/**
	* Entry point
	*/
	public static void main(String[] args) {
	
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FractalJFrame();
            }
        });
	
	}
}
