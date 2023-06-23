package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.User;
import util.HibernateUtil;

public class GenericDao<T> {

    public Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    // salvando o objeto
    public void save(T obj) {
        Transaction transaction = null;
        Session session = getSession();
        
        try {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }
            
            transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(T obj) {
        Transaction transaction = null;
        Session session = getSession();
        
        try {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }
            
            transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
    
    public T getObjectById(Class<T> classe, long id) {
        String className = classe.getSimpleName();

        Transaction transaction = null;
        T retorno = null;
        Session session = getSession();

        try {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }

            transaction = session.beginTransaction();
            retorno = session.get(classe, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("getObjectById - abriu transaction mas falhou");
                e.printStackTrace();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        return retorno;
    }
    
    public User getUserByUsernameAndPassword(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE username = :username AND password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        User user = query.uniqueResult();

        transaction.commit();
        session.close();

        return user;
    }

    public T getUserByUsername(String username, Class<T> entityClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM " + entityClass.getSimpleName() + " WHERE username = :username";
            Query<T> query = session.createQuery(hql, entityClass);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


    // Lista todos os registros
    public List<T> listAll(T obj) {
        Class classe = obj.getClass();
        String className = classe.getSimpleName().toString();

        Transaction transaction = null;
        List<T> objects = null;
        Session session = getSession();

        try {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }

            transaction = session.beginTransaction();

            objects = session.createQuery("from " + className).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ListALL - abriu transaction mas falhou");
                e.printStackTrace();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        return objects;
    }

    public void delete(T obj) {
        Class classe = obj.getClass();
        String className = classe.getSimpleName().toString();

        Transaction transaction = null;
        Session session = getSession();

        try {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }

            transaction = session.beginTransaction();
            session.delete(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Delete - abriu transaction mas falhou");
                e.printStackTrace();
            }
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
