package de.pxbox.aingraphs.visual;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class CatmulRomSpline {
	
	Polygon poly;
	CatmullRomSpline<Vector2> spline;
	int numControlls;
	
	public CatmulRomSpline(Vector2... controll) {
		sr = new ShapeRenderer();
		numControlls = controll.length;
		float[] curveArray = new float[numControlls * 2];
		for (int i = 0; i < numControlls; i++) {
			curveArray[i * 2] = controll[i].x;
			curveArray[i * 2 + 1] = controll[i].y;
		}
		
		poly = new Polygon(curveArray);

		refreshSpline();
	}

	public void setRotation(float degrees){
		poly.setRotation(degrees);
		refreshSpline();
	}

	public void setPosition(float x, float y){
		poly.setPosition(x,y);
		refreshSpline();
	}

	public void setScale(float x, float y){
		poly.setScale(x,y);
		refreshSpline();
	}

	public void setOrigin(float x, float y){
		poly.setOrigin(x,y);
		refreshSpline();
	}

	public void translate(float x, float y){
		poly.translate(x,y);
		refreshSpline();
	}

	public void rotate(float degree){
		poly.setRotation(degree + poly.getRotation());
		refreshSpline();
	}
	
	public void refreshSpline() {
		float[] translated = poly.getTransformedVertices();
		Vector2[] dataSet = new Vector2[numControlls];

		for (int i = 0; i < numControlls; i++) {
			dataSet[i] = new Vector2(translated[i * 2], translated[i * 2 + 1]);
		}

		spline = new CatmullRomSpline<Vector2>(dataSet, false);
	}	
	ShapeRenderer sr;
	
	public void draw(){
		sr.begin(ShapeType.Line);
		sr.setColor(1f, 1f, 1f, 1f);
		Vector2 out = new Vector2();
		out = spline.valueAt(out, 0);
		Vector2 lastout = new Vector2();
		for(float i = 0f; i <= 1.01; i += 0.1f){
			lastout = out.cpy();
			out = spline.valueAt(out, 0);
			out = spline.valueAt(out, i);
			sr.line(lastout.cpy(), out.cpy());
		}
		sr.end();
	}

}
