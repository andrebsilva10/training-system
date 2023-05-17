package model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Training {

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

	public String generateReport() {
		StringBuilder report = new StringBuilder();
		report.append("Training Report:\n");
		report.append("ID: ").append(id).append("\n");
		report.append("Name: ").append(name).append("\n");

		return report.toString();
	}

}
