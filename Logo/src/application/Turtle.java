package application;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;



public class Turtle {
	Circle turtle;
	Circle turtleTopHead;
	Pane canvas;
	ArrayList<Line> lines = new ArrayList<Line>();
	Line line; 
	Line startingLine;
	boolean penVisibility;
	ControllerWithArrowKeys controller; 
	double initialCenterX, initialCenterY, initialRotationAngle;
	
	public Turtle(ControllerWithArrowKeys controller){
		this.controller = controller;
		turtle = controller.turtleHead;
		turtleTopHead = controller.turtleTopHead;
		canvas = controller.canvas;
		initialRotationAngle = turtle.getRotate();
		initialCenterX = turtle.getCenterX();
		initialCenterY = turtle.getCenterY();
	}
	
	void moveToStartingPoint(){
		turtle.setCenterX(initialCenterX);
		turtle.setCenterY(initialCenterY);
		turtle.setRotate(initialRotationAngle);
		putHeadAtTop();
	}
	
	void startRunning(){
		if (lines.isEmpty()){
			penVisibility = true;
			double startingX =  turtle.getCenterX();
			double startingY =  turtle.getCenterY();
			startingLine =  new Line(startingX, startingY, startingX, startingY); 
			lines.add(startingLine);
		}
	}
	
	
	void drawLine(double startX,double startY,double endX,double endY){
		line = new Line(startX, startY, endX, endY);
		if (penVisibility == true){
			line.setFill(Color.BLACK);
			line.setVisible(true);}
		else if (penVisibility == false){
			line.setFill(Color.WHITE);
			line.setVisible(false);}
		lines.add(line);
		canvas.getChildren().add(line);	
	}
	
	void move(double distance){
		startRunning();
		double previous_x = turtle.getCenterX();
		double previous_y = turtle.getCenterY();
		double angInDeg = turtle.getRotate()-90;
		double x =previous_x +  distance*Math.cos(Math.toRadians(angInDeg));
		double y = previous_y + distance*Math.sin(Math.toRadians(angInDeg));
		
		moveTurtle(x, y);
		putHeadAtTop();
		drawLine(previous_x, previous_y, x, y);	
	}
	
	private void moveTurtle(double X,double Y){
		turtle.setCenterX(X); 
		turtle.setCenterY(Y);
	}
	
	void rotate(double angle){
		turtle.setRotate(angle);
		putHeadAtTop();
	}
	
	void putHeadAtTop(){
		double previous_x = turtle.getCenterX();
		double previous_y = turtle.getCenterY();
		double angInDeg = turtle.getRotate();
		double head_x = 10*Math.cos(Math.toRadians(angInDeg-90));
		double head_y = 10*Math.sin(Math.toRadians(angInDeg-90));
		
		turtleTopHead.setCenterX(head_x+previous_x);
		turtleTopHead.setCenterY(head_y+previous_y);	
	}
	
	
	void setPenUp(){
		penVisibility = false;
	}
	void setPenDown(){
		penVisibility = true;
	}

	boolean turtleIsUpward(){
		return (Math.abs(turtle.getRotate())%360 == 0); 
	}
	boolean turtleIsRightward(){
		return (Math.abs(turtle.getRotate())%360 == 90);
	}
	boolean turtleIsDownward(){
		return (Math.abs(turtle.getRotate())%360 == 180);
	}
	boolean turtleIsLeftward(){
		return (Math.abs(turtle.getRotate())%360 == 270);
	}
	void turnRight(double angle){
		double rotationAngle = turtle.getRotate()+angle;
		rotate(rotationAngle);	
	}
	void turnLeft(double angle){
		double rotationAngle = turtle.getRotate()-angle;
		rotate(rotationAngle);	
	}
}
