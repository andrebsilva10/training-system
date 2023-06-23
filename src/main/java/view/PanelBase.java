package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import control.RegisterManager;

public class PanelBase extends JPanel implements VisualComponent {
    private JButton btnEmployeeRegistration, btnTrainingRegistration, btnEmployeeReport, btnTrainingReport,
            btnEmployeeTraining, btnUserRegistration, btnLogout;
    private RegisterManager registerM;
    private boolean isAdminLoggedIn;

    public PanelBase(FrameBase frame) {
        setLayout(new GridLayout(0, 1));
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
        btnEmployeeTraining = new JButton("Funcionários e treinamentos");
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
        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 0, 10));
        buttonPanel.add(btnEmployeeRegistration);
        buttonPanel.add(btnTrainingRegistration);
        buttonPanel.add(btnEmployeeReport);
        buttonPanel.add(btnTrainingReport);
        buttonPanel.add(btnEmployeeTraining);
        buttonPanel.add(btnUserRegistration);
        buttonPanel.add(btnLogout);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(150));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.add(centerPanel);

        add(wrapperPanel);
    }

    @Override
    public void setLayouts() {
    }

    public void setEvents(FrameBase frame) {
        ActionListener panelBaseListener = e -> frame.showPanel(this);
        btnEmployeeRegistration.addActionListener(panelBaseListener);
        btnTrainingRegistration.addActionListener(panelBaseListener);
        btnEmployeeReport.addActionListener(panelBaseListener);
        btnTrainingReport.addActionListener(panelBaseListener);
        btnUserRegistration.addActionListener(panelBaseListener);
        btnLogout.addActionListener(panelBaseListener);

        btnEmployeeTraining.addActionListener(e -> frame.showPanel(new PanelEmployeeTraining(frame, registerM)));
    }

    public void setAdminLoggedIn(boolean adminLoggedIn) {
        this.isAdminLoggedIn = adminLoggedIn;
        btnUserRegistration.setVisible(isAdminLoggedIn);
    }
}
