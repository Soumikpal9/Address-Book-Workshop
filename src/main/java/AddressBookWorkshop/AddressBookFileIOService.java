package AddressBookWorkshop;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
	
	public static String CONTACT_READ_JSON = "./ContactRead.json";
	public static String CONTACT_WRITE_JSON = "./ContactWrite.json";
	
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
	
	public boolean readJson() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(CONTACT_READ_JSON));
			JsonParser jsonParser = new JsonParser();
			JsonElement object = jsonParser.parse(reader);
			JsonArray contactList = (JsonArray) object;
			return true;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean writeJson(List<ContactDetails> addBook) {
		Gson gson = new Gson();
		String json = gson.toJson(addBook);
		try {
			FileWriter fileWriter = new FileWriter(CONTACT_READ_JSON);
			fileWriter.write(json);
			fileWriter.close();
			return true;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}