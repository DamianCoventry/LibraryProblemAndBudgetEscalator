public class Library {
    public static final int NUM_BOOKS = 20;

    private final boolean[] _booksAvailability;

    Library() {
        _booksAvailability = new boolean[NUM_BOOKS];
        for (int i = 0; i <  NUM_BOOKS; ++i) {
            _booksAvailability[i] = false;
        }
    }

    public synchronized boolean requestBook(int who, int book) {
        if (book < 0 || book >= NUM_BOOKS || _booksAvailability[book]) {
            return false;
        }
        System.out.println("Library gives book " + book + " to thread " + who);
        _booksAvailability[book] = true;
        return true;
    }

    public synchronized void returnBook(int who, int book) {
        if (book >= 0 && book < NUM_BOOKS) {
            System.out.println("Library receives returned book " + book + " from thread " + who);
            _booksAvailability[book] = false;
        }
    }
}
