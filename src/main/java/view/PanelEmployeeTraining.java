package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.RegisterManager;
import model.Employee;
import model.Training;

public class PanelEmployeeTraining extends JPanel {
    private JLabel lblSelectEmployee;
    private JLabel lblSelectTraining;
    private JComboBox<String> comboBoxEmployees;
    private JComboBox<String> comboBoxTrainings;
    private JButton assignButton, btnBack;
    private RegisterManager registerManager;
    private JTable table;
    private DefaultTableModel tableModel;

    public PanelEmployeeTraining(FrameBase frame, RegisterManager registerManager) {
        this.registerManager = registerManager;
        setLayout(new BorderLayout());

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

        table = new JTable();
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        tableModel.addColumn("Nome");
        tableModel.addColumn("Treinamento");
        tableModel.addColumn("Status");

    }

    private void setComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(contentPanel, BorderLayout.CENTER);

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); 
        comboPanel.setPreferredSize(new Dimension(400, 60)); 
        comboPanel.add(lblSelectEmployee);
        comboPanel.add(comboBoxEmployees);
        comboPanel.add(lblSelectTraining);
        comboPanel.add(comboBoxTrainings);

        contentPanel.add(comboPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); 
        buttonPanel.add(assignButton);
        buttonPanel.add(btnBack);

        contentPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        add(scrollPane, BorderLayout.SOUTH);

        updateTable();
    }



    private void setEvents(FrameBase frame) {
        btnBack.addActionListener(e -> frame.showPanel(new PanelBase(frame)));
        assignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeName = (String) comboBoxEmployees.getSelectedItem();
                String trainingName = (String) comboBoxTrainings.getSelectedItem();

                Employee employee = registerManager.getEmployeeByName(employeeName);
                Training training = registerManager.getTrainingByName(trainingName);
                
                
                if (employee != null && training != null) {
                    registerManager.associateTrainingToEmployee(employee, training);
                    JOptionPane.showMessageDialog(null, "Treinamento atribuído ao empregado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Empregado ou treinamento não encontrado.");
                }
                
                updateTable();
            }
        });
    }

    private void updateTable() {
        tableModel.setRowCount(0); 

        List<Employee> employees = registerManager.getEmployees();
        for (Employee employee : employees) {
            List<Training> trainings = employee.getTrainings();
            StringBuilder trainingNames = new StringBuilder();
            StringBuilder trainingStatuses = new StringBuilder();

            for (Training training : trainings) {
                if (trainingNames.length() > 0) {
                    trainingNames.append(", ");
                }
                trainingNames.append(training.getName());

                if (trainingStatuses.length() > 0) {
                    trainingStatuses.append(", ");
                }
                trainingStatuses.append(training.getStatus());
            }

            // Adiciona uma nova linha à tabela com as informações do funcionário e seus treinamentos
            tableModel.addRow(new Object[]{employee.getName(), trainingNames.toString(), trainingStatuses.toString()});
        }
    }
}
