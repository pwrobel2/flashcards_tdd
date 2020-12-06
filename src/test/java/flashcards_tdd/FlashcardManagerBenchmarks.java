package flashcards_tdd;

import flashcards_tdd.model.Flashcard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 6)
@Measurement(iterations = 12)
@Fork(value = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)

public class FlashcardManagerBenchmarks {
    private SessionFactory sessionFactoryForTesting;
    private Session sessionForTesting;
    private Transaction transactionForTesting;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup(Level.Invocation)
    public void setup() {
        sessionFactoryForTesting = prepareSessionFactory();
        sessionForTesting = prepareSession();
        sessionForTesting.getTransaction().rollback();
    }

    @TearDown(Level.Invocation)
    public void closeTransaction(){
        try {
            transactionForTesting.rollback();
        } catch(Exception e) {
        }
    }

    @Benchmark
    public void getSessionFactory(){
        HibernateUtil.getSessionFactory();
    }

    @Benchmark
    public void openSession(){
        sessionFactoryForTesting.openSession();
    }

    @Benchmark
    public void beginTransaction(){
        transactionForTesting = sessionForTesting.beginTransaction();
    }

    @Benchmark
    public void closeSession(){
        sessionForTesting.close();
    }

    @Warmup(iterations = 6)
    @Measurement(iterations = 12)
    @Fork(value = 5)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class CreateFlashcardBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            transactionForTesting.rollback();
            sessionForTesting.close();
        }

        @Benchmark
        public void createFlashcard(){
            sessionForTesting.save(flashcardForTesting);
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 6)
    @Measurement(iterations = 12)
    @Fork(value = 5)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class UpdateFlashcardBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            new FlashcardManagerImpl().createFlashcard(flashcardForTesting);
            flashcardForTesting.setTerm("Nowy");
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            transactionForTesting.rollback();
            sessionForTesting.close();
        }

        @Benchmark
        public void updateFlashcard(){
            sessionForTesting.update(flashcardForTesting);
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 6)
    @Measurement(iterations = 12)
    @Fork(value = 5)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class RemoveFlashcardBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            new FlashcardManagerImpl().createFlashcard(flashcardForTesting);
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            transactionForTesting.rollback();
            sessionForTesting.close();
        }

        @Benchmark
        public void deleteFlashcard(){
            sessionForTesting.remove(flashcardForTesting);
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 10)
    @Measurement(iterations = 30)
    @Fork(value = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class QueryByIdBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;
        private int idForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            new FlashcardManagerImpl().createFlashcard(flashcardForTesting);
            idForTesting = flashcardForTesting.getId();
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            transactionForTesting.rollback();
            sessionForTesting.close();
        }

        @Benchmark
        public void queryById(){
            List<?> results = sessionForTesting.createQuery("SELECT f FROM Flashcard f WHERE f.id = " + String.valueOf(idForTesting)).list();
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    @Fork(value = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class TransactionCommitCreateBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
            sessionForTesting.save(flashcardForTesting);
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            new FlashcardManagerImpl().deleteFlashcard(flashcardForTesting);
            sessionForTesting.close();
        }

        @Benchmark
        public void createFlashcardCommit(){
            transactionForTesting.commit();
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    @Fork(value = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class TransactionCommitUpdateBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            new FlashcardManagerImpl().createFlashcard(flashcardForTesting);
            flashcardForTesting.setTerm("Nowy");
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
            sessionForTesting.update(flashcardForTesting);
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            new FlashcardManagerImpl().deleteFlashcard(flashcardForTesting);
            sessionForTesting.close();
        }

        @Benchmark
        public void updateFlashcardCommit(){
            transactionForTesting.commit();
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    @Warmup(iterations = 10)
    @Measurement(iterations = 20)
    @Fork(value = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @State(Scope.Benchmark)
    public static class TransactionCommitDeleteBenchmarks {
        private Flashcard flashcardForTesting;
        private Transaction transactionForTesting;
        private Session sessionForTesting;

        @Setup(Level.Invocation)
        public void setup(){
            flashcardForTesting = prepareFlashcard();
            new FlashcardManagerImpl().createFlashcard(flashcardForTesting);
            sessionForTesting = prepareSession();
            transactionForTesting = prepareTransaction();
            sessionForTesting.delete(flashcardForTesting);
        }

        @TearDown(Level.Invocation)
        public void tearDown(){
            sessionForTesting.close();
        }

        @Benchmark
        public void deleteFlashcardCommit(){
            transactionForTesting.commit();
        }


        private Flashcard prepareFlashcard(){
            return new Flashcard("Test", "Test", 0);
        }

        private Transaction prepareTransaction(){
            return sessionForTesting.beginTransaction();
        }

        private Session prepareSession(){
            return HibernateUtil.getSessionFactory().openSession();
        }

    }

    private SessionFactory prepareSessionFactory(){
        return HibernateUtil.getSessionFactory();
    }

    private Session prepareSession(){
        return sessionFactoryForTesting.openSession();
    }
}
