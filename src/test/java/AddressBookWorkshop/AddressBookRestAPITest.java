package AddressBookWorkshop;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestAPITest {
	@Before
    public void setup() {
    	RestAssured.baseURI = "http://localhost";
    	RestAssured.port = 3000;
    }
	
	private ContactDetails[] getContactList() {
		Response response = RestAssured.get("/contacts");
		ContactDetails[] arrOfCon = new Gson().fromJson(response.asString(),  ContactDetails[].class);
		return arrOfCon;
	}
	
	private Response addContactToJSONServer(ContactDetails addressBookData) {
		String conJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(conJson);
		return request.post("/contacts");
	}
	
	private Response updateContactToJSONServer(ContactDetails addressBookData) {
		String conJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(conJson);
		return request.put("/contacts/" + addressBookData.firstName);
	}
	
	private Response deleteContactFromJSONServer(ContactDetails addressBookData) {
		String conJson = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(conJson);
		return request.delete("/contacts/" + addressBookData.firstName);
	}
	
	@Test
	public void givenContactsDataInJsonServer_WhenRetrived_ShouldMatchCount() {
		ContactDetails[] arrOfCon = getContactList();
		AddressBookService addBookService;
		addBookService = new AddressBookService(Arrays.asList(arrOfCon));
		long entries = addBookService.countEntries(AddressBookService.IOService.REST_IO);
		assertEquals(3, entries);
	}
	
	@Test
	public void givenNewContact_WhenAdded_ShouldReturn201ResponseAndCount() {
		ContactDetails[] arrOfCon = getContactList();
		AddressBookService addBookService;
		addBookService = new AddressBookService(Arrays.asList(arrOfCon));
		
		ContactDetails addBookData = null;
		addBookData = new ContactDetails("Sreyansh", "Sharma", "NJP", "Siliguri", "West Bengal", "700087", "9875987534", "sreyansh@gmail.com");
		Response response = addContactToJSONServer(addBookData);
		int statusCode = response.getStatusCode();
		assertEquals(201, statusCode);
		
		ContactDetails[] arrOfContacts = getContactList();
		addBookService = new AddressBookService(Arrays.asList(arrOfContacts));
		long entries = addBookService.countEntries(AddressBookService.IOService.REST_IO);
		assertEquals(4, entries);
	}
	
	@Test
	public void givenCity_WhenUpdated_ShouldReturn200Response() {
		ContactDetails[] arrOfCon = getContactList();
		AddressBookService addBookService;
		addBookService = new AddressBookService(Arrays.asList(arrOfCon));
		
		addBookService.updateContactsCity("Sreyansh", "Durgapur", AddressBookService.IOService.REST_IO);
		ContactDetails addBookData = addBookService.getContactsData("Sreyansh");
		
		Response response = updateContactToJSONServer(addBookData);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
	
	@Test
	public void givenContactToDelete_WhenDeleted_ShouldReturn200ResponseAndCount() {
		ContactDetails[] arrOfCon = getContactList();
		AddressBookService addBookService;
		addBookService = new AddressBookService(Arrays.asList(arrOfCon));
		
		ContactDetails addBookData = addBookService.getContactsData("Sreyansh");
		
		Response response = deleteContactFromJSONServer(addBookData);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
		
		addBookService.deleteEmployeeFromPayroll("Sreyansh", AddressBookService.IOService.REST_IO);
		long entries = addBookService.countEntries(AddressBookService.IOService.REST_IO);
		assertEquals(3, entries);
	}
	
	
}
