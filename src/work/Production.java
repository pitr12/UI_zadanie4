package work;

import java.util.ArrayList;

public class Production {
	public static ArrayList<Fact> facts = new ArrayList<Fact>();
	public static ArrayList<Rule> rules = new ArrayList<Rule>();
	public static int ahoj;
	
	public static void main(String[] args) {	
		
		/**
		 * create facts*/
		Fact.createFacts();
		
		/**
		 * create rules*/
		Rule.createRules();
		
		Fact.printFacts();
		Rule.printRules();
		
		/**
		 * convert rule statements into regex expressions*/
		for(Rule r: rules){
			r.convertStatements();
		}
		
		/**
		 * infinite loop for running program*/
		//while(true){
			/**
			 * go through all rules and find all aplicable instances*/
			for(Rule r: rules){
				r.validateRule();
				r.printVariables();
			}
		//}
	}
}
