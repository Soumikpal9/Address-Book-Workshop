package AddressBookWorkshop;

import java.util.List;

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
}
