package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


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
	
	private String getTrainingsReport() {
		String s ="";
		s+= "Emerson " + 12 + " treino = \n";
		//fazer um for e construir uma string
		return s;
		
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
		if(trainings != null && !trainings.isEmpty()) {
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


	public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Employee Report:\n");
        report.append("ID: ").append(id).append("\n");
        report.append("Name: ").append(name).append("\n");
        report.append("Trainings:\n");
        for (Training training : trainings) {
            report.append("- ").append(training.getName()).append("\n");
        }
        return report.toString();
	}

}
