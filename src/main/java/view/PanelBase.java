package view;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class PanelBase  extends JPanel implements VisualComponent{
	
	
	private Page1 page1;
	
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
		add(page1, "page1");
		
	}

	public void setEvents() {
		
	}

}
