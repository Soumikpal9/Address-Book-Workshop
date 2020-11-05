package AddressBookWorkshop;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<ContactDetails> addBookList;
	private AddressBookDBService addBookDB;
	
	public AddressBookService() {
		addBookDB = AddressBookDBService.getInstance();
	}
	
	public AddressBookService(List<ContactDetails> addBookList) {
		this();
		this.addBookList = addBookList;
	}
	
	public List<ContactDetails> readAddresBookData(AddressBookWorkshop.AddressBookExecutor.IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			this.addBookList = addBookDB.readData();
		}
		return this.addBookList;
	}
	
	public void updateContactsCity(String firstName, String city) {
		int result = addBookDB.updateData(firstName, city);
		if(result == 0)	return;
		ContactDetails addBookData = this.checkAddressBookDataInSyncWithDB(firstName);
		if(addBookData != null)	addBookData.city = city;
	}

	public ContactDetails checkAddressBookDataInSyncWithDB(String firstName) {
		List<ContactDetails> addBookDataList = addBookDB.getAddressBookData(firstName);
		return addBookDataList.stream()
				  .filter(con -> con.firstName.equals(firstName))
				  .findFirst()
				  .orElse(null);
	}
	
	public List<ContactDetails> readAddressBookForDateRange(AddressBookWorkshop.AddressBookExecutor.IOService ioService, LocalDate startDate, LocalDate endDate) {
		if(ioService.equals(IOService.DB_IO)) {
			return addBookDB.getAddressBookForDateRange(startDate, endDate);
		}
		return null;
	}
	
	public Map<String, Integer> readCountContactsByCity(AddressBookWorkshop.AddressBookExecutor.IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			return addBookDB.getCountByCity();
		}
		return null;
	}

	public Map<String, Integer> readCountContactsByState(AddressBookWorkshop.AddressBookExecutor.IOService ioService) {
		if(ioService.equals(IOService.DB_IO)) {
			return addBookDB.getCountByState();
		}
		return null;
	}
	
	public void addContactToBook(String firstName, String lastName, String  address, String city, String state, String zipcode, String phone, String email) {
		addBookList.add(addBookDB.addContactToBook(firstName, lastName, address, city, state, zipcode, phone, email));
	}
	
	public void addContactsWithThreads(List<ContactDetails> addBookList) {
		Map<Integer, Boolean> contactAdditionStatus = new HashMap<Integer, Boolean>();
		addBookList.forEach(addBookData -> {
			Runnable task = () -> {
				contactAdditionStatus.put(addBookData.hashCode(), false);
				//System.out.println("Employee being added : " + Thread.currentThread().getName());
				this.addContactToBook(addBookData.firstName, addBookData.lastName, addBookData.address, addBookData.city, addBookData.state, addBookData.zipcode, addBookData.phone, addBookData.email);
				contactAdditionStatus.put(addBookData.hashCode(), true);
				//System.out.println("Employee added : " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, addBookData.firstName);
			thread.start();
		});
		while(contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			}
			catch(InterruptedException e) {}
		}
	}
}
