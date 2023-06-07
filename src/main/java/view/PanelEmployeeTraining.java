package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hibernate.Session;

import control.RegisterManager;
import model.Employee;
import model.Training;
import util.HibernateUtil;

public class PanelEmployeeTraining extends JPanel {
	private JLabel lblSelectEmployee;
	private JLabel lblSelectTraining;
	private JComboBox<String> comboBoxEmployees;
	private JComboBox<String> comboBoxTrainings;
	private JButton assignButton, btnBack;
	private RegisterManager registerManager;

	public PanelEmployeeTraining(FrameBase frame, RegisterManager registerManager) {
		this.registerManager = registerManager;
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 250));
		setPreferredSize(new Dimension(800, 600));

		initializeComponents();
		setComponents();
		setEvents(frame);
	}

	private void initializeComponents() {
		lblSelectEmployee = new JLabel("Selecionar Empregado");
		comboBoxEmployees = new JComboBox<>();
		List<String> employeeNames = registerManager.getAllEmployeesNames();
		for (String name : employeeNames) {
			comboBoxEmployees.addItem(name);
		}

		lblSelectTraining = new JLabel("Selecionar Treinamento");
		comboBoxTrainings = new JComboBox<>();
		List<String> trainingNames = registerManager.getAllTrainingsNames();
		for (String name : trainingNames) {
			comboBoxTrainings.addItem(name);
		}

		assignButton = new JButton("Atribuir Treinamento");
		btnBack = new JButton("Voltar");
	}

	private void setComponents() {
		add(lblSelectEmployee);
		add(comboBoxEmployees);
		add(lblSelectTraining);
		add(comboBoxTrainings);
		add(assignButton);
		add(btnBack);
	}

	private void setEvents(FrameBase frame) {
		btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
		assignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String employeeName = (String) comboBoxEmployees.getSelectedItem();
				String trainingName = (String) comboBoxTrainings.getSelectedItem();

				Employee employee = registerManager.getEmployeeByName(employeeName);
				Training training = registerManager.getTrainingByName(trainingName);

				if (employee != null && training != null) {;
					registerManager.associateTrainingToEmployee(employee, training);

					System.out.println("Treinamento atribuído ao empregado com sucesso.");
				} else {
					System.out.println("Empregado ou treinamento não encontrado.");
				}
			}
		});

	}
}
