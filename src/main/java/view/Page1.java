package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.RegisterManager;
import model.Employee;
import model.Training;

public class Page1 extends JPanel implements VisualComponent {

    JLabel lbTitle, lbName, lbAge, lbFunction;
    JTextField jtfName, jtfAge, jtfFunction;
    JButton btRegister, btEmpReports, btTrainingReports;
    RegisterManager registerM;

    public Page1() {
        setLayouts();
        setComponents();
        setEvents();
    }

    public void setLayouts() {
        setLayout(null);
        setBackground(Color.CYAN);
        setSize(800, 600);
        setVisible(true);
    }

    public void setComponents() {
        lbTitle = new JLabel("Cadastro de Empregados");
        lbTitle.setBounds(300, 10, 300, 30);

        lbName = new JLabel("Nome");
        lbName.setBounds(80, 80, 80, 30);
        jtfName = new JTextField();
        jtfName.setBounds(140, 80, 300, 30);

        lbAge = new JLabel("Idade");
        lbAge.setBounds(470, 80, 60, 30);
        jtfAge = new JTextField();
        jtfAge.setBounds(520, 80, 80, 30);

        lbFunction = new JLabel("Função");
        lbFunction.setBounds(80, 150, 80, 30);
        jtfFunction = new JTextField();
        jtfFunction.setBounds(140, 150, 180, 30);

        btRegister = new JButton("-- Gravar --");
        btRegister.setBounds(150, 200, 110, 30);

        btEmpReports = new JButton("Relatório empregados");
        btEmpReports.setBounds(280, 200, 180, 30);

        btTrainingReports = new JButton("Relatório treinamentos");
        btTrainingReports.setBounds(470, 200, 200, 30);

        add(lbTitle);
        add(lbName);
        add(jtfName);
        add(lbAge);
        add(jtfAge);
        add(lbFunction);
        add(jtfFunction);
        add(btRegister);
        add(btEmpReports);
        add(btTrainingReports);

        registerM = new RegisterManager();
    }

    public void setEvents() {
        btRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Employee employee = registerM.createNewEmployee();
                employee.setName(jtfName.getText());
                registerM.saveEmployee(employee);
            }
        });

        btEmpReports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Employee employee = new Employee();

            }
        });

        btTrainingReports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Training training = new Training();

            }
        });
    }
}
