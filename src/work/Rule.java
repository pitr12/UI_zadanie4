package work;

import gui.UI;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument.Iterator;

public class Rule {
	private String name;
	private ArrayList<String> statement = new ArrayList<String>();
	private ArrayList<String> regex_statement = new ArrayList<String>();
	private ArrayList<String> action = new ArrayList<String>();
	private ArrayList<ArrayList<HashMap<String,String>>> variables = new ArrayList<ArrayList<HashMap<String,String>>>();
	private ArrayList<HashMap<String,String>> final_varables = new ArrayList<HashMap<String,String>>();
	private boolean specialRule = false;
	
	
	/**
	 * constructor*/
	public Rule(String name, ArrayList<String> statement, ArrayList<String> action){
		this.name = name;
		this.action.addAll(action);
		this.statement.addAll(statement);
		this.regex_statement.addAll(statement);
	}
	
	/**
	 * get rule name*/
	public String getName(){
		return this.name;
	}
	
	/**
	 * get rule statement*/
	public ArrayList<String> getStatement(){
		return this.statement;
	}
	
	public ArrayList<String> getRegexStatement(){
		return this.regex_statement;
	}
	
	/**
	 * get rule action*/
	public ArrayList<String> getAction(){
		return this.action;
	}
	
	/**
	 * load rules*/
	public static void loadRules(){
		BufferedReader reader;
		try{
	        reader = new BufferedReader(new FileReader(new File("family_rules.txt")));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	String name = line;
	        	line = reader.readLine();
	        	String statements = line;
	        	line = reader.readLine();
	        	String actions = line;
	        	line = reader.readLine();
	        	String[] name_n = name.split(" ");
	        	String[] statements_n = statements.split("\t");
	        	String[] actions_n = actions.split("\t");
	        	
	        	ArrayList<String> statement = new ArrayList<String>();
	    		ArrayList<String> action = new ArrayList<String>();
	    		
	    		String[] statements_n2 = statements_n[1].split(",");
	    		for(int i=0; i<statements_n2.length; i++){
	    			statement.add(statements_n2[i]);
	    		}
	    		
	    		String[] actions_n2 = actions_n[1].split(",");
	    		for(int i=0; i<actions_n2.length; i++){
	    			action.add(actions_n2[i]);
	    		}
	    		
	    		Production.rules.add(new Rule(name_n[1], statement , action));
	        }
        }
        catch (Exception e){
        	e.printStackTrace();
        }
		Rule.printRules();
	}
	
	
	public static void printRules(){
		UI.textArea_1.setText("");
		for(Rule r: Production.rules){
			UI.textArea_1.append("Meno: " +r.getName() +"\n");
			UI.textArea_1.append("AK: " +r.getStatement() +"\n");
			UI.textArea_1.append("POTOM: " +r.getAction() +"\n");
			UI.textArea_1.append("\n");
			
			
			
			UI.textArea_1.setCaretPosition(0);
			System.out.println(r.getName() + ": " +r.getRegexStatement() + " => " +r.getAction());
		}
	}
	
	/**
	 * create regex version of statements*/
	public void convertStatements(){		
		int counter = 0;		
		for(String s: this.regex_statement){
			if(s.matches("^<>.+"))
				this.specialRule = true;
			else{
				String nstring = s.replaceAll("\\?[^\\s]+", "[A-Za-z]+");
				this.regex_statement.set(counter, nstring);	
				counter++;
			}
			
		}
	}
	
	/**
	 * create all possible mappings of rules on facts in memory*/
	public void validateRule(){		
			this.variables.clear();
			this.final_varables.clear();
			int counter1 = 0;
			int counter2 = 0;
			for(String s: this.regex_statement){
				this.variables.add(new ArrayList<HashMap<String,String>>());
				counter2 = 0;
				for(Fact f: Production.facts){
					if(f.getFact().matches(s)){
						this.variables.get(counter1).add(new HashMap<String,String>());
						String statement = this.statement.get(counter1);
						int counter3 = 0;
						for(int index = statement.indexOf('?'); index >= 0; index = statement.indexOf('?', index + 1)){
							String var = "" + statement.charAt(index+1);
							ArrayList<String> fact_variables = Fact.extractVariables(f.getFact());
							this.variables.get(counter1).get(counter2).put(var, fact_variables.get(counter3));
						counter3++;
						}
					counter2++;
					}
				}
			counter1++;
		}
	}
	
	/**
	 * find all possible rule maping combinations that are correct*/
	public void combine(){
		ArrayList<ArrayList<HashMap<String,String>>> itemLists = this.variables;
	    
		// Calculate how many combinations we'll need to build
	    int remainingCombinations = itemLists.get(0).size();
	    for(int i=1; i<itemLists.size(); i++)
	    {
	    	int size = itemLists.get(i).size();
	    	if(size == 0)
	    		size = 1;
	        remainingCombinations *= size;
	    }
	    
	    // Generate this combination
	    for (;remainingCombinations > 0; remainingCombinations --)
	    {
	        ArrayList<HashMap<String,String>> currentSet = new ArrayList<HashMap<String,String>>();
	        int positionInRow = remainingCombinations;

	        // Pick the required element from each list, and add it to the set.
	        for(int i=0; i<itemLists.size(); i++)
	        {
	            int sizeOfRow = itemLists.get(i).size();
	            if(sizeOfRow != 0){
		            currentSet.add(itemLists.get(i).get(positionInRow % sizeOfRow));
		            positionInRow /= sizeOfRow;
	            }
	        }
	        Boolean result = true;
	        HashMap<String,String> currentMap = new HashMap<String,String>();
	        for(int i=0; i<currentSet.size(); i++){
	        	for (Entry<String, String> entry : currentSet.get(i).entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    if(!currentMap.containsKey(key)){
				    	currentMap.put(key, value);
				    }
				    else{
				    	if(!currentMap.get(key).equals(value)){
				    		result = false;
				    		break;
				    	}
				    }
				}
	        }
	        
	        if(result){
	        	this.final_varables.add(currentMap);
	        }
	    }
	    if(this.specialRule)
        	this.useSpecialRule();
	}
	
	/**
	 * use special rule <> ?X ?Y ...*/
	public void useSpecialRule(){
		String var[] = new String[2];
		for(String s: this.regex_statement){
			if(s.matches("^<>.+")){
				int counter = 0;
				for(int index = s.indexOf('?'); index >= 0; index = s.indexOf('?', index + 1)){
					var[counter] = "" + s.charAt(index+1);
					counter++;
				}
			}
		}
		
		HashSet<Integer> remove = new HashSet<Integer>();
		
		for(int i=0; i<this.final_varables.size(); i++){
			HashMap<String,String> map = this.final_varables.get(i);
			String first = "";
			String second = "";
			int count = 0;
			if(map.containsKey(var[0])){
				first = map.get(var[0]);
				count++;
			}
			if(map.containsKey(var[1])){
				second = map.get(var[1]);
				count++;
			}
			if(count == 2){
				if(first.equals(second)){
					remove.add(i);
				}
			}
		}
		ArrayList<HashMap<String,String>> pom = new ArrayList<HashMap<String,String>>();
		for(int i=0; i<this.final_varables.size(); i++){
			if(!remove.contains(i))
				pom.add((HashMap<String, String>) this.final_varables.get(i).clone());
		}
		
		this.final_varables.clear();
		this.final_varables = (ArrayList<HashMap<String, String>>) pom.clone();
	}
	
	/**
	 * print all correct mappings for rule*/
	public void printFinalVariables(){
		ArrayList<HashMap<String,String>> variables = this.final_varables;
		for(int rule=0; rule<variables.size(); rule++){
			for (Entry<String, String> entry : variables.get(rule).entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    System.out.print(key + " -> " +value + "\t");
			}
			System.out.println("");
		}
	}
	

	/**
	 * print all possible mappings*/
	public void printVariables(){
		for(int i=0; i<variables.size(); i++){
			System.out.println("RULE " +i);
			for(int j=0; j<variables.get(i).size(); j++){
				System.out.print("Possible fact " +j +":\t");
				for (Entry<String, String> entry : variables.get(i).get(j).entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    System.out.print(key + " -> " +value + "\t");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}
	
	/**
	 * add all actions that can be performed*/
	public void addAction(){
		for(int i=0; i<this.action.size(); i++){
			String act_action = this.action.get(i);
				ArrayList<String> new_facts = new ArrayList<String>();
				String pom = "";
				for(int j=0; j<this.final_varables.size(); j++){
					pom = act_action;
					for(int index = act_action.indexOf('?'); index >= 0; index = act_action.indexOf('?', index + 1)){
						String var = "" + act_action.charAt(index+1);
						String var2 = "\\?" + var;
						pom = pom.replaceAll(var2, this.final_varables.get(j).get(var));
					}
					Production.actions.add(pom);
				}				
		}
	}
	
	/**
	 * find first action which can be used and use it*/
	public static boolean performAction(){
		for(String action: Production.actions){
			String [] split = action.split(" ");
			
			String new_fact = "";
			for(int j=1; j<split.length; j++){
				if(j == split.length -1)
					new_fact += split[j];
				else
					new_fact += split[j] + " "; 
			}
			
			switch(split[0]){
			case "pridaj":
				if(!Fact.containsFact(new_fact)){
					Production.facts.add(new Fact(new_fact));
					Rule.printAction(new_fact);
					
					StyledDocument doc = UI.textPane.getStyledDocument();
					Style style = UI.textPane.addStyle("I'm a Style", null);
					Color color = new Color(0,205,0);
			        StyleConstants.setForeground(style, color);
			        try { doc.insertString(doc.getLength(), " ",style); }
			        catch (BadLocationException e){}
					UI.textPane.setText("Adding: " +new_fact);
					UI.textPane.update(UI.textPane.getGraphics());
					
					return true;
				}
				break;
			case "vymaz":
				if(Fact.containsFact(new_fact)){
					Production.facts.remove(Fact.factPosition(new_fact));
					Fact.printFacts();
					
					StyledDocument doc = UI.textPane.getStyledDocument();
					Style style = UI.textPane.addStyle("I'm a Style", null);
					Color color = new Color(255,0,0);
			        StyleConstants.setForeground(style, color);
			        try { doc.insertString(doc.getLength(), " ",style); }
			        catch (BadLocationException e){}
					UI.textPane.setText("Removing: " +new_fact);
					UI.textPane.update(UI.textPane.getGraphics());
				}
				break;
			}
		}
		return false;
	}
	
	public static void printAction(String action){
		System.out.println(action);
		UI.textArea.append(action + "\n");
		
		try {
		    Thread.sleep(300);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		UI.textArea.setCaretPosition(UI.textArea.getText().length());
		UI.textArea.update(UI.textArea.getGraphics());
		
	}
}

