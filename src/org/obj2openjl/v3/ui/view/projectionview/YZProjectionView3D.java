package org.obj2openjl.v3.ui.view.projectionview;

import java.awt.geom.Point2D;

import org.obj2openjl.v3.model.Vertex;

public class YZProjectionView3D extends ProjectionView3D {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getHorizontalAxisCaption() {
		return "y";
	}

	@Override
	protected String getVerticalAxisCaption() {
		return "z";
	}

	@Override
	protected String getProjectionName() {
		return "YZ projection";
	}

	@Override
	protected Point2D getVertexCoordinates(Vertex vertex) {
		return new Point2D.Float(vertex.getFloatValues()[1], vertex.getFloatValues()[2]);
	}

}
