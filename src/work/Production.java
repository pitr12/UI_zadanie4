package work;

import java.util.ArrayList;
import java.util.HashMap;

public class Production {
	public static ArrayList<Fact> facts = new ArrayList<Fact>();
	public static ArrayList<Rule> rules = new ArrayList<Rule>();
	public static ArrayList<String> actions = new ArrayList<String>();
	
	public static void main(String[] args) {	
		
		
		
		/**
		 * load facts*/
		Fact.loadFacts();
		
		/**
		 * load rules*/
		Rule.loadRules();
		
		
		
		/**
		 * convert rule statements into regex expressions*/
		for(Rule r: rules){
			r.convertStatements();
		}
		
		Rule.printRules();
		
		/**
		 * infinite loop for running program*/
		while(true){
			/**
			 * go through all rules and find all aplicable instances*/
			for(Rule r: rules){
				r.validateRule();
				r.combine();
				r.addAction();
			}
			
			/**
			 * find first action which can be used and use it*/
			if(Rule.performAction() == false)
				break;
			else{
				actions.clear();
			}
			
		}
		
		Fact.printFacts();
	}
}
