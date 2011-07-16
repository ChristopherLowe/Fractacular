package com.lowware.fractal;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;

public class HelpJFrame extends JFrame
{
   
    private static final int WIDTH = 320;
    private static final int HEIGHT = 320;
    
    private static final String HELP_STRING = "<html>" +
		"<font size=\"13\">Fractacular 0.2</font><br/><br/>" +
		"<b>ENTER</b> - <i>Next fractal</i><br />" +
		"<b>LEFT CLICK</b> - <i>Zoom in</i><br/>" + 
		"<b>RIGHT CLIC</b> - <i>Zoom out</i><br/>" +
		"<b>ESC<b> - <i>Reset</i><br/>" +
		"<b>UP, DOWN, LEFT, RIGHT</b> - <i>Pan</i><br/>" +
		"<b>R, G, B</b> - <i>Change color</i><br/>" +
		"<b>P</b> - <i>Save to PNG</i><br/><br/>" +
		"<center>Copywrite (C) 2011<br/>" +
		"Christopher Lowe <a href=\"mailto:chris@lowware.com.au\">chris@lowware.com.au</a></center></html>";
    
    JPanel panel;
    JLabel help;
    JButton exit;
	
	
    public HelpJFrame()
    {
       super("About");
       
       panel = new JPanel();
       help = new JLabel(HELP_STRING);
       panel.add(help);
    
		exit = new JButton("Exit");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setToolTipText("Exit");
		exit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeHelpWindow();
			}
		});
		exit.setEnabled(true);
		
		getContentPane().setLayout(new FlowLayout());
	
       getContentPane().add(panel);
	   getContentPane().add(exit);
       setSize(WIDTH, HEIGHT);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        //Center the JFrame
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = screensize.width;
        int height = screensize.height;
        int width_diff = width - WIDTH;
        int height_diff = height - HEIGHT;
        setLocation(width_diff / 2, height_diff / 2);
       
       setResizable(true);
       setVisible(true);
       
    }
	
	private void closeHelpWindow() {
		dispose();
	}

   
}
