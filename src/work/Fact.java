package work;

import java.util.ArrayList;

public class Fact {
	private String fact;
	public int a;
	
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
	 * create facts*/
	public static void createFacts(){
	Production.facts.add(new Fact("Peter je rodic Jano"));
	Production.facts.add(new Fact("Peter je rodic Vlado"));
	Production.facts.add(new Fact("manzelia Peter Eva"));
	Production.facts.add(new Fact("Vlado je rodic Maria"));
	Production.facts.add(new Fact("Vlado je rodic Viera"));
	Production.facts.add(new Fact("muz Peter"));
	Production.facts.add(new Fact("muz Jano"));
	Production.facts.add(new Fact("muz Vlado"));
	Production.facts.add(new Fact("zena Maria"));
	Production.facts.add(new Fact("zena Viera"));
	Production.facts.add(new Fact("zena Eva"));
	}
	
	public static void printFacts(){
		for(Fact f: Production.facts){
			System.out.println(f.getFact());
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
}
