package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserAdministrator extends User {
    @OneToMany
    private List<User> registeredUsers;

    public UserAdministrator() {
        registeredUsers = new ArrayList<>();
    }

    public UserAdministrator(String username, String password) {
        super(username, password);
        registeredUsers = new ArrayList<>();
    }

    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    public void removeUser(User user) {
        registeredUsers.remove(user);
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
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
