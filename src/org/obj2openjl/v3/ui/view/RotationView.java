package org.obj2openjl.v3.ui.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.ui.view.projectionviewgroup.ProjectionView3DGroup;
import org.obj2openjl.v3.ui.view.projectionviewgroup.RotationProjectionViewGroup;

public class RotationView extends ModelViewer {

	public RotationView() {
		super((RawOpenGLModel)null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected JPanel getOptionsPanel(ProjectionView3DGroup view) {
		return this.getOptionsPanel((RotationProjectionViewGroup)view);
	}
	
	protected JPanel getOptionsPanel(RotationProjectionViewGroup view) {
		JPanel optionsPanel = super.getOptionsPanel(view);
		optionsPanel.add(view.getSlider());
		optionsPanel.add(new JLabel("Axis"));
		optionsPanel.add(view.getAxisTextField());
		optionsPanel.add(new JLabel("Vertex"));
		optionsPanel.add(view.getVertexTextField());
		
		return optionsPanel;
	}
	
	@Override
	protected RotationProjectionViewGroup getProjectionViewGroup(RawOpenGLModel model) {
		return new RotationProjectionViewGroup();
	}


}
