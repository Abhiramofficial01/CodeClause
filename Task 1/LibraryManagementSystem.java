import java.util.ArrayList;
import java.util.List;
import java.util.*;

class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

class LibraryTransaction {
    private Book book;
    private User user;
    private String transactionType; // "Borrow" or "Return"

    public LibraryTransaction(Book book, User user, String transactionType) {
        this.book = book;
        this.user = user;
        this.transactionType = transactionType;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public String getTransactionType() {
        return transactionType;
    }
}

class Library {
    private List<Book> books;
    private List<User> users;
    private List<LibraryTransaction> transactions;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        Book newBook = new Book(title, author);
        books.add(newBook);
        System.out.println("Book added: " + title);
    }

    public void addUser(String username) {
        User newUser = new User(username);
        users.add(newUser);
        System.out.println("User added: " + username);
    }

    public void borrowBook(String username, String bookTitle) {
        User user = findUser(username);
        Book book = findBook(bookTitle);

        if (user != null && book != null && book.isAvailable()) {
            book.setAvailable(false);
            transactions.add(new LibraryTransaction(book, user, "Borrow"));
            System.out.println(username + " borrowed " + bookTitle);
        } else {
            System.out.println("Book not available or user not found.");
        }
    }

    public void returnBook(String username, String bookTitle) {
        User user = findUser(username);
        Book book = findBook(bookTitle);

        if (user != null && book != null && !book.isAvailable()) {
            book.setAvailable(true);
            transactions.add(new LibraryTransaction(book, user, "Return"));
            System.out.println(username + " returned " + bookTitle);
        } else {
            System.out.println("Book not borrowed by the user or user not found.");
        }
    }

    public void displayBooks() {
        System.out.println("Available books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook("The Catcher in the Rye", "J.D. Salinger");
        library.addBook("To Kill a Mockingbird", "Harper Lee");
        library.addBook("1984", "George Orwell");

        library.addUser("John Doe");
        library.addUser("Jane Smith");

        library.displayBooks();

        library.borrowBook("John Doe", "To Kill a Mockingbird");
        library.borrowBook("Jane Smith", "1984");

        library.displayBooks();

        library.returnBook("John Doe", "To Kill a Mockingbird");
        library.displayBooks();
    }
}
