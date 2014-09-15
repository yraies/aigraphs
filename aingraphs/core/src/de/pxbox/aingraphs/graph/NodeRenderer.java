package de.pxbox.aingraphs.graph;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.pxbox.aingraphs.AIGraphs;

public class NodeRenderer {

	private ShapeRenderer sr;
	private Graph graph;

	public final float SCALE = 80;

	public NodeRenderer(Graph graph) {
		this.sr = new ShapeRenderer();
		this.graph = graph;
	}

	public NodeRenderer() {
		this.sr = new ShapeRenderer();
		this.graph = null;
	}

	/* TODO: Edge coloring*/
	public void draw() {

		sr.setProjectionMatrix(AIGraphs.getCamera().combined);

		sr.begin(ShapeType.Filled);
		sr.setColor(1f, 1f, 1f, 1f);
		for (Integer node : graph.getAllNodes()) {

			Vector3 p1 = graph.getPosition(node);
			float x1 = p1.x * SCALE;
			float y1 = p1.y * SCALE;
			// draw the Node
			sr.setColor(graph.getNode(node).getCol());
			sr.circle(x1, y1, 0.1f * SCALE);

			for (Edge edge : graph.getEdges(node)) {

				Vector3 p2 = edge.getNode2().getPos();
				float x2 = p2.x * SCALE;
				float y2 = p2.y * SCALE;
				Vector3 helper = p1.cpy().sub(p2);
				Vector2 path = new Vector2(helper.x,helper.y);
				float angle = path.angle() / 90;
				float xoffset = 0;
				float yoffset = 0;

				// calculate the offset for the connection lines
				if (angle < 1) {
					xoffset = 2;
					yoffset = 1;
				} else if (angle < 2) {
					xoffset = 2;
					yoffset = -1;
				} else if (angle < 3) {
					xoffset = -2;
					yoffset = -1;
				} else {
					xoffset = -2;
					yoffset = 1;
				}

				// draw the connection line
				sr.line(x1 + xoffset, y1 + yoffset, x2 + xoffset, y2 + yoffset);
				path.limit(path.len() * 0.9f);
				// draw the connection indicator
				sr.circle(x1 - path.x * SCALE, y1 - path.y * SCALE,	0.05f * SCALE);

			}
		}
		sr.end();

	}

	public ShapeRenderer getSr() {
		return sr;
	}

	public Graph getGraph() {
		return graph;
	}

	public float getSCALE() {
		return SCALE;
	}

	public void setSr(ShapeRenderer sr) {
		this.sr = sr;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	
}
