package model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class UserAdministrator extends User {
    
    public UserAdministrator() {
        super();
    }
    
    public UserAdministrator(String username, String password) {
        super(username, password);
    }
    
    public void manageUsers(List<User> users) {
    	
    }
    
    public void manageTrainings(List<Training> training) {
        
    }

    @Override
    public void setPassword(String password) {
    	
        if (password.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
        }
        
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um número");
        }
        
        this.password = password;
    }

}
