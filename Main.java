import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        LibraryFacade libraryFacade = new LibrarySystemFacade();

        // Test borrow book
        boolean success = libraryFacade.borrowBook("Title", "Author", "UserID");
        System.out.println("Borrow Book Success: " + success);

        // Test return book
        success = libraryFacade.returnBook("Title", "Author");
        System.out.println("Return Book Success: " + success);
    }
}

interface LibraryFacade {
    // Method to borrow a book
    boolean borrowBook(String title, String author, String borrowerId);

    // Method to return a book
    boolean returnBook(String title, String author);

    // Method to search for books by title or author
    List<Book> searchBooks(String query);

    // Method to check the availability of a book
    boolean checkAvailability(String title, String author);
}

class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class LibrarySystemFacade implements LibraryFacade {
    private BookInventorySystem bookInventorySystem;
    private UserManagementSystem userManagementSystem;

    public LibrarySystemFacade() {
        this.bookInventorySystem = new BookInventorySystem();
        this.userManagementSystem = new UserManagementSystem();
    }

    @Override
    public boolean borrowBook(String title, String author, String borrowerId) {
        if (!bookInventorySystem.checkAvailability(title, author)) {
            return false;
        }
        boolean success = bookInventorySystem.borrowBook(title, author);
        if (success) {
            userManagementSystem.addBorrowedBook(borrowerId, title, author);
        }
        return success;
    }

    @Override
    public boolean returnBook(String title, String author) {
        boolean success = bookInventorySystem.returnBook(title, author);
        if (success) {
            userManagementSystem.removeBorrowedBook(title, author);
        }
        return success;
    }

    @Override
    public List<Book> searchBooks(String query) {
        return bookInventorySystem.searchBooks(query);
    }

    @Override
    public boolean checkAvailability(String title, String author) {
        return bookInventorySystem.checkAvailability(title, author);
    }
}

class BookInventorySystem {
    private Map<String, Book> books;

    public BookInventorySystem() {
        this.books = new HashMap<>();
        // Initialize books in the inventory
    }

    public boolean borrowBook(String title, String author) {
        Book book = books.get(title + "_" + author);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    public boolean returnBook(String title, String author) {
        Book book = books.get(title + "_" + author);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }

    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        // Implement search logic based on title or author
        return result;
    }

    public boolean checkAvailability(String title, String author) {
        Book book = books.get(title + "_" + author);
        return book != null && book.isAvailable();
    }
}

class UserManagementSystem {
    private Map<String, List<Book>> borrowedBooks;

    public UserManagementSystem() {
        this.borrowedBooks = new HashMap<>();
    }

    public void addBorrowedBook(String userId, String title, String author) {
        Book book = new Book(title, author);
        borrowedBooks.computeIfAbsent(userId, k -> new ArrayList<>()).add(book);
    }

    public void removeBorrowedBook(String title, String author) {
        borrowedBooks.values().forEach(books -> books.removeIf(book -> book.getTitle().equals(title) && book.getAuthor().equals(author)));
    }
}
