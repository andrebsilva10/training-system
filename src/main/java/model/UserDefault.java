package model;

import javax.persistence.Entity;

@Entity
public class UserDefault extends User {

    public UserDefault() {
        super();
    }

    public UserDefault(String username, String password) {
        super(username, password);
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
