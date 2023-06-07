package control;

import dao.GenericDao;

import exception.EmployeeRegistrationException;
import exception.TrainingRegistrationException;
import model.Employee;
import model.Training;
import model.User;
import model.UserDefault;
import util.HibernateUtil;
import view.FrameBase;
import view.PanelEmployeeReport;
import view.PanelTrainingReport;

import java.util.List;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class RegisterManager {
	private Thread reportThread;

	private GenericDao<Employee> employeeDao;
	private GenericDao<Training> trainingDao;
	private GenericDao<User> userDao;

	public RegisterManager() {
		employeeDao = new GenericDao<>();
		trainingDao = new GenericDao<>();
		userDao = new GenericDao<>();
	}
	
	public void saveUser(User user) {
	    try {
	        userDao.save(user);
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao salvar o usuário: " + e.getMessage());
	    }
	}

	public boolean isValidCredentials(String username, String password) {
	    try {
	        User user = userDao.getUserByUsernameAndPassword(username, password);
	        return user != null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
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

	public Employee getEmployeeByName(String employeeName) {
		List<Employee> employees = employeeDao.listAll(new Employee());

		for (Employee employee : employees) {
			if (employee.getName().equals(employeeName)) {
				return employee;
			}
		}

		return null; // Empregado não encontrado
	}

	public void associateTrainingToEmployee(Employee employee, Training training) {
		try {
			employee.addTraining(training);
			employeeDao.update(employee);
			trainingDao.update(training);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao associar treinamento ao empregado: " + e.getMessage());
		}
	}

	public List<String> getAllEmployeesNames() {
		List<Employee> employees = employeeDao.listAll(new Employee());
		List<String> employeeNames = new ArrayList<>();

		for (Employee employee : employees) {
			employeeNames.add(employee.getName());
		}

		return employeeNames;
	}

	public List<String> getAllTrainingsNames() {
		List<Training> trainings = trainingDao.listAll(new Training());
		List<String> trainingNames = new ArrayList<>();

		for (Training training : trainings) {
			trainingNames.add(training.getName());
		}
		return trainingNames;
	}

	public Training getTrainingByName(String trainingName) {
		List<Training> trainings = trainingDao.listAll(new Training());

		for (Training training : trainings) {
			if (training.getName().equals(trainingName)) {
				return training;
			}
		}

		return null; // Treinamento não encontrado
	}

//    public void updateTrainingStatus(Training training, Training.Status status) {
//        training.setStatus(status);
//        trainingDao.update(training);
//    }

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
