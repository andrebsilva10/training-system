package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import dao.TrainingDao;

@Entity
public class Training implements Reportable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	private String name;

	private Status status;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Employee> employees;

	public Training(String name) {
		this.name = name;
		this.status = Status.PENDING;
	}

	public Training() {
		this.status = Status.PENDING;
	}
	
	public long getId() {
        return id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		if (status != null
				&& (status == Status.PENDING || status == Status.IN_PROGRESS || status == Status.COMPLETED)) {
			this.status = status;
		}
	}

	public enum Status {
		PENDING, IN_PROGRESS, COMPLETED
	}
	
	public List<Employee> getEmployees() {
	    return employees;
	}


	@Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Relatório de Treinamentos:\n");
        report.append("=======================\n");

        TrainingDao dao = new TrainingDao();
        List<Training> trainings = dao.listAll();
        for (Training training : trainings) {
            report.append("ID: ").append(training.getId()).append("\n");
            report.append("Nome: ").append(training.getName()).append("\n");
            report.append("Status: ").append(training.getStatus()).append("\n");
            report.append("Employees:\n");
            List<Employee> employees = training.getEmployees();
            for (Employee employee : employees) {
                report.append("- ").append(employee.getName()).append("\n");
            }
            report.append("=======================\n");
        }

        return report.toString();
    }

}
