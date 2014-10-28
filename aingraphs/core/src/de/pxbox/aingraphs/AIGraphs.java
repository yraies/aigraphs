package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.pxbox.aingraphs.graph.AStarPathfinder;
import de.pxbox.aingraphs.graph.Graph;
import de.pxbox.aingraphs.graph.GraphInteractor;
import de.pxbox.aingraphs.graph.Node;
import de.pxbox.aingraphs.graph.NodeIDGen;
import de.pxbox.aingraphs.visual.CatmulRomSpline;
import de.pxbox.aingraphs.visual.InformationPanel;
import de.pxbox.aingraphs.visual.NodeRenderer;

@SuppressWarnings("unused")
public class AIGraphs extends ApplicationAdapter {
	SpriteBatch batch;
	static OrthographicCamera camera;
//	Texture img;
	int[] indices;
	NodeRenderer nr;
	Graph graph;
	AStarPathfinder astar;
	
	GraphInteractor interactor;
	TextButton[] buttons;
	InformationPanel label;
	Table table;
	Table labelTable;
	Stage stage;
	Skin skin;
	
	public static int mode = 0;

	@Override
	public void create() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//		img = new Texture("badlogic.jpg");
		
		NodeIDGen.init();
		
		debugInit();
		initUI();
	}
	
	private void initUI(){
		stage = new Stage(new FitViewport(camera.viewportWidth, camera.viewportHeight, camera));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		table = new Table(skin);
		table.setFillParent(true);
		table.left();
		InputMultiplexer mux = new InputMultiplexer(stage,interactor);
		Gdx.input.setInputProcessor(mux);

		label = new InformationPanel(skin);
		table.top();
		table.add(label).width(200).padBottom(400);
		table.row().top();
		
		buttons = new TextButton[3];
		buttons[0] = new TextButton("Knoten",skin);
		buttons[1] = new TextButton("Kanten",skin);
		buttons[2] = new TextButton("Wegfindung",skin);
				
		buttons[0].addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mode = 0;
				interactor.setMode(mode);
				label.setMode(mode);
			}
		});
		buttons[1].addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mode = 1;
				interactor.setMode(mode);
				label.setMode(mode);
			}
		});
		buttons[2].addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mode = 2;
				interactor.setMode(mode);
				label.setMode(mode);
			}
		});
		
		table.bottom().row();
		table.add(buttons[0]).pad(1).padLeft(10).padTop(10).left().row();
		table.add(buttons[1]).pad(1).padLeft(10).left().row();
		table.add(buttons[2]).pad(1).padLeft(10).padBottom(10).left().row();

		table.pack();
		stage.setDebugAll(false);
		stage.addActor(label);
		
		stage.addActor(table);
	}

	private void debugInit() {
		
		graph = new Graph();
		
		ArrayList<Node> n = new ArrayList<Node>(0);
		n.add(new Node(new Vector3(1*80, 1*80,0)));
		n.add(new Node(new Vector3(1*80, 5*80,0)));
		n.add(new Node(new Vector3(2*80, 4*80,0)));
		n.add(new Node(new Vector3(4*80, 2*80,0)));
		n.add(new Node(new Vector3(4*80, 5*80,0)));
		n.add(new Node(new Vector3(5*80, 4*80,0)));
		
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

		interactor = new GraphInteractor(graph);
		astar = new AStarPathfinder(0,4,graph);
		
//		Vector2[] con = new Vector2[4];
//		con[0] = new Vector2(-5, -5);
//		con[1] = new Vector2(0, 0);
//		con[2] = new Vector2(10, 0);
//		con[3] = new Vector2(15, -5);
//		
//		spline = new CatmulRomSpline(con);
//		spline.setScale(10, 20);
		
		sr = new ShapeRenderer();
	}

	int timer = 0;
	CatmulRomSpline spline;
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, (int) camera.viewportWidth, (int) camera.viewportHeight);
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		batch.setProjectionMatrix(camera.combined);
		
//		if(Gdx.input.isKeyPressed(Keys.RIGHT))
//			spline.translate(5, 0);
//		if(Gdx.input.isKeyPressed(Keys.LEFT))
//			spline.translate(-5, 0);
//		if(Gdx.input.isKeyPressed(Keys.UP))
//			spline.translate(0, 5);
//		if(Gdx.input.isKeyPressed(Keys.DOWN))
//			spline.translate(0, -5);
			
//		spline.setRotation(0);
//		spline.setScale(25, 500);
//		
//		spline.draw();
		
/*		timer++;
		if(timer>200){
			astar.findStep();
			timer=0;
			if(astar.isAtTarget()){
				int rand1 = (int) Math.floor(Math.random()*5);
				int rand2 = (int) Math.floor(Math.random()*5);
				System.out.println("Searching for: " + rand1 + " -> " + rand2);
				astar = new AStarPathfinder(rand1, rand2, graph);
			}
		}
//		if (timer % 20 == 0 && timer != 0 && timer != 100) {
//			System.out.println(timer + "%");
//		}
*/		
		
		int[] pfnodes = interactor.getPathfindingNodes();
		if(interactor.getPathfindingNodes() != null){
			astar = new AStarPathfinder(pfnodes[0], pfnodes[1], graph);
			astar.findInstant();
		}
		
		nr.draw();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
//		sr.begin(ShapeType.Line);
//		sr.line(0, 0, 400, 400);
//		sr.end();
	}
	
	ShapeRenderer sr;
	
	public static Camera getCamera(){
		return camera;
	}
}