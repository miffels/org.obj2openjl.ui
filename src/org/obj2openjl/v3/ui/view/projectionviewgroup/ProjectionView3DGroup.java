package org.obj2openjl.v3.ui.view.projectionviewgroup;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.obj2openjl.v3.model.RawOpenGLModel;
import org.obj2openjl.v3.model.Vertex;
import org.obj2openjl.v3.model.transform.Transformation;
import org.obj2openjl.v3.model.transform.matrix.ScalingMatrix;
import org.obj2openjl.v3.model.transform.matrix.TransformationMatrix;
import org.obj2openjl.v3.ui.view.projectionview.ProjectionView3D;
import org.obj2openjl.v3.ui.view.projectionview.XYProjectionView3D;
import org.obj2openjl.v3.ui.view.projectionview.XZProjectionView3D;
import org.obj2openjl.v3.ui.view.projectionview.YZProjectionView3D;

public class ProjectionView3DGroup {
	
	private RawOpenGLModel model;
	private List<Vertex> vertices;
	private Set<ProjectionView3D> panels = new HashSet<ProjectionView3D>();
	private JCheckBox showCoordinatesCheckBox = new JCheckBox("Show coordinates");
	private JCheckBox showPolygonsCheckBox = new JCheckBox("Connect vertices");
	private JButton left = new JButton(new ImageIcon("img/left.png"));
	private JButton right = new JButton(new ImageIcon("img/right.png"));
	private JButton up = new JButton(new ImageIcon("img/up.png"));
	private JButton down = new JButton(new ImageIcon("img/down.png"));
	private JButton zoomIn = new JButton(new ImageIcon("img/zoom_in.png"));
	private JButton zoomOut = new JButton(new ImageIcon("img/zoom_out.png"));
	private boolean shiftPressed = false;
	private boolean controlPressed = false;
	ProjectionView3D bottomView;
	ProjectionView3D leftView;
	ProjectionView3D rightView;
	private int scrollX = 0;
	private int scrollY = 0;
	private float zoom = 1.0f;
	private final int scrollFactor = 5;
	private final float zoomFactor = 0.03f;
	private MouseWheelListener mouseWheelListener = new MouseWheelListener() {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			ProjectionView3DGroup.this.onScroll(
					e.getWheelRotation() *
					e.getScrollAmount());
		}
	};
	
	public ProjectionView3DGroup(RawOpenGLModel model) {
		this.model = model;
		this.vertices = model.getVertexPool();
		this.bottomView = new XYProjectionView3D();
		this.leftView = new XZProjectionView3D();
		this.rightView = new YZProjectionView3D();
		this.addPanel(this.bottomView);
		this.addPanel(this.leftView);
		this.addPanel(this.rightView);
		
		this.setVerticesForPanels();
		
		this.showCoordinatesCheckBox.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				ProjectionView3DGroup.this.setShowCoordinates(showCoordinatesCheckBox.isSelected());
			}
		});
		
		this.showPolygonsCheckBox.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ProjectionView3DGroup.this.setShowPolygons(showPolygonsCheckBox.isSelected());
			}
		});
		
		this.zoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.controlPressed = true;
				ProjectionView3DGroup.this.onScroll(10);
				ProjectionView3DGroup.this.controlPressed = false;
			}
		});
		
		this.zoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.controlPressed = true;
				ProjectionView3DGroup.this.onScroll(-10);
				ProjectionView3DGroup.this.controlPressed = false;
			}
		});
		
		this.left.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.shiftPressed = true;
				ProjectionView3DGroup.this.onScroll(-10);
				ProjectionView3DGroup.this.shiftPressed = false;
			}
		});
		
		this.right.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.shiftPressed = true;
				ProjectionView3DGroup.this.onScroll(10);
				ProjectionView3DGroup.this.shiftPressed = false;
			}
		});
		
		this.up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.onScroll(-10);
			}
		});
		
		this.down.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProjectionView3DGroup.this.onScroll(10);
			}
		});
		
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(
                new EventQueue() {
                    protected void dispatchEvent(AWTEvent event) {
                        if (event instanceof KeyEvent) {
                            KeyEvent keyEvent = (KeyEvent) event;
                            if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                            	if(keyEvent.getKeyCode() == KeyEvent.VK_SHIFT) {
                            		ProjectionView3DGroup.this.shiftPressed = true;
                            	}
                            	if(keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) {
                            		ProjectionView3DGroup.this.controlPressed = true;
                            	}
                            }
                            if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
                            	if(keyEvent.getKeyCode() == KeyEvent.VK_SHIFT) {
                            		ProjectionView3DGroup.this.shiftPressed = false;
                            	}
                            	if(keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) {
                            		ProjectionView3DGroup.this.controlPressed = false;
                            	}
                            }
                        }
                        super.dispatchEvent(event);
                    }
        });
	}
	
	protected RawOpenGLModel getModel() {
		return this.model;
	}
	
	public ProjectionView3D getBottomView() {
		return bottomView;
	}

	public ProjectionView3D getLeftView() {
		return leftView;
	}

	public ProjectionView3D getRightView() {
		return rightView;
	}
	
	public JCheckBox getShowCoordinatesCheckBox() {
		return showCoordinatesCheckBox;
	}
	
	public JCheckBox getShowPolygonsCheckBox() {
		return showPolygonsCheckBox;
	}
	
	public JButton getLeftButton() {
		return left;
	}

	public JButton getRightButton() {
		return right;
	}

	public JButton getUpButton() {
		return up;
	}

	public JButton getDownButton() {
		return down;
	}

	public JButton getZoomInButton() {
		return zoomIn;
	}

	public JButton getZoomOutButton() {
		return zoomOut;
	}

	public MouseWheelListener getMouseWheelListener() {
		return this.mouseWheelListener;
	}
	
	protected boolean addPanel(ProjectionView3D projectionView) {
		return this.panels.add(projectionView);
	}
	
	protected void setModel(RawOpenGLModel model) {
		this.model = model;
		this.setVerticesForPanels();
	}
	
	private void setVerticesForPanels() {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		this.vertices = this.model.getVertexPool();
		
		while(panelIterator.hasNext()) {
			panelIterator.next().setVertices(this.vertices);
		}
	}
	
	protected void repaintAll() {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		
		while(panelIterator.hasNext()) {
			panelIterator.next().repaint();
		}
	}
	
	protected void setShowCoordinates(boolean showCoordinates) {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		
		while(panelIterator.hasNext()) {
			panelIterator.next().setShowCoordinates(showCoordinates);
		}
	}
	
	protected void setShowPolygons(boolean showPolygons) {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		
		while(panelIterator.hasNext()) {
			panelIterator.next().setShowPolygons(showPolygons);
		}
	}
	
	private void setScrollX() {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		while(panelIterator.hasNext()) {
			panelIterator.next().setScrollX(this.scrollX);
		}
	}
	
	private void setScrollY() {
		Iterator<ProjectionView3D> panelIterator = this.panels.iterator();
		while(panelIterator.hasNext()) {
			panelIterator.next().setScrollY(this.scrollY);
		}
	}
	
	private void setZoom() {
		this.scale();
		this.repaintAll();
	}
	
	private void scale() {
		TransformationMatrix scalingMatrix = new ScalingMatrix(this.zoom, this.zoom, this.zoom);
		Transformation scaling = new Transformation(scalingMatrix);

		scaling.applyToVertices(this.vertices);
	}
	
	private void onScroll(int scrollAmount) {
			if(this.shiftPressed) {
				this.scrollX -= scrollAmount * this.scrollFactor;
				this.setScrollX();
			} else
			if(this.controlPressed) {
				zoom = 1 - scrollAmount * this.zoomFactor;
				this.setZoom();
			} else {
				this.scrollY -= scrollAmount * this.scrollFactor;
				this.setScrollY();
			}
	}

}
