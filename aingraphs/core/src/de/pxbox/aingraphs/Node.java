package de.pxbox.aingraphs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Node {

	int		ID;
	Vector3 pos;
	Color	col;
	
	// constructors
	public Node(){
		this.ID  = -1;
		this.pos = null;
		this.col = Color.WHITE;
	}
	public Node(int id){
		this.ID  = id;
		this.pos = null;
		this.col = Color.WHITE;
	}
	public Node(int id, Vector3 pos){
		this.ID  = id;
		this.pos = pos;
		this.col = Color.WHITE;
	}
	public Node(int id, Color col){
		this.ID  = id;
		this.pos = null;
		this.col = col;
	}
	public Node(int id, Vector3 pos, Color col){
		this.ID  = id;
		this.pos = pos;
		this.col = col;
	}
	
	
	// getters and setters
	public int getID() {
		return ID;
	}
	public Vector3 getPos() {
		return pos;
	}
	public Color getCol() {
		return col;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setPos(Vector3 pos) {
		this.pos = pos;
	}
	public void setCol(Color col) {
		this.col = col;
	}	
	
}
