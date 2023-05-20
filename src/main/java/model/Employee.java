package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dao.EmployeeDao;

@Entity
public class Employee implements Reportable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "training_id")
	private List<Training> trainings;

	public Employee() {

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

	public void addTraining(Training training, int index) {
		if (index < 0) {
			throw new IllegalArgumentException("O índice deve ser maior ou igual a zero.");
		}
		if (index > this.trainings.size()) {
			throw new IllegalArgumentException("O índice deve ser menor ou igual ao tamanho da lista de treinamentos.");
		}
		this.trainings.set(index, training);
	}

	@Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Relatório de Employees:\n");
        report.append("=======================\n");
        
        EmployeeDao dao = new EmployeeDao();
        List<Employee> employees = dao.listAll();
        for (Employee employee : employees) {
            report.append("ID: ").append(employee.getId()).append("\n");
            report.append("Nome: ").append(employee.getName()).append("\n");
            report.append("Treinamentos:\n");
            List<Training> trainings = employee.getTrainings();
            for (Training training : trainings) {
                report.append("- ").append(training.getName()).append("\n");
            }
            report.append("=======================\n");
        }

        return report.toString();
    }

}
