package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Training implements ReportGenerator<Training> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private Status status;

	@ManyToMany(mappedBy = "trainings")
	private List<Employee> employees;

	public Training(String name, Status status) {
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
    public String generateReport(List<Training> trainings) {
        StringBuilder report = new StringBuilder();
        for (Training training : trainings) {
            report.append("ID: ").append(training.getId()).append("\n");
            report.append("Nome: ").append(training.getName()).append("\n");
            report.append("Status: ").append(training.getStatus()).append("\n");
            report.append("====================================================\n");
        }
        return report.toString();
    }
}
