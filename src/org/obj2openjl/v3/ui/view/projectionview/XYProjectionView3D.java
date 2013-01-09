package org.obj2openjl.v3.ui.view.projectionview;

import java.awt.geom.Point2D;

import org.obj2openjl.v3.model.Vertex;

public class XYProjectionView3D extends ProjectionView3D {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getHorizontalAxisCaption() {
		return "x";
	}

	@Override
	protected String getVerticalAxisCaption() {
		return "y";
	}

	@Override
	protected String getProjectionName() {
		return "XY projection";
	}

	@Override
	protected Point2D getVertexCoordinates(Vertex vertex) {
		return new Point2D.Float(vertex.getFloatValues()[0], vertex.getFloatValues()[1]);
	}

}
