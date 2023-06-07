package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import control.RegisterManager;

public class PanelBase extends JPanel implements VisualComponent {
    private JButton btnEmployeeRegistration, btnTrainingRegistration, btnEmployeeReport, btnTrainingReport,
            btnEmployeeTraining, btnUserRegistration, btnLogout;
    private RegisterManager registerM;

    public PanelBase(FrameBase frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 250));
        setPreferredSize(new Dimension(800, 600));

        registerM = new RegisterManager();

        initializeComponents(frame);
        setComponents();
        setEvents(frame);
    }

    private void initializeComponents(FrameBase frame) {
        btnEmployeeRegistration = new JButton("Cadastro de Empregados");
        btnTrainingRegistration = new JButton("Cadastro de Treinamentos");
        btnEmployeeReport = new JButton("Relatório de Empregados");
        btnTrainingReport = new JButton("Relatório de Treinamentos");
        btnEmployeeTraining = new JButton("Vincular Treinamentos a Funcionários");
        btnUserRegistration = new JButton("Cadastro de Usuários");
        btnLogout = new JButton("Logout");

        btnEmployeeRegistration.addActionListener(e -> frame.showPanel(new PanelEmployeeRegistration(frame)));
        btnTrainingRegistration.addActionListener(e -> frame.showPanel(new PanelTrainingRegistration(frame)));
        btnEmployeeReport.addActionListener(e -> registerM.generateEmployeeReport(frame));
        btnTrainingReport.addActionListener(e -> registerM.generateTrainingReport(frame));
        btnEmployeeTraining.addActionListener(e -> frame.showPanel(new PanelEmployeeTraining(frame, registerM)));
        btnUserRegistration.addActionListener(e -> frame.showPanel(new PanelUserRegistration(frame, true)));
        btnLogout.addActionListener(e -> frame.showPanel(new PanelLogin(frame)));
    }

    public void setComponents() {
        add(btnEmployeeRegistration);
        add(btnTrainingRegistration);
        add(btnEmployeeReport);
        add(btnTrainingReport);
        add(btnEmployeeTraining);
        add(btnUserRegistration);
        add(btnLogout);
    }

    @Override
    public void setLayouts() {
        // TODO Auto-generated method stub

    }

    public void setEvents(FrameBase frame) {
        ActionListener panelBaseListener = e -> frame.showPanel(this);
        btnEmployeeRegistration.addActionListener(panelBaseListener);
        btnTrainingRegistration.addActionListener(panelBaseListener);
        btnEmployeeReport.addActionListener(panelBaseListener);
        btnTrainingReport.addActionListener(panelBaseListener);
    }
}
