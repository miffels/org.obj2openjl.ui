package org.obj2openjl.v3.ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.obj2openjl.v3.Obj2OpenJL;
import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.ui.view.projectionviewgroup.ProjectionView3DGroup;

public class ModelViewer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public ModelViewer(String modelName) throws FileNotFoundException {
		this(new Obj2OpenJL().convert(modelName));
	}
	
	public ModelViewer(RawOpenGLModel model) {
		super("Obj2OpenJL Model Viewer");
		this.setExtendedState(MAXIMIZED_BOTH);
		int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		ProjectionView3DGroup view = this.getProjectionViewGroup(model);
		
		this.addMouseWheelListener(view.getMouseWheelListener());
		
		JSplitPane splitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, view.getLeftView(), view.getBottomView());
		splitLeft.setDividerLocation(screenHeight / 2 - 50);
		JSplitPane splitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, view.getRightView(), new JPanel());
		splitRight.setDividerLocation(screenHeight / 2 - 50);
		
		new Toolbar(this, this.getOptionsPanel(view));
		
		JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeft, splitRight);
		mainSplit.setDividerLocation(screenWidth / 2);
		
		this.add(mainSplit);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	protected JPanel getOptionsPanel(ProjectionView3DGroup view) {
		JPanel optionsPanel = new JPanel();
		JButton button;
		
		this.add(optionsPanel);
		optionsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		button = view.getZoomInButton();
		button.setToolTipText("Zoom in (CTRL + mouse wheel up)");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		optionsPanel.add(button, c);
		
		button = view.getZoomOutButton();
		button.setToolTipText("Zoom out (CTRL + mouse wheel down)");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		optionsPanel.add(button, c);
		
		button = view.getUpButton();
		button.setToolTipText("Move up (mouse wheel up)");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		optionsPanel.add(button, c);
		
		button = view.getLeftButton();
		button.setToolTipText("Move left (SHIFT + mouse wheel up)");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,-20);
		optionsPanel.add(button, c);
		
		c.insets = new Insets(0,0,0,0);
		button = view.getRightButton();
		button.setToolTipText("Move right (SHIFT + mouse wheel down)");
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		optionsPanel.add(button, c);
		
		button = view.getDownButton();
		button.setToolTipText("Move down (mouse wheel down)");
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		optionsPanel.add(button, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 3;
		optionsPanel.add(view.getShowCoordinatesCheckBox(), c);
		c.gridy = 6;
		optionsPanel.add(view.getShowPolygonsCheckBox(), c);
		
		return optionsPanel;
	}
	
	protected ProjectionView3DGroup getProjectionViewGroup(RawOpenGLModel model) {
		return new ProjectionView3DGroup(model);
	}

}
