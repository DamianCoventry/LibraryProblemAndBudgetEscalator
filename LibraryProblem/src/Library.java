public class Library {
    public static final int NUM_BOOKS = 20;

    private final boolean[] _booksBorrowed;

    Library() {
        _booksBorrowed = new boolean[NUM_BOOKS];
        for (int i = 0; i <  NUM_BOOKS; ++i) {
            _booksBorrowed[i] = false;
        }
    }

    public synchronized boolean requestBook(int who, int book) {
        if (book < 0 || book >= NUM_BOOKS || _booksBorrowed[book]) {
            return false;
        }
        System.out.println("Library gives book " + book + " to thread " + who);
        _booksBorrowed[book] = true;
        return true;
    }

    public synchronized void returnBook(int who, int book) {
        if (book >= 0 && book < NUM_BOOKS) {
            System.out.println("Library receives returned book " + book + " from thread " + who);
            _booksBorrowed[book] = false;
        }
    }
}
