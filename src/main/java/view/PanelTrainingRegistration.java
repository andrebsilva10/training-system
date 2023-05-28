package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.RegisterManager;
import exception.EmployeeRegistrationException;
import exception.TrainingRegistrationException;
import model.Employee;
import model.Training;

public class PanelTrainingRegistration extends JPanel implements VisualComponent {
    private JLabel lblTitle;
    private JTextField txtName;
    private JButton btnRegister, btnBack;
    private RegisterManager registerM;

    public PanelTrainingRegistration(FrameBase frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 250));
        setPreferredSize(new Dimension(800, 600));

        initializeComponents();
        setComponents();
        setEvents(frame);
    }

    private void initializeComponents() {
        lblTitle = new JLabel("Título:");
        txtName = new JTextField(20);
        btnRegister = new JButton("Cadastrar");
        btnBack = new JButton("Voltar");
    }

    public void setComponents() {
        add(lblTitle);
        add(txtName);
        add(btnRegister);
        add(btnBack);
        
        registerM = new RegisterManager();
    }

    private void setEvents(FrameBase frame) {
        btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
        btnRegister.addActionListener(e -> {
            String name = txtName.getText().trim();
            try {
            	if (name.isEmpty()) {
            		throw new TrainingRegistrationException("O campo 'Nome' não pode estar vazio");
            	}

            	Training training= registerM.createNewTraining();
            	training.setName(name);
                registerM.saveTraining(training);
                txtName.setText("");
                } catch (TrainingRegistrationException exception) {
					JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
        });
    }


	@Override
	public void setLayouts() {
		// TODO Auto-generated method stub
		
	}


}