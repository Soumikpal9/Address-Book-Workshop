package AddressBookWorkshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	private static AddressBookDBService addBookDB;
	private PreparedStatement addBookDataStatement;
	
	public AddressBookDBService() {}
	
	public static AddressBookDBService getInstance() {
		if(addBookDB == null) {
			addBookDB = new AddressBookDBService();
		}
		return addBookDB;
	}
	
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
		String userName = "root";
		String password = "Resurrection@5";
		Connection connection;
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		return connection;
	}
	
	public List<ContactDetails> readData() {
		String sql = "SELECT * FROM addressbook;";
		List<ContactDetails> addBookList = new ArrayList<>();
		try(Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			addBookList = this.getAddressBookData(result);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addBookList;
	}

	private List<ContactDetails> getAddressBookData(ResultSet result) {
		List<ContactDetails> addressBookList = new ArrayList<>();
		try {
			while(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("address");
				String city = result.getString("city");
				String state = result.getString("state");
				String zipcode = result.getString("zipcode");
				String phone = result.getString("phone");
				String email = result.getString("email");
				addressBookList.add(new ContactDetails(firstName, lastName, address, city, state, zipcode, phone, email));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}
}
