package control;

import dao.EmployeeDao;
import model.Employee;

public class RegisterManager {

	public Employee getNewEmployee() {
		return new Employee();
	}

	public void saveEmployee(Employee employee) {

		EmployeeDao dao = new EmployeeDao();
		dao.save(employee);
	}

}
