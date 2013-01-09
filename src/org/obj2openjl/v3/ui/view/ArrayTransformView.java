package org.obj2openjl.v3.ui.view;

import java.awt.GridBagConstraints;
import java.io.FileNotFoundException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.ui.view.projectionviewgroup.ArrayTransformationProjectionViewGroup;
import org.obj2openjl.v3.ui.view.projectionviewgroup.ProjectionView3DGroup;

public class ArrayTransformView extends ModelViewer {
	
	public ArrayTransformView(RawOpenGLModel model) {
		super(model);
	}
	
	public ArrayTransformView(String modelName) throws FileNotFoundException {
		super(modelName);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	protected JPanel getOptionsPanel(ProjectionView3DGroup view) {
		return this.getOptionsPanel((ArrayTransformationProjectionViewGroup)view);
	}
	
	protected JPanel getOptionsPanel(ArrayTransformationProjectionViewGroup view) {
		JPanel optionsPanel = super.getOptionsPanel(view);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 3;
		optionsPanel.add(new JLabel("Transformation (use with point demo only!):"), c);
		c.gridy = 8;
		c.gridwidth = 1;
		optionsPanel.add(view.getSliderTextField(), c);
		c.gridx = 1;
		c.gridwidth = 2;
		optionsPanel.add(view.getSlider(), c);
		
		return optionsPanel;
	}
	
	@Override
	protected ArrayTransformationProjectionViewGroup getProjectionViewGroup(RawOpenGLModel model) {
		return new ArrayTransformationProjectionViewGroup(model);
	}

}
