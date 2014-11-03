package de.pxbox.aingraphs.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;

public class AStarPathfinder {

	Graph graph;
	Integer  startNode;
	Integer  targetNode;
	HashMap<Integer,Integer> bestPredecessor;
	ArrayList<Integer> open;
	ArrayList<Integer> closed;
	private boolean atTarget;
	private boolean isSolvable;
	
	float pathlength;
	
	public AStarPathfinder(Integer start, Integer target,Graph graph){
		startNode = start;
		targetNode = target;
		this.graph = graph;
		pathlength = 0;
		isSolvable = true;
		open = new ArrayList<Integer>(0);
		closed = new ArrayList<Integer>(0);
		bestPredecessor = new HashMap<Integer, Integer>(0);
		setAtTarget(false);
		open.add(startNode);
	}
	
	public ArrayList<Integer> findInstant() {		
		while (!isAtTarget() && isSolvable()) {
			findStep();
		}

		if (isSolvable()) {
			ArrayList<Integer> path = createPath();
			pathlength = calculateBestLen(path.get(0));
			return path;
		} else {
			pathlength = 0;
			return null;
		}
	}

	public void findStep() {
		if(isAtTarget() || !isSolvable())
			return;
		
		graph.clearColors();
		
		Integer x = null; 

//		for(Integer openNode : open)
//			System.out.print(graph.getPosition(openNode) + " - " );
		
		// getting the best ( = lowest distance to target + lowest distance
		// traveled ) open Node
		for (Integer node : open) {
			float entHeuristic = graph.getDistance(node, targetNode)+calculateBestLen(node);
			if (x != null) {
				float xDist = graph.getDistance(x, targetNode) + calculateBestLen(x);
				if (entHeuristic < xDist) {
					x = node;
				}
			} else {
				x = node;
			}
		}
		
		if(x == null){
			setSolvable(false);
			return;
		}
			
		
		// checking if x is allready the target
		setAtTarget(targetNode.equals(x));

		System.out.println(targetNode.equals(x));
		
		if (!isAtTarget()) {
			//expanding preperations
			open.remove(x);
			closed.add(x);
			
//			System.out.println("X: " + x);
			graph.setNodeColor(x,Color.GREEN);
			
			//expand x
			ArrayList<Integer> successors = graph.getExpansion(x);
			for (Integer y : successors) {

				graph.setNodeColor(y, Color.YELLOW);
				graph.setEdgeColor(x, y, Color.YELLOW);
				
				// check with other paths to y which is the best and set
				// the best or if there is no path add it to open
				if (open.contains(y)) {
					
					float currLen = calculateBestLen(y);
					float newLen = calculateBestLen(x) + graph.getDistance(x, y);
					
					if(newLen < currLen){
						setBestPredecessor(y, x);
					}
					
				} else if (closed.contains(y)) {
					
					float currLen = calculateBestLen(y);
					float newLen = calculateBestLen(x) + graph.getDistance(x, y);
					
					if(newLen < currLen){
						setBestPredecessor(y, x);
					}
					
				} else {
					setBestPredecessor(y, x);
					open.add(y);
				}
				
			}
			
		} else {
//			System.out.println("AT TARGET");
			ArrayList<Integer> path = createPath();
			Collections.reverse(path);
			graph.clearColors();
			
			for(int i = 0; i < path.size()-1; i++){
				graph.setNodeColor( path.get(i), Color.GREEN);
				graph.setEdgeColor( path.get(i), path.get(i+1), Color.GREEN);
			}
			
			graph.setNodeColor(path.get(path.size()-1), Color.GREEN);
			
		}
	}
	
	public float calculateBestLen(Integer startNode){
		boolean isAtEnd = false;

		float length = 0;
		Integer activeNode = startNode;
		
		while (!isAtEnd) {
			if (!hasPredecessor(activeNode)) {
				isAtEnd = true;
			} else {
				length += graph.getDistance(activeNode,getBestPredecessor(activeNode));
				activeNode = getBestPredecessor(activeNode);
			}
		}
		
		return length;
	}
	
	public boolean hasPredecessor(Integer node){
		return bestPredecessor.containsKey(node);
	}
	
	public Integer getBestPredecessor(Integer node){
		return bestPredecessor.get(node);
	}
	
	public void setBestPredecessor(Integer n, Integer nBest){
		bestPredecessor.put(n, nBest);
	}

	public ArrayList<Integer> createPath() {
		if(!isAtTarget())
			return null;
		
		boolean isAtEnd = false;

		ArrayList<Integer> path = new ArrayList<Integer>(0);
		Integer activeNode = targetNode;
		
		path.add(activeNode);
		while (!isAtEnd) {
			if (!hasPredecessor(activeNode)) {
				isAtEnd = true;
			} else {
				activeNode = getBestPredecessor(activeNode);
				path.add(activeNode);
			}
		}
		
		return path;
	}
	
	public void reset(){
		open = new ArrayList<Integer>(0);
		closed = new ArrayList<Integer>(0);
		pathlength = 0;
		isSolvable = true;
		bestPredecessor = new HashMap<Integer, Integer>(0);
		setAtTarget(false);
		open.add(startNode);
	}
	
	public void checkedResetTo(Integer start, Integer target){
		
		if(this.startNode == start && this.targetNode == target)
			return;
		
		startNode = start;
		targetNode = target;
		open = new ArrayList<Integer>(0);
		closed = new ArrayList<Integer>(0);
		bestPredecessor = new HashMap<Integer, Integer>(0);
		setAtTarget(false);
		open.add(startNode);
		pathlength = 0;
		isSolvable = true;
	}

	public boolean isAtTarget() {
		return atTarget;
	}

	private void setAtTarget(boolean atTarget) {
		this.atTarget = atTarget;
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	private void setSolvable(boolean solvability) {
		this.isSolvable = solvability;
	}

	public String getLength() {
		System.out.println("getting Lenght " + isAtTarget() + " " + pathlength);
		return Float.toString(Math.round(pathlength));
	}
}
