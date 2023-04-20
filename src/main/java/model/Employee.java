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
public class Employee implements Editable {

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
		this.name = name;
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
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

	public void edit() {
		if (name != null && !name.isEmpty() && trainings != null && !trainings.isEmpty()) {
			setName(name);
			setTrainings(trainings);
		}
	}

}
