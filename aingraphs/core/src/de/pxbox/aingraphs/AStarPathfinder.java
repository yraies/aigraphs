package de.pxbox.aingraphs;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;

public class AStarPathfinder {

	Graph graph;
	Integer  startNode;
	Integer  targetNode;
	HashMap<Integer,Integer> bestPredecessor;
	ArrayList<Integer> open;
	ArrayList<Integer> closed;
	boolean atTarget;
	
	public AStarPathfinder(Integer start, Integer target,Graph graph){
		startNode = start;
		targetNode = target;
		this.graph = graph;
		open = new ArrayList<Integer>(0);
		closed = new ArrayList<Integer>(0);
		bestPredecessor = new HashMap<Integer, Integer>(0);
		atTarget = false;
		open.add(startNode);
	}
	
	public ArrayList<Integer> findInstant() {		
		while (!atTarget) {
			findStep();
		}		
		return createPath();
	}

	public void findStep() {
		graph.clearColors();
		
		Integer x = null; 

		for(Integer openNode : open)
			System.out.print(graph.getPosition(openNode) + " - " );
		System.out.println("");
		
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
		
		

		// checking if x is allready the target
		atTarget = targetNode.equals(x);

		if (!atTarget) {
			//expanding preperations
			open.remove(x);
			closed.add(x);
			
			graph.setNodeColor(x,Color.GREEN);
			
			//expand x
			ArrayList<Integer> successors = graph.getExpansion(x);
			for (Integer y : successors) {
				
				graph.setNodeColor(y,Color.YELLOW);
				
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
			System.out.println("AT TARGET");
			ArrayList<Integer> path = createPath();
			
			for(Integer node : path)
				graph.setNodeColor(node, Color.GREEN);
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
		if(!atTarget)
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
		
		for(Integer node : path)
			System.out.print(node + " ");
		System.out.println(" ");
		
		return path;
	}
	
	public void reset(){
		open = new ArrayList<Integer>(0);
		closed = new ArrayList<Integer>(0);
		bestPredecessor = new HashMap<Integer, Integer>(0);
		atTarget = false;
		open.add(startNode);
	}
}
