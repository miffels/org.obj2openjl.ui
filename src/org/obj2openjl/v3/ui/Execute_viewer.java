package org.obj2openjl.v3.ui;

import java.io.FileNotFoundException;

import org.obj2openjl.v3.Obj2OpenJL;
import org.obj2openjl.v3.ui.view.ArrayTransformView;

public class Execute_viewer {
	
	private static final String ROOT_DIRECTORY = "samples/";

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Use slider (bottommost control in Toolbar) with Point demo only!
		// The slider applies some of the transformations implemented as part
		// of this project, which basically duplicate the vertices.
		// Any other than the Point demo easily kills the JVM.
		demoDragon();
	}
	
	public static void demo(String modelName) throws FileNotFoundException {
		new ArrayTransformView(new Obj2OpenJL().convert(ROOT_DIRECTORY + modelName).center().scale(10.f));
	}
	
	public static void demoDragon() throws FileNotFoundException {
		demo("dragon");
	}
	
	public static void demoGenerator() throws FileNotFoundException {
		demo("generator");
	}
	
	public static void demoHelicopter() throws FileNotFoundException {
		demo("helicopter");
	}
	
	public static void demoPoint() throws FileNotFoundException {
		demo("point");
	}
	
	public static void demoRobot() throws FileNotFoundException {
		demo("robot");
	}
	
	public static void demoTeapot() throws FileNotFoundException {
		demo("teapot");
	}

}
