package org.obj2openjl.v3.ui.view.projectionviewgroup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.model.Vertex;
import org.obj2openjl.v3.model.transform.Transformation;
import org.obj2openjl.v3.model.transform.matrix.RotationMatrix;
import org.obj2openjl.v3.model.transform.matrix.TransformationMatrix;

public class RotationProjectionViewGroup extends ProjectionView3DGroup {
	
	private int steps = 1;
	private final float angle = 360.0f;
	private Vertex vertex;
	private Vertex axis;
	
	private JSlider slider = new JSlider(1, 200, 1);
	private JTextField axisTextField = new JTextField("0.0,1.0,0.0", 20);
	private JTextField vertexTextField = new JTextField("100.0,0.0,100.0", 20);
	private JTextField sliderTextField = new JTextField("1", 5);
	
	public RotationProjectionViewGroup() {
		super(new RawOpenGLModel(null, null, Arrays.asList(new Vertex(100.0f, 0.0f, 100.0f, null))));
		
		this.vertex = new Vertex(100.0f, 0.0f, 100.0f, null);
		this.axis = new Vertex(0.0f, 1.0f, 0.0f, null);
		
		this.initialize();
	}
	
	private void calculateAndSetVertices() {
		double step = this.angle / this.steps;
		List<Vertex> vertices = new ArrayList<Vertex>();
		for(int i = 0; i < steps; i++) {
			TransformationMatrix rotationMatrix = new RotationMatrix((float)(i*step),
					this.axis.getX(),
					this.axis.getY(),
					this.axis.getZ());
			Transformation rotation = new Transformation(rotationMatrix);
			Vertex vertex = new Vertex(this.vertex.getX(),
					this.vertex.getY(),
					this.vertex.getZ(),
					null);
			rotation.applyTo(vertex);
			vertices.add(vertex);
		}
		this.setModel(new RawOpenGLModel(null, null, vertices));
	}
	
	private void setSteps(int steps) {
		this.steps = steps;
		this.calculateAndSetVertices();
	}
	
	private void setAxis(Vertex axis) {
		this.axis = axis;
		this.calculateAndSetVertices();
	}
	
	private void setVertex(Vertex vertex) {
		this.vertex = vertex;
		this.calculateAndSetVertices();
	}
	
	private void initialize() {
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				sliderTextField.setText("" + slider.getValue());
				RotationProjectionViewGroup.this.setSteps(slider.getValue());
			}
		});
		
		sliderTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(sliderTextField.getText().matches("^[0-9]*$")) {
					slider.setValue(Integer.parseInt(sliderTextField.getText()));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		axisTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(axisTextField.getText().matches("^-?[0-9]*\\.[0-9]*,-?[0-9]*\\.[0-9]*,-?[0-9]*\\.[0-9]*$")) {
					String[] strings = axisTextField.getText().split(",");
					RotationProjectionViewGroup.this.setAxis(new Vertex(
							Float.parseFloat(strings[0]),
							Float.parseFloat(strings[1]),
							Float.parseFloat(strings[2]),
							null));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		vertexTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(vertexTextField.getText().matches("^-?[0-9]*\\.[0-9]*,-?[0-9]*\\.[0-9]*,-?[0-9]*\\.[0-9]*$")) {
					String[] strings = vertexTextField.getText().split(",");
					RotationProjectionViewGroup.this.setVertex(new Vertex(
							Float.parseFloat(strings[0]),
							Float.parseFloat(strings[1]),
							Float.parseFloat(strings[2]),
							null));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
	}
	
	public JSlider getSlider() {
		return slider;
	}

	public JTextField getAxisTextField() {
		return axisTextField;
	}

	public JTextField getVertexTextField() {
		return vertexTextField;
	}

	public JTextField getSliderTextField() {
		return sliderTextField;
	}
	
}
