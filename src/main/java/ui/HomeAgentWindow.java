package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class HomeAgentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4924347209446756399L;

	private JScrollPane leftScrollPane;
	private JScrollPane rightScrollPane;
	
	public HomeAgentWindow() {
		super("Home Agent Controller");
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setup();
		setVisible(true);
	}
	
	private void setup() {
		leftScrollPane = new JScrollPane(new RetailerAgentList(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(leftScrollPane, BorderLayout.WEST);
		
		rightScrollPane = new JScrollPane(new RetailerAgentList(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(rightScrollPane, BorderLayout.EAST);
	}
	
}
