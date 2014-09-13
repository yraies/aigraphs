package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

@SuppressWarnings("unused")
public class AIGraphs extends ApplicationAdapter {
	SpriteBatch batch;
	static OrthographicCamera camera;
//	Texture img;
	Engine engine;
	int[] indices;
	NodeRenderer nr;
	Graph graph;
	AStarPathfinder astar;

	@Override
	public void create() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//		img = new Texture("badlogic.jpg");
		engine = new Engine();
		
		
		debugInit();
		
		engine.update(0f);
	}

	private void debugInit() {
		
		graph = new Graph();
		
		
		ArrayList<Node> n = new ArrayList<Node>(0);
		n.add(new Node(0,new Vector3(1, 1,0)));
		n.add(new Node(1,new Vector3(1, 5,0)));
		n.add(new Node(2,new Vector3(2, 4,0)));
		n.add(new Node(3,new Vector3(4, 2,0)));
		n.add(new Node(4,new Vector3(4, 5,0)));
		n.add(new Node(5,new Vector3(5, 4,0)));
		
		for(Node node : n)
			graph.addNode(node);
		
		graph.addConnection(0, 1);//0->1
		graph.addConnection(1, 0);//1->02
		graph.addConnection(1, 2);
		graph.addConnection(2, 3);//2->34
		graph.addConnection(2, 4);
		graph.addConnection(3, 0);//3->025
		graph.addConnection(3, 2);
		graph.addConnection(3, 5);
		graph.addConnection(4, 1);//4->15
		graph.addConnection(4, 5);
		graph.addConnection(5, 3);//5->34
		graph.addConnection(5, 4);
		
		nr = new NodeRenderer(graph);
		
		astar = new AStarPathfinder(0,4,graph);
	}

	int timer = 0;
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, (int) camera.viewportWidth, (int) camera.viewportHeight);
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		batch.setProjectionMatrix(camera.combined);
		
		timer++;
		if(timer>100){
			astar.findStep();
			timer=0;
			if(astar.atTarget){
				int rand1 = (int) Math.floor(Math.random()*5);
				int rand2 = (int) Math.floor(Math.random()*5);
				System.out.println("Searching for: " + rand1 + " -> " + rand2);
				astar = new AStarPathfinder(rand1, rand2, graph);
			}
		}
		if(timer%10 == 0){
			System.out.println(timer);
		}
		
		nr.draw();
		
		engine.update(Gdx.graphics.getDeltaTime());
	}
}