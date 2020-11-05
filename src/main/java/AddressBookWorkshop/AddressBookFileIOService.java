package AddressBookWorkshop;

import java.io.File;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {
	public static String CONTACT_READ_FILE = "ContactsReadFile.txt";
	public static String CONTACT_WRITE_FILE = "ContactsWriteFile.txt";
	
	public static String CONTACT_READ_CSV = "./demo1.csv";
	public static String CONTACT_WRITE_CSV = "./demo2.csv";
	
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
	
	public List<ContactDetails> readCsv(){
		List<ContactDetails> addBook = new ArrayList<>();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(CONTACT_READ_CSV));
			CsvToBean<ContactDetails> csvBean = new CsvToBeanBuilder(reader).withType(ContactDetails.class).withIgnoreLeadingWhiteSpace(true).build();
			addBook = csvBean.parse();
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return addBook;
	}
	
	public boolean writeCsv(List<ContactDetails> addBook) {
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(CONTACT_WRITE_CSV));
			StatefulBeanToCsv<ContactDetails> beanCsv = new StatefulBeanToCsvBuilder<ContactDetails>(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanCsv.write(addBook);
		}
		catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}