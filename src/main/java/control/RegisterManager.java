package control;

import javax.swing.SwingUtilities;

import dao.GenericDao;
import exception.EmployeeRegistrationException;
import exception.TrainingRegistrationException;
import model.Employee;
import model.Training;
import view.FrameBase;
import view.PanelEmployeeReport;
import view.PanelTrainingReport;

public class RegisterManager {

    private Thread reportThread;

    private GenericDao<Employee> employeeDao;
    private GenericDao<Training> trainingDao;

    public RegisterManager() {
        employeeDao = new GenericDao<>();
        trainingDao = new GenericDao<>();
    }

    public Employee createNewEmployee() {
        return new Employee();
    }

    public void saveEmployee(Employee employee) {
        try {
            employeeDao.save(employee);
        } catch (Exception e) {
            throw new EmployeeRegistrationException("Erro ao salvar o funcionário: " + e.getMessage());
        }
    }


    public void generateEmployeeReport(FrameBase frame) {
        if (isReportThreadRunning()) {
            return; // A thread já está em execução, não é necessário iniciar outra
        }

        reportThread = new Thread(() -> {
            PanelEmployeeReport panel = new PanelEmployeeReport(frame);

            Employee employee = new Employee();
            String report = employee.generateReport(employeeDao.listAll(employee));

            SwingUtilities.invokeLater(() -> {
                panel.updateReport(report);
                frame.showPanel(panel);
            });
        });
        reportThread.start();
    }

    public Training createNewTraining() {
        return new Training();
    }

    public void saveTraining(Training training) {
        try {
            trainingDao.save(training);
        } catch (Exception e) {
            throw new TrainingRegistrationException("Erro ao salvar o treinamento: " + e.getMessage());
        }
    }

    public void generateTrainingReport(FrameBase frame) {
        if (isReportThreadRunning()) {
            return; // A thread já está em execução, não é necessário iniciar outra
        }

        reportThread = new Thread(() -> {
            PanelTrainingReport panel = new PanelTrainingReport(frame);

            Training training = new Training();
            String report = training.generateReport(trainingDao.listAll(training));

            SwingUtilities.invokeLater(() -> {
                panel.updateReport(report);
                frame.showPanel(panel);
            });
        });
        reportThread.start();
    }

    private boolean isReportThreadRunning() {
        return reportThread != null && reportThread.isAlive();
    }
}
