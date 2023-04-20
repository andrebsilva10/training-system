package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.UserAdministrator;
import util.HibernateUtil;

public class UserAdministratorDAO {

    public void save(UserAdministrator user) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // save the user object
            session.save(user);
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
    public void update(UserAdministrator user) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // update the user object
            session.update(user);
            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Update: abriu transaction mas falhou");
            }
        }
    }

    public UserAdministrator getObjectById(long id) {

        String className = UserAdministrator.class.getName();

        Transaction transaction = null;
        UserAdministrator user = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // get the user object
            user = (UserAdministrator) session.get(className, id);
            // commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("abriu transaction mas falhou");
            }
        }
        return user;
    }

    // Lista todos os registros
    public List<UserAdministrator> listAll() {

        String className = UserAdministrator.class.getSimpleName().toString();

        Transaction transaction = null;
        List<UserAdministrator> objects = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // get the users
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

    public void delete(UserAdministrator user) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // delete the user object
            session.delete(user);
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
