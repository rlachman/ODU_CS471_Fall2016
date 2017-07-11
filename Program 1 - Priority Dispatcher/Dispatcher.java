/*CS 471 - Dispatcher Project - Fall 2016 - Ryan Lachman*/

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dispatcher{

	static ArrayList<Node> al = new ArrayList<Node>();
	static ArrayList<Node> bal = new ArrayList<Node>();
	/* The method creates the UI */
	private static void createAndShowGUI() {
		/* Making the frame and adding text and buttons */
        JFrame frame = new JFrame("Dispatcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setSize(600,600);
        frame.setVisible(true);
		
		final JTextArea ta = new JTextArea(30,30);
		JLabel label = new JLabel("Priority No");
		
		
		final JTextField textField = new JTextField(10);
		
		
		JButton addButton = new JButton("ADD");
		addButton.setSize(20,20);
		
		
		/* Making three buttons : Ready, Block and Kill */
		JButton rButton = new JButton("Ready");
		rButton.setSize(20,20);
		JButton bButton = new JButton("Block");
		bButton.setSize(20,20);
		JButton kButton = new JButton("kill");
		kButton.setSize(20,20);
		
		JButton resetButton = new JButton("Reset All");
		resetButton.setSize(20,20);
		/* Adding event listeners on each button */
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				ta.setText("Priority\t\tState");
				al = new ArrayList<Node>();
				bal = new ArrayList<Node>();
			}
		});
		
		/* Adding event listener for Add button */
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				Node temp = new Node(Integer.parseInt(textField.getText()),"Ready");
				al.add(temp);
				prioritize();
				ta.setText(makeResult(al,bal));
			}
		});
		
		/* Adding event listener for Block button */
		bButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				int i = 0;
				boolean flg = false;
				int pr = Integer.parseInt(textField.getText());
				for(Node v:al) {
					if(v.priority == pr){
						flg = true;
						break;
					}
					i++;
				}
				if(flg)
				{
					Node temp = al.remove(i);
					bal.add(temp);
				}
				ta.setText(makeResult(al,bal));
			}
		});
		
		/* Adding event listener for Ready button */
		rButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				int i = 0;
				boolean flg = false;
				int pr = Integer.parseInt(textField.getText());
				for(Node v:bal) {
					if(v.priority == pr){
						flg = true;
						break;
					}
					i++;
				}
				if(flg)
				{
					Node temp = bal.remove(i);
					al.add(temp);
				}
				prioritize();
				ta.setText(makeResult(al,bal));
			}
		});
		/* Adding event listener for Kill button */
		kButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				int pr = Integer.parseInt(textField.getText());
				boolean flg = false;
				int i = 0;
				for(Node v:al) {
					if(v.priority == pr) {
						flg = true;
						break;
					}
					i++;
				}
				if(flg) al.remove(i);
				i = 0;
				flg = false;
				for(Node v:bal) {
					if(v.priority == pr) {
						flg = true;
						break;
					}
					i++;
				}
				if(flg) bal.remove(i);
				ta.setText(makeResult(al,bal));
			}
		});
		
		/* Adding buttons on the frane */
		frame.add(label);
		frame.add(textField);
		frame.add(addButton);
		frame.add(rButton);
		frame.add(bButton);
		frame.add(kButton);
		frame.add(resetButton);
		frame.add(ta);
		
		
    }

	public static void main(String[] args) {
		/* A thread which runs the UI */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
	/* Displays the result for the Process. The result can be Ready, Blocked or Current Process */
	public static String makeResult(ArrayList<Node> al,ArrayList<Node> bal){
		String result = "Priority\t\tState\n";
		boolean flg = false;
		for(Node v:al){
			if(flg){
				result +=  v.priority;
				result += "\t\t";
				result += "Ready";
				result += "\n";
			}
			else{
				result +=  v.priority;
				result += "\t\t";
				result += "Current Process";
				result += "\n";
				flg = true;
			}
		}
		for(Node v:bal){
			result +=  v.priority;
			result += "\t\t";
			result += "Blocked";
			result += "\n";
		}
		return result;
	}
	
	/* Method to prioritize the process based on the Priority given at the time of addition of the process */
	public static void prioritize(){
		Node temp = null;
		if(!al.isEmpty()){
			temp = al.remove(0);
		}
		/* Using sort method of the collections class to sort the process. */
		Collections.sort(al,new Comparator<Node>(){
			public int compare(Node a , Node b){
				return a.priority - b.priority;
			}
		});
		al.add(0,temp);
	}
	
	
	/* Defines the node Structure to be stored in the queue. */
	public static class Node{
		int priority;
		String state;
		
		public Node(int p, String s){
			priority = p;
			state = s;
		}
	}

}