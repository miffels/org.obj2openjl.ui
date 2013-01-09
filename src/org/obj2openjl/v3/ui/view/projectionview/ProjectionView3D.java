package org.obj2openjl.v3.ui.view.projectionview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.obj2openjl.v3.model.Vertex;

public abstract class ProjectionView3D extends JPanel {
	
	private boolean showCoordinates = false;
	private boolean showPolygons = false;
	private List<Vertex> vertices;
	private List<Vertex> originalVertices;
	private int scrollX = 0;
	private int scrollY = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProjectionView3D() {
		this.vertices = null;
	}
	
	protected abstract String getHorizontalAxisCaption();
	protected abstract String getVerticalAxisCaption();
	protected abstract String getProjectionName();
	protected abstract Point2D getVertexCoordinates(Vertex vertex);
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		this.drawDiagramBackground(g2d);
		this.drawModel(g2d);
	}
	
	private void drawDiagramCaption(Graphics2D g2d) {
		g2d.drawString(this.getProjectionName(), 3, 10);
	}
	
	private void drawDiagramBackground(Graphics2D g2d) {
		this.drawDiagramCaption(g2d);
		this.drawAxes(g2d);
		this.drawAxesCaptions(g2d);
	}
	
	private void drawAxes(Graphics2D g2d) {
		g2d.drawLine(this.getWidth() / 2 + this.scrollX,
				0,
				this.getWidth() / 2 + this.scrollX,
				this.getHeight());
		g2d.drawLine(0,
				this.getHeight() / 2 + this.scrollY,
				this.getWidth(),
				this.getHeight() / 2 + this.scrollY);
	}
	
	private void drawAxesCaptions(Graphics2D g2d) {
		g2d.drawString(this.getHorizontalAxisCaption(), this.getWidth() - 5, this.getHeight()/2 + 10 + this.scrollY);
		g2d.drawString(this.getVerticalAxisCaption(), this.getWidth() / 2 + 5 + this.scrollX, 10);
	}
	
	private void drawModel(Graphics2D g2d) {
		if(this.vertices == null) return;
		Polygon polygon = new Polygon();
		
		for(int i = 0; i < this.vertices.size(); i++) {
			Vertex vertex = this.vertices.get(i);
			
			this.drawPoint(g2d, vertex);
			this.drawPointCaptions(g2d, i);
			this.addPointToPolygon(vertex, polygon);
		}
		
		this.drawPolygon(g2d, polygon);
	}
	
	private int getOffsetX() {
		return this.getWidth() / 2 + this.scrollX;
	}
	private int getOffsetY() {
		return this.getHeight() / 2 + this.scrollY;
	}
	
	private void drawPoint(Graphics2D g2d, Vertex vertex) {
		float x = (float)this.getTranslatedVertexCoordinates(vertex).getX();
		float y = (float)this.getTranslatedVertexCoordinates(vertex).getY();
		
		g2d.setColor(Color.RED);
		g2d.fillOval((int)x, (int)y, 3, 3);
	}
	
	private void addPointToPolygon(Vertex vertex, Polygon polygon) {
		float x = (float)this.getTranslatedVertexCoordinates(vertex).getX();
		float y = (float)this.getTranslatedVertexCoordinates(vertex).getY();
		
		polygon.addPoint((int)x, (int)y);
	}
	
	private void drawPolygon(Graphics2D g2d, Polygon polygon) {
		if(this.showPolygons) {
			g2d.setColor(Color.BLUE);
			g2d.drawPolygon(polygon);
		}
	}
	
	private Point2D getTranslatedVertexCoordinates(Vertex vertex) {
		return new Point2D.Float((float)this.getVertexCoordinates(vertex).getX() + this.getOffsetX(),
				-(float)this.getVertexCoordinates(vertex).getY() + this.getOffsetY());
	}
	
	private void drawPointCaptions(Graphics2D g2d, int vertexIndex) {
		g2d.setColor(Color.BLACK);
		if(this.showCoordinates) {
			float x = (float)this.getTranslatedVertexCoordinates(this.vertices.get(vertexIndex)).getX();
			float y = (float)this.getTranslatedVertexCoordinates(this.vertices.get(vertexIndex)).getY();
			DecimalFormat df = new DecimalFormat("#.#");
			g2d.drawString(df.format(this.originalVertices.get(vertexIndex).getX()) + ", " +
					df.format(this.originalVertices.get(vertexIndex).getY()) + ", " +
					df.format(this.originalVertices.get(vertexIndex).getZ()),
					x + 5,
					y + 5);
		}
	}
	
	public void setVertices(List<Vertex> vertices) {
		this.originalVertices = this.getCopyOf(vertices);
		this.vertices = vertices;
		this.repaint();
	}
	
	private List<Vertex> getCopyOf(List<Vertex> vertexPool) {
		List<Vertex> vertexCopy = new ArrayList<Vertex>();
		Iterator<Vertex> originalVertexIterator = vertexPool.iterator();
		
		while(originalVertexIterator.hasNext()) {
			vertexCopy.add(new Vertex(originalVertexIterator.next()));
		}
		
		return vertexCopy;
	}
	
	public void setShowCoordinates(boolean showCoordinates) {
		this.showCoordinates = showCoordinates;
		this.repaint();
	}
	
	public void setShowPolygons(boolean showPolygons) {
		this.showPolygons = showPolygons;
		this.repaint();
	}

	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
		this.repaint();
	}

	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
		this.repaint();
	}

}
