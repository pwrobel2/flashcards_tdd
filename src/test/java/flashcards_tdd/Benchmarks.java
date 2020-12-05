package flashcards_tdd;

import flashcards_tdd.model.Flashcard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3) 		// Warmup Iteration = 3
@Measurement(iterations = 8)
@Fork(value = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)

public class Benchmarks {
    private Flashcard flashcardForTesting;
    private SessionFactory sessionFactoryForTesting;
    private Session sessionForTesting;
    private Transaction transactionForTesting;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup(Level.Invocation)
    public void setup() {
        flashcardForTesting = prepareFlashcard();
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

    private Flashcard prepareFlashcard(){
        return new Flashcard("Test", "Test", 0);
    }

    private SessionFactory prepareSessionFactory(){
        return HibernateUtil.getSessionFactory();
    }

    private Session prepareSession(){
        return sessionFactoryForTesting.openSession();
    }
}
