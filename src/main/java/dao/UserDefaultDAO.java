package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.UserDefault;
import util.HibernateUtil;

public class UserDefaultDAO {

    public void save(UserDefault user) {

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
    public void update(UserDefault user) {

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

    public UserDefault getObjectById(long id) {

        String className = UserDefault.class.getName();

        Transaction transaction = null;
        UserDefault user = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // get the user object
            user = (UserDefault) session.get(className, id);
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
    public List<UserDefault> listAll() {

        String className = UserDefault.class.getSimpleName().toString();

        Transaction transaction = null;
        List<UserDefault> objects = null;
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

    public void delete(UserDefault user) {

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
