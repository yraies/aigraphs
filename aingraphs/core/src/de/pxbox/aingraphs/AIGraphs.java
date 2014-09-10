package de.pxbox.aingraphs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AIGraphs extends ApplicationAdapter {
	SpriteBatch batch;
	static OrthographicCamera camera;
//	Texture img;
	Engine engine;
	int[] indices;

	@Override
	public void create() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//		img = new Texture("badlogic.jpg");
		engine = new Engine();
		
		debugInit();
		
		engine.addSystem(new NodeUpdaterSystem(engine));
		engine.addSystem(new NodeRendererSystem(engine));
		engine.addSystem(new AISystem(engine));
		engine.update(0f);
	}

	private void debugInit() {
		NodeEntity[] e = new NodeEntity[6];
		e[0] = new NodeEntity(1, 1);
		e[1] = new NodeEntity(1, 5);
		e[2] = new NodeEntity(2, 4);
		e[3] = new NodeEntity(4, 2);
		e[4] = new NodeEntity(4, 5);
		e[5] = new NodeEntity(5, 4);
		
		e[0].add(new EdgeComponent(e[1]));
		e[1].add(new EdgeComponent(e[0],e[2]));
		e[2].add(new EdgeComponent(e[3],e[4]));
		e[3].add(new EdgeComponent(e[0],e[2],e[5]));
		e[4].add(new EdgeComponent(e[1],e[5]));
		e[5].add(new EdgeComponent(e[3],e[4]));
		
		indices = new int[e.length];
		for(int i = 0; i < indices.length; i++)
			indices[i] = e[i].getIndex();
		
		for(Entity entity : e)
			engine.addEntity(entity);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, (int) camera.viewportWidth, (int) camera.viewportHeight);
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		batch.setProjectionMatrix(camera.combined);
		
		engine.update(Gdx.graphics.getDeltaTime());
	}
}