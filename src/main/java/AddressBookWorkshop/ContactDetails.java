package AddressBookWorkshop;

import java.time.LocalDate;

public class ContactDetails {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public String zipcode;
	public String phone;
	public String email;
	public LocalDate start;
	
	public ContactDetails() {
		
	}
	
	public ContactDetails(String firstName, String lastName, String address, String city, String state, String zipcode, String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phone = phone;
		this.email = email;
	}
	
	public ContactDetails(String firstName, String lastName, String address, String city, String state, String zipcode, String phone, String email, LocalDate start) {
		this(firstName, lastName, address, city, state, zipcode, phone, email);
		this.start = start;
	}
	
	@Override
	public String toString() {
		return "First Name: "+firstName+"\nLast Name: "+lastName+"\nAddress: "+address+"\nCity: "+city+"\nState: "+state+"\nZip: "+zipcode+"\nPhone Number: "+phone+"\nEmail: "+email;
	}
}
