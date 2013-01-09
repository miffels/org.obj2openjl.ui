package org.obj2openjl.v3.ui.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Toolbar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Toolbar(JFrame frame, JPanel panel) {
		super(frame, "Tools");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.add(panel);
		
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

}
