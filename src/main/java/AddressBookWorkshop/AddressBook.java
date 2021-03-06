package AddressBookWorkshop;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {
	private List<ContactDetails> addBook = new ArrayList<>();
	
	public void setAddressBook(List<ContactDetails> addBook) {
		this.addBook = addBook;
	}
	
	public List<ContactDetails> getAddressBook(){
		return addBook;
	}
	
	public void addContact(ContactDetails contact) {
		addBook.add(contact);
	}
	
	public List<ContactDetails> showContact(){
		return addBook.stream().sorted((n1, n2) -> n1.firstName.compareTo(n2.firstName)).collect(Collectors.toList());
	}
	
	public List<ContactDetails> showSortedContactsBasedOnCity(){
		return addBook.stream().sorted((n1,n2) -> n1.city.compareTo(n2.city)).collect(Collectors.toList());
	}
	
	public List<ContactDetails> showSortedContactsBasedOnState(){
		return addBook.stream().sorted((n1,n2) -> n1.state.compareTo(n2.state)).collect(Collectors.toList());
	}
	
	public List<ContactDetails> showSortedContactsBasedOnZipcode(){
		return addBook.stream().sorted((n1,n2) -> n1.zipcode.compareTo(n2.zipcode)).collect(Collectors.toList());
	}
	
	public String updateContact(ContactDetails contact) {
		int count = 0;
		for(ContactDetails i : addBook) {
			if(i.firstName.equals(contact.firstName)) {
				addBook.remove(i);
				addBook.add(contact);
				count++;
				break;
			}
		}
		if(count == 0) {
			return "No such contact present in the Address Book";
		}
		else {
			return "Contact udated successfully";
		}
	}
	
	public boolean removeContact(String firstName) {
		ContactDetails contact = new ContactDetails("","","","","","","","");
		for(ContactDetails i : addBook) {
			if(i.firstName.equals(firstName)) {
				contact = i;
				break;
			}
		}
		return addBook.remove(contact);
	}
	
	public List<ContactDetails> searchContactByCity(String city) {
		return addBook.stream().filter(n -> n.city.equals(city)).collect(Collectors.toList());
	}
	
	public List<ContactDetails> searchContactByState(String state) {
		return addBook.stream().filter(n -> n.state.equals(state)).collect(Collectors.toList());
	}
	
	public long countOfContactByCity(String city) {
		return addBook.stream().filter(n -> n.city.equals(city)).count();
	}
	
	public long countOfContactByState(String state) {
		return addBook.stream().filter(n -> n.state.equals(state)).count();
	}
}
