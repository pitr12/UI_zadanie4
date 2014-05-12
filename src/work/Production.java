package work;

import gui.UI;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Production {
	public static ArrayList<Fact> facts = new ArrayList<Fact>();
	public static ArrayList<Rule> rules = new ArrayList<Rule>();
	public static ArrayList<String> actions = new ArrayList<String>();
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		/**
		 * set program theme*/
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		
		/**
		 * build user interface*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void runProductionSystem(){
		/**
		 * convert rule statements into regex expressions*/
		for(Rule r: rules){
			r.convertStatements();
		}
		
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
	}
}
