package model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	private long id;

	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Training_Employee",
		joinColumns = @JoinColumn(name = "employee_id"),
		inverseJoinColumns = @JoinColumn(name = "training_id"))
	private List<Training> trainings;

	public Employee() {
	    trainings = new ArrayList<>();
	}
	
    public Employee(String name, List<Training> trainings) {
        this.name = name;
        this.trainings = trainings;
    }

	public Employee(String name, List<Training> trainings, Training training) {
		this.name = name;
		this.trainings = trainings;
		this.trainings.add(training);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	    if (!trainings.contains(training)) {
	        trainings.add(training);
	        training.getEmployees().add(this);
	    }
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
