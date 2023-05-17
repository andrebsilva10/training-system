package dao;



import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import model.Employee;
import util.HibernateUtil;

public class EmployeeDao {

	
	public void save(Employee employee) {

		Transaction transaction = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			// start the transaction
			transaction = session.beginTransaction();
			// save the employee object
			session.save(employee);
			// commit the transaction
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				System.out.println("abriu transaction mas falhou");
			}
		}
	}
	// atualizando o objeto (precisa ler o id do banco)
		public void update(Employee emp) {

			Transaction transaction = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();
				// save the studendt object
				session.saveOrUpdate(emp);
				// commit the transaction
				transaction.commit();

			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					System.out.println("Update: abriu transaction mas falhou");
				}
			}
		}

		public Employee getObjectById(long id) {

			String className = Employee.class.getName();

			Transaction transaction = null;
			Employee emp = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();
				// get the object
				emp = (Employee) session.get(className, id);
				// commit the transaction
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					System.out.println("abriu transaction mas falhou");
				}
			}
			return emp;
		}

		// Lista todos os registros
		public List<Employee> listAll(Employee emp) {

			// Class classe = emp.getClass();
			String className = Employee.class.getSimpleName().toString();

			Transaction transaction = null;
			List<Employee> objects = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();
				// get the employees
				objects = session.createQuery("from " + className).list();

				transaction.commit();

			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					System.out.println("ListALL - abriu transaction mas falhou");
				}
			}

			return objects;
		}

		public void delete(Employee emp) {

			Class classe = emp.getClass();
			String className = classe.getSimpleName().toString();

			Transaction transaction = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();
				// delete the student object
				session.delete(emp);

				// commit the transaction
				transaction.commit();

			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					System.out.println("Delete - abriu transaction mas falhou");
				}
			}
		}


}
