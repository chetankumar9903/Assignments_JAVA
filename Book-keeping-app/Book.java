package com.aurionpro.assignbook;

public class Book {
	private int id;
	private String title;
	private String author;
	private boolean isIssued;

	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isIssued = false;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isIssued() {
		return isIssued;
	}

	public void issue() {
		this.isIssued = true;
	}

	public void returned() {
		this.isIssued = false;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Status: "
				+ (isIssued ? "Issued" : "Available");
	}
}
