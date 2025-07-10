package com.aurionpro.assignbook;

import java.util.Comparator;

public class TitleComparator implements Comparator<Book> {
	
	@Override
    public int compare(Book b1, Book b2) {
        return b2.getTitle().compareToIgnoreCase(b1.getTitle());  // Descending order
    }
}