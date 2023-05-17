package view;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class PanelBase extends JPanel implements VisualComponent{

	
	private Page1 page1;
	private Page2 page2;
		
	public PanelBase() {
		setLayouts();
		setComponents();
		setEvents();
	}
	
	
	public void setLayouts() {
		setSize(800, 600);
		setVisible(true);
		setBackground(Color.BLUE);
		setLayout(new CardLayout());
		
	}

	public void setComponents() {
		page1 = new Page1();
		page2 = new Page2();
		add(page1, "1");
		add(page2, "2");
		
	}

	public void setEvents() {
		// TODO Auto-generated method stub
		
	}

}
