package AddressBookWorkshop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {
	public static String CONTACT_READ_FILE = "ContactsReadFile.txt";
	public static String CONTACT_WRITE_FILE = "ContactsWriteFile.txt";
	
	public List<ContactDetails> readData(){
		List<ContactDetails> addBook = new ArrayList<>();
		try {
			Files.lines(new File(CONTACT_READ_FILE).toPath()).map(line -> line.trim()).forEach(line -> {
				String[] words = line.split("[\\\\s,=]+");
				
				ContactDetails contact = new ContactDetails();
				contact.firstName = words[1];
				contact.lastName = words[3];
				contact.address = words[5];
				contact.city = words[7];
				contact.state = words[9];
				contact.zipcode = words[11];
				contact.phone = words[13];
				contact.email = words[15];
				
				addBook.add(contact);
			});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return addBook;
	}
	
	public void writeData(List<ContactDetails> addBook) {
		StringBuffer empBuffer = new StringBuffer();
		addBook.forEach(e -> {
			String contactData = e.toString().concat("\n");
			empBuffer.append(contactData);
		});
		try {
			Files.write(Paths.get(CONTACT_WRITE_FILE), empBuffer.toString().getBytes());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
