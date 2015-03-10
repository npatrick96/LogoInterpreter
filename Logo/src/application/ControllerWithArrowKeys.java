package application;

import javax.script.ScriptException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import edu.hendrix.grambler.ParseException;

public class ControllerWithArrowKeys {
	final int DISTANCE = 50;
	
	@FXML
	BorderPane border;

	@FXML
	Pane canvas;

	@FXML
	Circle turtleHead;
	
	@FXML
	Circle turtleTopHead;

	@FXML
	VBox rightVBox;

	@FXML
	Button moveForwardButton;

	@FXML
	Button moveBackwardButton;

	@FXML
	Button turnLeftButton;

	@FXML
	Button turnRightButton;

	@FXML
	Button showTurtleButton;

	@FXML
	Button hideTurtleButton;

	@FXML
	Button clearScreenButton;
	
	@FXML
	Button penUPButton;

	@FXML
	Button PenDownButton;

	@FXML
	Button moveHomeButton;

	@FXML
	Button stopTheProgramButton;

	@FXML
	TextArea userCodeTextArea;

	@FXML
	Button runTypedCodeButton;

	Turtle turtleObject;
	private Evaluator evaluator;

	public ControllerWithArrowKeys() {
	}


	@FXML
	private void initialize() {
		this.turtleObject = new Turtle(this);
		this.evaluator = new Evaluator(turtleObject);

		canvas.setOnMouseDragOver(event -> {
			grabKeys();
		});

		turtleObject.startRunning();
		canvas.setOnKeyPressed(event -> {
			
			double x = 0;
			double y = 0;
			double previous_x = turtleObject.lines.get(
					turtleObject.lines.size() - 1).getEndX();
			double previous_y = turtleObject.lines.get(
					turtleObject.lines.size() - 1).getEndY();
			double rotationAngle = turtleHead.getRotate();

			if (event.getCode() == KeyCode.UP) {
				y = -1 * DISTANCE;
				rotationAngle = 0;
			} else if (event.getCode() == KeyCode.DOWN) {
				y = DISTANCE;
				rotationAngle = 180;
			} else if (event.getCode() == KeyCode.LEFT) {
				x = -1 * DISTANCE;
				rotationAngle = 270;
			} else if (event.getCode() == KeyCode.RIGHT) {
				grabKeys();
				x = DISTANCE;
				rotationAngle = 90;
			}

			turtleObject.drawLine(previous_x, previous_y, previous_x+ x, previous_y+y);
			
			turtleHead.setCenterX(previous_x + x);
			turtleHead.setCenterY(previous_y + y);
			turtleObject.rotate(rotationAngle);
			turtleObject.putHeadAtTop();
			
			
			event.consume();
			grabKeys();
		});
	}
	

	@FXML
	void runTypedCode() throws ParseException, ScriptException {
		if (!userCodeTextArea.getText().isEmpty()) {
			evaluator.eval(userCodeTextArea.getText());
		}
		turtleObject.startRunning();
		evaluator.runTyped();
		
	}

	@FXML
	void moveBackward() {
		turtleObject.move(-50);
	}

	@FXML
	void moveForward() {
		turtleObject.move(50);

	}

	@FXML
	void turnLeft() {
		double rotationAngle = turtleHead.getRotate() - 90;
		turtleObject.rotate(rotationAngle);
	}

	@FXML
	void turnRight() {
		double rotationAngle = turtleHead.getRotate() + 90;
		turtleObject.rotate(rotationAngle);
	}

	@FXML
	void showTurtle() {
		if (!canvas.getChildren().contains(turtleHead)) {
			canvas.getChildren().add(turtleHead);
			
		}
		if (!canvas.getChildren().contains(turtleTopHead)) {
			canvas.getChildren().add(turtleTopHead);
		}
	}

	@FXML
	void hideTurtle() {
		// turtle.setVisible(false);
		// turtle.setOpacity(0);
		canvas.getChildren().remove(turtleHead);
		canvas.getChildren().remove(turtleTopHead);
	}

	@FXML
	void clearScreen() {
		// turtleObject.lines.clear();
		canvas.getChildren().clear();
		turtleObject.moveToStartingPoint();
		turtleObject.lines.clear();
		showTurtle();
		turtleObject.startRunning();
	}
	
	@FXML
	void penUp(){
		turtleObject.setPenUp();
		System.out.println(turtleObject.penVisibility);
	}
	
	@FXML
	void penDown(){
		turtleObject.setPenDown();
	}
	
	@FXML
	void moveHome(){
		turtleObject.moveToStartingPoint();
	}

	@FXML
	void stopProgram() {
		System.exit(0);
	}

	@FXML
	void grabKeys() {
		canvas.requestFocus();
	}
}
