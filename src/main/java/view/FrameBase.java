package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class FrameBase extends JFrame implements VisualComponent{

	PanelBase base = new PanelBase();
	
	public FrameBase () {
		setLayouts();
		setComponents();
		setEvents();
	}
	
	public void setLayouts() {
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//encerra o processo ao clicar no X
		setLayout(new BorderLayout());//forma como os componentes são distribuídos na tela (esquerda, direta, centro, etc)
	}

	public void setComponents() {
		add(base, BorderLayout.CENTER);
		
	}

	public void setEvents() {
		
	}

	
}
