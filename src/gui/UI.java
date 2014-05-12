package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;

import work.Fact;
import work.Production;
import work.Rule;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class UI extends JFrame {

	private JPanel contentPane;
	public static JTextArea textArea_1;
	public static JTextArea textArea;
	public static JTextPane textPane;

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1649, 1045);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		/**
		 * textAreas*/
		JScrollPane scrollPane = new JScrollPane();
		JScrollPane scrollPane_1 = new JScrollPane();
		JScrollPane scrollPane_2 = new JScrollPane();
		
		textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Courier New", textArea_1.getFont().getStyle(), 25));
		scrollPane_1.setViewportView(textArea_1);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", textArea.getFont().getStyle(), 28));
		scrollPane.setViewportView(textArea);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Courier New", textArea_1.getFont().getStyle(), 25));
		scrollPane_2.setViewportView(textPane);
		
		
		
		/**
		 * buttons*/
		JButton btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnLoad.addActionListener(new ActionListener() {
			/**
			 * Load facts and rules from external file*/
			public void actionPerformed(ActionEvent arg0) {
				Production.facts.clear();
				Production.rules.clear();
				Production.actions.clear();
				Fact.loadFacts();
				Rule.loadRules();
			}
		});
		
		JButton btnRun = new JButton("Run");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnRun.addActionListener(new ActionListener() {
			/**
			 * run production system*/
			public void actionPerformed(ActionEvent arg0) {
				Production.runProductionSystem();
			}
		});
		
		
		/**
		 * labels*/
		JLabel lblFacts = new JLabel("FACTS");
		lblFacts.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lblRules = new JLabel("RULES");
		lblRules.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		
		/**
		 * layout*/
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(257)
					.addComponent(lblFacts)
					.addGap(805)
					.addComponent(lblRules, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(387)
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(387)
							.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
							.addGap(8)))
					.addGap(13))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFacts)
						.addComponent(lblRules))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
							.addGap(108)
							.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(52)
							.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(119)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addGap(21))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
