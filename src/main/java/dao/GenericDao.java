package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;


public class GenericDao<T> {
		// salvando o objeto
			
		public void save(T obj) {

				Transaction transaction = null;
				try {
					Session session = HibernateUtil.getSessionFactory().openSession();
					// start the transaction
					transaction = session.beginTransaction();

					session.save(obj);
					// commit the transaction
					transaction.commit();

				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
						System.out.println("abriu transaction mas falhou");
					}
				}
			}
		//atualizando o objeto (precisa ler o id do banco)	
		public void update(T obj) {
		    Transaction transaction = null;
		    try {
		        Session session = HibernateUtil.getSessionFactory().openSession();
		        // start the transaction
		        transaction = session.beginTransaction();

		        session.update(obj);
		        // commit the transaction
		        transaction.commit();
		    } catch (Exception e) {
		        if (transaction != null) {
		            transaction.rollback();
		            System.out.println("Update: abriu transaction mas falhou");
		        }
		    }
		}

		public T getObjectById(T obj, long id) {
			Class classe = obj.getClass();	
			String className = classe.getSimpleName().toString();	
			
		    Transaction transaction = null;
		    T retorno = null;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			//start the transaction
			transaction = session.beginTransaction();

			retorno = (T)session.get(classe, id);
			//commit the transaction
			transaction.commit();} 
		catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			    System.out.println("abriu transaction mas falhou");
			}
		}
		return retorno;
		}

		// Lista todos os registros
		public List<T> listAll(T obj) {

			Class classe = obj.getClass();
			String className = classe.getSimpleName().toString();

			Transaction transaction = null;
			List<T> objects = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();

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
		
		public void delete(T obj) {

			Class classe = obj.getClass();
			String className = classe.getSimpleName().toString();
			
			Transaction transaction = null;
			try {
				Session session = HibernateUtil.getSessionFactory().openSession();
				// start the transaction
				transaction = session.beginTransaction();
				// delete the student object
				session.delete(obj);

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