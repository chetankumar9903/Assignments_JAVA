package com.aurionpro.assignbook;

import java.util.*;

public class BookTest {
	private static final Scanner sc = new Scanner(System.in);
	private static final List<Book> books = new ArrayList<>();

	public static void main(String[] args) {
		while (true) {
			System.out.println("\n--- Book Management Menu ---");
			System.out.println("1. Add a new book");
			System.out.println("2. Issue a book by ID");
			System.out.println("3. Display all available books");
			System.out.println("4. Display all issued books");
			System.out.println("5. Return a book");
			System.out.println("6. Sort Books");
			System.out.println("7. Exit");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1: addBook(); break;
				case 2: issueBook(); break;
				case 3: displayAvailableBooks(); break;
				case 4: displayIssuedBooks(); break;
				case 5: returnBook(); break;
				case 6: sortBooks(); break;
				case 7:
					System.out.println("Exiting... Goodbye!");
					return;
				default:
					System.out.println("Invalid option.");
			}
		}
	}

	private static void addBook() {
		System.out.print("Enter Book ID: ");
		int id = sc.nextInt(); sc.nextLine();
		System.out.print("Enter Title: ");
		String title = sc.nextLine();
		System.out.print("Enter Author: ");
		String author = sc.nextLine();
		books.add(new Book(id, title, author));
		System.out.println("Book added successfully!");
	}

	private static void issueBook() {
		System.out.print("Enter Book ID to issue: ");
		int issueId = sc.nextInt();
		boolean issued = false;
		for (Book b : books) {
			if (b.getId() == issueId && !b.isIssued()) {
				b.issue();
				System.out.println("Book issued successfully!");
				issued = true;
				break;
			}
		}
		if (!issued) System.out.println("Book not found or already issued.");
	}

	private static void displayAvailableBooks() {
		System.out.println("Available Books:");
		boolean found = false;
		for (Book b : books) {
			if (!b.isIssued()) {
				System.out.println(b);
				found = true;
			}
		}
		if (!found) System.out.println("No available books.");
	}

	private static void displayIssuedBooks() {
		System.out.println("Issued Books:");
		boolean found = false;
		for (Book b : books) {
			if (b.isIssued()) {
				System.out.println(b);
				found = true;
			}
		}
		if (!found) System.out.println("No issued books.");
	}

	private static void returnBook() {
		System.out.print("Enter Book ID to return: ");
		int returnId = sc.nextInt();
		boolean returned = false;
		for (Book b : books) {
			if (b.getId() == returnId && b.isIssued()) {
				b.returned();
				System.out.println("Book returned successfully!");
				returned = true;
				break;
			}
		}
		if (!returned) System.out.println("Book not found or was not issued.");
	}
	
//	private static void sortBooks() {
//
////      System.out.println("1. Sort by Author (Ascending)");
////      System.out.println("2. Sort by Title (Descending)");
////      int sortChoice = sc.nextInt();
////      if (sortChoice == 1) {
////          books.sort(new AuthorComparator());
////          System.out.println("Books sorted by author:");
////      } else if (sortChoice == 2) {
////          books.sort(new TitleComparator());
////          System.out.println("Books sorted by title (descending):");
////      } else {
////          System.out.println("Invalid sort option.");
////          break;
////      }
////      for (Book b : books) System.out.println(b);
//
//
// // Sort only available books
//	System.out.println("1. Sort by Author (Ascending)");
//	System.out.println("2. Sort by Title (Descending)");
//	int sortChoice = sc.nextInt();
//
//	// Create a temporary list of only available books
//	List<Book> availableBooks = new ArrayList<>();
//	for (Book b : books) {
//		if (!b.isIssued()) {
//			availableBooks.add(b);
//		}
//	}
//
//	// Sort based on user choice
//	if (sortChoice == 1) {
//		availableBooks.sort(new AuthorComparator());
//		System.out.println("Available books sorted by author:");
//	} else if (sortChoice == 2) {
//		availableBooks.sort(new TitleComparator());
//		System.out.println("Available books sorted by title (descending):");
//	} else {
//		System.out.println("Invalid sort option.");
//		return;
//	}
//
//	// Display the sorted available books
//	if (availableBooks.isEmpty()) {
//		System.out.println("No available books to display.");
//	} else {
//		for (Book b : availableBooks) {
//			System.out.println(b);
//		}
//	}
//	
//	}

	private static void sortBooks() {
		System.out.println("Sort which books?");
		System.out.println("1. All Books");
		System.out.println("2. Available Books");
		System.out.println("3. Issued Books");
		int listChoice = sc.nextInt();

		List<Book> selectedBooks = new ArrayList<>();
		for (Book b : books) {
			if (listChoice == 1 || (listChoice == 2 && !b.isIssued()) || (listChoice == 3 && b.isIssued())) {
				selectedBooks.add(b);
			}
		}

		if (selectedBooks.isEmpty()) {
			System.out.println("No books found for the selected category.");
			return;
		}

		System.out.println("Sort by:");
		System.out.println("1. Author (Ascending)");
		System.out.println("2. Title (Descending)");
		int sortChoice = sc.nextInt();

		if (sortChoice == 1) {
			selectedBooks.sort(new AuthorComparator());
			System.out.println("Books sorted by Author (Ascending):");
		} else if (sortChoice == 2) {
			selectedBooks.sort(new TitleComparator());
			System.out.println("Books sorted by Title (Descending):");
		} else {
			System.out.println("Invalid sort option.");
			return;
		}

		for (Book b : selectedBooks) {
			System.out.println(b);
		}
	}
}
