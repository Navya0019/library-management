import java.io.*;
import java.util.Scanner;

// Abstract class representing a library item
abstract class LibraryItem {
    private String title;
    private String author;
    private int publicationYear;
    private String ISBN;

    public LibraryItem(String title, String author, int publicationYear, String ISBN) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getISBN() {
        return ISBN;
    }

    // Abstract method to display details of the library item
    public abstract void displayDetails();
}

// Class representing a Book, subclass of LibraryItem
class Book extends LibraryItem {
    private boolean isCheckedOut;
    private String borrowerName;
    private String borrowerContact;

    public Book(String title, String author, int publicationYear, String ISBN) {
        super(title, author, publicationYear, ISBN);
        isCheckedOut = false;
        borrowerName = "";
        borrowerContact = "";
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    // Method to check out the book
    public void checkOut(String borrowerName, String borrowerContact) {
        isCheckedOut = true;
        this.borrowerName = borrowerName;
        this.borrowerContact = borrowerContact;
    }

    // Method to return the book
    public void returnBook() {
        isCheckedOut = false;
        borrowerName = "";
        borrowerContact = "";
    }

    //Override
    public void displayDetails() {
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publication Year: " + getPublicationYear());
        System.out.println("ISBN: " + getISBN());
        System.out.println("Checked Out: " + (isCheckedOut ? "Yes" : "No"));
        if (isCheckedOut) {
            System.out.println("Borrower Name: " + borrowerName);
            System.out.println("Borrower Contact: " + borrowerContact);
        }
    }
}

// Class representing a Library
class Library {
    private Book[] books;
    private int numOfBooks;

    public Library(int capacity) {
        books = new Book[capacity];
        numOfBooks = 0;
    }

    // Method to add a book to the library
    public void addBook(Book book) {
        if (numOfBooks < books.length) {
            books[numOfBooks++] = book;
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    // Method to search for a book by title or author
    public void searchBook(String query) {
        boolean found = false;
        for (Book book : books) {
            if (book != null && (book.getTitle().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query))) {
                book.displayDetails();
                found = true;
            }
        }
        if (found==false) {
            System.out.println("Book not found.");
        }
    }

    // Method to check out a book from the library
    public void checkOutBook(String title, String borrowerName, String borrowerContact) {
        for (Book book : books) {
            if (book != null && book.getTitle().equalsIgnoreCase(title) && !book.isCheckedOut()) {
                book.checkOut(borrowerName, borrowerContact);
                System.out.println("Book checked out successfully.");
                return;
            }
        }
        System.out.println("Book not available for checkout.");
    }

    // Method to return a checked-out book to the library
    public void returnBook(String title) {
        for (Book book : books) {
            if (book != null && book.getTitle().equalsIgnoreCase(title) && book.isCheckedOut()) {
                book.returnBook();
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book not found or already returned.");
    }

    // Method to display the catalog of books in the library
    public void displayCatalog() {
        if (numOfBooks == 0) {
            System.out.println("Library catalog is empty.");
            return;
        }
        System.out.println("Library Catalog:");
        for (Book book : books) {
            if (book != null) {
                book.displayDetails();
                System.out.println("------------------------");
            }
        }
    }
}

class LibraryMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(10); // Initialize library with capacity 10

        while (true) {
            System.out.println("\nLibrary System Menu:");
            System.out.println("1. Add a Book");
            System.out.println("2. Search for a Book");
            System.out.println("3. Check Out a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Display Book Catalog");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int publicationYear = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter ISBN: ");
                    String ISBN = scanner.nextLine();
                    Book newBook = new Book(title, author, publicationYear, ISBN);
                    library.addBook(newBook);
                    break;
                case 2:
                    System.out.print("Enter title or author to search: ");
                    String query = scanner.nextLine();
                    library.searchBook(query);
                    break;
                case 3:
                    System.out.print("Enter title of the book to check out: ");
                    String checkoutTitle = scanner.nextLine();
                    System.out.print("Enter your name: ");
                    String borrowerName = scanner.nextLine();
                    System.out.print("Enter your contact details: ");
                    String borrowerContact = scanner.nextLine();
                    library.checkOutBook(checkoutTitle, borrowerName, borrowerContact);
                    break;
                case 4:
                    System.out.print("Enter title of the book to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnBook(returnTitle);
                    break;
                case 5:
                    library.displayCatalog();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
