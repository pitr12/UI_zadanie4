package work;

import gui.UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Fact {
	private String fact;
	
	/**
	 * constructor*/
	public Fact(String fact){
		this.fact = fact;
	}
	
	/**
	 * get Fact*/
	public String getFact(){
		return this.fact;
	}
	
	public static ArrayList<String> extractVariables(String statement){
		ArrayList<String> variables = new ArrayList<String>();
		String [] result = statement.split(" ");
		for(String s: result){
			if(s.matches("^[A-Z]{1}[a-z]+"))
				variables.add(s);
		}
		return variables;
	}
	
	/**
	 * load facts*/
	public static void loadFacts(){
		BufferedReader reader;
		try{
	        reader = new BufferedReader(new FileReader(new File("family_facts.txt")));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	Production.facts.add(new Fact(line));
	        }
        }
        catch (Exception e){
        	e.printStackTrace();
        }
		Fact.printFacts();
	}
	
	public static void printFacts(){
		UI.textArea.setText("");
		for(Fact f: Production.facts){
			UI.textArea.append(f.getFact() + "\n");
		}
	}
	
	/**
	 * check if memory contains fact*/
	public static boolean containsFact(String fact){
		for(Fact f: Production.facts){
			if(f.getFact().equals(fact))
				return true;
		}
		return false;
	}
	
	public static int factPosition(String fact){
		for(int i=0; i<Production.facts.size(); i++){
			if(Production.facts.get(i).getFact().equals(fact))
				return i;
		}
		return 0;
	}
}
