package application;

import java.util.ArrayList;

import javax.script.*;

import edu.hendrix.grambler.ParseException;
import edu.hendrix.grambler.Tree;

public class Evaluator {
	private Grammar grammar = new Grammar();
	private Turtle turtleObj;
	ArrayList<String> listOfInstructions = new ArrayList<String>();
	ArrayList<Double> listOfParameters = new ArrayList<Double>();
	ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");

	public Evaluator(Turtle turtleObject) {
		this.turtleObj = turtleObject;
	}

	public void eval(String input) throws ParseException, ScriptException {
		Tree tree = grammar.parse(input);
		System.out.println("we parsed yo");
		evalTree(tree);
	}

	public void runTyped() {
		for (int i = 0; i < listOfInstructions.size(); ++i) {
			String instr = listOfInstructions.get(i);
			Double parameter = listOfParameters.get(i);
			if (instr.equalsIgnoreCase("fd")
					|| instr.equalsIgnoreCase("forward")) {
				turtleObj.move(parameter);
			} else if (instr.equalsIgnoreCase("bk")
					|| instr.equalsIgnoreCase("back")) {
				turtleObj.move(-1 * parameter);
			} else if (instr.equalsIgnoreCase("rt")
					|| instr.equalsIgnoreCase("right")) {
				turtleObj.turnRight(parameter);
			} else if (instr.equalsIgnoreCase("lt")
					|| instr.equalsIgnoreCase("left")) {
				turtleObj.turnLeft(parameter);

			} else if (instr.equalsIgnoreCase("pd")
					|| instr.equalsIgnoreCase("pendown")) {
				turtleObj.setPenDown();
			} else if (instr.equalsIgnoreCase("pu")
					|| instr.equalsIgnoreCase("penup")) {
				turtleObj.setPenUp();
			} else if (instr.equalsIgnoreCase("cs")
					|| instr.equalsIgnoreCase("clearscreen")) {
				turtleObj.controller.clearScreen();
			} else if (instr.equalsIgnoreCase("st")
					|| instr.equalsIgnoreCase("showturtle")) {
				turtleObj.controller.showTurtle();
			} else if (instr.equalsIgnoreCase("ht")
					|| instr.equalsIgnoreCase("hideturtle")) {
				turtleObj.controller.hideTurtle();
			} else if (instr.equalsIgnoreCase("home")) {
				turtleObj.moveToStartingPoint();
			}
		}
		listOfInstructions.clear();
		listOfParameters.clear();
	}

	private void evalTree(Tree t) throws ScriptException {

		if (t.isNamed("lines")) {
			System.out.println(t.getChild(0) + " lines");
			if (t.getNumChildren() == 1) {
				evalTree(t.getNamedChild("line"));
			} else {

				evalTree(t.getChild(0));
				evalTree(t.getChild(1));
				
			}
		} else if (t.isNamed("line")) {
			System.out.println(t.getChild(0)+ " line");
			evalTree(t.getChild(0));
			
		} else if (t.isNamed("expr")) {
			System.out.println(t.getChild(0) + " expr");
			if (t.hasNamed("abbr")) {
				listOfParameters.add(toDouble(t.getNamedChild("num")));
				listOfInstructions.add(t.getNamedChild("abbr").toString());
			} else if (t.hasNamed("abbr1")) {
				listOfParameters.add(0.0);
				listOfInstructions.add(t.getNamedChild("abbr1").toString());
			}
		}else if (t.isNamed("expr0")) {
			System.out.println(t.getChild(0) + " expr0");
			if (t.getChild(0).isNamed("repeat")) {
				System.out.println(t.getChild(2) +"h");
				for (int i = 0; i< toDouble(t.getChild(2)); ++i){
					evalTree(t.getNamedChild("expr2"));
					
				}
			}
			else if( t.getChild(0).isNamed("if") ){
				
				if (t.getNamedChild("expr1").hasNamed("eq")){
					String toBeEvaluated = t.getNamedChild("expr1").toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated)).toString() == "true"){
						evalTree(t.getNamedChild("expr2"));
					}
				}else if (t.getNamedChild("expr1").hasNamed("or")){
					//System.out.println(t.getNamedChild("expr1").getChild(0).toString()
							//.replace("(", "").replace(")", "") + "  exprrr111");
					String toBeEvaluated1 = t.getNamedChild("expr1").getChild(0).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					String toBeEvaluated2 = t.getNamedChild("expr1").getChild(4).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated1)).toString() == "true" || 
							(engine.eval(toBeEvaluated2)).toString() == "true"){
						evalTree(t.getNamedChild("expr2"));
					}	
				}else if (t.getNamedChild("expr1").hasNamed("and")){
					String toBeEvaluated1 = t.getNamedChild("expr1").getChild(0).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					String toBeEvaluated2 = t.getNamedChild("expr1").getChild(4).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated1)).toString() == "true" && 
							(engine.eval(toBeEvaluated2)).toString() == "true"){
						evalTree(t.getNamedChild("expr2"));
					}	
				}
			}else if( t.getChild(0).isNamed("ifelse") ){
				System.out.println("we are in yooyoy");
				if (t.getNamedChild("expr1").hasNamed("eq")){
					String toBeEvaluated = t.getNamedChild("expr1").toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated)).toString() == "true"){
						evalTree(t.getChild(4));
					}else{evalTree(t.getChild(6));}
				}else if (t.getNamedChild("expr1").hasNamed("or")){
					String toBeEvaluated1 = t.getNamedChild("expr1").getChild(0).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					String toBeEvaluated2 = t.getNamedChild("expr1").getChild(4).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated1)).toString() == "true" || 
							(engine.eval(toBeEvaluated2)).toString() == "true"){
						evalTree(t.getChild(4));
					}else{evalTree(t.getChild(6));}	
				}else if (t.getNamedChild("expr1").hasNamed("and")){
					String toBeEvaluated1 = t.getNamedChild("expr1").getChild(0).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					String toBeEvaluated2 = t.getNamedChild("expr1").getChild(4).toString()
							.replace("(", "").replace(")", "").replace(" = ", " == ");
					if((engine.eval(toBeEvaluated1)).toString() == "true" && 
							(engine.eval(toBeEvaluated2)).toString() == "true"){
						evalTree(t.getChild(4));
					}else{evalTree(t.getChild(6));}
				}
			}
		}else if (t.isNamed("expr2")) {
			System.out.println(t.getChild(0) + " expr2");
			evalTree(t.getChild(1));
		}else if (t.isNamed("expr3")) {
			if (t.getNumChildren() == 1) {
				evalTree(t.getChild(0));
			} else {

				evalTree(t.getChild(0));
				evalTree(t.getChild(2));
				
			}
		}else {
			throw new IllegalArgumentException("What is \"" + t.getName()
					+ "\"");
		}
	}

	private Double toDouble(Tree t) {
		String strOfTree = t.toString();
		return Double.parseDouble(strOfTree);
	}

}
