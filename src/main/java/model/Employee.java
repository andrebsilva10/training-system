package model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Employee implements ReportGenerator<Employee> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idEmployee;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Training_Employee", joinColumns = @JoinColumn(name = "id_employee"), inverseJoinColumns = @JoinColumn(name = "id_training"))
	public List<Training> trainings = new ArrayList<>();

	public Employee() {
	}

//    public Employee(String name, List<Training> trainings) {
//        this.name = name;
//        this.trainings = trainings;
//    }
//
//	public Employee(String name, List<Training> trainings, Training training) {
//		this.name = name;
//		this.trainings = trainings;
//		this.trainings.add(training);
//	}

	public long getId() {
		return idEmployee;
	}

	public void setId(long idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		if (trainings != null && !trainings.isEmpty()) {
			this.trainings = trainings;
		}
	}

	public void addTraining(Training training) {
		trainings.add(training);
	}

	@Override
	public String generateReport(List<Employee> employees) {
		StringBuilder report = new StringBuilder();
		for (Employee employee : employees) {
			report.append("ID: ").append(employee.getId()).append("\n");
			report.append("Nome: ").append(employee.getName()).append("\n");
			report.append("=======================\n");
		}
		return report.toString();
	}

}
