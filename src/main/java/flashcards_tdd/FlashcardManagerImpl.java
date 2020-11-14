package flashcards_tdd;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.PersistenceException;

import flashcards_tdd.model.Flashcard;

public class FlashcardManagerImpl implements FlashcardManager {
    @Override
    public int createFlashcard(Flashcard flashcard) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        int id = -1;
        try{
            transaction = session.beginTransaction();
            id = (Integer) session.save(flashcard);
            transaction.commit();
        // TODO catch IllegalArgumentException
        } catch (PersistenceException e) {
            id = -1;
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public int updateFlashcard(Flashcard flashcard) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        int updatedCount = 0;
        try{
            transaction = session.beginTransaction();
            session.update(flashcard);
            transaction.commit();
            // TODO set real updatedCount
            updatedCount = 1;
        // TODO catch IllegalArgumentException
        } catch (PersistenceException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return updatedCount;
    }

    @Override
    public int deleteFlashcard(Flashcard flashcard) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        int deletedCount = 0;
        try{
            transaction = session.beginTransaction();
            session.remove(flashcard);
            transaction.commit();
            // TODO set real deletedCount
            deletedCount = 1;
        } catch (PersistenceException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return deletedCount;
    }

    @Override
    public Flashcard readFlashcardById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<?> results = session.createQuery("SELECT f FROM Flashcard f WHERE f.id = " + String.valueOf(id)).list();
        session.close();
        if (results.size() != 1) {
            return null;
        }
        if (!(results.get(0) instanceof Flashcard)) {
            return null;
        }
        return (Flashcard) results.get(0);
    }

    @Override
    public List<Flashcard> readAllFlashcardsList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<?> results = session.createQuery("SELECT f FROM Flashcard f").list();
        session.close();
        for (int i = 0; i < results.size(); i++) {
            if (!(results.get(i) instanceof Flashcard)) {
                return new ArrayList<>();
            }
        }
        return (List<Flashcard>) results;
    }
}
