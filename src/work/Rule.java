package work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Rule {
	private String name;
	private ArrayList<String> statement = new ArrayList<String>();
	private ArrayList<String> regex_statement = new ArrayList<String>();
	private ArrayList<String> action = new ArrayList<String>();
	private ArrayList<ArrayList<HashMap<String,String>>> variables = new ArrayList<ArrayList<HashMap<String,String>>>();
	
	
	/**
	 * constructor*/
	public Rule(String name, ArrayList<String> statement, ArrayList<String> action){
		this.name = name;
		this.action = action;
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
	 * create rules*/
	
	public static void createRules(){
		ArrayList<String> statement = new ArrayList<String>();
		ArrayList<String> action = new ArrayList<String>();

		statement.add("?X je rodic ?Y");
		statement.add("manzelia ?X ?Z");
		action.add("pridaj ?Z je rodic ?Y");
		
		Production.rules.add(new Rule("DruhyRodic1", statement , action));
	}
	
	public static void printRules(){
		for(Rule r: Production.rules){
			System.out.println(r.getName() + ": " +r.getRegexStatement() + " => " +r.getAction());
		}
	}
	
	/**
	 * create regex version of statements*/
	public void convertStatements(){
		//regex for "?X je nieco ?Y"
		String regex = "\\?[A-Za-z]{1}\\s.+\\s.+\\s\\?[a-zA-Z]{1}";
		//regex for "nieco ?X ?Y"
		String regex2 = "^[a-zA-Z]+\\s\\?[a-zA-Z]{1}\\s\\?[a-zA-Z]{1}";
		
		int counter = 0;		
		for(String s: this.regex_statement){
			
			if(s.matches(regex) || s.matches(regex2)){
				String nstring = s.replaceAll("\\?[A-Z]{1}", "[A-Za-z]+");
				this.regex_statement.set(counter, nstring);
			} 			
			counter++;
		}
	}
	
	/**
	 * check if statements in rule correspond with facts in memory*/
	public void validateRule(){		
			this.variables.clear();
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
	
	/*public boolean makeAction(){
		for(int i=0; i<this.action.size(); i++){
			String act_action = this.action.get(i);
			String [] split = act_action.split(" ");
			switch(split[0]){
			case "pridaj": 
				String new_fact = "";
				for(int j=1; j<split.length; j++){
					if(j == split.length -1)
						new_fact += split[j];
					else
						new_fact += split[j] + " "; 
				}
				for(int index = act_action.indexOf('?'); index >= 0; index = act_action.indexOf('?', index + 1)){
					String var = "" + act_action.charAt(index+1);
					String var2 = "\\?" + var;
					String new_fact2 = "";
					new_fact2 = new_fact.replaceAll(var2, this.variables.get(var));
					new_fact = new_fact2;
				}
				if(!Fact.containsFact(new_fact)){
					Production.facts.add(new Fact(new_fact));
					return true;
				}
				
				break;
			case "zmaz": System.out.println("zmazem fakt"); break;
			case "sprava": System.out.println("vypisem spravu"); break;
			}
		}
		return false;
	}*/
}

