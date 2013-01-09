package org.obj2openjl.v3.ui.view.projectionviewgroup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.obj2openjl.v3.Execute_obj2openjlv3;
import org.obj2openjl.v3.model.DirectionalVertex;
import org.obj2openjl.v3.model.Face;
import org.obj2openjl.v3.model.Normal;
import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.model.TexturePoint;
import org.obj2openjl.v3.model.Vertex;
import org.obj2openjl.v3.model.transform.array.ArrayTransformation;
import org.obj2openjl.v3.model.transform.array.CombineTransformation;
import org.obj2openjl.v3.model.transform.array.RotationTransformation;

public class ArrayTransformationProjectionViewGroup extends ProjectionView3DGroup {
	
	private int steps = 1;
	private JSlider slider = new JSlider(1, 200, 1);
	private JTextField sliderTextField = new JTextField("1", 5);
	
	public ArrayTransformationProjectionViewGroup(RawOpenGLModel model) {
		super(model);
		this.initialize();
	}
	
	private void calculateAndSetVertices() {
//		float distance = 100.f;
//		ArrayTransformation translation1 = new TranslationTransformation(this.steps,
//				this.distance,
//				this.distance,
//				this.distance);
//		ArrayTransformation translation2 = new TranslationTransformation(this.steps,
//				-this.distance,
//				-this.distance,
//				this.distance);
		
		float angle = 360.f;

		ArrayTransformation rotation1 = new RotationTransformation(this.steps, angle,
				0.0f,1.0f,0.0f);
		ArrayTransformation rotation2 = new RotationTransformation(this.steps, angle,
				1.0f,0.0f,0.0f);
		ArrayTransformation rotation3 = new RotationTransformation(this.steps, angle,
				0.0f,0.0f,1.0f);
		
		ArrayTransformation arrayTransformation = new CombineTransformation(Arrays.asList(rotation1, rotation2, rotation3));
//		ArrayTransformation arrayTransformation = new CombineTransformation(Arrays.asList(translation2, translation1));
		
		this.getModel().setArrayTransformation(arrayTransformation);
		float[] vertexCoordinates = this.getModel().getDataForGLDrawElements().getVertices();
//		this.getModel().setTransformation(rotation2);
//		vertexCoordinates = ListUtils.mergeArrays(vertexCoordinates, this.getModel().getDataForGLDrawElements().getVertices());
//		this.getModel().setTransformation(rotation3);
//		vertexCoordinates = ListUtils.mergeArrays(vertexCoordinates, this.getModel().getDataForGLDrawElements().getVertices());
		
		Execute_obj2openjlv3.printArray(vertexCoordinates, "asd");
		
		System.out.println(vertexCoordinates.length);
		List<Vertex> vertexList = new ArrayList<Vertex>();
		for(int i = 0; i < vertexCoordinates.length / 3; i++) {
			vertexList.add(new Vertex(vertexCoordinates[i*3 + 0],
					vertexCoordinates[i*3 + 1],
					vertexCoordinates[i*3 + 2],
					null));
		}
		List<Face> faces = new ArrayList<Face>();
		Iterator<Face> faceIterator = this.getModel().getFaces().iterator();
		Normal normal = new Normal(0.f, 0.f, 0.f);
		TexturePoint texturePoint = new TexturePoint(0.f,0.f,0.f);
		while(faceIterator.hasNext()) {
			Iterator<DirectionalVertex> vertexIterator = faceIterator.next().getVertices().iterator();
			List<DirectionalVertex> directionalVertices = new ArrayList<DirectionalVertex>();
			while(vertexIterator.hasNext()) {
				int index = vertexIterator.next().getIndex();
				directionalVertices.add(new DirectionalVertex((short)index,
						vertexList.get(index), normal, texturePoint));
			}
			faces.add(new Face(directionalVertices));
		}
		
		this.setModel(new RawOpenGLModel(faces, null, vertexList));
	}
	
	private void setSteps(int steps) {
		this.steps = steps;
		this.calculateAndSetVertices();
	}
	
	private void initialize() {
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				sliderTextField.setText("" + slider.getValue());
				ArrayTransformationProjectionViewGroup.this.setSteps(slider.getValue());
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
	}
	
	public JSlider getSlider() {
		return slider;
	}

	public JTextField getSliderTextField() {
		return sliderTextField;
	}
	
}
