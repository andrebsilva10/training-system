package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Training;
import util.HibernateUtil;

public class TrainingDAO {

    public void save(Training training) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // save the training object
            session.save(training);
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
    public void update(Training training) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // update the training object
            session.update(training);
            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Update: abriu transaction mas falhou");
            }
        }
    }

    public Training getObjectById(long id) {

        String className = Training.class.getName();

        Transaction transaction = null;
        Training training = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // get the training object
            training = (Training) session.get(className, id);
            // commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("abriu transaction mas falhou");
            }
        }
        return training;
    }

    // Lista todos os registros
    public List<Training> listAll() {

        String className = Training.class.getSimpleName().toString();

        Transaction transaction = null;
        List<Training> objects = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // get the trainings
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

    public void delete(Training training) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            // start the transaction
            transaction = session.beginTransaction();
            // delete the training object
            session.delete(training);
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
