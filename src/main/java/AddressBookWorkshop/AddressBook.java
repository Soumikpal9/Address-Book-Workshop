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
}
