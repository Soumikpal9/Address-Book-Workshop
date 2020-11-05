package AddressBookWorkshop;
import java.util.*;

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
		return addBook;
	}
	
	public String updateContact(ContactDetails contact) {
		int count = 0;
		for(ContactDetails i : addBook) {
			if(i.firstName.equals(contact.firstName)) {
				addBook.remove(i);
				addBook.add(contact);
				count++;
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
}
