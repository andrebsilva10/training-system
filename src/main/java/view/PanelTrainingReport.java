package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelTrainingReport extends JPanel implements VisualComponent {
    private JTextArea txtReport;
    private JButton btnBack;

    public PanelTrainingReport(FrameBase frame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        initializeComponents();
        setComponents();
        setEvents(frame);
    }

    private void initializeComponents() {
        txtReport = new JTextArea(10, 30);
        btnBack = new JButton("Voltar");
    }

    public void setComponents() {
        JScrollPane scrollPane = new JScrollPane(txtReport);
        add(scrollPane, BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);
        
        txtReport.setEditable(false);
    }

    private void setEvents(FrameBase frame) {
        btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
    }

    public void updateReport(String report) {
        txtReport.setText(report);
    }

	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		
	}


}
