import java.util.concurrent.CountDownLatch;

public class Main {
    private static final int NUM_READERS = 100;

    public static void main(String[] args) {
        try {
            Library library = new Library();
            CountDownLatch done = new CountDownLatch(NUM_READERS);

            for (int i = 0; i < NUM_READERS; ++i) {
                Reader reader = new Reader(i, library, done);
                reader.start();
            }

            done.await();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
