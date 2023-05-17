package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class FrameBase extends JFrame implements VisualComponent{

	PanelBase base; 
	
	
	
	public FrameBase() {
		setLayouts();
		setComponents();
		setEvents();
	    validate();
	}
	
	
	public void setLayouts() {
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
	}

	public void setComponents() {
	    base = new PanelBase();
		add(base, BorderLayout.CENTER);
		
		
	}

	public void setEvents() {
		// TODO Auto-generated method stub
		
	}

     
	
}
