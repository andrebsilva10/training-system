package model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserDefault extends User {

	public UserDefault() {
		super();
	}

	public UserDefault(String username, String password) {
		super(username, password);
	}

	public void addEmployee(List<Employee> employees) {

	}

	public void removeEmployee(List<Employee> employees) {

	}

	public void editEmployee(List<Employee> employees) {

	}

	public void viewEmployee(List<Employee> employees) {

	}

	public void addTraining(List<Training> trainings) {

	}

	public void removeTraining(List<Training> trainings) {

	}

	public void viewTraining(List<Training> trainings) {

	}

	@Override
	public void setPassword(String password) {

		if (password.length() < 4) {
			throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres");
		}

		if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			throw new IllegalArgumentException("A senha deve conter pelo menos 1 caractere especial.");
		}

		this.password = password;
	}
}
