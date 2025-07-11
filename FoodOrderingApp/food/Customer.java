package com.aurionpro.food;

import com.aurionpro.exceptions.FieldNotFilledException;
import com.aurionpro.exceptions.InValidFormatException;


public class Customer {
	private String customerId;
	private String name;
	private String phone;
	private String address;
	private String email;

	private static int counter = 1;

	public Customer() {
		super();
	}

	public Customer(String name, String phone, String address, String email) {
		validatePhone(phone);
		validateEmail(email);
		this.customerId = String.format("C%03d", counter++);
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private void validatePhone(String phone) {
		if (phone == null) {
			throw new FieldNotFilledException("Phone Number is required");
		} else if (!phone.matches("\\d{10}")) {
			throw new InValidFormatException("Invalid phone number. Must be 10 digits.");
		}
	}

	private void validateEmail(String email) {
		if (email == null) {
			throw new FieldNotFilledException("Email is required");

		} else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new InValidFormatException("Invalid email.");
		}
	}

	


	@Override
	public String toString() {
		return String.format("%-6s | %-20s | %-12s | %-25s | %-25s", customerId, name, phone, address, email);
	}

//	public static void main(String[] args) {
//		Customer c = new Customer("Chetan", "8899990357", "VJTI College", "chetan@gmail.com");
//		System.out.printf("%-6s | %-20s | %-12s | %-25s | %-25s%n", "ID", "Name", "Phone", "Address", "Email");
//		System.out.println("----------------------------------------------------------------------");
//		System.out.println(c);
//	}

}
