package util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import model.Employee;
import model.Training;



public class HibernateUtil {

	private static SessionFactory sessionFactory;
	public static SessionFactory getSessionFactory() {
		if( sessionFactory==null ) {	
			try {
				Configuration configuration = new Configuration();
				
				// equivalente ao hibernate.cfg.xml
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://172.19.185.165:3306/training_system");
				settings.put(Environment.USER, "developer");
				settings.put(Environment.PASS, "developer");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				
				configuration.setProperties(settings);
				
				// aqui cadastro as classes que serão persistidas
				configuration.addAnnotatedClass(Employee.class);
				configuration.addAnnotatedClass(Training.class);
				
				
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
	
	
}
