package de.pxbox.aingraphs.graph;

import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GraphInteractor extends InputAdapter {
	Graph graph;
	
	int mode = 0;
	final int MODE_MAX = 2;
	
	int selectedNode1;
	int selectedNode2;
	
//	public Polygon poly;
	
	public GraphInteractor(Graph graph){
		this.graph = graph;
		selectedNode1 = -1;
		selectedNode2 = -1;
	}
	
	public boolean keyDown (int keycode) {
		
		return false;
	}

	public boolean keyUp (int keycode) {
		
		switch(keycode){
			case Keys.LEFT:
				switchMode(true);
				System.out.println("Mode:" + mode);
				return true;
			case Keys.RIGHT:
				switchMode(false);
				System.out.println("Mode:" + mode);
				return true;
			case Keys.UP:

				return true;
			case Keys.DOWN:

				return true;
			default:
				return false;
		}
	}

	public boolean keyTyped (char character) {
		return false;
	}

	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		
		return false;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		ArrayList<Integer> nodes = graph.getAllNodes();
		Circle c1 = new Circle(new Vector2(screenX, screenY), 2);
		
//		System.out.println("Touch up.");
		
		// NODE MODE
		if (mode == 0) {
//			System.out.println("mode 0");

			if (button == Buttons.LEFT) {
				graph.addNode(new Node(new Vector3(screenX, Gdx.graphics
						.getHeight() - screenY, 0)));
			}
 
			if (button == Buttons.RIGHT) {
				for (Integer node : nodes) {
					Vector3 v = graph.getPosition(node);
					Circle c2 = new Circle(v.x, Gdx.graphics.getHeight() - v.y,
							10);
					if (c2.contains(c1) || c2.overlaps(c1)) {
						graph.deleteNode(node);
						return true;
					}
				}
			}
		} 
		
		// EDGE MODE
		if(mode == 1 ){
//			System.out.println("mode 1");
			
			for (Integer node : nodes) {
				Vector3 v = graph.getPosition(node);
				Circle c2 = new Circle(v.x, Gdx.graphics.getHeight() - v.y,	10);
				if (c2.contains(c1) || c2.overlaps(c1)) {
					if (button == Buttons.LEFT) {
						selectedNode1 = node;
					} else if (button == Buttons.RIGHT) {
						selectedNode2 = node;
//						System.out.println("right " + selectedNode1 + " " + selectedNode2);
					}

					if(selectedNode1 != -1 && selectedNode2 != -1){
						graph.addConnection(selectedNode1, selectedNode2);
						graph.setEdgeColor(selectedNode1, selectedNode2, Color.RED);
						selectedNode1 = -1;
						selectedNode2 = -1;				
					}
					return true;
				}
			}
			
			for (Integer node : nodes) {
				ArrayList<Edge> edges = graph.getEdges(node);
				for(Edge edge : edges){
					Vector3 v1 = graph.getNode(edge.getNode1().getID()).pos;
					Vector3 v2 = graph.getNode(edge.getNode2().getID()).pos;
					Vector2 p1 = new Vector2(v1.x, v1.y);
					Vector2 p2 = new Vector2(v2.x, v2.y);
					Vector2 path = p2.cpy().sub(p1);
					Vector2 offset = path.cpy().rotate(-90);
					offset.nor().scl(20);

					float[] vertices = { p1.x, p1.y, p2.x, p2.y, p2.x - offset.x,
							p2.y - offset.y, p1.x - offset.x, p1.y - offset.y };

					StringBuilder sb = new StringBuilder();
					
					sb.append((int) vertices[0] + "-");
					sb.append((int) vertices[1] + "-");
					sb.append((int) vertices[2] + "-");
					sb.append((int) vertices[3] + "-");
					sb.append((int) vertices[4] + "-");
					sb.append((int) vertices[5] + "-");
					sb.append((int) vertices[6] + "-");
					sb.append((int) vertices[7]);
					
					Polygon poly = new Polygon(vertices);
					if(poly.contains(screenX, Gdx.graphics.getHeight()-screenY)){
						System.out.println("found");
//						this.poly = poly;

						if(Gdx.input.isKeyPressed(Input.Keys.X)){
							graph.deleteConnection(edge);
						}
						
						return true;
					}else{
//						System.out.println("nope");
//						this.poly = null;
					}
				}
			}
		}
		
		//PATHFINDING MODE
		if (mode == 2) {
//			System.out.println("mode 2");
			for (Integer node : nodes) {
				Vector3 v = graph.getPosition(node);
				Circle c2 = new Circle(v.x, Gdx.graphics.getHeight() - v.y, 10);
				if (c2.contains(c1) || c2.overlaps(c1)) {
					if (button == Buttons.LEFT)
						selectedNode1 = node;
					if (button == Buttons.RIGHT)
						selectedNode2 = node;

					System.out.println("PF: " + selectedNode1 + " " + selectedNode2);
					
					graph.setNodeColor(node, Color.RED);

					return true;
				}

			}
		}

		return false;
	}

	public boolean touchDragged (int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}
	
	public void switchMode(boolean up) {
		if (up) {
			if (mode == MODE_MAX)
				mode = 0;
			else
				mode++;
		} else {
			if (mode == 0)
				mode = MODE_MAX;
			else
				mode--;
		}
	}

	public void setMode(int mode2) {
		selectedNode1 = -1;
		selectedNode2 = -1;
		this.mode = mode2;
	}
	
	public int[] getPathfindingNodes(){
		if(selectedNode1 != -1 && selectedNode2 != -1){
			return new int[] {selectedNode1,selectedNode2};
		}
		return null;
	}
}
